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
import com.example.athena.databinding.OrganizerCreateEventPageBinding;
import com.example.athena.databinding.OrganizerMyEventsPageBinding;
import com.example.athena.databinding.ProfileScreenEditBinding;


public class viewMyCreatedEventsFragment extends Fragment {


    OrganizerMyEventsPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ///Initializer the binding object
        binding = OrganizerMyEventsPageBinding.inflate(inflater, container, false);


        ///Inflates the layout for the fragment
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.returnHomeEntrantAndOrganizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new entrantAndOrganizerHomeFragment());
                transaction.commit();
            }
        });
    }

}