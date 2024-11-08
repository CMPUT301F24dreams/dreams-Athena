package com.example.athena;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.athena.AdminFragments.adminProfileDetail;
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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI tests for the adminProfileDetail fragment.
 * This class tests the user interface interactions and ensures they behave as expected.
 */
@RunWith(AndroidJUnit4.class)
public class AdminProfileDetailUITest {

    /**
     * Sets up the environment for the UI tests.
     * This includes initializing Espresso Intents and launching the fragment scenario.
     */
    @Before
    public void setup() {
        Intents.init();

        // Create fragment arguments
        Bundle args = new Bundle();
        args.putString("userID", "testUserID");

        // Launch the fragment in a container
        FragmentScenario<adminProfileDetail> scenario = FragmentScenario.launchInContainer(adminProfileDetail.class, args);
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
     * Tests the functionality of fetching and displaying user details.
     * This ensures that the user details are fetched from the database and displayed correctly.
     */
    @Test
    public void testUserDetailsAreDisplayed() {
        // Check if user details are displayed correctly
        onView(withId(R.id.profileName)).check(matches(withText("Name: Mock User")));
        onView(withId(R.id.ProfileEmail)).check(matches(withText("Email: mockuser@example.com")));
        onView(withId(R.id.ProfileNumber)).check(matches(withText("Number: 123-456-7890")));
    }

    /**
     * Tests the functionality of the back button.
     * This ensures that clicking the back button navigates to the previous fragment.
     */
    @Test
    public void testBackButton() {
        // Click the back button
        onView(withId(R.id.backBtnProfileAdmin)).perform(click());

        // Verify that the previous fragment is displayed
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of the delete button.
     * This ensures that clicking the delete button shows the delete confirmation dialog.
     */
    @Test
    public void testDeleteButton() {
        // Click the delete button
        onView(withId(R.id.deleteBtnProfileAdmin)).perform(click());

        // Verify that the delete confirmation dialog is displayed
        onView(withText("DELETE PROFILE?")).check(matches(isDisplayed()));
    }
}
