package com.example.athena.Event;

import java.text.SimpleDateFormat;

public class Event {
    private String eventName;
    private String description;
    private String facilityID;
    private Boolean geoRequire;
    private Integer maxParticipants;
    private String poster;
    private SimpleDateFormat regStart;
    private SimpleDateFormat regEnd;
    private SimpleDateFormat eventDate;

    public Event(
        String eventName,
        String description,
        String facilityID,
        Boolean geoRequire,
        Integer maxParticipants,
        String poster,
        SimpleDateFormat regStart,
        SimpleDateFormat regEnd,
        SimpleDateFormat eventDate
        )
        {
            this.eventName = eventName;
            this.description = description;
            this.facilityID = facilityID;
            this.geoRequire = geoRequire;
            this.maxParticipants = maxParticipants;
            this.poster = poster;
            this. regStart = regStart;
            this.regEnd = regEnd;
            this.eventDate = eventDate;
        }

    public void getEventName() {

    }

    public void setEventName() {

    }
}
