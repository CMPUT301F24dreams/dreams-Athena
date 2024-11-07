package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.athena.Firebase.EventsDBManager;
import com.example.athena.Models.Event;
import com.example.athena.R;


public class OrganizerEntrantOperations extends Fragment implements OrgChooseNumDialog.numOfEntListener {

    private String eventID;
    private Event event;
    private EventsDBManager eventDB;
    @Override

    /**
     * this method will chose a random number of users to send invites to
     * and then updates the db
     */
    public void choseEntrants(int num) {
        event.chooseUsers(num,eventID);
        //update the dataBase:

    }

    /**
     * this creates a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventID the eventID of the chosen.
     * @return A new instance of fragment temp.
     */
    public static OrganizerEntrantOperations newInstance(String eventID) {
        OrganizerEntrantOperations fragment = new OrganizerEntrantOperations();
        Bundle args = new Bundle();
        args.putString("eventID", eventID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.organizer_entrant_operations, container, false);
        Bundle args = getArguments();
        eventID = args.getString("eventID");
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;


    }
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ///get the event here

        //this is temp
        event = new Event("Name");
        ImageButton notifyEntrants = view.findViewById(R.id.notify_entrants_button);

        notifyEntrants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Change with your own logic, for now it will just go to the page that shows all of the sampling options

            }
        });
    }

    private void showDialog(Bundle bundle) {
        FragmentManager fm = getParentFragmentManager();
        userViewNotisChose frag = new userViewNotisChose();
        frag.setArguments(bundle);
        frag.setTargetFragment(this,0);
        frag.show(fm,"ChoseNumEnt");
    }

    private void displayFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

}