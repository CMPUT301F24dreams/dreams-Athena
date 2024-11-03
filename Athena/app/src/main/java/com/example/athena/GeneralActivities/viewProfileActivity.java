package com.example.athena.GeneralActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.EntrantAndOrganizerActivities.editProfileDetailsActivity;
import com.example.athena.EntrantAndOrganizerActivities.entrantAndOrganizerHomeActivity;
import com.example.athena.R;

/**
 * This is the activity where the user is able to view all of the information about their profile, and optionally navigate to change their profile settings
 */
public class viewProfileActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        ImageButton backButton = findViewById(R.id.BackButton);
        ImageView editProfileButton = findViewById(R.id.EditProfileAll);
        TextView nameField = findViewById(R.id.profileName);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCorrespondingActivity(getApplicationContext(), editProfileDetailsActivity.class);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //in the future, this will check if the user is an admin or not to know which activity to return to
                switchToCorrespondingActivity(getApplicationContext(), entrantAndOrganizerHomeActivity.class);
            }
        });



    }

    public void switchToCorrespondingActivity(Context context, Class<?> chosenActivity) {
        Intent activityToSwitchTo = new Intent(context, chosenActivity);
        startActivity(activityToSwitchTo);
    }
}
