package com.example.athena.EntrantAndOrganizerActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.GeneralActivities.viewProfileActivity;
import com.example.athena.R;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This will be the home screen for people that are entrants or organizers
 * Using this screen, users and organizer will be able to navigate through all of their responsibilities and privileges by navigating to the respective button or drawer
 */

public class entrantAndOrganizerHomeActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private TextView databaseOutput;
    ImageButton CheckCurrentEventsButton;
    ImageButton NotificationsButton;
    ImageButton ProfilePictureButton;
    ImageButton ScanQRCodeButton;
    ImageButton CreateEventButton;
    ImageButton MoreOptionsButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrants_and_organizer_home);



        /// Assigns Button used for checking currently registered events
        ImageButton CheckCurrentEventsButton = findViewById(R.id.check_events_button);

        /// Assigns button used for checking notifications
        ImageButton NotificationsButton = findViewById(R.id.check_updates_button);

        ///Assigns button used for checking notifications
        ImageButton ProfilePictureButton =  findViewById(R.id.profile_picture_button);

        ///Assigns button used for checking notifications
        ImageButton ScanQRCodeButton = findViewById(R.id.scan_qr_code_button);


        ///Assigns button used using additional features
        ImageButton MoreOptionsButton = findViewById(R.id.more_options_button);



        ///Click listener for the check current events button
        CheckCurrentEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE REGISTRATION PAGE
            }
        });

        NotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATE PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        ///This method is responsible for any clicks of the profile picture button
        ProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
                switchToCorrespondingActivity(getApplicationContext(), viewProfileActivity.class);
            }
        });

        ScanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });


        NotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });


        MoreOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });



    }

    /**
     * This method is responsible for switching to the desired activities throughout the application's navigation
     * It is used with the same logic in other classes, so it will only be documented once
     * @param context the context that the application is currently in
     * @param chosenActivity the activity that the user is currently aiming to switch to
     */
    public void switchToCorrespondingActivity(Context context, Class<?> chosenActivity) {
        Intent activityToSwitchTo = new Intent(context, chosenActivity);
        startActivity(activityToSwitchTo);
        finish();
    }



}

