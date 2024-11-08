package com.example.athena.EntrantAndOrganizerFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;

import androidx.activity.result.ActivityResultLauncher;

public class viewProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1; // You can now remove this as we're using the ActivityResultLauncher

    ProfileScreenBinding binding;


    public viewProfileFragment() {
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
        // Navigate back to the main profile screen
        binding.BackButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            entrantAndOrganizerHomeFragment frag = new entrantAndOrganizerHomeFragment();
            frag.setArguments(bundle);
            transaction.replace(R.id.entrant_and_organizer_constraint_layout, frag);
            transaction.commit();
        });

        // Navigate to the notification edit fragment
        binding.EditNotfis.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            profileNotiEditFragment frag = new profileNotiEditFragment();
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

            // Set the new image in the profile image view
            binding.profileImage.setImageURI(imageUri);

            // Save image URI
            saveProfileImageUri(imageUri);
        }
    }

    /**
     * Delete the current profile picture
     */
    private void deleteProfilePicture() {
        //binding.profileImage.setImageResource(R.drawable.SOMETHING);
        // TODO: Daman, this is the default pic from db

        // Will hopefully clear the db of the old pfp
        clearProfileImageUrl();
    }

    /**
     * Save pfp to the db
     */
    private void saveProfileImageUri(Uri imageUri) {
        // TODO: save the imageUri to the db
    }

    /**
     * Delete the old pfp from db
     */
    private void clearProfileImageUrl() {
        // TODO: remove the saved image URL to the db
    }
}