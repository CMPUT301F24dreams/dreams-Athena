package com.example.athena.Models;

import java.util.ArrayList;

/**
 * Represents a user in the application. This class holds the user's personal information,
 * including their name, email, phone number, profile image URL, and a list of events they are associated with.
 * The class allows for easy management and retrieval of user details and their events.
 */
public class User {

    private String name;
    private String email;
    private String phone;
    private String facility;
    private String imageURL;
    private ArrayList<Event> Events;
    private boolean isAdmin;

    /**
     * Constructor to initialize the user with basic information: name, email, phone, and image URL.
     */
    public User() {
        this.name = "NULL";
        this.email = "NULL";
        this.phone = "NULL";
        this.imageURL = "NULL";
        this.facility = "NULL";
    }

    /**
     * Constructor to initialize the user with basic information: name, email, phone, and image URL.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     * @param imageURL The profile image URL of the user.
     */
    public User(String name, String email, String phone, String imageURL) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageURL = imageURL;
        this.Events = new ArrayList<>();  // Initialize the events list
    }

    /**
     * Constructor to initialize the user with basic information: name, email, and phone, without an image URL.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     */
    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.Events = new ArrayList<>();  // Initialize the events list
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of the facility.
     *
     * @return The facility's name
     */
    public String getFacility() {
        return facility;
    }

    /**
     * Sets the name of the facility.
     *
     * @param facility The name of the facility.
     */
    public void setFacility(String facility) {
        this.facility = facility;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name to set for the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The user's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phone The phone number to set for the user.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of events associated with the user.
     *
     * @return The list of events for the user.
     */
    public ArrayList<Event> getEvents() {
        return Events;
    }

    /**
     * Adds an event to the user's list of events.
     *
     * @param event The event to add to the user's event list.
     */
    public void addEvent(Event event) {
        this.Events.add(event);
    }

    /**
     * Gets the profile image URL of the user.
     *
     * @return The user's profile image URL.
     */
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
