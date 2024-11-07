package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.athena.Interfaces.displayFragments;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.athena.R;
import com.example.athena.RegistrationFragments.signUpFragment;
import com.example.athena.databinding.ProfileScreenBinding;
import com.example.athena.databinding.SigninScreenFragmentBinding;


public class viewProfileFragment extends Fragment implements displayFragments{

    ProfileScreenBinding binding;
    public viewProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ///Initializer the binding object
        binding = ProfileScreenBinding.inflate(inflater, container, false);
        ///Inflates the layout for the fragment


        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNewFragment(new entrantAndOrganizerHomeFragment());
            }
        });

        binding.EditNotfis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchToNewFragment(new profileNotiEditFragment());


            }
        });
        binding.EditProfileAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNewFragment(new profileScreenEditFragment());
            }
        });
    }

    @Override
    public void displayChildFragment(Fragment fragment){
    }

    @Override
    public void switchToNewFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();
    }


}