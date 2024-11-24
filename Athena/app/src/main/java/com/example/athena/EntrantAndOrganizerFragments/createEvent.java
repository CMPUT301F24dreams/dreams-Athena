/**
 * createEvent Fragment class handles the creation of a new event by the user.
 * It allows the user to fill in event details, upload an event poster, and save
 * the event information to Firebase, including an image upload to Firebase Storage.
 */
package com.example.athena.EntrantAndOrganizerFragments;

import static com.example.athena.EntrantAndOrganizerFragments.viewProfileFragment.PICK_IMAGE_REQUEST;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.imageDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.GeneralActivities.MainActivity;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class createEvent extends Fragment {
    Uri imageURI;
    public ImageView imageView;
    public String userFacility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event, container, false);
        super.onCreate(savedInstanceState);

        ///Inflates the layout for the fragment
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        eventsDB eventsDB = new eventsDB();
        userDB userDB = new userDB();
        Bundle bundle = getArguments();
        assert bundle != null;
        String deviceID = bundle.getString("deviceID");

        ///Retrieves the facility from the DB rather than having the user input it
        ///made it this way because every event needs to have the same facilityID as the organizer that owns it
        ///the user cannot have more than one facility, so once they make one that is the only facility that will be used for all of their events
        Task getUser = userDB.getUser(deviceID);
        getUser.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot user : task.getResult().getDocuments()) {
                        if (user.contains("facility")) {
                            userFacility = user.getString("facility");
                        }
                    }
                }
            }
        });


        TextView eventNameText = view.findViewById(R.id.eventName);
        TextView eventDateText = view.findViewById(R.id.eventDate);
        TextView regStartText = view.findViewById(R.id.regDateStart);
        TextView regEndText = view.findViewById(R.id.regDateEnd);
        TextView descriptionText = view.findViewById(R.id.description);
        TextView participantsText = view.findViewById(R.id.participants);
        CheckBox georequireText = view.findViewById(R.id.geoRequire);
        Button createEvent = view.findViewById(R.id.createEventButton);
        TextView upload = view.findViewById(R.id.uploadPoster);
        imageView = view.findViewById(R.id.testView);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameText.getText().toString();
                String organizer = deviceID;
                String eventDescription = descriptionText.getText().toString();
                Integer maxParticipants = Integer.parseInt(participantsText.getText().toString());
                Boolean georequire = georequireText.isChecked();

                Event event = new Event();

                Task eventAdd = eventsDB.addEvent(event);
                eventAdd.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        DocumentReference doc = (DocumentReference) task.getResult();
                        String eventID = doc.getId();

                        eventsDB.updateEventID(eventID);
                        userDB.updateOrgEvents(deviceID, eventID);

                        imageDB imageDB = new imageDB();
                        UploadTask upload = imageDB.addImage(eventID, imageURI);
                        Task changeURL = eventsDB.saveURLToEvent(upload, eventID);
                        changeURL.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Bundle eventIDBundle = new Bundle();
                                eventIDBundle.putString("eventID", eventID);
                                displayChildFragment(new eventDetails(), eventIDBundle);
                            }
                        });
                    }
                });
            }
        });

    upload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openGallery();
        }
    });
}
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        createEvent.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageURI = data.getData();
            imageView.setImageURI(imageURI); // delete
        }
    }
    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}

