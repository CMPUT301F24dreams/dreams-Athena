package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.R;

/**
 * This is a fragment used as a home page for entrants and organizers
 */
public class entrantAndOrganizerHomeFragment extends Fragment {


    public entrantAndOrganizerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.entrants_and_organizer_home_fragment, container, false);
    }
}