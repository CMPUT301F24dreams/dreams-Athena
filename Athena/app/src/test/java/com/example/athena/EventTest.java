package com.example.athena;

import com.example.athena.Models.Event;
import com.example.athena.Models.Event;
import com.example.athena.WaitList.WaitList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EventTest {

    private Event eventWithFullProperties;
    private Event eventWithBasicProperties;
    private WaitList mockWaitList;

    /**
     * Sets up the test environment before each test.
     * Initializes the Event objects and mock WaitList.
     */
    @Before
    public void setUp() {
        mockWaitList = mock(WaitList.class);

        eventWithFullProperties = new Event("Full Event", "http://example.com/image.jpg", "This is a full event description", "Organizer", "Facility", 100, true, "event123");
        eventWithBasicProperties = new Event("Basic Event", "http://example.com/image.jpg", "event456");

        eventWithBasicProperties.waitList = mockWaitList;  // Mock the WaitList for testing purposes
    }

    /**
     * Tests the constructor and getters for an event with full properties.
     * Verifies that the constructor correctly initializes the object and getters return the correct values.
     */
    @Test
    public void testConstructorAndGettersWithFullProperties() {
        assertEquals("Full Event", eventWithFullProperties.getEventName());
        assertEquals("http://example.com/image.jpg", eventWithFullProperties.getImageURL());
        assertEquals("This is a full event description", eventWithFullProperties.getEventDescription());
        assertEquals("Organizer", eventWithFullProperties.getOrganizer());
        assertEquals("Facility", eventWithFullProperties.getFacility());
        assertEquals(Integer.valueOf(100), eventWithFullProperties.getMaxParticipants());
        assertTrue(eventWithFullProperties.getGeoRequire());
        assertEquals("event123", eventWithFullProperties.getEventID());
    }

    /**
     * Tests the constructor and getters for an event with basic properties.
     * Verifies that the constructor correctly initializes the object and getters return the correct values.
     */
    @Test
    public void testConstructorAndGettersWithBasicProperties() {
        assertEquals("Basic Event", eventWithBasicProperties.getEventName());
        assertEquals("http://example.com/image.jpg", eventWithBasicProperties.getImageURL());
        assertEquals("event456", eventWithBasicProperties.getEventID());
        assertNotNull(eventWithBasicProperties.getWaitList());
    }

    /**
     * Tests the setters for an event.
     * Verifies that the setters correctly update the object's fields.
     */
    @Test
    public void testSetters() {
        eventWithFullProperties.setEventName("Updated Event Name");
        eventWithFullProperties.setEventDescription("Updated Event Description");
        eventWithFullProperties.setOrganizer("Updated Organizer");
        eventWithFullProperties.setFacility("Updated Facility");
        eventWithFullProperties.setMaxParticipants(200);
        eventWithFullProperties.setGeoRequire(false);

        assertEquals("Updated Event Name", eventWithFullProperties.getEventName());
        assertEquals("Updated Event Description", eventWithFullProperties.getEventDescription());
        assertEquals("Updated Organizer", eventWithFullProperties.getOrganizer());
        assertEquals("Updated Facility", eventWithFullProperties.getFacility());
        assertEquals(Integer.valueOf(200), eventWithFullProperties.getMaxParticipants());
        assertFalse(eventWithFullProperties.getGeoRequire());
    }

    /**
     * Tests the addUser method.
     * Verifies that the addUser method correctly interacts with the WaitList object.
     */
    @Test
    public void testAddUser() {
        eventWithBasicProperties.addUser("user123", "event456");
        verify(mockWaitList, times(1)).addWaiting("user123", "event456");
    }

    /**
     * Tests the chooseUsers method.
     * Verifies that the chooseUsers method correctly interacts with the WaitList object.
     */
    @Test
    public void testChooseUsers() {
        eventWithBasicProperties.chooseUsers(10, "event456");
        verify(mockWaitList, times(1)).selectUsersToInvite(10, "event456");
    }

    /**
     * Tests the removeUser method.
     * Verifies that the removeUser method correctly interacts with the WaitList object.
     */
    @Test
    public void testRemoveUser() {
        eventWithBasicProperties.removeUser("user123", "event456");
        verify(mockWaitList, times(1)).removeUser("user123", "event456");
    }

    /**
     * Tests the moveUser method.
     * Verifies that the moveUser method correctly interacts with the WaitList object.
     */
    @Test
    public void testMoveUser() {
        eventWithBasicProperties.moveUser("user123", "confirmed");
        verify(mockWaitList, times(1)).moveUserFromInvited("user123", "confirmed");
    }
}
