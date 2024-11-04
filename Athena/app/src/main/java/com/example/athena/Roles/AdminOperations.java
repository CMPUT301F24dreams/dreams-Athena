package com.example.athena.Roles;


/**
 * This is an interface which is used to represent the administrator and the administrator's operations
 */
public interface AdminOperations {
    /**
     * This is the method which a user with admin privileges will be able to use to delete users,
     * *granted the user is not administrator themselves*
     */
    void deleteUser();

    /**
     * This is the method which a user with admin privileges will be able to use to delete Images
     */
    void deleteUserProfileImage();

    /**
     * This is the method which a user with admin privileges will be able to use to delete Facilities
     */
    void deleteQRCode();

    /**
     * This is the method which a user with admin privileges will be able to use to delete Facilities
     */
    void deleteFacility();

    /**
     * This is the method which a user with admin privileges will be able to use to Event Images
     */
    void deleteEventImage();




}
