package com.example.athena.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import com.example.athena.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoadingScreenActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        //15 second timer and then go to the main activity
        new CountDownTimer(15000, 60000) {

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