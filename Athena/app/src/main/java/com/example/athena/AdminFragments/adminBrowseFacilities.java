package com.example.athena.AdminFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.athena.ArrayAdapters.FacilitiesArrayAdapter;
import com.example.athena.Firebase.FacilitiesDB;
import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.Facility;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 *
 */
public class AdminBrowseFacilities extends Fragment {
    private String deviceID;
    private com.example.athena.Firebase.UserDB userDB;
    private com.example.athena.Firebase.FacilitiesDB facilitiesDB;
    private ArrayList<Facility> facilities;
    private ListView listView;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_facilities_browse, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.deviceID = bundle.getString("deviceID");
        userDB = new UserDB();
        facilitiesDB = new FacilitiesDB();
        facilities = new ArrayList<>();
        listView = view.findViewById(R.id.admin_facility_list);

        Task getAllFacilities  = facilitiesDB.getAllFacilities();
        getAllFacilities.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                FacilitiesArrayAdapter facilitiesArrayAdapter = new FacilitiesArrayAdapter(getContext(), facilities);
                    listView.setAdapter(facilitiesArrayAdapter);
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        String name = documentSnapshot.getString("facilityName");
                        String location = documentSnapshot.getString("facilityLocation");
                        String orgID = documentSnapshot.getString("organizer");
                        String facilityID = documentSnapshot.getString("facilityID");
                        Facility facility = new Facility(name, location, orgID, facilityID);
                        facilities.add(facility);
                    }
                    facilitiesArrayAdapter.notifyDataSetChanged();

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Facility facility = (Facility) parent.getAdapter().getItem(position);

                String facilityID = facility.getFacilityID();
                String facilityName = facility.getFacilityName();
                String facilityLocation = facility.getFacilityLocation();

                bundle.putString("facilityID", facilityID);
                bundle.putString("facilityName", facilityName);
                bundle.putString("facilityLocation", facilityLocation);
                displayChildFragment(new FacilityDetailsAdmin(), bundle);
            }
        });




    }
    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }



}