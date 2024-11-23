package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.athena.ArrayAdapters.EventArrayAdapter;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.example.athena.databinding.OrganizerMyEventsViewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This fragment displays the list of events created by the organizer and allows navigation to event details.
 * It fetches events from Firestore and displays them using a custom adapter.
 */
public class viewMyCreatedEventsFragment extends Fragment{

    private ListView eventList;
    private EventArrayAdapter eventAdapter;
    private ArrayList<Event> events;
    private String deviceID;
    public userDB userDB;
    public eventsDB eventsDB;
    OrganizerMyEventsViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.organizer_my_events_view, container, false);
        ///Put database here
        eventList = view.findViewById(R.id.organizer_event_listview);
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;

    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.deviceID = bundle.getString("deviceID");
        userDB = new userDB();
        eventsDB = new eventsDB();
        events = new ArrayList<>();



        Task getOrgEvents = userDB.getOrganizerEvent(deviceID);
        Task getEventList = eventsDB.getEventsList();

        Task eventsLoaded = Tasks.whenAll(getOrgEvents, getEventList);
        eventsLoaded.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                EventArrayAdapter eventAdapter = new EventArrayAdapter(getContext(), events);
                eventList.setAdapter(eventAdapter);

                if (task.isSuccessful()) {
                    QuerySnapshot userEvents = (QuerySnapshot) getOrgEvents.getResult();
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
                            Event currentEvent = new Event(eventName, imageURL, document.getId());
                            events.add(currentEvent);
                        }
                    }

                    eventAdapter.notifyDataSetChanged();
                } else {
                    Exception e = task.getException();
                }
            }
        });

        eventAdapter = new EventArrayAdapter(getContext(), events);
        eventList.setAdapter(eventAdapter);

        eventList.setClickable(Boolean.TRUE);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String eventID = eventAdapter.getItem(position).getEventID();
                ManageEvent detailFrag = new ManageEvent();
                Bundle bundleSend = new Bundle();
                bundleSend.putString("eventID",eventID);
                detailFrag.setArguments(bundleSend);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, detailFrag)
                        .commit();

            }
        });
        //This may or not be useless depending on our decided implementation
        /*
        binding.returnHomeEntrantAndOrganizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new homeScreen());
                transaction.commit();
            }
        });
        */
    }

}