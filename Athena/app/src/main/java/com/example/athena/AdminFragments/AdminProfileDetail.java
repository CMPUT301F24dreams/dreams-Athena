/**
 * Fragment that displays detailed information about a user profile in an admin view.
 * The fragment allows the admin to view user information, such as name, email, and phone number,
 * as well as delete the user's profile if necessary.
 * The user details are fetched from the Firebase Firestore database.
 * This class manages interactions between the UI and the data model, such as retrieving user details,
 * navigating to a previous fragment, and displaying a confirmation dialog for deletion.
 */
package com.example.athena.AdminFragments;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.EventsDB;
import com.example.athena.Firebase.UserDB;
import com.example.athena.Firebase.FacilitiesDB;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class AdminProfileDetail extends Fragment {
    private String userID;
    public UserDB usersDB;
    private User user;
    private Bundle bundle;
    private String selectedUserFacilityID;
    private FacilitiesDB facilitiesDB;
    private EventsDB eventDB;
    private String deviceID;
    private String imageURL;
    /// Binds the fragment to its elements
    ProfileScreenAdminBinding binding;

    /**
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return the layout for the fragment for usage
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ///Initializer the binding object
        binding = ProfileScreenAdminBinding.inflate(inflater, container, false);


        ///Inflates the layout for the fragment
        return binding.getRoot();
    }

    /**
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.In this case this is the profile information screen
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     *                           This method is responsible for the operations that take place once the fragment is inflated
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        assert bundle != null;
        deviceID = bundle.getString("deviceID");
        userID = bundle.getString("userID");
        usersDB = new UserDB();
        facilitiesDB = new FacilitiesDB();
        eventDB = new EventsDB();

        Task getUser = usersDB.getUser(userID);

        Task userLoaded = Tasks.whenAll(getUser);
        userLoaded.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userdoc = (DocumentSnapshot) getUser.getResult();
                    if (userdoc.exists()) {
                        if (userdoc.contains("facility")) {
                            selectedUserFacilityID = userdoc.getString("facility");
                        }

                        String name = userdoc.getString("name");
                        String email = userdoc.getString("email");
                        String phone = userdoc.getString("phone");
                        imageURL = userdoc.getString("imageURL");
                        user = new User(name, email, phone, imageURL);

                    }
                    // Check if an image URL exists
                    if (!Objects.equals(imageURL, "NULL") & !Objects.equals(imageURL, "")) {
                        // Load the image using Glide
                        Glide.with(getContext()).load(user.getImageURL()).into(binding.profileImage);

                    } else {
                        // Generate a default profile picture
                        Bitmap defaultImage = generateProfilePicture(user.getName());
                        Glide.with(getContext()).load(defaultImage).into(binding.profileImage);
                    }

                    binding.profileName.setText(String.format("Name: %s", user.getName()));
                    binding.ProfileEmail.setText(String.format("Email: %s", user.getEmail()));
                    binding.ProfileNumber.setText(String.format("Number: %s", user.getPhone()));

                } else {
                    Exception e = task.getException();
                }
            }
        });

        binding.backBtnProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChildFragment(new AdminProfileBrowse(), bundle);
            }
        });

        binding.deleteBtnProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });


    }


    /**
     * Is responsible for all aspects related to deleting a user
     * deletes the user's profile,
     * deletes the user's events,
     * deletes the user's facility.
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("DELETE PROFILE?");

        final TextView text = new TextView(requireContext());
        text.setText("are you sure you want to delete this profile?");
        builder.setView(text);

        builder.setPositiveButton("Confirm", (dialog, which) -> {

            usersDB.deleteUser(userID);

            ///ensures that the selected user owns a facility before trying to delete a facility with the given facility ID
            if(selectedUserFacilityID != null){
                ///if the user has a facility, their facility will
                facilitiesDB.deleteFacility(selectedUserFacilityID);
            }



            Task getEvents = eventDB.getEventsList();

            ///Deletes all of the user's event
            getEvents.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task){
                    if (task.isSuccessful()) {
                        for(DocumentSnapshot event: task.getResult().getDocuments()) {
                            String eventName = event.getId();
                            if((event.contains("facility"))){
                                String eventFacility = event.getString("facility");
                                if (eventFacility.equals(selectedUserFacilityID)) {
                                    eventDB.deleteEvent(eventName);
                                }

                            }
                        }

                    }else{
                        Exception e = task.getException();
                    }

                }
            });

            displayChildFragment(new AdminProfileBrowse(), bundle);
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
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

    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
