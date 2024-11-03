package com.example.athena.RegistrationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.athena.R;

/**
 *
 */
public class signinScreenFragment extends Fragment {

    public signinScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.signin_screen_fragment, container, false);
      ImageButton regButton = view.findViewById(R.id.register_button);
      regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signinScreenFragment_to_signUpFragment);
            }
        });
      return view;
    }
}