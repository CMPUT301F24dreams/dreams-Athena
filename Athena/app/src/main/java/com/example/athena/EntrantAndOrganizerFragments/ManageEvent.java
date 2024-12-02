package com.example.athena.EntrantAndOrganizerFragments;

import static android.app.Activity.RESULT_OK;
import static com.example.athena.EntrantAndOrganizerFragments.ViewProfileFragment.PICK_IMAGE_REQUEST;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.EventsDB;
import com.example.athena.Firebase.ImageDB;
import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Fragment that handles operations for organizing and managing entrants for an event.
 * It provides functionality to choose the number of entrants to send invitations to and view
 * different lists of entrants, such as accepted, declined, pending, and invited.
 */
public class ManageEvent extends Fragment implements OrgChooseNumDialog.numOfEntListener {

    private String eventID;
    public Event event;
    private String deviceID;
    public EventsDB eventDB;
    public UserDB userDB;
    public Bundle bundle;
    public ImageDB imageDB;
    @Override

    /**
     * this method will chose a random number of users to send invites to
     * and then updates the db
     */
    public void choseEntrants(int num) {
        ArrayList<String> invitedUserIDs = event.getWaitList().getInvited();

        int numInvited = invitedUserIDs.size();

        int maxParticipants = event.getMaxParticipants();

        if (num <= maxParticipants - numInvited) {
            event.chooseUsers(num,eventID);
            //update the dataBase:

            invitedUserIDs = event.getWaitList().getInvited();
            ArrayList<String> waitingUserIDs = event.getWaitList().getWaiting();
            // update user's status to invited and notify
            for(String deviceID: invitedUserIDs){
                eventDB.moveUserID("pending","invited",deviceID, eventID);
                userDB.changeEventStatusInvited(eventID,deviceID);
                userDB.resetEventNotifiedStatus(deviceID, eventID);
            }

            // notify waiting users that the lottery happened
            for (String deviceID: waitingUserIDs) {
                userDB.resetEventNotifiedStatus(deviceID, eventID);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Cannot add more than " + (maxParticipants - numInvited) + " participants",
                    Toast.LENGTH_SHORT).show();
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
        userDB = new UserDB();
        eventDB = new EventsDB();
        imageDB = new ImageDB();

        //get the event here
        Task getEvent = eventDB.getEvent(eventID);
        Task getAccept = eventDB.getEventUserList(eventID,"accepted");
        Task getDecline = eventDB.getEventUserList(eventID,"declined");
        Task getPen = eventDB.getEventUserList(eventID,"pending");
        Task getInvite = eventDB.getEventUserList(eventID,"invited");
        Task eventsLoaded = Tasks.whenAll( getEvent, getAccept,getDecline,getPen,getInvite);
        Task eventDetails = eventDB.getEvent(eventID);

        ImageView qrCodeView = view.findViewById(R.id.qrCodeView2);

        eventsLoaded.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> accepted = new ArrayList<>();
                ArrayList<String> declined = new ArrayList<>();
                ArrayList<String> pending = new ArrayList<>();
                ArrayList<String> invited = new ArrayList<>();

                if (eventDetails.isSuccessful()) {
                    DocumentSnapshot document = (DocumentSnapshot) eventDetails.getResult();

                    if(document.contains("geoRequire")) {
                        boolean hasGeoRequire = document.getBoolean("geoRequire");
                        if(hasGeoRequire){
                            ImageView eventMap = view.findViewById(R.id.mapImage);
                            eventMap.setVisibility(view.VISIBLE);
                        }
                    }


                    // QrCode
                    String qrCode = document.getString("qrCode");
                    if (qrCode != null && !qrCode.equals("NULL")) {
                        Glide.with(getContext())
                                .load(qrCode)
                                .into(qrCodeView);
                    } else {
                        qrCodeView.setImageResource(android.R.color.transparent); // Clear the view
                        qrCodeView.setContentDescription("No QR code available");
                    }
                }

                if (task.isSuccessful()) {

                    DocumentSnapshot userEvents = (DocumentSnapshot) getEvent.getResult();

                    String eventName = userEvents.getString("eventName");
                    String imageURL = userEvents.getString("imageURL");
                    int maxParticipants = Math.toIntExact((Long) userEvents.get("maxParticipants"));

                    ImageView eventPicture = view.findViewById(R.id.EventPicture);
                    if (imageURL != null && !imageURL.isEmpty()) {
                        Glide.with(getContext())  // Use the fragment context
                                .load(imageURL)  // Load the image URL from Firestore
                                .into(eventPicture);  // Load image into the ImageView
                    }
                    Event currentEvent = new Event(eventName, imageURL, userEvents.getId(), maxParticipants);
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
        ImageView eventMap = view.findViewById(R.id.mapImage);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChildFragment(new ViewMyOrgEvents());
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


    public void displayChildFragment(Fragment fragment){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
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