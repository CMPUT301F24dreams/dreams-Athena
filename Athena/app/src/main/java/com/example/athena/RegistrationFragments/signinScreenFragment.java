package com.example.athena.RegistrationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.athena.RegistrationFragments.navHostFragment;
import com.example.athena.R;
import com.example.athena.databinding.SigninScreenFragmentBinding;

/**
 *
 */
public class signinScreenFragment extends Fragment {

    private SigninScreenFragmentBinding binding;
    public signinScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ///Initializer the binding object
      binding = SigninScreenFragmentBinding.inflate(inflater, container, false);
      ///Inflates the layout for the fragment
      View signInScreenLayout = binding.getRoot();

      ImageButton regButton = signInScreenLayout.findViewById(R.id.register_button);

      return signInScreenLayout;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager(); // or getSupportFragmentManager() if in Activity
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new signUpFragment());
                transaction.commit();
        }
        });
    }







}