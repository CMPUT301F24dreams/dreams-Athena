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

import com.example.athena.ArrayAdapters.eventViewAdapter;
import com.example.athena.Firebase.eventsDB;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;

/**
 * This fragment class represents the details of a specific event within the application.
 */
 public class eventDetails extends Fragment {
    private eventsDB EventsDB = new eventsDB();
    private userDB UserDB = new userDB();

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

        EventsDB.loadEventData(event, eventID);

        Button leaveBtn = view.findViewById(R.id.leaveBtn);

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLeaveDialog(deviceID,eventID, bundle);
            }
        });
    }

    // Code to display a dialog
    private void showLeaveDialog(String deviceID, String eventID,Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Leave event?");
        builder.setMessage("Are you sure you want to leave this event?");

        // Set up buttons
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            EventsDB.removeUser(deviceID,eventID);
            UserDB.leaveEvent(deviceID,eventID);
            displayChildFragment(new myEventsList(),bundle);
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }
}

