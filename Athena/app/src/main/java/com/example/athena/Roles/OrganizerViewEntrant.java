package com.example.athena.Roles;


/**
 * This class represents an Organizer's view entrants
 */
public class OrganizerViewEntrant{
    private String status;
    private String name;
    private Boolean sentNotification;

    public OrganizerViewEntrant(String name, String status, Boolean sentNotification){
        this.status = status;
        this.name = name;
        this.sentNotification = sentNotification;


    }

}
