package com.example.athena.Models;

import java.util.Objects;

public class detailsForNotification {
    private userNotifDetails user;
    private String eventName;
    private String eventId;
    private String notifType;

    public detailsForNotification(userNotifDetails user, String eventName, String eventId, String notifType) {
        this.user = user;
        this.eventName = eventName;
        this.eventId = eventId;
        this.notifType = notifType;
    }

    public userNotifDetails getUser() {
        return user;
    }

    public void setUser(userNotifDetails user) {
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getNotifType() {
        return notifType;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }
}
