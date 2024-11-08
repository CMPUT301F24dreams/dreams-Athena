/*
 * This fragment is responsible for displaying a list of events to the admin in the application.
 * It retrieves event data from Firebase and populates a ListView with event details such as event name, image, and ID.
 */

package com.example.athena.AdminFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.R;


public class browseAppImages extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.admin_view_images, container, false);
    }
}