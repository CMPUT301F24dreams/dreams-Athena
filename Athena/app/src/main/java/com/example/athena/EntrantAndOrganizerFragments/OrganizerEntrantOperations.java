package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.athena.Firebase.eventsDB;
import com.example.athena.Models.Event;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.R;


public class OrganizerEntrantOperations extends Fragment implements OrgChooseNumDialog.numOfEntListener,displayFragments {


    private String eventID;
    private Event event;
    private eventsDB eventDB;
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
        event = new Event("Name","https://www.google.com/imgres?q=cat&imgurl=https%3A%2F%2Fmedia.4-paws.org%2Fd%2F2%2F5%2Ff%2Fd25ff020556e4b5eae747c55576f3b50886c0b90%2Fcut%2520cat%2520serhio%252002-1813x1811-720x719.jpg&imgrefurl=https%3A%2F%2Fwww.four-paws.org%2Four-stories%2Fpublications-guides%2Fa-cats-personality&docid=bh-aFWFDaFL8GM&tbnid=1YGJmRFWjzXDeM&vet=12ahUKEwiF-Lmb38mJAxWnFTQIHdAQAVQQM3oECHwQAA..i&w=720&h=719&hcb=2&ved=2ahUKEwiF-Lmb38mJAxWnFTQIHdAQAVQQM3oECHwQAA",eventID);
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

    }
    public void displayChildFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction() .replace(R.id.content_frame, fragment) .commit();
    }

    @Override
    public void switchToNewFragment(Fragment fragment){
    }

}
