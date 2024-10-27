package com.example.athena.Event;

import com.example.athena.Roles.User;
import com.example.athena.WaitList.WaitList;

import java.util.ArrayList;

/**
 * Class representing an event
 */
public class Event {
    private WaitList Waitlist;
    private User Organizer;//must have Organizer privileges

    Event(WaitList waitlist, User organizer){
        this.Waitlist = waitlist;
    }
}
