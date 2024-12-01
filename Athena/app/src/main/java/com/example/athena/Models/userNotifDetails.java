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
     * Default constructor for creating an instance without setting any fields.
     */
    public userNotifDetails() {
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

