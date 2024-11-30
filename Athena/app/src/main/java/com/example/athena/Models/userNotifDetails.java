package com.example.athena.Models;

/**
 * Represents the notification preferences of a user. This class stores the user's name
 * along with their choices for receiving or not receiving notifications. It is used to manage
 * individual notification settings for users within the application.
 */
public class userNotifDetails {

    private String userName;
    private boolean notifyIfChosen;
    private boolean notifyIfNotChosen;

    /**
     * Constructor to initialize the notification preferences for a user.
     *
     * @param userName The name of the user.
     * @param notifyIfChosen A flag indicating whether the user has chosen to receive notifications.
     * @param notifyIfNotChosen A flag indicating whether the user has opted not to receive notifications.
     */
    public userNotifDetails(String userName, boolean notifyIfChosen, boolean notifyIfNotChosen) {
        this.userName = userName;
        this.notifyIfChosen = notifyIfChosen;
        this.notifyIfNotChosen = notifyIfNotChosen;
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
    public boolean isNotifyIfChosen() {
        return notifyIfChosen;
    }

    /**
     * Sets the flag for whether the user has chosen to receive notifications.
     *
     * @param notifyIfChosen True if the user has chosen to receive notifications, false otherwise.
     */
    public void setNotifyIfChosen(boolean notifyIfChosen) {
        this.notifyIfChosen = notifyIfChosen;
    }

    /**
     * Gets the flag indicating whether the user has opted not to receive notifications.
     *
     * @return True if the user has opted not to receive notifications, false otherwise.
     */
    public boolean isNotifyIfNotChosen() {
        return notifyIfNotChosen;
    }

    /**
     * Sets the flag for whether the user has opted not to receive notifications.
     *
     * @param notifyIfNotChosen True if the user has opted not to receive notifications, false otherwise.
     */
    public void setNotifyIfNotChosen(boolean notifyIfNotChosen) {
        this.notifyIfNotChosen = notifyIfNotChosen;
    }
}

