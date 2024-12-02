package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import com.example.athena.Firebase.FacilitiesDB;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.Facility;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class CreateFacility extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_facility, container, false);
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        FacilitiesDB facilitiesDB = new FacilitiesDB();
        UserDB userDB = new UserDB();

        bundle = getArguments();
        assert bundle != null;
        String deviceID = bundle.getString("deviceID");

        EditText facilityNameText = view.findViewById(R.id.facility_name_editText);
        EditText facilityLocationText = view.findViewById(R.id.facility_location_textView);
        Button createFacility = view.findViewById(R.id.createFacilityButton);

        createFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facilityName = facilityNameText.getText().toString();
                String organizer = deviceID;
                String facilityLocation = facilityLocationText.getText().toString();

                // Input validation
                if (!facilityName.matches(".*[a-zA-Z]+.*") || facilityName.length() > 50) {
                    Toast.makeText(requireContext(), "valid facility names contain at least one letter, and are under 50 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!facilityLocation.matches(".*[a-zA-Z]+.*") || facilityLocation.length() > 50) {
                    Toast.makeText(requireContext(), "Facility location must contain at least letter, and be less than 50 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                Facility newFacility = new Facility(facilityName, facilityLocation, organizer);

                Task facilityAdd = facilitiesDB.addFacility(newFacility);

                bundle.putString("facilityName", facilityName);
                bundle.putString("facilityLocation", facilityLocation);


            facilityAdd.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                DocumentReference doc = (DocumentReference) task.getResult();
                String facilityID = doc.getId();

                ///Adds the facility ID to the facility's fields
                facilitiesDB.updateFacilityID(facilityID);
                userDB.updateOrgFacilities(deviceID, facilityID);
                bundle.putString("facilityID", facilityID);

                displayChildFragment(new OrgFacilityDetails(), bundle);
            }
        });

            }
        });

    }


    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}

