package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.athena.Models.Event;
import com.example.athena.R;


public class createEvent extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_event, container, false);

        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;


        }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
            TextView eventName = view.findViewById(R.id.eventNameText);
            TextView eventDate = view.findViewById(R.id.eventDate);
            TextView regStart = view.findViewById(R.id.regDateStart);
            TextView regEnd = view.findViewById(R.id.regDateEnd);
            TextView facility = view.findViewById(R.id.facilityID);
            TextView description = view.findViewById(R.id.description);
            TextView participants = view.findViewById(R.id.participants);
            CheckBox georequire = view.findViewById(R.id.geoRequire);
            Button createEvent = view.findViewById(R.id.createEventButton);
            TextView upload = view.findViewById(R.id.uploadPhoto);

            createEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Event newEvent = new Event(eventName.getText().toString(), description.getText().toString(), facility.getText().toString(), georequire.isChecked(), Integer.parseInt(participants.getText().toString()), upload.getText().toString(), regStart.getText().toString(), regEnd.getText().toString(), eventDate.getText().toString());
                    displayFragment(new eventDetails());
                }
            });
        }
        private void displayFragment(Fragment fragment){
            getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
        }
}

