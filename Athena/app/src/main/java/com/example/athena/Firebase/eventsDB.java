package com.example.athena.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * This class handles database operations for events collection, interacting with the Firestore database.
 */
public class eventsDB {
    private FirebaseFirestore db;
    private CollectionReference eventsCollection;

    public eventsDB(){
        // Initialize the Firestore connection and specify the collection
        this.db = FirebaseFirestore.getInstance();
        this.eventsCollection = db.collection("Events");
    }

    public Task<QuerySnapshot> getEventsList() {
        return db.collection("Events").get();
    }

    /**
     * get the document of the event from the database
     * @param eventID the id of the event to grab
     * @return a Task(DocumentSnapshot) of the event
     */
    public Task<DocumentSnapshot> getEvent(String eventID) {
        return db.collection("Events").document(eventID).get();
    }
    /**
     * get the sub-collection of users from the event from the database
     * @param eventID the id of the event to grab
     * @return a Task(QuerySnapshot) of the event
     */
    public Task<QuerySnapshot> getEventUserList(String eventID,String list){
        return db.collection("Events/").document(eventID).collection(list).get();
    }

    /**
     * change the status of the user in the events collection to invited and set notified to false
     * @param eventID the ID of the event
     * @param userIDs ArrayList of user IDs
     */
    public void changeStatusInvited(String eventID, ArrayList<String> userIDs){
        for(String userID: userIDs) {
            db.collection("Events").document(eventID).collection("UserList").document(userID).update("status","invited");
            db.collection("Events").document(eventID).collection("UserList").document(userID).update("notified",Boolean.FALSE);
        }
    }
    /**
     * change the status of the user in the events collection to accepted
     * @param eventID the ID of the event
     * @param userID the users ID
     */
    public void changeStatusAccepted(String eventID, String userID){
        db.collection("Events").document(eventID).collection("UserList").document(userID).update("status","accepted");
    }
    /**
     * change the status of the user in the events collection to declined
     * @param eventID the ID of the event
     * @param userID the users ID
     */
    public void changeStatusDeclined(String eventID, String userID){
            db.collection("Events").document(eventID).collection("UserList").document(userID).update("status","declined");
    }

    public void moveUserID(String donor, String receiver, String userID,String eventID ){

        Map<String,Boolean> notifi = new HashMap<>();
        notifi.put("notified",Boolean.FALSE);
        db.collection("Events").document(eventID).collection(receiver).document(userID).set(notifi);
        db.collection("Events").document(eventID).collection(donor).document(userID).delete();
    }



    // Adds a new event to the Events collection
    public Task<DocumentReference> addEvent(HashMap<String, Object> eventData){
        return eventsCollection.add(eventData);
    }

    // Updates an existing event with new data
    public Task<Void> updateEvent(String eventId, HashMap<String, Object> updatedData){
        return eventsCollection.document(eventId).update(updatedData);
    }

    // Deletes a specific event by its ID
    public Task<Void> deleteEvent(String eventId){
        return eventsCollection.document(eventId).delete();
    }

    //Add Entrant to Waitlist
    public Task<DocumentReference> addEntrantToWaitlist(String eventId, HashMap<String, Object> entrantData) {
        return eventsCollection.document(eventId).collection("Waitlist").add(entrantData);
    }

    //Get Waitlist for an Event
    public Task<QuerySnapshot> getWaitlist(String eventId) {
        return eventsCollection.document(eventId).collection("Waitlist").get();
    }

    //Update Entrant Status to Waitlist
    public Task<Void> updateEntrantStatus(String eventId, String entrantId, HashMap<String,Object> statusData){
        return eventsCollection.document(eventId).collection("Waitlist").document(entrantId).update(statusData);
    }

    //Delete Entrant from Waitlist
    public Task<Void> deleteEntrantFromWaitlist(String eventId, String entrantId) {
        return eventsCollection.document(eventId).collection("Waitlist").document(entrantId).delete();
    }

    //Update Event Summary
    public Task<Void> updateEventSummary(String eventId, HashMap<String, Object> summaryData) {
        return eventsCollection.document(eventId).collection("eventSummary").document("summary").set(summaryData);
    }

    //Get Event Summary
    public Task<DocumentSnapshot> getEventSummary(String eventId) {
        return eventsCollection.document(eventId).collection("eventSummary").document("summary").get();
    }


}
