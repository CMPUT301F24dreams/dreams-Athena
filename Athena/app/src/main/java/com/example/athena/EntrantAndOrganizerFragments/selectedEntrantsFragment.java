package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.R;


public class selectedEntrantsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.selected_entrants_fragment, container, false);
    }
}