package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenBinding;
import com.example.athena.databinding.ProfileScreenEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Fragment to handle editing of a user's profile details.
 * This fragment allows the user to modify their profile information (name, email, phone number).
 * After changes are made, the user can save the updated details to the database.
 */
public class profileScreenEditFragment extends Fragment {

    private String deviceID;
    private UserDB usersDB;
    private User user;
    ///Binds the fragment to its elements
    ProfileScreenEditBinding binding;
    public profileScreenEditFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the layout for the fragment for usage
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ///Initializer the binding object
        binding = ProfileScreenEditBinding.inflate(inflater, container, false);


        ///Inflates the layout for the fragment
        return binding.getRoot();
    }

    /**
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.In this case this is the profile information screen
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * This method is responsible for the operations that take place once the fragment is inflated
     *
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        deviceID = bundle.getString("deviceID");
        usersDB = new UserDB();
        Task getUser = usersDB.getUser(deviceID);
        Task userLoaded = Tasks.whenAll(getUser);
        userLoaded.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    DocumentSnapshot userdoc = (DocumentSnapshot) getUser.getResult();
                    if (userdoc.exists()){
                        String name = userdoc.getString("name");
                        String email = userdoc.getString("email");
                        String phone = userdoc.getString("phone");
                        String imageURL = userdoc.getString("imageURL");
                        user = new User(name,email,phone, imageURL);
                    }

                    binding.profileName3.setText(String.format("Name: %s", user.getName()));
                    binding.profileEmail2.setText(String.format("Email: %s", user.getEmail()));
                    binding.profileNumber2.setText(String.format("Number: %s", user.getPhone()));
                } else {
                    Exception e = task.getException();
                }
            }
        });

        ///This is the back button that leads back to the profile view screen, the click listener will return to view profile
        ///and replace the current view with that of the profile screen
        binding.backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager(); // or getSupportFragmentManager() if in Activity
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                ViewProfileFragment frag = new ViewProfileFragment();
                frag.setArguments(bundle);
                transaction.replace(R.id.entrant_and_organizer_constraint_layout,frag);
                transaction.commit();
            }
        });

        // Edit Name button functionality
        binding.EditName.setOnClickListener(v -> {
            // Open dialog or activity for editing name
            showEditNameDialog();
        });

        // Edit Email button functionality
        binding.EditEmail.setOnClickListener(v -> {
            // Open dialog or activity for editing email
            showEditEmailDialog();
        });

        // Edit Number button functionality
        binding.EditNumber.setOnClickListener(v -> {
            // Open dialog or activity for editing number
            showEditNumberDialog();
        });

        // Save Profile button functionality
        binding.saveProfile.setOnClickListener(v -> {
            // Save changes to profile information
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            saveProfileChanges();
        });
    }
    /**
     * Method for editing name
     */

    // Code to display a dialog
    private void showEditNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Name");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = input.getText().toString();
            binding.profileName3.setText("Name: " + newName); // Update the TextView with the new name
            user.setName(newName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Method for editing email
     */
    private void showEditEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Email");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newEmail = input.getText().toString();
            binding.profileEmail2.setText("Email: " + newEmail);
            user.setEmail(newEmail);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Method for editing number
     */
    private void showEditNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Phone Number");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newNumber = input.getText().toString();
            binding.profileNumber2.setText("Number: " + newNumber);
            user.setPhone(newNumber);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveProfileChanges() {
        // TODO: database stuffs
        usersDB.saveUserDetail(user,deviceID);
    }
}