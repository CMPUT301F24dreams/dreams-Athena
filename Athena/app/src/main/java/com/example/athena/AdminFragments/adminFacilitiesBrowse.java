package com.example.athena.AdminFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.R;

/**
 *
 */
public class adminFacilitiesBrowse extends Fragment {

    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facility_list_layout, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }
}