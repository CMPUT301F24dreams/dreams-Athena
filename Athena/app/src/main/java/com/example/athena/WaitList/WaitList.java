package com.example.athena.WaitList;

import com.example.athena.Event.Event;
import com.example.athena.Roles.User;
import com.example.athena.dbInfoRetrieval.DBConnector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    //constructor for existing events in the db
    public WaitList(String eventID, Event event){
        FirebaseFirestore db = DBConnector.getInstance().getDb(); //get the db
        //get all entrants who's status is accepted and add them to the accepted list
        List<DocumentSnapshot> accepted = db.collection("Events").document(eventID)
                .collection("waitlist")
                .whereEqualTo("status","accepted")
                .get().getResult().getDocuments();
        for (DocumentSnapshot x: accepted){
            this.accepted.add(x.get("entrantID").toString());

        }
        //get all entrants who declined
        List<DocumentSnapshot> declined = db.collection("Events").document(eventID)
                .collection("waitlist")
                .whereEqualTo("status","declined")
                .get().getResult().getDocuments();
        for (DocumentSnapshot x: declined){
            this.declined.add(x.get("entrantID").toString());

        }
        //get all user ids of those who are in the waiting list
        List<DocumentSnapshot> pending = db.collection("Events").document(eventID)
                .collection("waitlist")
                .whereEqualTo("status","pending")
                .get().getResult().getDocuments();
        for (DocumentSnapshot x: pending){
            this.waiting.add(x.get("entrantID").toString());

        }

        //get all user id of those invited
        List<DocumentSnapshot> chosen = db.collection("Events").document(eventID)
                .collection("waitlist")
                .whereEqualTo("status","chosen")
                .get().getResult().getDocuments();
        for (DocumentSnapshot x: chosen){
            this.invited.add(x.get("entrantID").toString());

        }

        this.attachedEvent = event;


    }

    public void sendInvites(){
        //to be implemented
        ;
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

    public void addWaiting(String userId) {
        this.waiting.add(userId);
    }

    public void removeUser(String userId){
        this.waiting.remove(userId);
    }


    /**
     * Randomly selects x amount of users to be sent a invitation to the event
     * @param numSelect the number of users to be selected
     */
    public void selectUsersToInvite(int numSelect){

        if (numSelect > waiting.size()){
            //if number to select is greater than amount signed up send all to invited
            for(String userId: waiting){
                moveUsers(userId,invited,waiting);
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
}
