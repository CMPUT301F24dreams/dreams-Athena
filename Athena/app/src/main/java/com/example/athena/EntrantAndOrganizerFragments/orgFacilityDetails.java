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
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

/**
 * This class is responsible for handling all organizer duties pertaining to their facility
 */
public class orgFacilityDetails extends Fragment {
    private String facilityID;
    private String deviceID;
    private FacilitiesDB facilitiesDB;
    private userDB usersDB;
    private eventsDB EventsDB;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.facility_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        assert bundle != null;
        deviceID = bundle.getString("deviceID");
        usersDB = new userDB();
        facilitiesDB = new FacilitiesDB();
        EventsDB = new eventsDB();
        Task getUser = usersDB.getUser(deviceID);
        Task loadedUser = Tasks.whenAll(getUser);

        //retrieves the facility ID
        facilityID = bundle.getString("facilityID");

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


        ImageButton deleteFacility = view.findViewById(R.id.delete_facility_org_button);

        deleteFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
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
                            Toast.makeText(requireContext(), "Could Not Update Facility Location Name" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                Toast.makeText(requireContext(), "Invalid facility name entered, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method handles the deletion of all facilities
     */
    private void showDeleteDialog() {




        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("ARE YOU SURE YOU WANT DELETE THIS FACILITY?");

        final TextView text = new TextView(requireContext());
        text.setText("\n          All of your events will be permanently deleted.\n                                 click 'YES' to confirm");
        builder.setView(text);

        builder.setPositiveButton("YES", (dialog, which) -> {



            ///Delete the ID from the user, and the facility object from the DB
            facilitiesDB.deleteFacility(facilityID);
            usersDB.deleteOrgFacility(deviceID);

            ///deletes all of the events at a given facility
            Task getEvents = EventsDB.getEventsList();
            getEvents.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task){
                    if (task.isSuccessful()) {
                        for(DocumentSnapshot event: task.getResult().getDocuments()) {
                            String eventName = event.getId();
                            String eventFacility = event.getString("facility");


                            if((event.contains("facility")) & eventFacility.equals(facilityID)) {
                                    EventsDB.deleteEvent(eventName);


                                }
                            }
                            Toast.makeText(getContext(), "The events you have created will be deleted.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Facility Deleted", Toast.LENGTH_SHORT).show();

                        } else{
                                Exception e = task.getException();
                    }

                }

            });
        });
        builder.setNeutralButton("CANCEL", (dialog, which) -> dialog.cancel());

        builder.show();
    }


    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}