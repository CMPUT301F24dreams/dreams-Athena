package com.example.athena.Firebase;

import android.net.Uri;

import androidx.annotation.NonNull;


import com.example.athena.Models.Event;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public void updateEventID(String eventID) {
        Map<String, Object> data = new HashMap<>();
        data.put("eventID", eventID);
        eventsCollection.document(eventID).set(data, SetOptions.merge());
    }

    /**
     * get the document of the event from the database
     * @param eventID the id of the event to grab
     * @return a Task(DocumentSnapshot) of the event
     */
    public Task<DocumentSnapshot> getEvent(String eventID) {
        return eventsCollection.document(eventID).get();
    }
    /**
     * get the sub-collection of users from the event from the database
     * @param eventID the id of the event to grab
     * @return a Task(QuerySnapshot) of the event
     */
    public Task<QuerySnapshot> getEventUserList(String eventID,String list){
        return db.collection("Events").document(eventID).collection(list).get();
    }
    public Task<QuerySnapshot> getEventsList() {
        return eventsCollection.get();
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

    public Task<Void> saveURLToEvent(UploadTask uploadTask, String eventID) {
        TaskCompletionSource<Void> changeURLSource = new TaskCompletionSource<>();
        Task<Void> changeTask = changeURLSource.getTask();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child("images/" + eventID);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri URL = task.getResult();
                Map<String, Object> data = new HashMap<>();
                data.put("imageURL", URL.toString());
                eventsCollection.document(eventID).set(data, SetOptions.merge());
                changeURLSource.setResult(null);
            }
        });
        return changeTask;
    }
    public void moveUserID(String donor, String receiver, String userID,String eventID ){

        Map<String,Boolean> notifi = new HashMap<>();
        notifi.put("notified",Boolean.FALSE);
        db.collection("Events").document(eventID).collection(receiver).document(userID).set(notifi);
        db.collection("Events").document(eventID).collection(donor).document(userID).delete();
    }




}
