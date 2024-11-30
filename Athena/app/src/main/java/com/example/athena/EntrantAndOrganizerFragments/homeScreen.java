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

import com.example.athena.AdminFragments.adminBrowseFacilities;
import com.example.athena.EntrantAndOrganizerFragments.eventCreation.createEvent;
import com.example.athena.Firebase.userDB;
import com.example.athena.AdminFragments.adminProfileBrowse;
import com.example.athena.AdminFragments.browseAppEvents;
import com.example.athena.AdminFragments.browseAppImages;
import com.example.athena.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


/**
 * This is a fragment used as a home page for entrants and organizers
 */
public class homeScreen extends Fragment {
    private String eventID;
    private Bundle bundle;
    private userDB userDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ent_and_org_home_fragment, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        userDB = new userDB();
        String deviceID = bundle.getString("deviceID");

        Task<DocumentSnapshot> getUser = userDB.getUser(deviceID);
        getUser.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot user = (DocumentSnapshot) task.getResult();

                    if (user.contains("isAdmin")) {

                        String isAdmin = user.getBoolean("isAdmin").toString();

                        bundle.putString("isAdmin", isAdmin);


                        if (!user.get("isAdmin").equals(true)) {
                            ///Disable admin privileges
                            ImageButton viewAppImages = view.findViewById(R.id.adminImageBrowse);
                            viewAppImages.setVisibility(ViewGroup.GONE);

                            ImageButton browseAppFacilities = view.findViewById(R.id.browse_app_facilities_button);
                            browseAppFacilities.setVisibility(ViewGroup.GONE);

                            ImageButton browseAppProfiles = view.findViewById(R.id.adminProfileBrowse);
                            browseAppProfiles.setVisibility(ViewGroup.GONE);
                        }
                    }
                }
            }
        });


        ///Assigns the button to check currently waitlisted events
        ImageButton checkCurrentEventsButton = view.findViewById(R.id.check_events_button);

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


        manageFacilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                checkForFacility(deviceID, "manageFacilityButton", bundle);
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
                displayChildFragment(new adminBrowseFacilities(), bundle);

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
                displayChildFragment(new viewMyOrgEvents(), bundle);

            }
        });

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                checkForFacility(deviceID,"createEventButton",bundle);
            }
        });

        ///Click Listener used to close the app drawer
        closeDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
            }
        });



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
                ///Removes the child fragment so that you cannot click any of the buttons that were on the previous page
                removeChildFragment();
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
                scanCode(bundle);
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

    /**
     * This is a very important method, it removes the any nested fragments when the entire screen's view get replaced
     * so that you are not able to interact with invisible views
     */
    public void removeChildFragment(){
        Fragment childFragment = getChildFragmentManager().findFragmentById(R.id.content_frame);
        getChildFragmentManager().beginTransaction().remove(childFragment).commit();
    }


    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result-> {
        if(result.getContents() != null) {
            Log.d("scanQR", ": " + result.getContents().toString());
            displayScan(bundle, result.getContents().toString());
        } else {
            Log.d("QRScan", "No result");
        }
    });

    private void displayScan(Bundle bundle, String eventID){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Successful");
        builder.setPositiveButton("OK", (dialog, which) -> {
            bundle.putString("eventID", eventID);
            displayChildFragment(new JoinEventDetails(), bundle);
        });
        builder.show();
    }

    private void scanCode(Bundle bundle) {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);

        barLauncher.launch(options);
    }

    /**
     * This method checks the user currently has a facility before they try to create an event or manage a facility.
     * It is invoked whenever the create event or manage event buttons are clicked.
     * It is responsive to changes in the DB as it checks the for the User's facility field in the DB when either of the two buttons,
     * (create event button or manage facility button) are clicked.
     * Steps of its logic are as follows:
     * Checks if the user has a facility.
     * If the user does have a facility: it takes the user to the facility details page,
     * If they do not: it informs them that they do not have a facility with a dialogue box the dialogue box will tell them they do not have any facilities, and will ask them if they want to create one (the buttons say Yes and cancel, respectively).
     * If they say the user selects yes, then they will be led to the facility creation page, and if they successfully create one, they will be led to the facility details page.
     * Else (if they press cancel they will stay on the drawer, and will remain unable to create any events).
     * @param deviceID the string value of the current user's deviceID
     * @param buttonClicked the string value of the button led the user to the invocation of this method
     * @param bundle the bundle containing all relevant information for moving between pages and displaying relevant information
     */
    public void checkForFacility(String deviceID, String buttonClicked, Bundle bundle){

        Task<DocumentSnapshot> getUser = userDB.getUser(deviceID);
        getUser.addOnCompleteListener(task -> {


            ///Upon a successful read of the facility details, the user will be led to the facility details page
            if (task.isSuccessful()) {

                ///Directs the user to the correct page to handle their next operations (managing facility or creating an event)
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists() & documentSnapshot.contains("facility")) {
                    String facilityID = documentSnapshot.getString("facility");
                    Log.d(TAG, "Retrieved string: " + facilityID);
                    bundle.putString("facilityID", facilityID);

                    ///checks which button was clicked by the user, sends them to the respective page they requested
                    if(buttonClicked.equals("manageFacilityButton")) {
                        displayChildFragment(new orgFacilityDetails(), bundle);

                    }else{
                        displayChildFragment(new createEvent(), bundle);

                    }

                }

                //If the read yields no documents, they will be prompted to create a new facility
                else {

                    //retrieves the current fragment's details
                    FragmentManager fragmentManager = getChildFragmentManager();
                    Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);


                    ///This is the case for whenever the user clicks create event or manage facility from anywhere in the app that
                    ///isn't the facility creation page
                    //These statements ensure that if a new facility is currently being created (if the user is currently on the create a facility fragment),
                    //the user will not be able to trigger the "create a facility" dialog
                    //instead, they'll just be reminded that they need a facility before creating an event

                    if (!currentFragment.getClass().getSimpleName().equals("createFacility")) {//checks for the current fragment's name


                        if(buttonClicked.equals("manageFacilityButton")) {
                            Toast.makeText(requireContext(), "No facility found.", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(requireContext(), "No facility found, A facility is needed to create event.", Toast.LENGTH_SHORT).show();
                        }
                        createFacilityDialog(bundle);

                    }

                    //this is the case that will remind the user instead of creating redundant pop-up
                    else {

                        if(buttonClicked.equals("manageFacilityButton")){
                            Toast.makeText(requireContext(), "No Facilities Found.", Toast.LENGTH_SHORT).show();
                        }


                        else{
                            Toast.makeText(requireContext(), "No facility found, A facility is needed to create event.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }else {
                Log.d(TAG, "Error getting documents: ", task.getException());
                Toast.makeText(requireContext(), "Error getting documents: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
