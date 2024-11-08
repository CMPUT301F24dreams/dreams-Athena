package com.example.athena;

import android.os.Bundle;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.truth.content.IntentSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onView;

import static org.hamcrest.Matchers.not;

import com.example.athena.EntrantAndOrganizerFragments.homeScreen;
import com.example.athena.R;

@RunWith(AndroidJUnit4.class)
public class HomeScreenTest {
///some tests in this package are derived from copilot: https://copilot.microsoft.com/chats/XkgPPffcGXrUadGYr6Wvi, 2024-11-08
    @Before
    public void setup() {
        // Initialize fragment scenario
        FragmentScenario<homeScreen> scenario = FragmentScenario.launchInContainer(homeScreen.class);
    }

    @Test
    public void testNotificationsButtonIsDisplayed() {
        // Check if notifications button is displayed
        onView(withId(R.id.check_updates_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickNotificationsButton() {
        // Perform click on notifications button and check if the correct fragment is displayed
        onView(withId(R.id.check_updates_button)).perform(click());
        onView(withId(R.id.user_notifications_listview)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickProfilePictureButton() {
        // Perform click on profile picture button and check if the correct fragment is displayed
        onView(withId(R.id.profile_picture_button)).perform(click());
        onView(withId(R.id.BackButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testMoreOptionsButtonVisibility() {
        // Check if the app drawer is hidden initially
        onView(withId(R.id.more_options_drawer)).check(matches(not(isDisplayed())));
        // Perform click on more options button to show the drawer
        onView(withId(R.id.more_options_button)).perform(click());
        // Check if the app drawer is displayed
        onView(withId(R.id.more_options_drawer)).check(matches(isDisplayed()));
    }

    @Test
    public void testCloseDrawerButton() {
        // Perform click on more options button to show the drawer
        onView(withId(R.id.more_options_button)).perform(click());
        // Perform click on close drawer button to hide the drawer
        onView(withId(R.id.close_drawer_button)).perform(click());
        // Check if the app drawer is hidden
        onView(withId(R.id.more_options_drawer)).check(matches(not(isDisplayed())));
    }
}
