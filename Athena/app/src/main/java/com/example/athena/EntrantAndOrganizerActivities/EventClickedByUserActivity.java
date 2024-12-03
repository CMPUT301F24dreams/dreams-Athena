package com.example.athena.EntrantAndOrganizerActivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.R;

// Activity that handles the display of the event details when clicked by the user.
public class EventClickedByUserActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_clicked_by_user_page);
    }
}
