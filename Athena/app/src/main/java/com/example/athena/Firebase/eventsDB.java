package com.example.athena.Firebase;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.athena.EntrantAndOrganizerFragments.JoinEventDetails;
import com.example.athena.EntrantAndOrganizerFragments.myEventsList;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

/**
 * This class handles database operations for events collection, interacting with the Firestore database.
 */
public class eventsDB {
    private FirebaseFirestore db;
    private static CollectionReference eventsCollection;

    public eventsDB(){
        this.db = FirebaseFirestore.getInstance();
        this.eventsCollection = db.collection("Events");
    }

    public static Task updateEventID(String eventID) {
        Map<String, Object> data = new HashMap<>();
        data.put("eventID", eventID);
        return eventsCollection.document(eventID).set(data, SetOptions.merge());
    }

    /**
     * get the document of the event from the database
     * @param eventID the id of the event to grab
     * @return a Task(DocumentSnapshot) of the event
     */
    public Task<DocumentSnapshot> getEvent(String eventID) {
        return eventsCollection.document(eventID).get();
    }

    public void loadEventData(Event event, String eventID) {
        Task getEventTask = this.getEvent(eventID);
        getEventTask.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                if (task.isSuccessful()) {
                    event.setEventName(document.getString("eventName"));
                    event.setImageURL(document.getString("imageURL"));
                    event.setEventDescription(document.getString("eventDescription"));
                    event.setOrganizer(document.getString("organizer"));
                    event.setFacility(document.getString("Facility"));
                    event.setMaxParticipants(Math.toIntExact((Long) document.get("maxParticipants")));
                    event.setGeoRequire(document.getBoolean("geoRequire"));
                    event.setEventID(document.getString("eventID"));

                    event.notifyObservers();
                } else {


                    Exception exception = task.getException();
                }
            }
        });
    }

    public void loadQRData(Event event, String eventID, FragmentManager fragment, Bundle bundle, Activity activity) {
        Task getEventTask = this.getEvent(eventID);
        getEventTask.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                if (task.isSuccessful()) {
                    if (document.exists()) {
                        event.setEventName(document.getString("eventName"));
                        event.setImageURL(document.getString("imageURL"));
                        event.setEventDescription(document.getString("eventDescription"));
                        event.setOrganizer(document.getString("organizer"));
                        event.setFacility(document.getString("Facility"));
                        event.setMaxParticipants(Integer.parseInt(document.getString("maxParticipants")));
                        event.setGeoRequire(document.getBoolean("geoRequire"));
                        event.setEventID(document.getString("eventID"));

                        event.notifyObservers();

                    } else {
                        Fragment frag = new myEventsList();
                        frag.setArguments(bundle);
                        fragment.beginTransaction().replace(R.id.content_frame,frag).commit();

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Invalid QR code");
                        builder.setPositiveButton("OK", (dialog, which) -> {
                        });
                        builder.show();
                    }

                } else {
                    Exception exception = task.getException();
                }
            }
        });
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

    public Task addEvent(Event event, String userID, Uri imageURI){
        TaskCompletionSource finishTaskSource = new TaskCompletionSource();
        Task eventPrelim = db.collection("Events").add(event);
        eventPrelim.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                DocumentReference doc = (DocumentReference) task.getResult();
                String eventID = doc.getId();

                UploadTask upload = new imageDB().addImage(eventID, imageURI);
                Task changeURL = eventsDB.saveURLToEvent(upload, eventID);

                Task updateID = eventsDB.updateEventID(eventID);
                Task updateUserOrg = userDB.updateOrgEvents(userID, eventID);

                Task finishTask = Tasks.whenAll(upload, changeURL, updateID, updateUserOrg);
                finishTask.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        finishTaskSource.setResult(eventID);
                    }
                });
            }
        });
        return finishTaskSource.getTask();
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

    public static Task<Void> saveURLToEvent(UploadTask uploadTask, String eventID) {
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
    public void moveUserID(String donor, String receiver, String userID, String eventID ){

        Map<String,Boolean> notifi = new HashMap<>();
        notifi.put("notified",Boolean.FALSE);
        db.collection("Events").document(eventID).collection(receiver).document(userID).set(notifi);
        db.collection("Events").document(eventID).collection(donor).document(userID).delete();
    }

    public void deleteEvent(String eventID){
        db.collection("Events").document(eventID).delete();
    }

    public void removeUser(String deviceID, String eventID){

        db.collection("Events").document(eventID).collection("accepted").document(deviceID).delete();
        db.collection("Events").document(eventID).collection("declined").document(deviceID).delete();
        db.collection("Events").document(eventID).collection("invited").document(deviceID).delete();
        db.collection("Events").document(eventID).collection("UserList").document(deviceID).delete();
        db.collection("Events").document(eventID).collection("pending").document(deviceID).delete();
    }

    public void addUser(String deviceID, String eventID){
        Map<String,Boolean> notif = new HashMap<>();
        notif.put("notified",Boolean.FALSE);
        eventsCollection.document(eventID).collection("pending").document(deviceID).set(notif);
    }

}
