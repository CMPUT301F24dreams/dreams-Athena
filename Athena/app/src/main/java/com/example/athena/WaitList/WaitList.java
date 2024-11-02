package com.example.athena.WaitList;

import com.example.athena.Event.Event;
import com.example.athena.Roles.User;


import java.util.ArrayList;

/**
 * This class is responsible for storing information about waitlists for corresponding events
 * It has methods which are used to retrieve lists, as well as handle changes made to the waitlists
 */
public class WaitList{
    private ArrayList<User> waiting;
    private ArrayList<User> invited;
    private ArrayList<User> declined;
    private Event attachedEvent;

    ///This is a constructor for the waitlist class, it initializes it waitlist with 3 categories:
    ///entrants who have been sent notifications, entrants who are waiting to be notified,
    ///and entrants who have denied invitations
    public WaitList(Event event){
        waiting = new ArrayList<User>();
        invited = new ArrayList<User>();
        declined = new ArrayList<User>();
        attachedEvent = event;
    }

    public ArrayList<User> getDeclined() {
        return declined;
    }

    public Event getAttachedEvent() {
        return attachedEvent;
    }

    public ArrayList<User> getWaiting() {
        return waiting;
    }

    public ArrayList<User> getInvited() {
        return invited;
    }

    public void addWaiting(User user) {
        this.waiting.add(user);
    }


    /**
     * Randomly selects x amount of users to be sent a invitation to the event
     * @param numSelect the number of users to be selected
     */
    public void selectUsersToInvite(int numSelect){

        if (numSelect > waiting.size()){
            //Throw an exception if numSelected is greater than the amount of users waiting
            try {
                throw new Exception("Number selected is greater than amount of users signed up");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //get random from list and move it
        for (int i = 0; i < numSelect; i++) {
            int indexNum = (int) (Math.random() * waiting.size());
            moveUsers(waiting.get(indexNum),invited,waiting);
        }

    }

    /**
     * Moves user object from one array list to another
     * @param user user object to be moved
     * @param receiver array list that adds the user
     * @param donor array list that removes the user
     */
    private void moveUsers(User user, ArrayList<User> receiver, ArrayList<User> donor){
        receiver.add(user);
        donor.remove(user);
    }
}
