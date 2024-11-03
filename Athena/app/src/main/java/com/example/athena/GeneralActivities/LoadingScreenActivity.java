package com.example.athena.GeneralActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;

import com.example.athena.EntrantAndOrganizerActivities.entrantAndOrganizerHomeActivity;
import com.example.athena.R;

public class LoadingScreenActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        //15 second timer and then go to the main activity
        //loading screen can later be adjusted so that it switches acivities once all info is retrieved from the database
        new CountDownTimer(15000, 15000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
               switchToCorrespondingActivity(getApplicationContext(), entrantAndOrganizerHomeActivity.class);
            }
        }.start();

    }
    public void switchToCorrespondingActivity(Context context, Class<?> chosenActivity) {
        Intent activityToSwitchTo = new Intent(context, chosenActivity);
        startActivity(activityToSwitchTo);
    }
}