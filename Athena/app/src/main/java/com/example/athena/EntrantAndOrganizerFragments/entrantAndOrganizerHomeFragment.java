package com.example.athena.EntrantAndOrganizerFragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.GeneralActivities.MainActivity;
import com.example.athena.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


/**
 * This is a fragment used as a home page for entrants and organizers
 */
public class entrantAndOrganizerHomeFragment extends Fragment {

    private FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ent_and_org_home_fragment, container, false);
        Toast.makeText(getActivity(), "main", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /// Assigns Button used for checking currently registered events
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
        NavigationView appDrawer = view.findViewById(R.id.entrant_and_organizer_nav_view);

        ///Assigns the home screen
        ConstraintLayout homeScreen = view.findViewById(R.id.entrant_and_organizer_view);

        ///Assigns the view my events button
        MenuItem viewMyEventsOption = view.findViewById(R.id.view_my_events_option);

        ///Click listener for the check current events button
        checkCurrentEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE REGISTRATION PAGE
            }
        });


        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATE PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        ///This method is responsible for any clicks of the profile picture button
        profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager(); // or getSupportFragmentManager() if in Activity
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new viewProfileFragment());
                transaction.commit();
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

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
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
                appDrawer.setVisibility(View.INVISIBLE);
            }
        });



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
