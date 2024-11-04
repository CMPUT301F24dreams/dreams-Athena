import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.athena.EntrantAndOrganizerActivities.editNotificationSettingsActivity;
//import com.example.athena.EntrantAndOrganizerActivities.editProfileDetailsActivity;

/*

package com.example.athena.GeneralActivities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.EntrantAndOrganizerActivities.editNotificationSettingsActivity;
import com.example.athena.EntrantAndOrganizerActivities.editProfileDetailsActivity;
//import com.example.athena.EntrantAndOrganizerActivities.entrantAndOrganizerHomeActivity;
import com.example.athena.R;

/**
 * This is the activity where the user is able to view all of the information about their profile, and optionally navigate to change their profile settings

public class viewProfileActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private ImageView profileImage;
    private TextView profileName, profileEmail, profileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        profileImage = findViewById(R.id.profile_image);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.ProfileEmail);
        profileNumber = findViewById(R.id.ProfileNumber);

        ImageButton backButton = findViewById(R.id.BackButton);
        ImageView editProfileButton = findViewById(R.id.EditProfileAll);
        ImageView editNotfisButton = findViewById(R.id.EditNotfis);
        ImageView editPictureButton = findViewById(R.id.EditPicture);
        ImageView deletePictureButton = findViewById(R.id.DeletePicture);
        ImageButton profilePictureButton = findViewById(R.id.profile_picture_button);

        // Navigate to Edit Profile Activity
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCorrespondingActivity(getApplicationContext(), editProfileDetailsActivity.class);
            }
        });

        // Navigate back to Home Activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // In the future, this can check if the user is an admin or not to know which activity to return to
                switchToCorrespondingActivity(getApplicationContext(), entrantAndOrganizerHomeActivity.class);
            }
        });

        // Open Notification Settings Activity
        editNotfisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCorrespondingActivity(getApplicationContext(), editNotificationSettingsActivity.class);
            }
        });

        // Open Gallery to Select a Profile Picture
        editPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        // Open Gallery when Profile Picture Button is Clicked
        profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Delete the Profile Picture
        deletePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProfilePicture();
            }
        });
    }
    // https://stackoverflow.com/questions/77571331/how-to-open-image-gallery-in-android-14-api-34-to-pick-image
    // https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
    // Method to open the gallery to select a profile picture
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    // Handle the result from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profileImage.setImageURI(selectedImage);
        }
    }

    // Method to delete the profile picture and set a default image
    private void deleteProfilePicture() {
        profileImage.setImageResource(R.drawable.profile_placeholder); // Replace with your default image
    }

    // Method to switch activities
    public void switchToCorrespondingActivity(Context context, Class<?> chosenActivity) {
        Intent activityToSwitchTo = new Intent(context, chosenActivity);
        startActivity(activityToSwitchTo);
    }
}*/