package com.example.athena.EntrantAndOrganizerFragments;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.athena.AdminFragments.adminFacilitiesBrowse;
import com.example.athena.Firebase.userDB;
import com.example.athena.AdminFragments.adminProfileBrowse;
import com.example.athena.AdminFragments.browseAppEvents;
import com.example.athena.AdminFragments.browseAppImages;
import com.example.athena.R;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


/**
 * This is a fragment used as a home page for entrants and organizers
 */
public class homeScreen extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ent_and_org_home_fragment, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        String deviceID = bundle.getString("deviceID");

        /// Assigns button used for checking notifications
        ImageButton notificationsButton = view.findViewById(R.id.check_updates_button);

        ///Assigns button used for checking notifications
        ImageButton profilePictureButton = view.findViewById(R.id.profile_picture_button);

        ///Assigns button used for checking notifications
        ImageButton scanQRCodeButton = view.findViewById(R.id.scan_qr_code_button);

        ///Assigns button used using additional features
        ImageButton moreOptionsButton = view.findViewById(R.id.more_options_button);

        ///Assigns the app drawer
        LinearLayout appDrawer = view.findViewById(R.id.more_options_drawer);
        appDrawer.setVisibility(View.GONE);

        ///Assigns the home screen
        ConstraintLayout homeScreen = view.findViewById(R.id.entrant_and_organizer_view);

        ///Assigns the close drawer button
        ImageButton closeDrawerButton = view.findViewById(R.id.close_drawer_button);

        ///Assigns the close drawer button
        ImageButton manageFacilityButton = view.findViewById(R.id.manage_faciliity);

        ///Assigns the create event buttons
        ImageButton createEventButton = view.findViewById(R.id.create_event_button);

        ///Assigns the create events I'm hosting button
        ImageButton eventsImHostingButton = view.findViewById(R.id.events_im_hosting_button);

        ///displays the list of events as the initial home screen
        displayChildFragment(new myEventsList(), bundle);

        ///Assigns the button for the admin to view all of the application's images
        ImageButton viewAppImages = view.findViewById(R.id.adminImageBrowse);

        ///Assigns the create button for the admin to view all of the application images
        ImageButton viewAppEvents = view.findViewById(R.id.adminEventsBrowse);

        ///Assigns the button to browse the application's facilities
        ImageButton browseAppFacilities = view.findViewById(R.id.browse_app_facilities_button);


        ///checks if the user has a facilities
        ///if the user does have a facility:
        ///it takes the user to the facility details page
        ///if they do not: it informs them that they do not have a facility with a dialogue box
        ///the dialogue box will tell them they do not have any facilities, and will ask them if they want to create one (the buttons say Yes and cancel, respectively)
        ///if they say the user selects yes, then they will be led to the facility creation page, and if they successfully create one, they will be led to the facility details page (DONE)
        ///else (if they press cancel they will stay on the drawer, and will remain unable to create any events)
        manageFacilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                userDB userDB = new userDB();

                Task<QuerySnapshot> getFacility = userDB.getOrgFacility(deviceID);
                getFacility.addOnCompleteListener(task -> {



                    //Upon a successful read of the facility details, the user will be led to the facility details page
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                // Retrieves the ID of the current facility owned by the organizer so that it can be used later for editing
                                String facilityID = document.getId();
                                Log.d(TAG, "Retrieved string: " + facilityID);
                                bundle.putString("facilityID", facilityID);
                                displayChildFragment(new facilityDetails(), bundle);
                            }



                        //If the read yields no documents, they will be prompted to create a new facility
                        } else { FragmentManager fragmentManager = getChildFragmentManager();
                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);

                            //These statements ensure that if a new facility is currently being created (if the user is currently on the create a facility fragment),
                            //the user will not be able to trigger the "create a facility" dialog
                            //instead, they'll just be reminded that they need a facility before creating an event
                            if (!currentFragment.getClass().getSimpleName().equals("createFacility")) {
                                Toast.makeText(requireContext(), "No facility found, A facility is needed to create event.", Toast.LENGTH_SHORT).show();
                                createFacilityDialog(bundle);

                            }

                            //this is the case that will remind the user instead of creating redundant pop-up
                            else {

                                Toast.makeText(requireContext(), "No Facilities Found.", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



                /*
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new createFacility(), bundle);

                 */
            }

        });

        viewAppImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new browseAppImages(), bundle);

            }
        });

        browseAppFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new adminFacilitiesBrowse(), bundle);

            }
        });

        viewAppEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new browseAppEvents(), bundle);

            }
        });

        eventsImHostingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new viewMyCreatedEventsFragment(), bundle);

            }
        });

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                userDB userDB = new userDB();

                Task<QuerySnapshot> getFacility = userDB.getOrgFacility(deviceID);
                getFacility.addOnCompleteListener(task -> {


                    //Upon a successful read of the facility details, the user will be led to the facility details page
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                // Retrieves the ID of the current facility owned by the organizer so that it can be used later for editing
                                String facilityID = document.getId();
                                Log.d(TAG, "Retrieved string: " + facilityID);
                                bundle.putString("facilityID", facilityID);
                                displayChildFragment(new createEvent(), bundle);
                            }



                            //If the read yields no documents, they will be prompted to create a new facility
                        } else { FragmentManager fragmentManager = getChildFragmentManager();
                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);

                            //These statements ensure that if a new facility is currently being created (if the user is currently on the create a facility fragment),
                            //the user will not be able to trigger the "create a facility" dialog
                            //instead, they'll just be reminded that they need a facility before creating an event
                            if (!currentFragment.getClass().getSimpleName().equals("createFacility")) {
                                Toast.makeText(requireContext(), "No facility found, A facility is needed to create event.", Toast.LENGTH_SHORT).show();
                                createFacilityDialog(bundle);

                            }

                            //this is the case that will remind the user instead of creating redundant pop-up
                            else {

                                Toast.makeText(requireContext(), "No facility found, A facility is needed to create event.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ///Click Listener used to close the app drawer
        closeDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
            }
        });

        ImageButton checkCurrentEventsButton = view.findViewById(R.id.check_events_button);

        checkCurrentEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new myEventsList(), bundle);
            }
        });

        ImageButton adminProfileButton = view.findViewById(R.id.adminProfileBrowse);
        adminProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new adminProfileBrowse(), bundle);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new userViewAttendingEventsFragment(), bundle);
            }
        });

        profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNewFrag(new viewProfileFragment(), bundle);
            }
        });

        scanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "qr", Toast.LENGTH_SHORT).show();
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.content_layout, new qrCodeFragment()); // Replace with your container ID
//                transaction.commit();
                scanCode();
            }
        });
        
        moreOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDrawer.setVisibility(View.VISIBLE);
            }

        });

        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
            }
        });
    }

    private void createFacilityDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("You do not currently have an event facility. Would you like to create one?");


        builder.setPositiveButton("Yes", null); // Set to null to override the default behavior
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            displayChildFragment(new createFacility(), bundle);
            dialog.dismiss();
        });

    }
    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
    public void displayNewFrag(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.entrant_and_organizer_constraint_layout, fragment).commit();
    }
    private void scanCode() {
    ScanOptions options = new ScanOptions();
    options.setPrompt("Volume up to flash on");
    options.setBeepEnabled(true);
    options.setOrientationLocked(true);
    options.setCaptureActivity(CaptureAct.class);
    barLauncher.launch(options);
}


ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result-> {
    if(result.getContents() != null) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(result.getContents());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
});
}
