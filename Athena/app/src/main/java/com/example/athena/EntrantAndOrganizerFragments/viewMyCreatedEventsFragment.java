package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.athena.ArrayAdapters.EventArrayAdapter;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.example.athena.WaitList.UserInviteArrayAdapter;
import com.example.athena.databinding.OrganizerMyEventsViewBinding;

import java.util.ArrayList;


public class viewMyCreatedEventsFragment extends Fragment {

    private ListView eventList;
    private EventArrayAdapter eventAdapter;
    private ArrayList<Event> events;
    OrganizerMyEventsViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.organizer_my_events_view, container, false);
        ///Put database here
        eventList = view.findViewById(R.id.organizer_event_listview);
        events = new ArrayList<>();
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;

    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventAdapter = new EventArrayAdapter(getContext(), events);
        eventList.setAdapter(eventAdapter);

        eventList.setClickable(Boolean.TRUE);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
        //This may or not be useless depending on our decided implementation
        /*
        binding.returnHomeEntrantAndOrganizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new entrantAndOrganizerHomeFragment());
                transaction.commit();
            }
        });
        */
    }

}