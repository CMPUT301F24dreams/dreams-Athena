package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.ArrayAdapters.eventViewAdapter;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class JoinEventDetails extends Fragment {
    private eventsDB EventsDB = new eventsDB();
    private userDB UserDB = new userDB();
    private boolean eventHasGeolocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String eventID = bundle.getString("eventID");
        String deviceID = bundle.getString("deviceID");
        eventViewAdapter fragment = new eventViewAdapter(getContext());
        fragment.setEventName(view.findViewById(R.id.eventName));
        fragment.setImageView(view.findViewById(R.id.event_poster));

        Event event = new Event();
        event.addObserver(fragment);

        EventsDB.loadQRData(event, eventID,getParentFragmentManager(),bundle,getActivity());

        Button joinBtn = view.findViewById(R.id.leaveBtn);
        joinBtn.setText("Join Waitlist");

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJoinDialog(deviceID,eventID, bundle);
            }
        });
    }

    /// Code to display a dialog

    private void showJoinDialog(String deviceID, String eventID,Bundle bundle) {
        ///Checks if the event requires geolocation, notifies the user if that is the case before they join the waitlist
        Task getEventDetails = EventsDB.getEvent(eventID);
        getEventDetails.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                DocumentSnapshot event = (DocumentSnapshot) task.getResult();
                if(event.get("geoRequire").equals(true)){
                    eventHasGeolocation = true;

                    ///create the alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    ///inform the user that the event has geolocation before they join the waitlist
                    if(eventHasGeolocation) {
                        builder.setTitle("Join event?");
                        builder.setMessage("\n\nWARNING: THE FOLLOWING EVENT USES GEOLOCATION\n\nAre you sure you want to join this event?");
                    }else {
                        builder.setTitle("Join event?");
                        builder.setMessage("Are you sure you want to join this event?");
                    }

                    /// Set up buttons
                    builder.setPositiveButton("Confirm", (dialog, which) -> {
                        EventsDB.addUser(deviceID,eventID);
                        UserDB.joinEvent(deviceID,eventID);
                        displayChildFragment(new myEventsList(),bundle);
                    });
                    builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

                    builder.show();
                }
            }
        });


    }



    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }

}
