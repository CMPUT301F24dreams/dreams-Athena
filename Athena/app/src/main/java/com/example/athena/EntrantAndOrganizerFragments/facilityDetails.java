package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
        facilityName.setText("Facility Name:" + (String) bundle.getString("facilityName"));
        TextView facilityLocation = view.findViewById(R.id.facility_location_editText);
        facilityLocation.setText(bundle.getString("facilityLocation"));

        ImageButton editFacilityDetails = view.findViewById(R.id.edit_facility_name_button);

        editFacilityDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       /* loadedUser.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    //TODO: fix this so that it retrieves facility details
                    //TODO: add the logic that makes the image load, sets the event title, and sets the image location

                } else {
                    Exception e = task.getException();
                }
            }
        });*/
    }
    /**
     * Method for editing email
     */
    private void editFacilityNameDialog(Bundle facilityDetailsBundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Facility Name");

        final EditText input = new EditText(requireContext());
        input.setText(facilityDetailsBundle.getString("facilityName"));
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newFacilityName = input.getText().toString();
            TextView facilityName = getView().findViewById(R.id.facility_name_textview);
            facilityName.setText("Email: " + newEmail);
            user.setEmail(newEmail);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}