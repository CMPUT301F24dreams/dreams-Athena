package com.example.athena.Event;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Observer;
import Interfaces.Model;

public class Event implements Model {
    private String eventID;
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

    public Event(String eventID)
        {
            this.eventID = eventID;
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

    public String getEventID() {
        return eventID;
    }
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
