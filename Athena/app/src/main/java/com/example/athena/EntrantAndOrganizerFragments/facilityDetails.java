package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.Firebase.userDB;
import com.example.athena.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

//TODO: make the homescreen navigate here when you click the manage facility button
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
        Task getUser = usersDB.getUser(deviceID);
        Task loadedUser = Tasks.whenAll(getUser);

        TextView facilityName = view.findViewById(R.id.facility_name_textview);
        facilityName.setText((String) bundle.getString("facilityName"));
        TextView facilityLocation = view.findViewById(R.id.facility_location_textView);
        facilityLocation.setText(bundle.getString("facilityLocation"));

        ImageButton editFacilityName = view.findViewById(R.id.edit_facility_name_button);

        editFacilityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFacilityNameDialog(bundle);
            }
        });

        ImageButton editFacilityLocation = view.findViewById(R.id.edit_facility_location_button);

        editFacilityLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFacilityLocationDialog(bundle);
            }
        });


    }


    //TODO: MAKE THIS CONNECT TO DB, VALIDATE INPUT


    /**
     * method to edit facility Name
     * calls a dialog box with edit texts that allow the user to enter a new facility name
     */
    private void editFacilityNameDialog(Bundle facilityDetailsBundle) {
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
                facilityName.setText(newFacilityName);
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Invalid facility name entered, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editFacilityLocationDialog(Bundle facilityDetailsBundle) {
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
                facilityLocation.setText(newFacilityLocation);
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Invalid facility location name entered, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}