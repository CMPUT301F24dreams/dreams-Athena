package com.example.athena.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.R;

/**
 * This is the first screen in the application that every user encounters upon their first usage.
 */
public class InitialScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_screen);

        /// Assigns Button used for checking currently registered events
        final ImageButton CheckCurrentEventsButton = findViewById(R.id.check_events_button);

        /// Assigns button used for checking notifications
        final ImageButton NotificationsButton = findViewById(R.id.check_updates_button);

        ///Assigns button used for checking notifications
        final ImageButton ProfilePictureButton =  findViewById(R.id.profile_picture_button);

        ///Assigns button used for checking notifications
        final ImageButton ScanQRCodeButton = findViewById(R.id.scan_qr_code_button);

        ///Assigns button used for checking notifications
        final ImageButton CreateEventButton = findViewById(R.id.create_event_button);

        ///Assigns button used using additional features
        final ImageButton MoreOptionsButton = findViewById(R.id.more_options_button);

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

       ProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        ScanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        CreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        NotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });







    }
}