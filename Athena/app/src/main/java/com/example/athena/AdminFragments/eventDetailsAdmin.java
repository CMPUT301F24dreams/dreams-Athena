/*
 * This fragment displays detailed information about a specific event for the admin, including the event name, image, and a button for deleting the event.
 * The event data is retrieved from the Firebase Firestore database using the event's ID.
 */
package com.example.athena.AdminFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.imageDB;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class eventDetailsAdmin extends Fragment{
    private com.example.athena.Firebase.eventsDB eventsDB;
    private String eventID;
    private Bundle bundle;
    private com.example.athena.Firebase.imageDB imageDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details_admin, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        TextView eventName = view.findViewById(R.id.eventName);
        ImageView image = view.findViewById(R.id.event_poster);
        Button delete = view.findViewById(R.id.deleteEventAdmin);

        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        eventID = bundle.getString("eventID");

        eventsDB = new eventsDB();
        imageDB = new imageDB();

        Task eventDetails = eventsDB.getEvent(eventID);
        eventDetails.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                    eventName.setText(document.getString("eventName"));
                    Glide.with(getContext()).load(document.getString("imageURL")).into(image);
                } else {
                    Exception e = task.getException();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();

            }
        });
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("DELETE EVENT?");

        final TextView text = new TextView(requireContext());
        text.setText("are you sure you want to delete this event?");
        builder.setView(text);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            imageDB.deleteImage(eventID);
            eventsDB.deleteEvent(eventID);
            displayChildFragment(new browseAppEvents(), bundle);
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }
}
