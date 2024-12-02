package com.example.athena.RegistrationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

//import com.example.athena.EntrantAndOrganizerActivities.entrantAndOrganizerHomeActivity;
import com.example.athena.EntrantAndOrganizerFragments.HomeScreen;
import com.example.athena.R;
import com.example.athena.databinding.SignUpFragmentBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;
/**
 * Fragment for the user sign-up screen where the user can input their details and register.
 */
public class SignUpFragment extends Fragment {
    private SignUpFragmentBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ///Initializer the binding object
        binding = SignUpFragmentBinding.inflate(inflater, container, false);
        ///Inflates the layout for the fragment
        View signUpScreenLayout = binding.getRoot();

        return signUpScreenLayout;

    }

    /**
     * This is where all of the input validation and database information transfer takes place while the user signs up for the application
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ///Validates user entries. Once the user has entered valid information in  all of the fields:
        ///The information is inputted in the the database
        ///The user is sent the home screen
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve values from EditText fields
                String name = binding.editNameReg.getText().toString().trim();
                String email = binding.editEmailReg.getText().toString().trim();
                String number = binding.editNumberReg.getText().toString().trim();

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

                //Check if the fields match the correct formats
                if (!email.matches("^[a-zA-Z]+@[a-zA-Z]+\\.com$")){
                    binding.editEmailReg.setError("Please enter a valid email");
                    binding.editEmailReg.requestFocus();
                    return;
                }

                //Check if the fields match the correct formats
                if (!name.matches("^[a-zA-Z ]*[a-zA-Z][a-zA-Z ]*$")){
                    binding.editNameReg.setError("Your name can only contain letters");
                    binding.editNameReg.requestFocus();
                    return;
                }



                Bundle mainActivity = getArguments();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                HashMap<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("email", email);
                data.put("phone", number);
                data.put("notifyIfChosen",true);
                data.put("notifyIfNotChosen",true);
                data.put("geolocationWarn", true);
                data.put("notifsFromOthers",true);
                data.put("imageURL","");
                data.put("isAdmin", false);
                assert mainActivity != null;
                db.collection("Users").document(Objects.requireNonNull(mainActivity.getString("deviceID"))).set(data);

                HomeScreen homeScreen = new HomeScreen();

                // If all fields are filled, proceed with action
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                homeScreen.setArguments(mainActivity);
                transaction.replace(R.id.content_layout, homeScreen);
                transaction.commit();
            }
        });
    }
}


