package com.example.athena.EntrantAndOrganizerActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.GeneralActivities.viewProfileActivity;
import com.example.athena.R;


/**
 * This class is used to edit a User's Notification settings
 */
public class editNotificationSettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen_notif_edit);

        ImageButton BackButton = findViewById(R.id.backButton);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCorrespondingActivity(getApplicationContext(), viewProfileActivity.class);

            }
        });

    }
    public void switchToCorrespondingActivity(Context context, Class<?> chosenActivity) {
        Intent activityToSwitchTo = new Intent(context, chosenActivity);
        startActivity(activityToSwitchTo);
        finish();
    }

}

