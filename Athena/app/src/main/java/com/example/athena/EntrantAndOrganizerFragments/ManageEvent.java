package com.example.athena.EntrantAndOrganizerFragments;

import static android.app.Activity.RESULT_OK;
import static com.example.athena.EntrantAndOrganizerFragments.viewProfileFragment.PICK_IMAGE_REQUEST;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.imageDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Fragment that handles operations for organizing and managing entrants for an event.
 * It provides functionality to choose the number of entrants to send invitations to and view
 * different lists of entrants, such as accepted, declined, pending, and invited.
 */
 public class ManageEvent extends Fragment implements OrgChooseNumDialog.numOfEntListener, displayFragments {


    private String eventID;
    public Event event;
    private String deviceID;
    public eventsDB eventDB;
    public userDB userDB;
    public Bundle bundle;
    public imageDB imageDB;
    @Override

    /**
     * this method will chose a random number of users to send invites to
     * and then updates the db
     */
    public void choseEntrants(int num) {
        event.chooseUsers(num,eventID);
        //update the dataBase:
        ArrayList<String> userIDs;

        userIDs = event.getWaitList().getInvited();
        for(String deviceID: userIDs){
            eventDB.moveUserID("pending","invited",deviceID, eventID);
            userDB.changeEventStatusInvited(eventID,deviceID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manage_event_page, container, false);
        bundle = getArguments();
        assert bundle != null;
        eventID = bundle.getString("eventID");
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        userDB = new userDB();
        eventDB = new eventsDB();
        imageDB = new imageDB();

        //get the event here
        Task getEvent = eventDB.getEvent(eventID);
        Task getAccept = eventDB.getEventUserList(eventID,"accepted");
        Task getDecline = eventDB.getEventUserList(eventID,"declined");
        Task getPen = eventDB.getEventUserList(eventID,"pending");
        Task getInvite = eventDB.getEventUserList(eventID,"invited");
        Task eventsLoaded = Tasks.whenAll( getEvent, getAccept,getDecline,getPen,getInvite);

        eventsLoaded.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> accepted = new ArrayList<>();
                ArrayList<String> declined = new ArrayList<>();
                ArrayList<String> pending = new ArrayList<>();
                ArrayList<String> invited = new ArrayList<>();
                if (task.isSuccessful()) {

                    DocumentSnapshot userEvents = (DocumentSnapshot) getEvent.getResult();

                    String eventName = userEvents.getString("eventName");
                    String imageURL = userEvents.getString("imageURL");

                    ImageView eventPicture = view.findViewById(R.id.EventPicture);
                    if (imageURL != null && !imageURL.isEmpty()) {
                        Glide.with(getContext())  // Use the fragment context
                                .load(imageURL)  // Load the image URL from Firestore
                                .into(eventPicture);  // Load image into the ImageView
                    }
                    Event currentEvent = new Event(eventName, imageURL, userEvents.getId());
                    event = currentEvent;

                    QuerySnapshot acceptList = (QuerySnapshot) getAccept.getResult();
                    for (Iterator<DocumentSnapshot> it = acceptList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                            accepted.add(document.getId());
                    }
                    QuerySnapshot declineList = (QuerySnapshot) getDecline.getResult();
                    for (Iterator<DocumentSnapshot> it = declineList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        declined.add(document.getId());
                    }
                    QuerySnapshot pendList = (QuerySnapshot) getPen.getResult();
                    for (Iterator<DocumentSnapshot> it = pendList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        pending.add(document.getId());
                    }
                    QuerySnapshot inviteList = (QuerySnapshot) getInvite.getResult();
                    for (Iterator<DocumentSnapshot> it = inviteList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        invited.add(document.getId());
                    }

                    event.getWaitList().setAccepted(accepted);
                    event.getWaitList().setWaiting(pending);
                    event.getWaitList().setDeclined(declined);
                    event.getWaitList().setInvited(invited);

                } else {
                    Exception e = task.getException();
                }
            }
        });


        //this is temp
        ImageButton notifyEntrants = view.findViewById(R.id.notify_entrants_button);
        ImageButton viewInvited = view.findViewById(R.id.view_selected_entrants);
        ImageButton viewCanclled = view.findViewById(R.id.viewCanclledBtn);
        ImageButton viewAccepted = view.findViewById(R.id.viewAcceptedBtn);
        ImageButton backButton = view.findViewById(R.id.organizer_return_btn);
        ImageButton editEventPicture = view.findViewById(R.id.editEventPicture);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChildFragment(new viewMyOrgEvents());
            }
        });

        notifyEntrants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        viewInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("eventID", eventID);
                bundle.putString("status", "invited");
                displayChildFragment(new ProfileBrowseOrg());
            }
        });

        viewCanclled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("eventID", eventID);
                bundle.putString("status", "cancelled");
                displayChildFragment(new ProfileBrowseOrg());
            }
        });

        viewAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("eventID", eventID);
                bundle.putString("status", "accepted");
                displayChildFragment(new ProfileBrowseOrg());
            }
        });


        editEventPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    public void showDialog() {
        FragmentManager fm = getParentFragmentManager();
        OrgChooseNumDialog frag = new OrgChooseNumDialog();
        frag.setTargetFragment(this,0);
        frag.show(fm,"ChoseNumEnt");
    }

    private void displayFragment(Fragment fragment){

    }

    public void displayChildFragment(Fragment fragment){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

    @Override
    public void switchToNewFragment(Fragment fragment){
    }

    /**
     * method to open android os gallery
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            eventDB.uploadEventImage(eventID, imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String newImageUrl = task.getResult();
                    ImageView eventPicture = getView().findViewById(R.id.EventPicture);
                    Glide.with(getContext()).load(newImageUrl).into(eventPicture);
                }
            });
        }
    }

}
