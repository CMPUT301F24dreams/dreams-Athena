package com.example.athena;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.athena.Models.Notification;

public class NotificationTest {

    private Notification notification;

    /**
     * Sets up the test environment before each test.
     * Initializes the Notification object.
     */
    @Before
    public void setUp() {
        notification = new Notification("This is the body text", "Notification Title", "activityLink123");
    }

    /**
     * Tests the constructor and getters.
     * Verifies that the constructor correctly initializes the object and getters return the correct values.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("This is the body text", notification.getBodyText());
        assertEquals("Notification Title", notification.getTitle());
        assertEquals("activityLink123", notification.getActivityLink());
        assertEquals("PLACEHOLDER", notification.getIconID());
    }

    /**
     * Tests the setters.
     * Verifies that the setters correctly update the object's fields.
     */
    @Test
    public void testSetters() {
        notification.setBodyText("Updated body text");
        notification.setTitle("Updated Title");
        notification.setActivityLink("updatedActivityLink");
        notification.setIconID("updatedIconID");

        assertEquals("Updated body text", notification.getBodyText());
        assertEquals("Updated Title", notification.getTitle());
        assertEquals("updatedActivityLink", notification.getActivityLink());
        assertEquals("updatedIconID", notification.getIconID());
    }
}
