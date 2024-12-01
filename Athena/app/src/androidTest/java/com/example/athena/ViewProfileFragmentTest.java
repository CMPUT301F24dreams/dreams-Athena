package com.example.athena;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.*;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import androidx.fragment.app.testing.FragmentScenario;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.profileNotiEditFragment;
import com.example.athena.EntrantAndOrganizerFragments.profileScreenEditFragment;
import com.example.athena.EntrantAndOrganizerFragments.viewProfileFragment;
import com.example.athena.Firebase.imageDB;
import com.example.athena.Firebase.userDB;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;


@RunWith(AndroidJUnit4.class)
public class ViewProfileFragmentTest {

    private userDB mockUserDB;
    private imageDB mockImageDB;

    @Before
    public void setUp() {

        // Initialize mock objects
        mockUserDB = Mockito.mock(userDB.class);
        mockImageDB = Mockito.mock(imageDB.class);

        // Use FragmentScenario to launch the fragment
        FragmentScenario<viewProfileFragment> scenario = FragmentScenario.launch(viewProfileFragment.class);

        /// Issue: had to make dbs public to run these tests, idk how else to do it
        scenario.onFragment(fragment -> {
            // Inject mock objects into the fragment's fields
            fragment.usersDB = mockUserDB;
            fragment.imageDB = mockImageDB;
        });
    }

    @Test
    public void testDeleteProfilePicture() {
        // click delete pic button
        onView(withId(R.id.DeletePicture)).perform(click());

        // Verify that delete image methods were called in the database
        verify(mockUserDB).resetImage(anyString());
        verify(mockImageDB).deleteImage(anyString());

        // check to see if url value is ""
        verify(mockUserDB).resetImage("");  // Check if the URL is cleared in the mock UserDB
    }

    @Test
    public void testEditProfileDetailsButton() {
        // click edit all button
        onView(withId(R.id.EditProfileAll)).perform(click());

        // check if view changed
        intended(hasComponent(profileScreenEditFragment.class.getName()));
    }

    @Test
    public void testNotifProfileButton() {
        // click noti button
        onView(withId(R.id.EditNotfis)).perform(click());

        // check if view changed
        intended(hasComponent(profileNotiEditFragment.class.getName()));
    }
}

