package com.example.athena.Models;

import java.util.Objects;

/**
 * Represents the details of a notification, including information about the user, event name,
 * event ID, and notification type. This class is used to encapsulate all necessary data
 * for a notification before it is processed and displayed.
 */
public class DetailsForNotification {

    private UserNotifDetails user;
    private String eventName;
    private String eventId;
    private String status;

    /**
     * Constructor to initialize the details of a notification.
     *
     * @param user The user associated with the notification.
     * @param eventName The name of the event triggering the notification.
     * @param eventId The unique identifier for the event.
     * @param notifType The type of the notification (e.g., email, push).
     */
    public DetailsForNotification(UserNotifDetails user, String eventName, String eventId, String notifType) {
        this.user = user;
        this.eventName = eventName;
        this.eventId = eventId;
        this.status = notifType;
    }

    ;

    /**
     * Gets the user details associated with the notification.
     *
     * @return The user details object.
     */
    public UserNotifDetails getUser() {
        return user;
    }

    /**
     * Sets the user details for the notification.
     *
     * @param user The user details to set.
     */
    public void setUser(UserNotifDetails user) {
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
     * Gets the type of the notification (e.g., email, push).
     *
     * @return The notification type.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the notification type for the notification.
     *
     * @param status The notification type to set.
     */
    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsForNotification that = (DetailsForNotification) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(eventName, that.eventName) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, eventName, eventId, status);
    }
}
