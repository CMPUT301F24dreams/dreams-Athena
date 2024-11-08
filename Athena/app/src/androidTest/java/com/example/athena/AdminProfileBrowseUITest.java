package com.example.athena;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.athena.AdminFragments.adminProfileBrowse;
import com.example.athena.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * UI tests for the adminProfileBrowse fragment.
 * This class tests the user interface interactions and ensures they behave as expected.
 */
@RunWith(AndroidJUnit4.class)
public class AdminProfileBrowseUITest {

    /**
     * Sets up the environment for the UI tests.
     * This includes initializing Espresso Intents and launching the fragment scenario.
     */
    @Before
    public void setup() {
        Intents.init();

        // Create fragment arguments
        Bundle args = new Bundle();
        args.putString("deviceID", "testDeviceID");

        // Launch the fragment in a container
        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class, args);
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
        onView(withId(R.id.event_view_list)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of fetching and displaying the list of users.
     * This ensures that the users are fetched from the database and displayed correctly.
     */
    @Test
    public void testUserListIsDisplayed() {
        // Check if the user list is displayed
        // the same list is used for events and user lists, will be adjusted in the next iteration
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of clicking a user in the list.
     * This ensures that clicking a user navigates to the detailed user profile page.
     */
    @Test
    public void testUserListItemClick() {
        /// Perform click on the list item and verify navigation to user detail fragment
        onView(withId(R.id.myEventList))
                .perform(click());

        /// Verify that the user detail fragment is displayed by making sure the right view is visible
        onView(withId(R.id.admin_profile_details)).check(matches(isDisplayed()));
    }
}
