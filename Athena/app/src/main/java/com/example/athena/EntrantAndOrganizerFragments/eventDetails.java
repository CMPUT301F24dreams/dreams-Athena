package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.ArrayAdapters.eventViewAdapter;
import com.example.athena.Firebase.eventsDB;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.Models.Event;
import com.example.athena.R;

/**
 * This fragment class represents the details of a specific event within the application.
 */
 public class eventDetails extends Fragment {
    private eventsDB EventsDB = new eventsDB();

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

        eventViewAdapter fragment = new eventViewAdapter(getContext());
        fragment.setEventName(view.findViewById(R.id.eventName));
        fragment.setImageView(view.findViewById(R.id.event_poster));

        Event event = new Event();
        event.addObserver(fragment);

        EventsDB.loadEventData(event, eventID);
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }
}

