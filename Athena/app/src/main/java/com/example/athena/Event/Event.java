package com.example.athena.Event;

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
    private SimpleDateFormat regStart;
    private SimpleDateFormat regEnd;
    private SimpleDateFormat eventDate;

    private List<Interfaces.Observer> observers = new ArrayList<>();

    public Event(String eventID) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public Event(SimpleDateFormat eventDate, SimpleDateFormat regEnd, SimpleDateFormat regStart, String poster, Integer maxParticipants, Boolean geoRequire, String facilityID, String description, String eventName) {
        this.eventDate = eventDate;
        this.regEnd = regEnd;
        this.regStart = regStart;
        this.poster = poster;
        this.maxParticipants = maxParticipants;
        this.geoRequire = geoRequire;
        this.facilityID = facilityID;
        this.description = description;
        this.eventName = eventName;

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

    public SimpleDateFormat getRegStart() {
        return regStart;
    }

    public void setRegStart(SimpleDateFormat regStart) {
        this.regStart = regStart;
    }

    public SimpleDateFormat getRegEnd() {
        return regEnd;
    }

    public void setRegEnd(SimpleDateFormat regEnd) {
        this.regEnd = regEnd;
    }

    public SimpleDateFormat getEventDate() {
        return eventDate;
    }

    public void setEventDate(SimpleDateFormat eventDate) {
        this.eventDate = eventDate;
    }
}
