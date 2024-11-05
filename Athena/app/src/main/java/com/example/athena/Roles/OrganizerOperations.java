package com.example.athena.Roles;

public interface OrganizerOperations {

    void createEvent();
    void addFacility();
    void generateQrCode();
    void deleteEventQRcode();
    void deleteEvent();
    void sampleReplacementEntrants();
    void notifyChosenEntrants();
    void notifyCancelledEntrants();

}
