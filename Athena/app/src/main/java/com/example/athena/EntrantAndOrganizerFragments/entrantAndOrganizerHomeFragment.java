package com.example.athena.EntrantAndOrganizerFragments;

import android.content.DialogInterface;
import android.os.Bundle;

<<<<<<< Updated upstream
import androidx.annotation.NonNull;
=======
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
>>>>>>> Stashed changes
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.EntrantAndOrganizerActivities.entrantAndOrganizerHomeActivity;
import com.example.athena.GeneralActivities.signUpActivity;
import com.example.athena.GeneralActivities.viewProfileActivity;
import com.example.athena.R;
<<<<<<< Updated upstream
import com.example.athena.databinding.EntAndOrgHomeFragmentBinding;
import com.example.athena.databinding.ProfileScreenNotifEditBinding;
=======
import com.google.firebase.firestore.FirebaseFirestore;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;

import com.example.athena.RegistrationFragments.signinScreenFragment;
import com.example.athena.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

>>>>>>> Stashed changes

/**
 * This is a fragment used as a home page for entrants and organizers
 */
public class entrantAndOrganizerHomeFragment extends Fragment {

<<<<<<< Updated upstream

    ///Binding for the edit profile notifications page
    EntAndOrgHomeFragmentBinding binding;
    public entrantAndOrganizerHomeFragment() {
        // Required empty public constructor
    }
=======
    private FirebaseFirestore db;
    private TextView databaseOutput;
    ImageButton CheckCurrentEventsButton;
    ImageButton NotificationsButton;
    ImageButton ProfilePictureButton;
    ImageButton ScanQRCodeButton;
    ImageButton CreateEventButton;
    ImageButton MoreOptionsButton;
>>>>>>> Stashed changes



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< Updated upstream

        ///Initializer the binding object
        binding = EntAndOrgHomeFragmentBinding.inflate(inflater, container, false);


        ///Inflates the layout for the fragment
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new viewProfileFragment());
                transaction.commit();
            }
        });
=======
        View view = inflater.inflate(R.layout.entrants_and_organizer_home_fragment, container, false);
        Toast.makeText(getActivity(), "main", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);

        /// Assigns Button used for checking currently registered events
        ImageButton CheckCurrentEventsButton = view.findViewById(R.id.check_events_button);

        /// Assigns button used for checking notifications
        ImageButton NotificationsButton = view.findViewById(R.id.check_updates_button);

        ///Assigns button used for checking notifications
        ImageButton ProfilePictureButton = view.findViewById(R.id.profile_picture_button);

        ///Assigns button used for checking notifications
        ImageButton ScanQRCodeButton = view.findViewById(R.id.scan_qr_code_button);

        ///Assigns button used using additional features
        ImageButton MoreOptionsButton = view.findViewById(R.id.more_options_button);

        ///Click listener for the check current events button
        CheckCurrentEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE REGISTRATION PAGE
            }
        });

        NotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WILL SWITCH TO THE DESIGNATE PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        ///This method is responsible for any clicks of the profile picture button
        ProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
                Toast.makeText(getActivity(), "prof", Toast.LENGTH_SHORT).show();

            }
        });

        ScanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
//                Toast.makeText(getActivity(), "qr", Toast.LENGTH_SHORT).show();
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.content_layout, new qrCodeFragment()); // Replace with your container ID
//                transaction.commit();
                scanCode();
            }
        });

//        CreateEventButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
//            }
//        });

        NotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });


        MoreOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///WILL SWITCH TO THE DESIGNATED PAGE FOR THE USER'S SPECIFIC ROLE
            }
        });

        // Inflate the layout for this fragment
        return view;

>>>>>>> Stashed changes
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result-> {
        if(result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}
