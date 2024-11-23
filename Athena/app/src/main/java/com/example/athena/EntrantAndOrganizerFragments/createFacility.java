package com.example.athena.EntrantAndOrganizerFragments;

import android.net.Uri;
import android.os.Bundle;
import com.example.athena.Firebase.FacilitiesDB;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Facility;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class createFacility extends Fragment {
    Uri imageURI;
    public ImageView imageView;
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
        userDB userDB = new userDB();

        Bundle bundle = getArguments();
        assert bundle != null;
        String deviceID = bundle.getString("deviceID");

        EditText facilityNameText = view.findViewById(R.id.facility_name_editText);
        EditText facilityLocationText = view.findViewById(R.id.facility_location_editText);
        Button createFacility = view.findViewById(R.id.createFacilityButton);

        createFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facilityName = facilityNameText.getText().toString();
                String organizer = deviceID;
                String facilityLocation = facilityLocationText.getText().toString();
                //TODO: Input validation

               Facility newfacility = new Facility(facilityName, facilityLocation, organizer);

                Task facilityAdd = facilitiesDB.addFacility(newfacility);

                bundle.putString("facilityName", facilityName);
                bundle.putString("facilityLocation", facilityLocation);


                /*facilityAdd.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        DocumentReference doc = (DocumentReference) task.getResult();
                        String facilityID = doc.getId();

                        facilitiesDB.updateFacilityID(facilityID);
                        userDB.updateOrgFacilities(deviceID, facilityID);;
                        Bundle facilityIDBundle = new Bundle();
                        facilityIDBundle.putString("facilityID", facilityID);
                       displayChildFragment(new facilityDetails(), facilityIDBundle);
                    }
                });*/

                displayChildFragment(new facilityDetails(),bundle);

            }
        });

    }


    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}

