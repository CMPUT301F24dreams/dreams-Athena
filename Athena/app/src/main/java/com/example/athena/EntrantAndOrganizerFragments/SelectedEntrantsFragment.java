package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.athena.Interfaces.DisplayFragments;
import com.example.athena.R;

/**
 * selectedEntrantsFragment is a Fragment that inflates a layout for displaying selected entrants.
 *Outstanding Issues:
 * - The fragment currently only inflates a static layout. Future updates may involve adding
 *   dynamic content, such as populating a list of selected entrants, implementing UI logic
 *   for interacting with the list, or integrating event handling.

 */
public class SelectedEntrantsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.selected_entrants_fragment, container, false);
    }
}