package com.example.athena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.athena.EntrantAndOrganizerFragments.userViewNotisChose;
import com.example.athena.R;

import org.junit.After;
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
 * UI tests for the userViewNotisChose DialogFragment.
 * This class tests the user interface interactions and ensures they behave as expected.
 */
@RunWith(AndroidJUnit4.class)
public class UserViewNotisChoseUITest {

    private userViewNotisChose.acceptDeclineListener mockListener;

    /**
     * Sets up the environment for the UI tests.
     * This includes initializing Espresso Intents and setting up the mock listener.
     */
    @Before
    public void setup() {
        Intents.init();
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
     * Cleans up the environment after the UI tests.
     * This includes releasing Espresso Intents.
     */
    @After
    public void cleanup() {
        Intents.release();
    }

    /**
     * Tests whether the dialog is displayed correctly.
     * This ensures that the dialog's layout is loaded and visible.
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
