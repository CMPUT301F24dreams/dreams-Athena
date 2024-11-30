/*
 * This fragment is responsible for displaying a list of events to the admin in the application.
 * It retrieves event data from Firebase and populates a ListView with event details such as event name, image, and ID.
 */

package com.example.athena.AdminFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.athena.ArrayAdapters.eventArrayAdapter;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class browseAppEvents extends Fragment {
    private String deviceID;
    private com.example.athena.Firebase.userDB userDB;
    private com.example.athena.Firebase.eventsDB eventsDB;
    private ArrayList<Event> events;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_events_view, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.deviceID = bundle.getString("deviceID");
        userDB = new userDB();
        eventsDB = new eventsDB();
        events = new ArrayList<>();

        listView = view.findViewById(R.id.admin_events_list);

        Task getEvents = eventsDB.getEventsList();
        getEvents.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    eventArrayAdapter eventArrayAdapter = new eventArrayAdapter(getContext(), events);
                    listView.setAdapter(eventArrayAdapter);
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String name = documentSnapshot.getString("eventName");
                        String img = documentSnapshot.getString("imageURL");
                        String ID = documentSnapshot.getString("eventID");
                        Event event = new Event(name,img,ID);
                        events.add(event);
                    }
                    eventArrayAdapter.notifyDataSetChanged();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TO - DO
                Event event = (Event) parent.getAdapter().getItem(position);
                String eventID = event.getEventID();
               bundle.putString("eventID", eventID);
               displayChildFragment(new eventDetailsAdmin(), bundle);
            }
        });

    }
    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

}
