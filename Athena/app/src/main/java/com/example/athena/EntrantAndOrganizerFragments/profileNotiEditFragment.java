package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;
import com.example.athena.databinding.ProfileScreenNotifEditBinding;


/**
 * This is the fragment responsible for handling the operations of the user when they want to edit their profile
 * notification settings
 */
public class profileNotiEditFragment extends Fragment implements displayFragments{

    // Variables for profile information
    private String name;
    private String number;
    private String email;

    ///Binding for the edit profile notifications page
    ProfileScreenNotifEditBinding binding;
    public profileNotiEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ///Initializer the binding object
        binding = ProfileScreenNotifEditBinding.inflate(inflater, container, false);


        ///Inflates the layout for the fragment
        return binding.getRoot();



    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //when ever the user clicks on the switch get if it is checked then update the user.
        binding.chosenNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.chosenNotif.isChecked();
            }
        });
        binding.notChosenNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.notChosenNotif.isChecked();
            }
        });
        binding.notifsFromOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.notifsFromOthers.isChecked();
            }
        });
        binding.geolocationWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.geolocationWarn.isChecked();
            }
        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNewFragment(new viewProfileFragment());
            }
        });
    }


    @Override
    public void displayChildFragment(Fragment fragment) {
    }

    @Override
    public void switchToNewFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();

    }




}