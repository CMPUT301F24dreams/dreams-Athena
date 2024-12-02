package com.example.athena.EntrantAndOrganizerFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.ImageDB;
import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Objects;

/**
 * This fragment displays the user's profile, including their name, email, phone, and profile picture.
 * It also provides options to edit the profile, change the profile picture, or navigate to other profile-related sections.
 */
public class ViewProfileFragment extends Fragment {

    public static final int PICK_IMAGE_REQUEST = 1; // You can now remove this as we're using the ActivityResultLauncher
    public UserDB usersDB;
    public ImageDB imageDB;
    private String deviceID;
    private User user;
    public ProfileScreenBinding binding;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize the binding object
        binding = ProfileScreenBinding.inflate(inflater, container, false);

        // Inflate the layout for the fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        deviceID = bundle.getString("deviceID");
        usersDB = new UserDB();
        imageDB = new ImageDB();


        Task getUser = usersDB.getUser(deviceID);
        Task userLoaded = Tasks.whenAll(getUser);
        userLoaded.addOnCompleteListener(new OnCompleteListener() {
            @Override

            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userdoc = (DocumentSnapshot) getUser.getResult();
                    if (userdoc.exists()) {
                        String name = userdoc.getString("name");
                        String email = userdoc.getString("email");
                        String phone = userdoc.getString("phone");
                        String imageURL = userdoc.getString("imageURL");
                        user = new User(name, email, phone, imageURL);

                        // Update UI with user details
                        binding.profileName.setText(String.format("Name: %s", user.getName()));
                        binding.ProfileEmail.setText(String.format("Email: %s", user.getEmail()));
                        binding.ProfileNumber.setText(String.format("Number: %s", user.getPhone()));

                      
                        // Check if an image URL exists
                        if (!Objects.equals(imageURL, "NULL") & !Objects.equals(imageURL, "")) {
                            // Load the image using Glide
                            Glide.with(getContext()).load(user.getImageURL()).into(binding.profileImage);
                        } else {
                            // Generate a default profile picture
                            Bitmap defaultImage = generateProfilePicture(user.getName());
                            Glide.with(getContext()).load(defaultImage).into(binding.profileImage);
                        }
                    }
                } else {
                    // Handle task failure (e.g., show a toast or log the error)
                    Exception e = task.getException();
                }
            }
        });
        // Navigate back to the main profile screen
        binding.BackButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            HomeScreen frag = new HomeScreen();
            frag.setArguments(bundle);
            transaction.replace(R.id.entrant_and_organizer_constraint_layout, frag);
            transaction.commit();
        });

        // Navigate to the notification edit fragment
        binding.EditNotfis.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ProfileNotiEditFragment frag = new ProfileNotiEditFragment();
            frag.setArguments(bundle);
            transaction.replace(R.id.entrant_and_organizer_constraint_layout, frag);
            transaction.commit();
        });

        // Navigate to the profile edit screen
        binding.EditProfileAll.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            profileScreenEditFragment frag = new profileScreenEditFragment();
            frag.setArguments(bundle);
            transaction.replace(R.id.entrant_and_organizer_constraint_layout,frag);
            transaction.commit();
        });

        // Open gallery when EditPicture is clicked
        binding.EditPicture.setOnClickListener(v -> openGallery());

        // Delete the current profile picture when DeletePicture is clicked
        binding.DeletePicture.setOnClickListener(v -> deleteProfilePicture());
    }

    /**
     * method to open android os gallery
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * Stuff for opening gallery
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            binding.profileImage.setImageURI(imageUri);

            // Upload the new image and save URL to the database
            UploadTask upload = imageDB.addImage(deviceID, imageUri);
            usersDB.saveURLToUser(upload, deviceID);
        } else if (data == null) {
            // Handle case where no image was selected
            Bitmap generatedImage = generateProfilePicture(user.getName());
            Glide.with(getContext()).load(generatedImage).into(binding.profileImage);
        }
    }

    private void deleteProfilePicture() {
        usersDB.resetImage(deviceID);
        imageDB.deleteImage(deviceID);

        // Generate a default profile picture
        Bitmap generatedImage = generateProfilePicture(user.getName());
        Glide.with(getContext()).load(generatedImage).into(binding.profileImage);
    }

    private Bitmap generateProfilePicture(String name) {
        int size = 200; // size of the square bitmap
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Draw gray circle
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);
        canvas.drawCircle(size / 2, size / 2, size / 2, circlePaint);

        // Draw the letter
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(size / 2);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        String letter = name != null && !name.isEmpty() ? String.valueOf(name.charAt(0)).toUpperCase() : "?";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(letter, 0, letter.length(), textBounds);
        float textX = size / 2;
        float textY = size / 2 - textBounds.exactCenterY();
        canvas.drawText(letter, textX, textY, textPaint);

        return bitmap;
    }

}