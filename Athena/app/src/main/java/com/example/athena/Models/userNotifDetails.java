package com.example.athena.Models;

/**
 * Represents the notification preferences of a user. This class stores the user's name
 * along with their choices for receiving or not receiving notifications. It is used to manage
 * individual notification settings for users within the application.
 */
public class userNotifDetails {

    private String userName;
    private boolean chosenNotif;
    private boolean notChosenNotif;

    /**
     * Constructor to initialize the notification preferences for a user.
     *
     * @param userName The name of the user.
     * @param chosenNotif A flag indicating whether the user has chosen to receive notifications.
     * @param notChosenNotif A flag indicating whether the user has opted not to receive notifications.
     */
    public userNotifDetails(String userName, boolean chosenNotif, boolean notChosenNotif) {
        this.userName = userName;
        this.chosenNotif = chosenNotif;
        this.notChosenNotif = notChosenNotif;
    }

    /**
     * Default constructor for creating an instance without setting any fields.
     */
    public userNotifDetails() {
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the name of the user.
     *
     * @param userName The name to set for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the flag indicating whether the user has chosen to receive notifications.
     *
     * @return True if the user has chosen to receive notifications, false otherwise.
     */
    public boolean isChosenNotif() {
        return chosenNotif;
    }

    /**
     * Sets the flag for whether the user has chosen to receive notifications.
     *
     * @param chosenNotif True if the user has chosen to receive notifications, false otherwise.
     */
    public void setChosenNotif(boolean chosenNotif) {
        this.chosenNotif = chosenNotif;
    }

    /**
     * Gets the flag indicating whether the user has opted not to receive notifications.
     *
     * @return True if the user has opted not to receive notifications, false otherwise.
     */
    public boolean isNotChosenNotif() {
        return notChosenNotif;
    }

    /**
     * Sets the flag for whether the user has opted not to receive notifications.
     *
     * @param notChosenNotif True if the user has opted not to receive notifications, false otherwise.
     */
    public void setNotChosenNotif(boolean notChosenNotif) {
        this.notChosenNotif = notChosenNotif;
    }
}

