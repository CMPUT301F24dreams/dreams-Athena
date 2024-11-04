package com.example.athena.controllers;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.R;


public class UserProfileController extends AppCompatActivity {
    // ?????????????????????????????
    // Where is Main()?
    ImageButton EditPicture;

    public void PfpUpload() {

    }

    public void GetImageFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

}
