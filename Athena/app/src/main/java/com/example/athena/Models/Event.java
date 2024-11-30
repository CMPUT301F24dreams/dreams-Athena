package com.example.athena.Models;
import com.example.athena.Interfaces.Model;
import com.example.athena.Interfaces.Observer;
import com.example.athena.WaitList.WaitList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Event} class represents an event in the application.
 * It encapsulates the details of the event, such as its name, description, organizer, participants, and associated waitlist.
 * This class is responsible for managing users who are waiting to participate in the event and selecting users to invite.
 *
 * It follows the observer design pattern by interacting with a {@link WaitList} object to handle user management.
 *
 */
public class Event implements Model {
    private String eventName;
    private String imageURL;
    private String eventDescription;
    private String organizer;
    private String facility;
    private String eventDate;
    private String startReg;
    private String endReg;
    private Integer maxParticipants;
    private Boolean geoRequire;
    private String eventID;
    private ArrayList<Observer> observers = new ArrayList<>();
    private WaitList waitList;
    private String qrCode;

    public Event() {
        this.eventName = "";
        this.imageURL = "";
        this.eventDescription = "";
        this.organizer = "";
        this.facility = "";
        this.maxParticipants = 0;
        this.geoRequire = Boolean.FALSE;
        this.eventID = "";
        this.eventDate = "";
        this.startReg = "";
        this.endReg = "";
        this.eventID = "";
        this.qrCode="";
        this.waitList = new WaitList(this);
        this.observers = new ArrayList<>();
    }

    /**
     * Constructor with just a facility
     * @param facility the facilityID
     */
    public Event(String facility) {
        this.eventName = "NULL";
        this.imageURL = "NULL";
        this.eventDescription = "NULL";
        this.eventDate = "NULL";
        this.startReg = "NULL";
        this.endReg = "NULL";
        this.organizer = "NULL";
        this.facility = facility;
        this.maxParticipants = 0;
        this.geoRequire = Boolean.FALSE;
        this.eventID = "NULL";
        this.qrCode="NULL";
        this.waitList = new WaitList(this);
        this.observers = new ArrayList<>();
    }

    /**
     * Constructor to initialize the event with a name, image, and eventID.
     * This constructor automatically initializes the waitlist for the event.
     *
     * @param eventName The name of the event.
     * @param imageURL  The image URL associated with the event.
     * @param eventID   The unique identifier of the event.
     */
    public Event(String eventName, String imageURL, String eventID) {
        this.eventName = eventName;
        this.imageURL = imageURL;
        this.eventID = eventID;
        this.waitList = new WaitList(this);
    }

    /**
     * Adds a user to the waitlist for the event.
     *
     * @param userId  The ID of the user to be added.
     * @param eventID The ID of the event to which the user is being added.
     */
    public void addUser(String userId,String eventID){
        waitList.addWaiting(userId, eventID);
    }
    /**
     * Chooses a specified number of users from the waitlist and invites them to the event.
     *
     * @param numOfUser The number of users to invite.
     * @param eventID   The ID of the event from which users will be invited.
     */
    public void chooseUsers(int numOfUser, String eventID){
        waitList.selectUsersToInvite(numOfUser, eventID);
    }
    /**
     * Removes a user from the waitlist for the event.
     *
     * @param userId  The ID of the user to be removed.
     * @param eventID The ID of the event from which the user will be removed.
     */
    public void removeUser(String userId, String eventID){
        waitList.removeUser(userId, eventID);
    }
    /**
     * Moves a user from the invited list based on their current status.
     *
     * @param userID The ID of the user to be moved.
     * @param status The new status of the user (e.g., from invited to confirmed).
     */
    public void moveUser(String userID,String status){
        waitList.moveUserFromInvited(userID,status);
    }
    /**
     * Gets the waitlist associated with the event.
     *
     * @return The waitlist for the event.
     */

    public boolean checkEvent() {
        if (Objects.equals(this.eventName, "")) {
            return false;
        } else if (Objects.equals(this.eventDescription, "")) {
            return false;
        } else if (Objects.equals(this.facility, "")) {
            return false;
        } else if (Objects.equals(this.maxParticipants, 0)) {
            return false;
        } else if ((Objects.equals(this.eventDate, ""))) {
            return false;
        } else if ((Objects.equals(this.startReg, ""))) {
            return false;
        } else if ((Objects.equals(this.endReg, ""))) {
            return false;
        }
        return true;
    }
    public WaitList getWaitList() {
        return waitList;
    }

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public String getEventName() {
        return eventName;
    }
    /**
     * Sets the name of the event.
     *
     * @param eventName The name to set for the event.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    /**
     * Gets the geographic requirement status of the event.
     *
     * @return {@code true} if geographic requirements are enforced, otherwise {@code false}.
     */
    public Boolean getGeoRequire() {
        return geoRequire;
    }
    /**
     * Sets the geographic requirement status of the event.
     *
     * @param geoRequire The geographic requirement status to set.
     */
    public void setGeoRequire(Boolean geoRequire) {
        this.geoRequire = geoRequire;
    }
    /**
     * Gets the maximum number of participants for the event.
     *
     * @return The maximum number of participants.
     */
    public Integer getMaxParticipants() {
        return maxParticipants;
    }
    /**
     * Sets the maximum number of participants for the event.
     *
     * @param maxParticipants The maximum number of participants to set.
     */
    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
    /**
     * Gets the organizer of the event.
     *
     * @return The organizer of the event.
     */

    public String getOrganizer() {
        return organizer;
    }
    /**
     * Sets the organizer of the event.
     *
     * @param organizer The organizer to set.
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    /**
     * Gets the description of the event.
     *
     * @return The event description.
     */
    public String getEventDescription() {
        return eventDescription;
    }
    /**
     * Sets the description of the event.
     *
     * @param eventDescription The description to set.
     */
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    /**
     * Gets the facility where the event takes place.
     *
     * @return The event facility.
     */

    public String getFacility() {
        return facility;
    }
    /**
     * Sets the facility where the event takes place.
     *
     * @param facility The facility to set.
     */
    public void setFacility(String facility) {
        this.facility = facility;
    }
    /**
     * Gets the image URL of the event.
     *
     * @return The image URL of the event.
     */
    public String getImageURL() {
        return imageURL;
    }
    /**
     * Gets the event ID.
     *
     * @return The ID of the event.
     */
    public String getEventID() {
        return eventID;
    }

    public String getQRCode() {
        return qrCode;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartReg() {
        return startReg;
    }

    public void setStartReg(String startReg) {
        this.startReg = startReg;
    }

    public String getEndReg() {
        return endReg;
    }

    public void setEndReg(String endReg) {
        this.endReg = endReg;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setQRCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}