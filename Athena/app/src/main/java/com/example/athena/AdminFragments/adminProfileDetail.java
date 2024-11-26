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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Firebase.FacilitiesDB;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.example.athena.databinding.ProfileScreenAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class adminProfileDetail extends Fragment {
    private String userID;
    public userDB usersDB;
    private User user;
    private Bundle bundle;
    private String facilityID;
    private FacilitiesDB facilitiesDB;
    private eventsDB eventDB;
    private String deviceID;
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
        facilityID = bundle.getString("facilityID");
        usersDB = new userDB();
        facilitiesDB = new FacilitiesDB();
        eventDB = new eventsDB();

        Task getUser = usersDB.getUser(userID);
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
                        user = new User(name, email, phone, null);
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
                displayChildFragment(new adminProfileBrowse(), bundle);
            }
        });

        binding.deleteBtnProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });


    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("DELETE PROFILE?");

        final TextView text = new TextView(requireContext());
        text.setText("are you sure you want to delete this profile?");
        builder.setView(text);

        builder.setPositiveButton("Confirm", (dialog, which) -> {

            //TODO: check if the user has any facilities
            // if they do: delete all of their facilities, THEN delete the user
            // this is because deleting the facilities removes all of the user's events
            Task getEvents = eventDB.getEventsList();
            //UNTESTED, NEED TO MAKE SOME CHANGES
            /*getEvents.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task){
                    if (task.isSuccessful()) {
                        for(DocumentSnapshot event: task.getResult().getDocuments()) {
                            String eventName = event.getId();
                            if((event.contains("facility"))){
                                String eventFacility = event.getString("facility");
                                if (eventFacility.equals(facilityID)) {
                                    eventDB.deleteEvent(eventName);
                                }

                            }
                        }

                    }else{
                        Exception e = task.getException();
                    }

                }
            });*/

            ///Delete the user's facility which in turn deletes all of the events they've made:
            ///TODO: add an on compete listener that confirms the user has been deleted with a toast
            usersDB.deleteUser(userID);
            displayChildFragment(new adminProfileBrowse(), bundle);
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
