package com.example.athena.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserNotifDetailsTest {

    private userNotifDetails userDetails;

    /**
     * Sets up the test environment before each test.
     * Initializes the userNotifDetails object.
     */
    @Before
    public void setUp() {
        userDetails = new userNotifDetails("John Doe", true, false);
    }

    /**
     * Tests the constructor and getters.
     * Verifies that the constructor correctly initializes the object and getters return the correct values.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("John Doe", userDetails.getUserName());
        assertTrue(userDetails.isChosenNotif());
        assertFalse(userDetails.isNotChosenNotif());
    }

    /**
     * Tests the setters.
     * Verifies that the setters correctly update the object's fields.
     */
    @Test
    public void testSetters() {
        userDetails.setUserName("Jane Doe");
        userDetails.setChosenNotif(false);
        userDetails.setNotChosenNotif(true);

        assertEquals("Jane Doe", userDetails.getUserName());
        assertFalse(userDetails.isChosenNotif());
        assertTrue(userDetails.isNotChosenNotif());
    }

    /**
     * Tests the default constructor and setters.
     * Verifies that the default constructor correctly initializes the object and setters update the fields.
     */
    @Test
    public void testDefaultConstructorAndSetters() {
        userNotifDetails userDetails = new userNotifDetails();
        userDetails.setUserName("Default User");
        userDetails.setChosenNotif(true);
        userDetails.setNotChosenNotif(false);

        assertEquals("Default User", userDetails.getUserName());
        assertTrue(userDetails.isChosenNotif());
        assertFalse(userDetails.isNotChosenNotif());
    }
}
