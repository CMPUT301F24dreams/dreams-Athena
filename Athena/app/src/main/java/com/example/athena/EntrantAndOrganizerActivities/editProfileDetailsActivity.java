/*

package com.example.athena.EntrantAndOrganizerActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.GeneralActivities.viewProfileActivity;
import com.example.athena.R;


public class editProfileDetailsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen_edit);

        ImageButton BackButton = findViewById(R.id.backButton3);
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
*/