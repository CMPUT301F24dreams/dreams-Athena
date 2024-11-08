package com.example.athena;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import androidx.fragment.app.testing.FragmentScenario;

import com.example.athena.EntrantAndOrganizerFragments.profileScreenEditFragment;
import com.example.athena.EntrantAndOrganizerFragments.viewProfileFragment;


@RunWith(AndroidJUnit4.class)
public class ProfileEditTest {

    @Before
    public void setUp(){
        FragmentScenario<profileScreenEditFragment> scenario =
                FragmentScenario.launch(profileScreenEditFragment.class);

    }

    @Test
    public void testProfileEditing() {
        // Define new values for testing
        String newName = "New Name";
        String newNumber = "123456789";
        String newEmail = "newemail@example.com";

        // Click the Edit button for Name
        onView(withId(R.id.EditName)).perform(click());

        // Enter new name in the TextView
        onView(withId(R.id.profileName3)).perform(replaceText(newName), closeSoftKeyboard());

        // Click the Edit button for Number
        onView(withId(R.id.EditNumber)).perform(click());

        // Enter new number in the TextView
        onView(withId(R.id.profileNumber2)).perform(replaceText(newNumber), closeSoftKeyboard());

        // Click the Edit button for Email
        onView(withId(R.id.EditEmail)).perform(click());

        // Enter new email in the TextView
        onView(withId(R.id.profileEmail2)).perform(replaceText(newEmail), closeSoftKeyboard());

        // Click the Save button
        // onView(withId(R.id.saveProfile)).perform(click());

        // Verify that the TextViews display the new values
        onView(withId(R.id.profileName3)).check(matches(withText(newName)));
        onView(withId(R.id.profileNumber2)).check(matches(withText(newNumber)));
        onView(withId(R.id.profileEmail2)).check(matches(withText(newEmail)));
    }
}
