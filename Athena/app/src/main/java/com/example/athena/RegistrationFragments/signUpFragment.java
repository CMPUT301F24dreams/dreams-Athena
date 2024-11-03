package com.example.athena.RegistrationFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.athena.EntrantAndOrganizerActivities.entrantAndOrganizerHomeActivity;
import com.example.athena.EntrantAndOrganizerFragments.entrantAndOrganizerHomeFragment;
import com.example.athena.GeneralActivities.MainActivity;
import com.example.athena.GeneralActivities.signUpActivity;
import com.example.athena.R;
import com.example.athena.databinding.SignUpFragmentBinding;
import com.example.athena.databinding.SigninScreenFragmentBinding;

public class signUpFragment extends Fragment {

    private SignUpFragmentBinding binding;

    public signUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ///Initializer the binding object
        binding = SignUpFragmentBinding.inflate(inflater, container, false);
        ///Inflates the layout for the fragment
        View signUpScreenLayout = binding.getRoot();

        ImageButton regButton = signUpScreenLayout.findViewById(R.id.register_button);

        return signUpScreenLayout;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve values from EditText fields
                String name = binding.editNameReg.getText().toString().trim();
                String email = binding.editEmailReg.getText().toString().trim();
                String number = binding.editNumberReg.getText().toString().trim();

                // Debugging Toasts to verify values
                Toast.makeText(getContext(), "Name: " + name, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Email: " + email, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Number: " + number, Toast.LENGTH_SHORT).show();

                // Check if fields are empty
                if (name.isEmpty()) {
                    binding.editNameReg.setError("Name cannot be empty");
                    binding.editNameReg.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    binding.editEmailReg.setError("Email cannot be empty");
                    binding.editEmailReg.requestFocus();
                    return;
                }

                // If all fields are filled, proceed with action
                Toast.makeText(getContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new entrantAndOrganizerHomeFragment());
                transaction.commit();
            }

        });


        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new signinScreenFragment());
                transaction.commit();
            }
        });
    }
}


