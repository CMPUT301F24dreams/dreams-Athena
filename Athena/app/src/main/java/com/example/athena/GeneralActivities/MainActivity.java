package com.example.athena.GeneralActivities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.athena.EntrantAndOrganizerFragments.homeScreen;

import com.example.athena.R;
import com.example.athena.RegistrationFragments.signUpFragment;
import com.example.athena.Services.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
/**
 * MainActivity is the entry point of the application.
 * It determines whether the user is already registered or not by querying the Firestore database.
 * Based on the result, it either displays the home screen (for registered users) or the sign-up screen (for new users).
 */
public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieve user data after checking build version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            db.collection("Users").document(String.valueOf(getDeviceId())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Bundle bundle = new Bundle();
                    bundle.putString("deviceID", String.valueOf(getDeviceId()));
                    if (task.getResult().exists()) {
                        homeScreen homeScreen = new homeScreen();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        homeScreen.setArguments(bundle);
                        transaction.replace(R.id.content_layout, homeScreen); // Replace with your container ID
                        transaction.commit();
                    } else {
                        signUpFragment signUp = new signUpFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        signUp.setArguments(bundle);
                        transaction.replace(R.id.content_layout, signUp); // Replace with your container ID
                        transaction.commit();
                    }
                    
                    // Notification setup
                    requestNotificationPermission();

                    Intent notificationIntent = new Intent(MainActivity.this, NotificationService.class);
                    notificationIntent.putExtra("deviceId", String.valueOf(getDeviceId()));

                    startService(notificationIntent);
                }
            });
        }
    }

    /**
     * Activity launcher for requesting permission from the user for push notifications
     */
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // permission is granted
                } else {
                    // permission is denied, might need to disable notifications somewhere
                    // make a toast that says "notifs are disabled, go to settings to enable them"
                }
            });

    /**
     * Calls the activity launcher in the current cotext
     */
    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, no further code required
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
