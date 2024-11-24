package com.example.athena.AdminFragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.Firebase.FacilitiesDB;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.GeneralActivities.MainActivity;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 *
 */
public class facilityDetailsAdmin extends Fragment {
    private eventsDB eventsDB;
    private FacilitiesDB facilitiesDB;
    private userDB usersDB;
    private String deviceID;
    private String facilityID;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facility_details_admin, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        TextView facilityName = view.findViewById(R.id.facility_name_textview_admin);
        Button back = view.findViewById(R.id.back_from_facility_details);
        Button delete = view.findViewById(R.id.delete_facility_button);
        TextView facilityLocation = view.findViewById(R.id.facility_location_textView_admin);

        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        assert bundle!= null;
        deviceID = bundle.getString("deviceID");
        facilityID = bundle.getString("facilityID");
        facilitiesDB = new FacilitiesDB();
        usersDB = new userDB();
        eventsDB = new eventsDB();

        Task eventDetails = facilitiesDB.getFacility(facilityID);
        eventDetails.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                    facilityName.setText(document.getString("facilityName"));
                    facilityLocation.setText(document.getString("facilityLocation"));
                } else {
                    Exception e = task.getException();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChildFragment(new adminBrowseFacilities(), bundle);

            }
        });

    }


    /**
     * This method handles the deletion of all facilities
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("DELETE EVENT?");

        final TextView text = new TextView(requireContext());
        text.setText("Are you sure you want to delete this facility?");
        builder.setView(text);

        builder.setPositiveButton("Confirm", (dialog, which) -> {

            facilitiesDB.deleteFacility(facilityID);
            usersDB.deleteOrgFacility(deviceID);

            Task getEvents = eventsDB.getEventsList();
            getEvents.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task){
                    if (task.isSuccessful()) {
                        for(DocumentSnapshot event: task.getResult().getDocuments()) {
                            String eventName = event.getId();
                            if((event.contains("facility"))){
                                String eventFacility = event.getString("facility");
                                if (eventFacility.equals(facilityID)) {
                                    eventsDB.deleteEvent(eventName);
                                }

                            }
                        }

                    }else{
                        Exception e = task.getException();
                    }

                }
            });

            //TODO: make sure that when a facility is deleted, so are all of the events at the corresponding facility
            displayChildFragment(new adminBrowseFacilities(), bundle);

        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }
}