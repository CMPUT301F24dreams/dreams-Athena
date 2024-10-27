package com.example.athena.WaitList;

import com.example.athena.Roles.User;

import java.util.ArrayList;


/**
 * This class represents waitlists within the application
 */
public class WaitList {
    private ArrayList <User> Waitlist;

    public WaitList(){
       this.Waitlist = new ArrayList<User>();
   }
    public void setWaitlist(ArrayList <User> waitlist){
            this.Waitlist = waitlist;
   }

    public ArrayList<User> getWaitlist() {
        return Waitlist;
    }

    public void add(User newEntrant){
       Waitlist.add(newEntrant);

    }
//CREATE METHODS FOR THE WAITLIST


}
