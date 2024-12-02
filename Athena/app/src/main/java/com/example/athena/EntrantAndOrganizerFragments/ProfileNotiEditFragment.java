package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.athena.Firebase.UserDB;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;
import com.example.athena.databinding.ProfileScreenNotifEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;


/**
 * This is the fragment responsible for handling the operations of the user when they want to edit their profile
 * notification settings
 */
public class ProfileNotiEditFragment extends Fragment {
    private UserDB usersDB;
    private String deviceID;
    ///Binding for the edit profile notifications page
    ProfileScreenNotifEditBinding binding;
    public ProfileNotiEditFragment() {
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
        usersDB = new UserDB();

        Task getUser = usersDB.getUser(deviceID);
        Task loadedUser = Tasks.whenAll(getUser);
        loadedUser.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    DocumentSnapshot user = (DocumentSnapshot) getUser.getResult();

                    binding.notifyIfChosen.setChecked(user.getBoolean("notifyIfChosen"));
                    binding.notifyIfNotChosen.setChecked(user.getBoolean("notifyIfNotChosen"));
                    binding.geolocationWarn.setChecked(user.getBoolean("geolocationWarn"));
                    binding.notifsFromOthers.setChecked(user.getBoolean("notifsFromOthers"));

                } else {
                    Exception e = task.getException();
                }
            }
        });


        // Listeners for each switch
        // Chosen notifications
        binding.notifyIfChosen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSwitchStateToDb("notifyIfChosen", isChecked);
        });

        // Not chosen notifications
        binding.notifyIfNotChosen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSwitchStateToDb("notifyIfNotChosen", isChecked);
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
                ViewProfileFragment frag = new ViewProfileFragment();
                frag.setArguments(bundle);
                transaction.replace(R.id.entrant_and_organizer_constraint_layout, frag);
                transaction.commit();
            }
        });
    }


    /**
     * Method for saving the switch state to the database
     */
    private void saveSwitchStateToDb(String key, boolean isChecked) {

        usersDB.saveNotifSetting(deviceID,key,isChecked);
    }
}
