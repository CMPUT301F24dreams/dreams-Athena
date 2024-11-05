package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }
}

