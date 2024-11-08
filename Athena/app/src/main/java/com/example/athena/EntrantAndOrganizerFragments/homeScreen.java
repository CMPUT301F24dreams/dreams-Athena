package com.example.athena.EntrantAndOrganizerFragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.athena.AdminFragments.browseAppEvents;
import com.example.athena.AdminFragments.browseAppImages;
import com.example.athena.R;

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

        ///Assigns the create event buttons
        ImageButton createEventButton = view.findViewById(R.id.create_event_button);

        ///Assigns the create events I'm hosting button
        ImageButton eventsImHostingButton = view.findViewById(R.id.events_im_hosting_button);

        displayChildFragment(new myEventsList(), bundle);


        ImageButton viewAppImages = view.findViewById(R.id.adminImageBrowse);
        ImageButton viewAppEvents = view.findViewById(R.id.adminEventsBrowse);


        viewAppImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new browseAppImages(), bundle);

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

        //getChildFragmentManager().beginTransaction() .replace(R.id.content_frame, new viewMyCreatedEventsFragment()) .commit();
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDrawer.setVisibility(View.GONE);
                displayChildFragment(new createEvent(), bundle);
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
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
//                Toast.makeText(getActivity(), "qr", Toast.LENGTH_SHORT).show();
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.content_layout, new qrCodeFragment()); // Replace with your container ID
//                transaction.commit();
                scanCode();
            }
        });

//        CreateEventButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
//            }
//        });

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
