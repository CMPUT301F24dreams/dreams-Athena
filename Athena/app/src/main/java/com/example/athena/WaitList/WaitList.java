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

    public void addWaiting(String userID, String eventID) {

        this.waiting.add(userID);
        addToDatabase(userID,eventID);

    }

    public void removeUser(String userID, String eventID){

        this.waiting.remove(userID);
        deleteFromDatabase(userID, eventID);
    }


    /**
     * Randomly selects x amount of users to be sent a invitation to the event
     * @param numSelect the number of users to be selected
     */
    public void selectUsersToInvite(int numSelect, String eventID){

        if (numSelect > waiting.size()){

            //if number to select is greater than amount signed up send all to invited
            for(String userID: waiting){
                moveUsers(userID,invited,waiting);
                updateDatabaseChosen(userID,eventID);
            }
        }else {
            //get random from list and move it
            for (int i = 0; i < numSelect; i++) {
                int indexNum = (int) (Math.random() * waiting.size());
                moveUsers(waiting.get(indexNum), invited, waiting);
                updateDatabaseChosen(waiting.get(indexNum),eventID);
            }
        }

    }

    private void addToDatabase(String userID,String eventID){
        /*  we are calling to the data base to add a new document with the same ID as the events but in a different collection
         *  we are doing this so that the user collection can store its own events and not have to search through each event in event
         *  collection to find it's id to then display or modify
         */
        FirebaseFirestore db = DBConnector.getInstance().getDb();
        Map<String, Object> eventDetail = new HashMap<>();
        eventDetail.put("eventID", eventID);
        eventDetail.put("status", "pending");
        db.collection("Users").document(userID).collection("Events").document(eventID).set(eventDetail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("WaitListAdd", "onSuccess: event details added to user");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("WaitListAdd", "onFailure: Error writing to doc");
                    }
                });


        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("UserID", userID);
        userDetail.put("status", "pending");
        db.collection("Events").document(eventID).collection("Waitlist").document(userID).set(userDetail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("WaitListAdd", "onSuccess: user details added to event");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("WaitListAdd", "onFailure: Error writing to doc");
                    }
                });
    }


    private void deleteFromDatabase(String userID, String eventID){
        /*
            we are removing both references to the user and event from the database when the user no longer wish to be in the event

         */
        FirebaseFirestore db = DBConnector.getInstance().getDb();
        db.collection("Events").document(eventID).collection("waitlist").document(userID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("WaitlistDelete", "onSuccess: delete user on event side");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("WaitlistDelete", "onFailure: delete user on event side");
                    }
                });
        db.collection("Users").document(userID).collection("Events").document(eventID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("WaitlistDelete", "onSuccess: delete event on user side");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("WaitlistDelete", "onFailure: delete event on user side");
                    }
                });
    }

    private void updateDatabaseChosen(String userID, String eventID){
        FirebaseFirestore db = DBConnector.getInstance().getDb();
        // updates the database on the status of the user in the user collection
        db.collection("Users").document(userID).collection("Events").document(eventID).update("status","chosen")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("WaitlistInviteUser", "onSuccess: status change on user side");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("WaitlistInviteUser", "onFailure: status change on user side");
                    }
                });
        // updates the database on the status of the user in the event collection
        db.collection("Events").document(eventID).collection("Waitlist").document(userID).update("status","chosen")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("WaitlistInviteUser", "onSuccess: status change on event side");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("WaitlistInviteUser", "onFailure: status change on event side");
                    }
                });
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
