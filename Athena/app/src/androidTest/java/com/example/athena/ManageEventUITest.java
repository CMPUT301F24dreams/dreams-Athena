package com.example.athena;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.athena.EntrantAndOrganizerFragments.ManageEvent;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;

/**
 * UI tests for the ManageEvent fragment.
 * This class tests the user interface interactions and ensures they behave as expected.
 */
@RunWith(AndroidJUnit4.class)
public class ManageEventUITest {

    private ManageEvent fragment;
    private eventsDB mockEventDB;
    private userDB mockUserDB;

    /**
     * Sets up the environment for the UI tests.
     * This includes initializing Espresso Intents and setting up mock dependencies.
     */
    @Before
    public void setup() {
        Intents.init();

        mockEventDB = mock(eventsDB.class);
        mockUserDB = mock(userDB.class);

        // Create fragment arguments
        Bundle args = new Bundle();
        args.putString("eventID", "testEventID");

        // Launch the fragment in a container
        FragmentScenario<ManageEvent> scenario = FragmentScenario.launchInContainer(ManageEvent.class, args);
        scenario.onFragment(fragment -> {
            fragment.eventDB = mockEventDB;
            fragment.userDB = mockUserDB;
            this.fragment = fragment;
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
     * Tests whether the fragment is displayed correctly.
     * This ensures that the fragment's layout is loaded and visible.
     */
    @Test
    public void testFragmentIsDisplayed() {
        // Check if the fragment is displayed
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of the Notify Entrants button.
     * This ensures that clicking the Notify Entrants button opens the dialog.
     */
    @Test
    public void testNotifyEntrantsButton() {
        // Click the Notify Entrants button
        onView(withId(R.id.notify_entrants_button)).perform(click());

        // Check if the dialog is displayed
        onView(withText("ChoseNumEnt")).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of the View Invited button.
     * This ensures that clicking the View Invited button navigates to the ProfileBrowseOrg fragment.
     */
    @Test
    public void testViewInvitedButton() {
        // Click the View Invited button
        onView(withId(R.id.view_selected_entrants)).perform(click());

        // Check if the new fragment is displayed
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of the View Accepted button.
     * This ensures that clicking the View Accepted button navigates to the ProfileBrowseOrg fragment.
     */
    @Test
    public void testViewAcceptedButton() {
        // Click the View Accepted button
        onView(withId(R.id.viewAcceptedBtn)).perform(click());

        // Check if the new fragment is displayed
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of the View Cancelled button.
     * This ensures that clicking the View Cancelled button navigates to the ProfileBrowseOrg fragment.
     */
    @Test
    public void testViewCancelledButton() {
        // Click the View Cancelled button
        onView(withId(R.id.viewCanclledBtn)).perform(click());

        // Check if the new fragment is displayed
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }
}
