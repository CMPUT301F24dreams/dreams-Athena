package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.athena.Firebase.userDB;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

public class facilityDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facility_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        String deviceID = bundle.getString("deviceID");
        userDB usersDB = new userDB();
        // TODO: Need to add the db stuff
        Task getUser = usersDB.getUser(deviceID);
        Task loadedUser = Tasks.whenAll(getUser);

        ImageView facilityImage = view.findViewById(R.id.facility_image);
        TextView facilityName = view.findViewById(R.id.facility_name_textview);
        TextView facilityLocation = view.findViewById(R.id.facility_location_textview);
        ImageButton editFacilityDetails = view.findViewById(R.id.edit_facility_details_button);

        loadedUser.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    //TODO: fix this so that it retrieves facility details
                }
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
}