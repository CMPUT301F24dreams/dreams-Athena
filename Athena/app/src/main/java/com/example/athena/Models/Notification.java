package com.example.athena.Models;

/**
 * Represents a notification in the application, containing details such as the notification's
 * title, body text, icon ID, and an activity link that can be triggered when the notification is interacted with.
 * This class is used to model the structure of a notification that can be displayed to users.
 */
public class Notification {

    private String bodyText;
    private String title;
    private String iconID;
    private String activityLink;

    /**
     * Constructor to initialize a Notification object.
     *
     * @param bodyText The main text content of the notification.
     * @param title The title of the notification.
     * @param activityLink A link that points to an activity or page related to the notification.
     */
    public Notification(String bodyText, String title, String activityLink) {
        this.bodyText = bodyText;
        this.title = title;
        this.activityLink = activityLink;
        // icon id will presumably be the same for all notifications
        // so it will be set here as a placeholder for now.
        this.iconID = "PLACEHOLDER";
    }

    /**
     * Gets the body text of the notification.
     *
     * @return The body text of the notification.
     */
    public String getBodyText() {
        return bodyText;
    }

    /**
     * Gets the title of the notification.
     *
     * @return The title of the notification.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for the notification.
     *
     * @param title The title to set for the notification.
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
