package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.R;


public class eventDetails extends Fragment {



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.event_details, container, false);

            super.onCreate(savedInstanceState);
            ///Inflates the layout for the fragment
            return view;


        }
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ImageButton sampleParticipants = view.findViewById(R.id.sample_participants_button);

        sampleParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Change with your own logic, for now it will just go to the page that shows all of the sampling options
                Fragment organizerOperations = new OrganizerEntrantOperations();
                displayFragment(organizerOperations);
            }
        });
    }
    private void displayFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

}

