package com.example.athena.EntrantAndOrganizerFragments;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.athena.Firebase.eventsDB;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * This fragment class represents the details of a specific event within the application.
 */
 public class eventDetails extends Fragment {
    private eventsDB eventsDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        TextView eventName = view.findViewById(R.id.eventName);
        ImageView image = view.findViewById(R.id.event_poster);

        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String eventID = bundle.getString("eventID");

        eventsDB = new eventsDB();

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
    }
    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment).commit();
    }
}

