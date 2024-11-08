package com.example.athena;

import com.example.athena.Models.Event;
import com.example.athena.WaitList.WaitList;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WaitListTest {

    private WaitList waitList;
    private Event event;

    /**
     * Sets up the test environment before each test.
     * Initializes the WaitList and Event objects.
     */
    @Before
    public void setUp() {
        event = new Event("Test Event", "http://example.com/image.jpg", "event123");
        waitList = new WaitList(event);
    }

    /**
     * Tests the initialization of the waitlist.
     * Verifies that the waitlist is correctly initialized with empty lists.
     */
    @Test
    public void testInitialization() {
        assertNotNull(waitList.getWaiting());
        assertNotNull(waitList.getInvited());
        assertNotNull(waitList.getDeclined());
        assertNotNull(waitList.getAccepted());
        assertTrue(waitList.getWaiting().isEmpty());
        assertTrue(waitList.getInvited().isEmpty());
        assertTrue(waitList.getDeclined().isEmpty());
        assertTrue(waitList.getAccepted().isEmpty());
    }

    /**
     * Tests adding a user to the waiting list.
     * Verifies that the user is correctly added to the waiting list.
     */
    @Test
    public void testAddWaiting() {
        waitList.addWaiting("user1", "event123");
        assertEquals(1, waitList.getWaiting().size());
        assertTrue(waitList.getWaiting().contains("user1"));
    }

    /**
     * Tests removing a user from the waiting list.
     * Verifies that the user is correctly removed from the waiting list.
     */
    @Test
    public void testRemoveUser() {
        waitList.addWaiting("user1", "event123");
        waitList.removeUser("user1", "event123");
        assertEquals(0, waitList.getWaiting().size());
        assertFalse(waitList.getWaiting().contains("user1"));
    }

    /**
     * Tests selecting users to invite.
     * Verifies that the specified number of users are invited from the waiting list.
     */
    @Test
    public void testSelectUsersToInvite() {
        waitList.addWaiting("user1", "event123");
        waitList.addWaiting("user2", "event123");
        waitList.selectUsersToInvite(1, "event123");

        assertEquals(1, waitList.getInvited().size());
        assertEquals(1, waitList.getWaiting().size());
    }

    /**
     * Tests moving a user from invited to accepted.
     * Verifies that the user is correctly moved from the invited list to the accepted list.
     */
    @Test
    public void testMoveUserFromInvitedToAccepted() {
        waitList.addWaiting("user1", "event123");
        waitList.selectUsersToInvite(1, "event123");
        waitList.moveUserFromInvited("user1", "accepted");

        assertEquals(1, waitList.getAccepted().size());
        assertFalse(waitList.getInvited().contains("user1"));
    }

    /**
     * Tests moving a user from invited to declined.
     * Verifies that the user is correctly moved from the invited list to the declined list.
     */
    @Test
    public void testMoveUserFromInvitedToDeclined() {
        waitList.addWaiting("user1", "event123");
        waitList.selectUsersToInvite(1, "event123");
        waitList.moveUserFromInvited("user1", "declined");

        assertEquals(1, waitList.getDeclined().size());
        assertFalse(waitList.getInvited().contains("user1"));
    }
}
