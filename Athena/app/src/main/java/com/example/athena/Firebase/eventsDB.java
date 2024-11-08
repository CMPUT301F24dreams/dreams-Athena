package com.example.athena.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;


import com.example.athena.Models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

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

    public void updateEventID(String eventID) {
        Map<String, Object> data = new HashMap<>();
        data.put("eventID", eventID);
        eventsCollection.document(eventID).set(data, SetOptions.merge());
    }
    public Task<QuerySnapshot> getEventUserList(String eventID){
        return db.collection("Events/" + eventID + "/UserList").get();
    }
    public Task<QuerySnapshot> getEventsList() {
        return eventsCollection.get();
    }

    public Task<DocumentSnapshot> getEvent(String eventID) {
        return eventsCollection.document(eventID).get();
    }
    public Task addEvent(Event event){
        return eventsCollection.add(event);
    }

    public void changeStatusInvited(String eventID, ArrayList<String> userIDs){
        for(String userID: userIDs) {
            db.collection("Events").document(eventID).collection("UserList").document(userID).update("status","invited");
            db.collection("Events").document(eventID).collection("UserList").document(userID).update("notified",Boolean.FALSE);
        }
    }
    public void changeStatusAccepted(String eventID, String userID){
        db.collection("Events").document(eventID).collection("UserList").document(userID).update("status","accepted");
    }
    public void changeStatusDeclined(String eventID, String userID){
        db.collection("Events").document(eventID).collection("UserList").document(userID).update("status","declined");
    }

}
