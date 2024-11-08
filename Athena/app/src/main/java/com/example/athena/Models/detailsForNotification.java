package com.example.athena.Models;

import java.util.Objects;

/**
 * Represents the details of a notification, including information about the user, event name,
 * event ID, and notification type. This class is used to encapsulate all necessary data
 * for a notification before it is processed and displayed.
 */
public class detailsForNotification {

    private userNotifDetails user;
    private String eventName;
    private String eventId;
    private String notifType;

    /**
     * Constructor to initialize the details of a notification.
     *
     * @param user The user associated with the notification.
     * @param eventName The name of the event triggering the notification.
     * @param eventId The unique identifier for the event.
     * @param notifType The type of the notification (e.g., email, push).
     */
    public detailsForNotification(userNotifDetails user, String eventName, String eventId, String notifType) {
        this.user = user;
        this.eventName = eventName;
        this.eventId = eventId;
        this.notifType = notifType;
    }

    /**
     * Gets the user details associated with the notification.
     *
     * @return The user details object.
     */
    public userNotifDetails getUser() {
        return user;
    }

    /**
     * Sets the user details for the notification.
     *
     * @param user The user details to set.
     */
    public void setUser(userNotifDetails user) {
        this.user = user;
    }

    /**
     * Gets the name of the event triggering the notification.
     *
     * @return The event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the event name for the notification.
     *
     * @param eventName The event name to set.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the unique identifier for the event.
     *
     * @return The event ID.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the event ID for the notification.
     *
     * @param eventId The event ID to set.
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets the type of the notification (e.g., email, push).
     *
     * @return The notification type.
     */
    public String getNotifType() {
        return notifType;
    }

    /**
     * Sets the notification type for the notification.
     *
     * @param notifType The notification type to set.
     */
    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        detailsForNotification that = (detailsForNotification) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(eventName, that.eventName) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(notifType, that.notifType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, eventName, eventId, notifType);
    }
}
