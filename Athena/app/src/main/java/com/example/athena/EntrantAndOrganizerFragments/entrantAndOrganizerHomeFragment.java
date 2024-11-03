package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.R;
import com.example.athena.databinding.EntAndOrgHomeFragmentBinding;
import com.example.athena.databinding.ProfileScreenNotifEditBinding;

/**
 * This is a fragment used as a home page for entrants and organizers
 */
public class entrantAndOrganizerHomeFragment extends Fragment {


    ///Binding for the edit profile notifications page
    EntAndOrgHomeFragmentBinding binding;
    public entrantAndOrganizerHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ///Initializer the binding object
        binding = EntAndOrgHomeFragmentBinding.inflate(inflater, container, false);


        ///Inflates the layout for the fragment
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new viewProfileFragment());
                transaction.commit();
            }
        });
    }
}