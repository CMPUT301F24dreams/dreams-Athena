package com.example.athena.EntrantAndOrganizerFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.userDB;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import androidx.activity.result.ActivityResultLauncher;

import java.util.HashMap;
import java.util.Map;

public class viewProfileFragment extends Fragment {
    private ProfileScreenBinding binding;
    private userDB userDatabase;
    private String userId = "0";

    // Define ActivityResultLauncher for picking images
    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    binding.profileImage.setImageURI(imageUri);
                    uploadProfileImageToStorage(imageUri);
                }
            }
    );

    public viewProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize the binding object
        binding = ProfileScreenBinding.inflate(inflater, container, false);
        userDatabase= new userDB();
        // Inflate the layout for the fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Navigate back to the main profile screen
        binding.BackButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_layout, new entrantAndOrganizerHomeFragment());
            transaction.commit();
        });

        // Navigate to the notification edit fragment
        binding.EditNotfis.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_layout, new profileNotiEditFragment());
            transaction.commit();
        });

        // Navigate to the profile edit screen
        binding.EditProfileAll.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_layout, new profileScreenEditFragment());
            transaction.commit();
        });

        // Open gallery when EditPicture is clicked
        binding.EditPicture.setOnClickListener(v -> openGallery());

        // Delete the current profile picture when DeletePicture is clicked
        binding.DeletePicture.setOnClickListener(v -> deleteProfilePicture());
    }

    /**
     * Opens the Android gallery to pick an image
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    /**
     * Upload the selected image to Firebase Storage
     */
    private void uploadProfileImageToStorage(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference profilePicRef = storageRef.child("profile_pictures/" + userId + ".jpg");

        profilePicRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> profilePicRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> saveProfileImageUrlToFirestore(uri.toString())))
                .addOnFailureListener(e -> Log.e("ProfilePicUpload", "Upload failed", e));
    }

    /**
     * Save the profile image URL to Firestore
     */
    private void saveProfileImageUrlToFirestore(String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("profilePicUrl", imageUrl);

        db.collection("users").document(userId)
                .update(userUpdates)
                .addOnSuccessListener(aVoid -> Log.d("ProfilePic", "Profile image URL updated successfully!"))
                .addOnFailureListener(e -> Log.e("ProfilePic", "Failed to update profile image URL", e));
    }

    /**
     * Load the profile image from Firestore
     */
    private void loadProfileImageFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String profilePicUrl = documentSnapshot.getString("profilePicUrl");
                    if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
                        Glide.with(this)
                                .load(profilePicUrl)
                                .into(binding.profileImage);
                    } else {
                        generateLetterAvatarIfEmpty();
                    }
                });
    }

    /**
     * Delete the current profile picture and set a default letter avatar
     */
    private void deleteProfilePicture() {
        userDatabase.clearProfilePictureUrl(userId)
                .addOnSuccessListener(aVoid -> generateLetterAvatarIfEmpty())
                .addOnFailureListener(e -> Log.e("ProfilePic", "Failed to delete profile image", e));
    }


     /**
     * Generates a letter-based avatar if profilePicUrl is empty in Firestore
     */
    private void generateLetterAvatarIfEmpty() {
        userDatabase.getUser(userId).addOnSuccessListener(documentSnapshot -> {
            String userName = documentSnapshot.getString("userName");
            if (userName != null) {
                char firstLetter = userName.charAt(0);
                binding.profileImage.setImageDrawable(generateLetterAvatar(firstLetter));
            }
        });
    }

    /*
    *Reference- ChatGPT for the custom avatar
     */

    /**
     * Generates a drawable with the first letter of the user’s name as the avatar
     */
    public Drawable generateLetterAvatar(char firstLetter) {
        int size = 100; // Avatar size
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.BLUE); // Background color

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        Rect textBounds = new Rect();
        paint.getTextBounds(String.valueOf(firstLetter), 0, 1, textBounds);
        int x = size / 2;
        int y = (size / 2) - (textBounds.centerY());

        canvas.drawText(String.valueOf(firstLetter), x, y, paint);

        return new BitmapDrawable(getResources(), bitmap);
    }


}