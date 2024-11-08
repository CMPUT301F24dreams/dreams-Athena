package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.Firebase.userDB;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;
import com.example.athena.databinding.ProfileScreenNotifEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This is the fragment responsible for handling the operations of the user when they want to edit their profile
 * notification settings
 */
public class profileNotiEditFragment extends Fragment {
    private userDB usersDB;
    private String deviceID;
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
        Bundle bundle = getArguments();
        assert bundle != null;
        deviceID = bundle.getString("deviceID");
        usersDB = new userDB();
        // TODO: Need to add the db stuff
        Task getUser = usersDB.getUser(deviceID);
        Task loadedUser = Tasks.whenAll(getUser);
        loadedUser.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    DocumentSnapshot user = (DocumentSnapshot) getUser.getResult();

                    binding.chosenNotif.setChecked(user.getBoolean("chosenNotif"));
                    binding.notChosenNotif.setChecked(user.getBoolean("notChosenNotif"));
                    binding.geolocationWarn.setChecked(user.getBoolean("geolocationWarn"));
                    binding.notifsFromOthers.setChecked(user.getBoolean("notifsFromOthers"));

                } else {
                    Exception e = task.getException();
                }
            }
        });


        // Listeners for each switch
        // Chosen notifications
        binding.chosenNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSwitchStateToDb("chosenNotif", isChecked);
        });

        // Not chosen notifications
        binding.notChosenNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSwitchStateToDb("notChosenNotif", isChecked);
        });

        // Geolocation warning notifcations
        binding.geolocationWarn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSwitchStateToDb("geolocationWarn", isChecked);
        });

        // Notifications from organizers and admins
        binding.notifsFromOthers.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSwitchStateToDb("notifsFromOthers", isChecked);
        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getParentFragmentManager(); // or getSupportFragmentManager() if in Activity
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                viewProfileFragment frag = new viewProfileFragment();
                frag.setArguments(bundle);
                transaction.replace(R.id.entrant_and_organizer_constraint_layout, frag);
                transaction.commit();
            }
        });
    }

    /**
     * Method for retrieving the switch state from the database
     */
    private boolean getSwitchStateFromDb(String key) {
        // TODO: Take the switch state from db

        return false; // Default value
    }

    /**
     * Method for saving the switch state to the database
     */
    private void saveSwitchStateToDb(String key, boolean isChecked) {
        // TODO: Save the switch stuff to db
        usersDB.saveNotifSetting(deviceID,key,isChecked);
    }
}
