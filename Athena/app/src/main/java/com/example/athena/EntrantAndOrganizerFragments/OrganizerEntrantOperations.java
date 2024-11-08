package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.athena.ArrayAdapters.EventArrayAdapter;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class OrganizerEntrantOperations extends Fragment implements OrgChooseNumDialog.numOfEntListener,displayFragments {


    private String eventID;
    private Event event;
    private String deviceID;
    private eventsDB eventDB;
    private userDB userDB;
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
        eventDB.changeStatusInvited(eventID,userIDs);
        for(String deviceID: userIDs){
            userDB.changeEventStatusInvited(eventID,deviceID);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.organizer_entrant_operations, container, false);
        Bundle args = getArguments();
        eventID = args.getString("eventID");
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;


    }
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        userDB = new userDB();
        eventDB = new eventsDB();
        ///get the event here

//        Task getUser = userDB.getUser(deviceID);
        Task getEvent = eventDB.getEvent(eventID);
        Task getUserList = eventDB.getEventUserList(eventID);
        Task eventsLoaded = Tasks.whenAll( getEvent, getUserList);
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
                    Event currentEvent = new Event(eventName, imageURL, userEvents.getId());
                    event = currentEvent;

                    QuerySnapshot userList = (QuerySnapshot) getUserList.getResult();
                    for (Iterator<DocumentSnapshot> it = userList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                            String status = document.getString("status");
                        if(Objects.equals(status, "invited")){
                            invited.add(document.getId());
                        }
                        if(Objects.equals(status, "pending")){
                            pending.add(document.getId());
                        }
                        if(Objects.equals(status, "accepted")){
                            accepted.add(document.getId());
                        }
                        if(Objects.equals(status, "declined")){
                            declined.add(document.getId());
                        }
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

        notifyEntrants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Change with your own logic, for now it will just go to the page that shows all of the sampling options

                showDialog();
            }
        });
    }

    private void showDialog() {
        FragmentManager fm = getParentFragmentManager();
        OrgChooseNumDialog frag = new OrgChooseNumDialog();
        frag.setTargetFragment(this,0);
        frag.show(fm,"ChoseNumEnt");
    }

    private void displayFragment(Fragment fragment){

    }
    public void displayChildFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

    @Override
    public void switchToNewFragment(Fragment fragment){
    }

}
