package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.ArrayAdapters.EventArrayAdapter;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class myEventsList extends Fragment implements displayFragments{
    private String deviceID;
    private userDB userDB;
    private eventsDB eventsDB;
    private ArrayList<Event> events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_view, container, false);
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

        ListView listView = view.findViewById(R.id.myEventList);

        Task getUserEvents = userDB.getUserEvents(deviceID);
        Task getEventList = eventsDB.getEventsList();

        Task eventsLoaded = Tasks.whenAll(getUserEvents, getEventList);
        eventsLoaded.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                EventArrayAdapter eventAdapter = new EventArrayAdapter(getContext(), events);
                listView.setAdapter(eventAdapter);

                if (task.isSuccessful()) {
                    QuerySnapshot userEvents = (QuerySnapshot) getUserEvents.getResult();
                    List<String> userEventList = new ArrayList<>();

                    for (Iterator<DocumentSnapshot> it = userEvents.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        userEventList.add(document.getId());
                    }

                    QuerySnapshot eventsList = (QuerySnapshot) getEventList.getResult();
                    for (Iterator<DocumentSnapshot> it = eventsList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        if (userEventList.contains(document.getId())) {
                            String eventName = document.getString("eventName");
                            String imageURL = document.getString("imageURL");
                            Event currentEvent = new Event(eventName, imageURL);
                            events.add(currentEvent);
                        }
                    }

                    eventAdapter.notifyDataSetChanged();
                } else {
                    Exception e = task.getException();
                }
            }
        });
    }

    @Override
    public void displayChildFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

    @Override
    public void switchToNewFragment(Fragment fragment){
    }
}

