package com.example.athena;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.userViewNotisChose;
import com.example.athena.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the userViewNotisChose DialogFragment.
 * This class tests the functionality of the dialog fragment including UI interactions
 * and the logic for accepting or declining event invitations.
 */
@RunWith(AndroidJUnit4.class)
public class UserViewNotisChoseTest {

    private userViewNotisChose.acceptDeclineListener mockListener;

    /**
     * Sets up the mock listener and initializes the fragment scenario before each test.
     * This ensures that each test starts with a fresh instance of the dialog fragment.
     */
    @Before
    public void setup() {
        // Mock the listener
        mockListener = mock(userViewNotisChose.acceptDeclineListener.class);

        // Create fragment arguments
        Bundle args = new Bundle();
        args.putString("name", "Test Event");
        args.putString("description", "A test event description");
        args.putInt("pos", 0);

        // Launch the fragment in a container
        FragmentScenario<userViewNotisChose> scenario = FragmentScenario.launchInContainer(userViewNotisChose.class, args);
        scenario.onFragment(fragment -> {
            fragment.setTargetFragment((Fragment) mockListener, 0);
        });
    }

    /**
     * Tests whether the dialog is displayed correctly.
     * This ensures the dialog's layout is loaded and visible.
     */
    @Test
    public void testDialogIsDisplayed() {
        // Check if the dialog is displayed
        onView(withText("YOU'RE INVITED!")).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of the Accept button.
     * This ensures that clicking the Accept button calls the acceptInvite method on the listener.
     */
    @Test
    public void testAcceptButton() {
        // Click the Accept button
        onView(withText("Accept")).perform(click());

        // Verify that the acceptInvite method was called on the listener
        verify(mockListener).acceptInvite(0);
    }

    /**
     * Tests the functionality of the Decline button.
     * This ensures that clicking the Decline button calls the declineInvite method on the listener.
     */
    @Test
    public void testDeclineButton() {
        // Click the Decline button
        onView(withText("Decline")).perform(click());

        // Verify that the declineInvite method was called on the listener
        verify(mockListener).declineInvite(0);
    }

    /**
     * Tests whether the event details are displayed correctly in the dialog.
     * This ensures that the event name and description are correctly set in the dialog's layout.
     */
    @Test
    public void testEventDetailsDisplayed() {
        // Check if event title and description are displayed correctly
        onView(withId(R.id.accept_event_title)).check(matches(withText("Test Event")));
        onView(withId(R.id.accept_discription)).check(matches(withText("you have been invited to A test event description. Do you wish to accept or decline the invite?")));
    }
}
