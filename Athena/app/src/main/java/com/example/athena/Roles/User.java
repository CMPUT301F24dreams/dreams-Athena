package com.example.athena.Roles;

import android.media.Image;

import java.util.ArrayList;

/**
 *This class represents users in the application
 * and implements the roles of the different types of users in the application
 */
public class User implements AdminOperations, EntrantOperations, OrganizerOperations {
    String name;
    ArrayList<String> roles;
    String email;
    Integer phoneNumber;
    Image profilePicture;
    ArrayList<String> invitedEvents;
    ArrayList<String> events;

    public User(String name, ArrayList<String> roles, String email){
        this.name = name;
        this.roles = new ArrayList<String>();
        this.email = email;
    }

    /**
     * This method implementation will contain the logic for an admin to be able to delete a user
     */
    @Override
    public void deleteUser(){
        if(roles.contains("admin")){

        }

    }

    /**
     * This method implementation will contain the logic for an admin to be able to delete a QR Code
     */
    @Override
    public void deleteQRCode(){
        if(roles.contains("admin")){

        }

    }

    /**
     * This method implementation will contain the logic for an admin to be able to delete an event image
     */
    @Override
    public void deleteEventImage(){
        if(roles.contains("admin")){

        }

    }

    /**
     * This method implementation will contain the logic for an admin to be able to delete a user's profile image
     */
    @Override
    public void deleteUserProfileImage(){
        if(roles.contains("admin")){

        }

    }

    /**
     * This method implementation will contain the logic for an admin to be able to delete a facility
     */
    @Override
    public void deleteFacility(){
        if(roles.contains("admin") || roles.contains("organizer")){

        }

    }

    /**
     * This method implementation will contain the logio for an organizer to be able to create an event
     */
    @Override
    public void createEvent() {
        if(roles.contains("organizer")){

        }

    }

    /**
     * This method implementation will contain the logic for an organizer to be able to add a facility
     */
    @Override
    public void addFacility(){
        if(roles.contains("organizer")){

        }

    }

    /**
     * This method implementation will contain the logic for an organizer to be able to add a facility
     */
    @Override
    public void generateQrCode(){
        if(roles.contains("organizer")){

        }

    }

    /**
     * This method implementation will contain the logic for an organizer or admin
     * to be able to delete and Event QR Code
     */
    @Override
    public void deleteEventQRcode(){
        if(roles.contains("admin") || roles.contains("organizer")){

        }

    }

    /**
     * This method implementation will contain the logic for an admin or organizer to be able to delete an event
     */
    @Override
    public void deleteEvent(){
        if(roles.contains("admin") || roles.contains("organizer")){

        }
    }
    /**
     * This method implementation will contain the logic
     * for an organizer to be able to sample a replacement for a cancelled entrant
     */
    @Override
    public void sampleReplacementEntrants(){
        if(roles.contains("organizer")){

        }
    }

    /**
     * This method implementation will contain the logic
     * for an organizer to be able to notify entrants that they have been selected for an event
     */
    @Override
    public void notifyChosenEntrants(){
        if(roles.contains("organizer")){

        }
    }

    /**
     * This method implementation will contain the logic
     * for an organizer to be able to notify cancelled entrants that they have been selected for an event
     */
    @Override
    public void notifyCancelledEntrants(){
        if(roles.contains("organizer")){

        }
    }

    /**
     * This method implementation will contain the logic
     * for an entrant to be able to join an event
     */
    @Override
    public void joinEvent(){
        if(roles.contains("entrant")){

        }

    }

    /**
     * This method implementation will contain the logic
     * for an entrant to be able to accept an event invitation
     */
    @Override
    public void acceptEventInvitation(){
        if(roles.contains("entrant")){

        }

    }

    /**
     * This method implementation will contain the logic
     * for an entrant to be able to deny an event invitation
     */
    @Override
    public void denyEventInvitation(){
        if(roles.contains("entrant")){

        }

    }

    /**
     * This method implementation will contain the logic
     * for an entrant to be able to join a waitlist
     */
    @Override
    public void joinWaitlist(){
        if(roles.contains("entrant")){

        }

    }

}
