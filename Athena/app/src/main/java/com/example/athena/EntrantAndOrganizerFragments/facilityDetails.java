//input validation logic partly derived from Copilot search "make [a requirement that each text field must] have at least one character that is a letter for both fields". 2024 - 11 -22
package com.example.athena.EntrantAndOrganizerFragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.athena.Firebase.FacilitiesDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

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
        FacilitiesDB facilitiesDB = new FacilitiesDB();
        Task getUser = usersDB.getUser(deviceID);
        Task loadedUser = Tasks.whenAll(getUser);

        //retrieves the facility ID
        String facilityID = bundle.getString("facilityID");

        TextView facilityName = view.findViewById(R.id.facility_name_textview);

        TextView facilityLocation = view.findViewById(R.id.facility_location_textView);

        Task getFacilityDetails = facilitiesDB.getFacility(facilityID);
        Task facilityDetailsLoaded = Tasks.whenAll(getFacilityDetails);

        getFacilityDetails.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    DocumentSnapshot facilityDetails = (DocumentSnapshot) getFacilityDetails.getResult();
                    facilityLocation.setText(facilityDetails.getString("facilityLocation"));
                    facilityName.setText(facilityDetails.getString("facilityName"));
                } else {
                    Exception e = task.getException();
                }
            }
        });



        ImageButton editFacilityName = view.findViewById(R.id.edit_facility_name_button);

        editFacilityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFacilityNameDialog(bundle, facilitiesDB);
            }
        });

        ImageButton editFacilityLocation = view.findViewById(R.id.edit_facility_location_button);

        editFacilityLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFacilityLocationDialog(bundle, facilitiesDB);
            }
        });


    }


    /**
     * Updates the facility  name in the db
     * if the facility name successfully updates in the db, it is also updated in the application
     * if the facility name fails to update, the error is displayed in a toast message
     * @param facilityDetailsBundle: this is the bundle containing all of the facility details
     * @param db: this is the database connection
     */
    private void editFacilityNameDialog(Bundle facilityDetailsBundle, FacilitiesDB db) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Facility Name");

        final EditText input = new EditText(requireContext());
        //Sets the text of the edit text to whatever the current facility name text is
        TextView facilityName = getView().findViewById(R.id.facility_name_textview);
        input.setText(facilityName.getText());

        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        //creates the input filter which will be used for text validation
        input.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(50) });
        builder.setView(input);
        builder.setPositiveButton("Save", null); // Set to null to override the default behavior
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());


        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String newFacilityName = input.getText().toString();
            if (newFacilityName.matches(".*[a-zA-Z]+.*")) {

                HashMap<String, Object> updatedData = new HashMap<>();
                updatedData.put("facilityName", newFacilityName);

                //Updates the facility name in the db
                Task<Void> updateTask = db.updateFacility(facilityDetailsBundle.getString("facilityID"), updatedData);
                updateTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    //if the facility name successfully updates in the db, it is also updated in the application
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            facilityName.setText(newFacilityName);
                            dialog.dismiss();

                        }
                        //if the facility name fails to update, the error is displayed in a toast message
                        else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(requireContext(), "Could Not Update Facility Name" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                Toast.makeText(requireContext(), "Invalid facility name entered, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Updates the facility location name in the db
     * if the facility location name successfully updates in the db, it is also updated in the application
     * if the facility location name fails to update, the error is displayed in a toast message
     * @param facilityDetailsBundle: this is the bundle containing all of the facility details
     * @param db: this is the database connection
     */
    private void editFacilityLocationDialog(Bundle facilityDetailsBundle, FacilitiesDB db) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Facility Location");

        final EditText input = new EditText(requireContext());

        TextView facilityLocation = getView().findViewById(R.id.facility_location_textView);
        input.setText(facilityLocation.getText());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        input.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(50) });
        builder.setView(input);

        builder.setPositiveButton("Save", null); // Set to null to override the default behavior
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String newFacilityLocation = input.getText().toString();
            if (newFacilityLocation.matches(".*[a-zA-Z]+.*")) {

                HashMap<String, Object> updatedData = new HashMap<>();
                updatedData.put("facilityLocation", newFacilityLocation);

                //Updates the facility location name in the db
                Task<Void> updateTask = db.updateFacility(facilityDetailsBundle.getString("facilityID"), updatedData);
                updateTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    //if the facility location name successfully updates in the db, it is also updated in the application
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            facilityLocation.setText(newFacilityLocation);
                            dialog.dismiss();

                        }
                        //if the facility location name fails to update, the error is displayed in a toast message
                        else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(requireContext(), "Could Not Update Facility Name" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                Toast.makeText(requireContext(), "Invalid facility name entered, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}