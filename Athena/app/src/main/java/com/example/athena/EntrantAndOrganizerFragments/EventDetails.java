package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.athena.ArrayAdapters.EventViewAdapter;
import com.example.athena.Firebase.EventsDB;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.Event;
import com.example.athena.R;

/**
 * This fragment class represents the details of a specific event within the application.
 */
public class EventDetails extends Fragment {
    private EventsDB eventsDB = new EventsDB();
    private UserDB userDB = new UserDB();

    /**
     * Inflates the layout for the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String eventID = bundle.getString("eventID");
        String deviceID = bundle.getString("deviceID");

        EventViewAdapter fragment = new EventViewAdapter(getContext());
        fragment.setEventName(view.findViewById(R.id.eventName));
        fragment.setEventDesc(view.findViewById(R.id.event_details_event_desc));
        fragment.setImageView(view.findViewById(R.id.event_poster));
        fragment.setRegStart(view.findViewById(R.id.event_details_reg_open));
        fragment.setRegEnd(view.findViewById(R.id.event_details_reg_close));
        fragment.setEventDesc(view.findViewById(R.id.event_details_event_desc));

        Event event = new Event();
        event.addObserver(fragment);

        eventsDB.loadEventData(event, eventID);

        // Removes the leave waitlist button from event details if coming from the event creation page
        boolean fromCreateEvent = bundle.getBoolean("fromCreateEvent");
        if (fromCreateEvent) {
            Button leaveBtn = view.findViewById(R.id.leaveBtn);
            leaveBtn.setVisibility(View.GONE);
            bundle.remove("fromCreateEvent");
        }

        Button leaveBtn = view.findViewById(R.id.leaveBtn);

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLeaveDialog(deviceID, eventID, bundle);
            }
        });
    }

    /**
     * Displays a dialog to confirm leaving the event.
     *
     * @param deviceID The ID of the device.
     * @param eventID The ID of the event.
     * @param bundle The bundle containing the fragment's arguments.
     */
    private void showLeaveDialog(String deviceID, String eventID, Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Leave event?");
        builder.setMessage("Are you sure you want to leave this event?");

        // Set up buttons
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            eventsDB.removeUser(deviceID, eventID);
            userDB.leaveEvent(deviceID, eventID);
            displayChildFragment(new myEventsList(), bundle);
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Displays a child fragment.
     *
     * @param fragment The fragment to display.
     * @param bundle The bundle containing the fragment's arguments.
     */
    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
