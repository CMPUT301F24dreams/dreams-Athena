package com.example.athena.WaitList;

import com.example.athena.Roles.OrganizerViewEntrant;

import java.util.ArrayList;


/**
 * This class represents waitlists within the application
 */
public class WaitList {
    private ArrayList <OrganizerViewEntrant> Waitlist;

   public WaitList(){
       this.Waitlist = new ArrayList<OrganizerViewEntrant>();
   }
   public void setWaitlist(ArrayList <OrganizerViewEntrant> waitlist){
            this.Waitlist = waitlist;
   }

   public void add(OrganizerViewEntrant newEntrant){
       Waitlist.add(newEntrant);

}
//CREATE METHODS FOR THE WAITLIST


}
