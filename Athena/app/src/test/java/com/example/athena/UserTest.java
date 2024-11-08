package com.example.athena;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.athena.Models.Event;
import com.example.athena.Models.User;

public class UserTest {

    private User user;
    private User userWithoutImage;
    private Event event;

    /**
     * Sets up the test environment before each test.
     * Initializes the User and Event objects.
     */
    @Before
    public void setUp() {

        user = new User("Jane Doe", "jane.doe@example.com", "098-765-4321");
        event = new Event("Test Event", "http://example.com/event.jpg", "event123");
    }

    /**
     * Tests the constructor and getters for a user with an image.
     * Verifies that the constructor correctly initializes the object and getters return the correct values.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("123-456-7890", user.getPhone());
        assertNotNull(user.getEvents());
    }

    /**
     * Tests the setters for a user.
     * Verifies that the setters correctly update the object's fields.
     */
    @Test
    public void testSetters() {
        user.setName("Johnny Doe");
        user.setEmail("johnny.doe@example.com");
        user.setPhone("111-222-3333");;

        assertEquals("Johnny Doe", user.getName());
        assertEquals("johnny.doe@example.com", user.getEmail());
        assertEquals("111-222-3333", user.getPhone());
    }

    /**
     * Tests the functionality of adding an event to the user's event list.
     * Verifies that the event is added correctly.
     */
    @Test
    public void testAddEvent() {
        user.addEvent(event);
        assertEquals(1, user.getEvents().size());
        assertEquals(event, user.getEvents().get(0));
    }
}
