package com.example.athena.User;
import android.media.Image;

import com.example.athena.Event.Event;
import com.example.athena.WaitList.WaitList;

import java.util.ArrayList;


/**
 * This class Serves as a representation of the user within the application
 * it contains all of the variables which directly pertain to the user
 */
public class Entrant {



    //Stores the UserID so that the specific device can be remembered for future logins
    private String EntrantDeviceID;

    //Stores lists of the currently registered events,
    //the cancelled events.
    //and the events the user is currently in a waitlist for
    private ArrayList<Event> WaitlistedEvents;
    private ArrayList<Event> RegisteredEvents;
    private ArrayList<Event> CancelledEvents;

    //Stores the current waitlists that the user is waiting in
    private ArrayList<WaitList> CurrentWaitlists;

    //User Information
    private String name;
    private String EntrantEmail;
    private Integer EntrantPhoneNumber;
    private Image ProfilePicture;

    /**
     *
     * @return The events that the user is waitlisted in
     */
    public String getEntrantDeviceID() {
        return EntrantDeviceID;
    }

    /**
     *
     * @param entrantDeviceID
     *
     */
    public void setEntrantDeviceID(String entrantDeviceID) {
        EntrantDeviceID = entrantDeviceID;
    }

    /**
     *
     * @return The events that the user is waitlisted in
     */
    public ArrayList<Event> getWaitlistedEvents() {
        return WaitlistedEvents;
    }

    public void addWaitlistedEvent(Event event){
        WaitlistedEvents.add(event);
    }
    public ArrayList<Event> getRegisteredEvents() {
        return RegisteredEvents;
    }

    /**
     *
     * @return The user's username
     */
    public String getEntrantName() {
        return name;
    }


    public void setEntrantName(String name) {
        this.name = name;
    }



}
