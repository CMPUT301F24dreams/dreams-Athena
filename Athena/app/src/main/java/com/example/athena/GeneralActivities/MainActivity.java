package com.example.athena.GeneralActivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;

import com.example.athena.RegistrationFragments.signinScreenFragment;
import com.example.athena.R;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        //TODO make this check if the user is an admin or entrant/organizer

        //15 second timer and then go to the main activity
        //loading screen can later be adjusted so that it switches activities once all info is retrieved from the database
        new CountDownTimer(15000, 15000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new signinScreenFragment()); // Replace with your container ID
                transaction.commit();
            }
        }.start();

    }

}