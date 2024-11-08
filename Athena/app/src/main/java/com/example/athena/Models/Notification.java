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
     * Sets the body text for the notification.
     *
     * @param bodyText The body text to set.
     */
    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
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

    /**
     * Gets the icon ID of the notification, which can be used to set the notification icon.
     *
     * @return The icon ID for the notification.
     */
    public String getIconID() {
        return iconID;
    }

    /**
     * Sets the icon ID for the notification.
     *
     * @param iconID The icon ID to set.
     */
    public void setIconID(String iconID) {
        this.iconID = iconID;
    }

    /**
     * Gets the activity link associated with the notification.
     *
     * @return The link to the activity or page triggered by the notification.
     */
    public String getActivityLink() {
        return activityLink;
    }

    /**
     * Sets the activity link for the notification.
     *
     * @param activityLink The activity link to set.
     */
    public void setActivityLink(String activityLink) {
        this.activityLink = activityLink;
    }
}
