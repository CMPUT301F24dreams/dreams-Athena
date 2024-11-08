package com.example.athena.Models;
import com.example.athena.Interfaces.Observer;
import com.example.athena.WaitList.WaitList;
import com.example.athena.Firebase.DBConnector;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.athena.Interfaces.Model;
public class Event { // TO-DO Java-doc
    private String eventName;
    private String imageURL;
    private String eventDescription;
    private String organizer;
    private String facility;
    private Integer maxParticipants;
    private Boolean geoRequire;
    private String eventID;
    private WaitList waitList;


    public Event(String eventName, String imageURL, String eventDescription, String organizer, String facility, Integer maxParticipants, Boolean georequire, String eventID) {
        this.eventName = eventName;
        this.imageURL = imageURL;
        this.eventDescription = eventDescription;
        this.organizer = organizer;
        this.facility = facility;
        this.maxParticipants = maxParticipants;
        this.geoRequire = georequire;
        this.eventID = eventID;
    }

    public Event(String eventName, String imageURL, String eventID) {
        this.eventName = eventName;
        this.imageURL = imageURL;
        this.eventID = eventID;
    }

    public void addUser(String userId,String eventID){
        waitList.addWaiting(userId, eventID);
    }

    public void chooseUsers(int numOfUser, String eventID){
        waitList.selectUsersToInvite(numOfUser, eventID);
    }

    public void removeUser(String userId, String eventID){
        waitList.removeUser(userId, eventID);
    }

    public void moveUser(String userID,String status){
        waitList.moveUserFromInvited(userID,status);
    }

    // TO-DO: Add getters/setters properly
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Boolean getGeoRequire() {
        return geoRequire;
    }

    public void setGeoRequire(Boolean geoRequire) {
        this.geoRequire = geoRequire;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getEventID() {
        return eventID;
    }
}