package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.ArrayAdapters.EventViewAdapter;
import com.example.athena.Firebase.EventsDB;
import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Fragment that handles users joining a waitlist for a event and displays event details
 */
public class JoinEventDetails extends Fragment {
    private EventsDB eventsDB = new EventsDB();
    private UserDB userDB = new UserDB();
    private boolean eventHasGeolocation;
    private boolean warnAboutGeolocation;

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
        warnAboutGeolocation = bundle.getBoolean("geolocationWarn");
        EventViewAdapter fragment = new EventViewAdapter(getContext());
        fragment.setEventName(view.findViewById(R.id.eventName));
        fragment.setEventDesc(view.findViewById(R.id.event_details_event_desc));
        fragment.setImageView(view.findViewById(R.id.event_poster));
        fragment.setRegStart(view.findViewById(R.id.event_details_reg_open));
        fragment.setRegEnd(view.findViewById(R.id.event_details_reg_close));
        fragment.setEventDesc(view.findViewById(R.id.event_details_event_desc));


        Event event = new Event();
        event.addObserver(fragment);

        eventsDB.loadQRData(event, eventID,getParentFragmentManager(),bundle,getActivity());

        Button joinBtn = view.findViewById(R.id.leaveBtn);
        joinBtn.setText("Join Waitlist");

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validDate = eventsDB.checkEventDateValid(eventID);
                if (validDate) {
                    showJoinDialog(deviceID,eventID, bundle);
                } else {
                    // show message that registration deadline has passed
                    Toast.makeText(getActivity(), "Registration deadline has passed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Code to display a dialog

    /**
     * displays a popup dialog for the user to confirm to join the event
     * @param deviceID the users deviceID
     * @param eventID the ID of the event the user is joining
     * @param bundle bundle with information need to send to another fragment
     */
    private void showJoinDialog(String deviceID, String eventID,Bundle bundle) {



        Task getUserGeoWarnStatus = userDB.getUser(deviceID);
        getUserGeoWarnStatus.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override

            ///This segment of code is responsible for fetching the user's current geolocation warning status
            ///When it completes the task, it gets the event details and checks if the event requires geolocation.
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot user = (DocumentSnapshot) task.getResult();
                    if (user.contains("geolocationWarn")) {
                        warnAboutGeolocation = user.getBoolean("geolocationWarn");
                        }else{
                        Toast.makeText(getContext(),"Error Fetching Documents: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    ///After getting the user's geolocation warning status, this task checks the event's geolocation requirement
                    ///If the user currently has the geolocation warning set to true AND the event requires geolocation, they will be notified before they join the waitlist
                    ///Otherwise they will simply asked to confirm that they want to join the waitlist

                    Task getEventDetails = eventsDB.getEvent(eventID);
                    getEventDetails.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            DocumentSnapshot event = (DocumentSnapshot) task.getResult();
                            if (event.get("geoRequire").equals(true)) {
                                eventHasGeolocation = true;

                                ///create the alert dialog
                                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                ///inform the user that the event has geolocation before they join the waitlist
                                if (eventHasGeolocation & warnAboutGeolocation) {
                                    builder.setTitle("Join Waitlist?");
                                    builder.setMessage("\n\nWARNING: THE FOLLOWING EVENT USES GEOLOCATION\n\nAre you sure you want to join this waitlist?");
                                } else {
                                    builder.setTitle("Join Waitlist?");
                                    builder.setMessage("Are you sure you want to join this waitlist?");
                                }

                                // Set up buttons
                                builder.setPositiveButton("Confirm", (dialog, which) -> {
                                    eventsDB.addUser(deviceID,eventID);
                                    userDB.joinEvent(deviceID,eventID);
                                    displayChildFragment(new myEventsList(),bundle);
                                });
                                builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

                                builder.show();
                            }
                        }
                    });


                }
            }
            });


    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }

}
