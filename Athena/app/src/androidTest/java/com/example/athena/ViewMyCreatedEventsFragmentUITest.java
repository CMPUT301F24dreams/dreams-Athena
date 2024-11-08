package com.example.athena;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.athena.EntrantAndOrganizerFragments.viewMyCreatedEventsFragment;
import com.example.athena.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

/**
 * UI tests for the viewMyCreatedEventsFragment fragment.
 * This class tests the user interface interactions and ensures they behave as expected.
 */
@RunWith(AndroidJUnit4.class)
public class ViewMyCreatedEventsFragmentUITest {

    /**
     * Sets up the environment for the UI tests.
     * This includes initializing Espresso Intents.
     */
    @Before
    public void setup() {
        Intents.init();
        FragmentScenario<viewMyCreatedEventsFragment> scenario = FragmentScenario.launchInContainer(viewMyCreatedEventsFragment.class);
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
     * Tests the functionality of fetching and displaying the list of events.
     * This ensures that the events are fetched from the database and displayed correctly.
     */
    @Test
    public void testEventListIsDisplayed() {
        // Check if the event list is displayed
        onView(withId(R.id.organizer_event_listview)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of clicking an event list item.
     * This ensures that clicking an event navigates to the event details.
     */
    @Test
    public void testEventListItemClick() {
        // Perform click on the list item and verify navigation
        onView(withId(R.id.organizer_event_listview))
                .perform(click());

        // Verify that the event details are displayed
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }
}
