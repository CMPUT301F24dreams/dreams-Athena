package com.example.athena.Models;
import com.example.athena.Interfaces.Observer;
import com.example.athena.WaitList.WaitList;

import java.util.ArrayList;
import java.util.List;

public class Event { // TO-DO Java-doc
    private String eventName;
    private String eventID;
    private String description;
    private String facilityID;
    private Boolean geoRequire;
    private Integer maxParticipants;
    private String regStart;
    private String regEnd;
    private String eventDate;
    private String imageURL;
    private WaitList waitList;
    private final List<Observer> observers = new ArrayList<>();


    public Event(String eventName, String imageURL, String eventID) {
        this.eventName = eventName;
        this.imageURL = imageURL;
        this.eventID = eventID;
        this.waitList = new WaitList(this);
    }
    public Event() {

    }

    //TODO make complete implementations
    /*
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
    */


    //waitlist access things

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

    public WaitList getWaitList() {
        return waitList;
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

    public String getImageURL() {
        return imageURL;
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

    public String getEventID() {
        return eventID;
    }
}