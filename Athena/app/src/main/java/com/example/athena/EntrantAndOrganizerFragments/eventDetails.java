package com.example.athena.EntrantAndOrganizerFragments;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.athena.ArrayAdapters.EventViewAdapter;
import com.example.athena.Firebase.eventsDB;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

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

        EventViewAdapter fragment = new EventViewAdapter(getContext());
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

