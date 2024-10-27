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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public Boolean getGeoRequire() {
        return geoRequire;
    }

    public void setGeoRequire(Boolean geoRequire) {
        this.geoRequire = geoRequire;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public SimpleDateFormat getRegStart() {
        return regStart;
    }

    public void setRegStart(SimpleDateFormat regStart) {
        this.regStart = regStart;
    }

    public SimpleDateFormat getRegEnd() {
        return regEnd;
    }

    public void setRegEnd(SimpleDateFormat regEnd) {
        this.regEnd = regEnd;
    }

    public SimpleDateFormat getEventDate() {
        return eventDate;
    }

    public void setEventDate(SimpleDateFormat eventDate) {
        this.eventDate = eventDate;
    }
}
