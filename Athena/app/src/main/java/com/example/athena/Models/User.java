package com.example.athena.Models;

import java.util.ArrayList;

/**
 * The {@code User} class represents a user of the application with basic personal details
 * and a list of events they are associated with.
 * This class holds the user's name, email, phone, image URL, and the list of events
 * the user is participating in or has created.
 */
public class User {
    private String name;
    private String email;
    private String phone;
    private String imageURL;
    private ArrayList<Event> Events;

    /**
     * Constructor to initialize a {@code User} object with the provided name, email, phone, and image URL.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     * @param imageURL The URL of the user's image.
     */
    public User(String name, String email, String phone, String imageURL) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageURL = imageURL;
        this.Events = new ArrayList<>();
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The phone number of the user.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phone The new phone number of the user.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The new email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of events associated with the user.
     *
     * @return The list of events associated with the user.
     */
    public ArrayList<Event> getEvents() {
        return Events;
    }

    /**
     * Adds an event to the user's list of events.
     *
     * @param event The event to be added.
     */
    public void addEvent(Event event) {
        this.Events.add(event);
    }

    /**
     * Gets the URL of the user's image.
     *
     * @return The image URL of the user.
     */
    public String getImageURL() {
        return imageURL;
    }
}
