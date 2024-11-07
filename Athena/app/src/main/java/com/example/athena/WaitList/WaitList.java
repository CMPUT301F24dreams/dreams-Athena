package com.example.athena.WaitList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.athena.Models.Event;
import com.example.athena.Firebase.DBConnector;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is responsible for storing information about waitlists for corresponding events
 * It has methods which are used to retrieve lists, as well as handle changes made to the waitlists
 */
public class WaitList{
    private ArrayList<String> waiting;
    private ArrayList<String> invited;
    private ArrayList<String> declined;
    private ArrayList<String> accepted;
    private Event attachedEvent;

    ///This is a constructor for the waitlist class, it initializes it waitlist with 3 categories:
    ///entrants who have been sent notifications, entrants who are waiting to be notified,
    ///and entrants who have denied invitations
    public WaitList(Event event){
        waiting = new ArrayList<String>();
        invited = new ArrayList<String>();
        declined = new ArrayList<String>();
        accepted = new ArrayList<String>();


        attachedEvent = event;
    }

    public void setAccepted(ArrayList<String> accepted) {
        this.accepted = accepted;
    }

    public void setDeclined(ArrayList<String> declined) {
        this.declined = declined;
    }

    public void setInvited(ArrayList<String> invited) {
        this.invited = invited;
    }

    public void setWaiting(ArrayList<String> waiting) {
        this.waiting = waiting;
    }

    public ArrayList<String> getDeclined() {
        return declined;
    }

    public Event getAttachedEvent() {
        return attachedEvent;
    }

    public ArrayList<String> getWaiting() {
        return waiting;
    }

    public ArrayList<String> getInvited() {
        return invited;
    }

    public void addWaiting(String userID, String eventID) {

        this.waiting.add(userID);


    }

    public void removeUser(String userID, String eventID){

        this.waiting.remove(userID);

    }


    /**
     * Randomly selects x amount of users to be sent a invitation to the event moving them from pending to chosen
     * @param numSelect the number of users to be selected
     */
    public void selectUsersToInvite(int numSelect, String eventID){

        if (numSelect > waiting.size()){

            //if number to select is greater than amount signed up send all to invited
            for(String userID: waiting){
                moveUsers(userID,invited,waiting);
            }
        }else {
            //get random from list and move it
            for (int i = 0; i < numSelect; i++) {
                int indexNum = (int) (Math.random() * waiting.size());
                moveUsers(waiting.get(indexNum), invited, waiting);

            }
        }

    }



    /**
     * Moves user object from one array list to another
     * @param userId userId string to be moved
     * @param receiver array list that adds the user
     * @param donor array list that removes the user
     */
    private void moveUsers(String userId, ArrayList<String> receiver, ArrayList<String> donor){
        receiver.add(userId);
        donor.remove(userId);
    }
    /**
     * moves the user from chosen to either declined or accepted
     * @param userID the users unique ID
     * @param newStatus whether the user accepted or declined the event
     */
    public void moveUserFromInvited(String userID, String newStatus){
        if(Objects.equals(newStatus, "accepted")){
            moveUsers(userID,accepted,invited);
        } else {
            moveUsers(userID,declined,invited);
        }

    }
}
