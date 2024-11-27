/**
 * createEvent Fragment class handles the creation of a new event by the user.
 * It allows the user to fill in event details, upload an event poster, and save
 * the event information to Firebase, including an image upload to Firebase Storage.
 */
package com.example.athena.EntrantAndOrganizerFragments.eventCreation;

import static com.example.athena.EntrantAndOrganizerFragments.viewProfileFragment.PICK_IMAGE_REQUEST;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.athena.EntrantAndOrganizerFragments.eventDetails;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.imageDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;


public class createEvent extends Fragment implements dateDialog.datePickerListener {
    private Event event;
    private String userFacility;

    dateDialog datePicker;
    private TextView eventDateText;
    private TextView regStartText;
    private TextView regEndText;

    Uri imageURI;

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

        TextView eventNameText = view.findViewById(R.id.eventName);
        datePicker = dateDialog.newInstance(this);
        eventDateText = view.findViewById(R.id.eventDate);
        regStartText = view.findViewById(R.id.regDateStart);
        regEndText = view.findViewById(R.id.regDateEnd);
        TextView descriptionText = view.findViewById(R.id.description);
        TextView participantsText = view.findViewById(R.id.participants);
        CheckBox geoRequireBox = view.findViewById(R.id.geoRequire);
        TextView upload = view.findViewById(R.id.uploadPoster);

        event = new Event();

        Button createEvent = view.findViewById(R.id.createEventButton);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                userDB.loadUserData(user, deviceID);


                //TODO (Roger): whenever you update your logic, update the event creation so that a facility is always initialized from the user database
                // this is to make sure that events always have the same facilityID as their organizer (one-to-one association)
                ///Retrieves the facility from the DB rather than having the user input it
                ///made it this way because every event needs to have the same facilityID as the organizer that owns it
                ///the user cannot have more than one facility, so once they make one that is the only facility that will be used for all of their events

                Event event = new Event(userFacility);

                Task eventAddTask = eventsDB.addEvent(event, deviceID, imageURI);
                eventAddTask.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            DocumentReference doc = (DocumentReference) task.getResult();
                            String eventID = doc.getId();
                            Bundle eventIDBundle = new Bundle();
                            eventIDBundle.putString("eventID", eventID);
                            displayChildFragment(new eventDetails(), eventIDBundle);
                        }
                    }
                });
            }
        });

    eventNameText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            event.setEventName(s.toString());
        }
    });

    descriptionText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            event.setEventDescription(s.toString());
        }
    });

    participantsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                event.setMaxParticipants(Integer.valueOf(s.toString()));
            }
        });

    geoRequireBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            event.setGeoRequire(isChecked);
        }
    });

    eventDateText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.setTextField("eventDate");
            datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    });

    regStartText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.setTextField("regStart");
            datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    });

    regEndText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.setTextField("regEnd");
            datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
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
        }
    }

    @Override
    public void onDateSet(String textField, String date) {
        if (Objects.equals(textField, "eventDate")) {
            event.setEventDate(date);
            eventDateText.setText(date);
        } else if (Objects.equals(textField, "regStart")) {
            event.setStartReg(date);
            regStartText.setText(date);
        } else {
            event.setEndReg(date);
            regEndText.setText(date);
        }
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}

