package com.example.athena.Event;

import com.example.athena.dbInfoRetrieval.DBConnector;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Observer;
import Interfaces.Model;

public class Event implements Model { // TO-DO Java-doc
    private String eventName;
    private String description;
    private String facilityID;
    private Boolean geoRequire;
    private Integer maxParticipants;
    private String poster;
    private String regStart;
    private String regEnd;
    private String eventDate;
    private List<Interfaces.Observer> observers = new ArrayList<>();

    public Event(String eventID) {
        FirebaseFirestore db = DBConnector.getInstance().getDb();
        DocumentReference docRef = db.collection("Events").document(eventID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event newEvent = documentSnapshot.toObject(Event.class);
                eventName = newEvent.getEventName();
                description = newEvent.getDescription();
                facilityID = newEvent.getFacilityID();
                geoRequire = newEvent.getGeoRequire();
                maxParticipants = newEvent.getMaxParticipants();
                poster = newEvent.getPoster();
                regStart = newEvent.getRegStart();
                regEnd = newEvent.getRegEnd();
                eventDate = newEvent.getEventDate();
            }
        });
    }

    public Event(String eventName, String description, String facilityID, Boolean geoRequire, Integer maxParticipants, String poster, String regStart, String regEnd, String eventDate) {
        this.eventName = eventName;
        this.description = description;
        this.facilityID = facilityID;
        this.geoRequire = geoRequire;
        this.maxParticipants = maxParticipants;
        this.poster = poster;
        this.regStart = regStart;
        this.regEnd = regEnd;
        this.eventDate = eventDate;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Events").add(this);
    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // TO-DO: Add getters/setters properly
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRegStart() {
        return regStart;
    }

    public void setRegStart(String regStart) {
        this.regStart = regStart;
    }

    public String getRegEnd() {
        return regEnd;
    }

    public void setRegEnd(String regEnd) {
        this.regEnd = regEnd;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}