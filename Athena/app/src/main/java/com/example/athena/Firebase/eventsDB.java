package com.example.athena.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.athena.Models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

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
        return eventsCollection.get();
    }

    public Task<DocumentSnapshot> getEvent(String eventID) {
        return eventsCollection.document(eventID).get();
    }

    // Adds a new event to the Events collection, return it's eventID
    public Task addEvent(Event event){
        return eventsCollection.add(event);
    }

    public void updateEventID(String eventID) {
        Map<String, Object> data = new HashMap<>();
        data.put("eventID", eventID);
        eventsCollection.document(eventID).set(data, SetOptions.merge());
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
