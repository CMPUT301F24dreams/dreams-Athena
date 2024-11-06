package com.example.athena.GeneralActivities;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.athena.EntrantAndOrganizerFragments.entrantAndOrganizerHomeFragment;

import com.example.athena.R;
import com.example.athena.RegistrationFragments.signUpFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            db.collection("Users").document(String.valueOf(getDeviceId())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Bundle bundle = new Bundle();
                    bundle.putString("deviceID", String.valueOf(getDeviceId()));
                    if (task.getResult().exists()) {
                        entrantAndOrganizerHomeFragment homeScreen = new entrantAndOrganizerHomeFragment();
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
                }
            });
        }
    }
}