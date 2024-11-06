package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.athena.R;


public class OrganizerEntrantOperations extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.organizer_entrant_operations, container, false);

        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;


    }
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ImageButton viewSampledEntrants = view.findViewById(R.id.view_sampled_entrants_button);

        viewSampledEntrants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Change with your own logic, for now it will just go to the page that shows all of the sampling options
                Fragment selectedEntrants = new selectedEntrantsFragment();
                displayFragment(selectedEntrants);
            }
        });
    }
    private void displayFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

}