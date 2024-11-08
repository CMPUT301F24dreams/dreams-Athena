package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.athena.Firebase.eventsDB;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class createEvent extends Fragment {
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

        Bundle bundle = getArguments();
        assert bundle != null;
        String deviceID = bundle.getString("deviceID");

        TextView eventNameText = view.findViewById(R.id.eventNameText);
        TextView eventDateText = view.findViewById(R.id.eventDate);
        TextView regStartText = view.findViewById(R.id.regDateStart);
        TextView regEndText = view.findViewById(R.id.regDateEnd);
        TextView facilityText = view.findViewById(R.id.facilityID);
        TextView descriptionText = view.findViewById(R.id.description);
        TextView participantsText = view.findViewById(R.id.participants);
        CheckBox georequireText = view.findViewById(R.id.geoRequire);
        Button createEvent = view.findViewById(R.id.createEventButton);
        TextView upload = view.findViewById(R.id.uploadPhoto);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameText.getText().toString();
                String imageURL = upload.getText().toString();
                String organizer = deviceID;
                Log.d("DATA", organizer);
                String eventDescription = descriptionText.getText().toString();
                String facility = facilityText.getText().toString();
                Integer maxParticipants = Integer.parseInt(participantsText.getText().toString());
                Boolean georequire = georequireText.isChecked();

                Event event = new Event(eventName, imageURL, eventDescription, organizer, facility, maxParticipants, georequire, "");
                Task eventAdd = eventsDB.addEvent(event);
                eventAdd.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        DocumentReference doc = (DocumentReference) task.getResult();
                        String eventID = doc.getId();

                        eventsDB.updateEventID(eventID);

                        Bundle eventIDBundle = new Bundle();
                        eventIDBundle.putString("eventID", eventID);
                        displayChildFragment(new eventDetails(), eventIDBundle);
                    }
                });
            }
        });
}


    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}

