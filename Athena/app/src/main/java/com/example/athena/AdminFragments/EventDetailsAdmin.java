/*
 * This fragment displays detailed information about a specific event for the admin, including the event name, image, and a button for deleting the event.
 * The event data is retrieved from the Firebase Firestore database using the event's ID.
 */
package com.example.athena.AdminFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.EventsDB;
import com.example.athena.Firebase.ImageDB;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventDetailsAdmin extends Fragment{
    private com.example.athena.Firebase.EventsDB eventsDB;
    private String eventID;
    private Bundle bundle;
    private com.example.athena.Firebase.ImageDB imageDB;
    private String isAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details_admin, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        TextView eventName = view.findViewById(R.id.eventName);
        ImageView image = view.findViewById(R.id.event_poster);
        ImageView qrCodeView = view.findViewById(R.id.qrCodeView);
        Button delete = view.findViewById(R.id.deleteEventAdmin);
        Button deleteQrCodeButton = view.findViewById(R.id.deleteQrCodeButton);

        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        eventID = bundle.getString("eventID");
        eventsDB = new EventsDB();
        imageDB = new ImageDB();


        ///If the current user is an administrator:
        if(bundle.containsKey("isAdmin")) {

            isAdmin = bundle.getString("isAdmin");

            ///Hide the deletion buttons from non-admin users
            if (!isAdmin.equals("true")) {
                delete = view.findViewById(R.id.deleteEventAdmin);
                deleteQrCodeButton = view.findViewById(R.id.deleteQrCodeButton);
                delete.setVisibility(View.INVISIBLE);
                deleteQrCodeButton.setVisibility(View.INVISIBLE);

            }
        }

        Task eventDetails = eventsDB.getEvent(eventID);
        eventDetails.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                    eventName.setText(document.getString("eventName"));
                    Glide.with(getContext()).load(document.getString("imageURL")).into(image);

                    // QrCode
                    String qrCode = document.getString("qrCode");
                    if (qrCode != null && !qrCode.equals("NULL")) {
                        Glide.with(getContext())
                                .load(qrCode)
                                .into(qrCodeView);
                    } else {
                        qrCodeView.setImageResource(android.R.color.transparent); // Clear the view
                        qrCodeView.setContentDescription("No QR code available");
                    }
                } else {
                    Exception e = task.getException();
                }

            }
        });

        // Delete event functionality
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        deleteQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQrDeleteDialog(qrCodeView);
            }
        });

    }

    //qr code deletion
    private void showQrDeleteDialog(ImageView qrCodeView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        new AlertDialog.Builder(requireContext())
                .setTitle("DELETE QR CODE?")
                .setMessage("Are you sure you want to delete the QR code? This action cannot be undone.")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Delete the 'qrCode' field from Firestore
                    db.collection("Events").document(eventID)
                            .update("qrCode", FieldValue.delete()) // Use FieldValue.delete()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Update the UI to reflect deletion
                                    qrCodeView.setImageResource(android.R.color.transparent); // Clear image
                                    qrCodeView.setContentDescription("No QR code available"); // Set contentDescription

                                    Toast.makeText(requireContext(), "QR Code deleted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle failure
                                    Exception e = task.getException();
                                    Toast.makeText(requireContext(), "Failed to delete QR Code: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e("FirestoreError", "Error deleting QR Code", e);
                                }
                            });
                })
                .setNeutralButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }


    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("DELETE EVENT?");

        final TextView text = new TextView(requireContext());
        text.setText("Are you sure you want to delete this event?");
        builder.setView(text);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            imageDB.deleteImage(eventID);
            eventsDB.deleteEvent(eventID);
            displayChildFragment(new BrowseAppEvents(), bundle);
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
