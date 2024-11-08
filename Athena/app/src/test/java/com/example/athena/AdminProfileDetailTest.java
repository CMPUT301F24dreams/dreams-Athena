package com.example.athena;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.AdminFragments.adminProfileDetail;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the adminProfileDetail fragment.
 * This class tests the functionality of the fragment including fetching and displaying user details.
 */
@RunWith(AndroidJUnit4.class)
public class AdminProfileDetailTest {

    private userDB mockUserDB;

    /**
     * Sets up the mock dependencies and initializes the fragment scenario before each test.
     * This ensures that each test starts with a fresh instance of the fragment.
     */
    @Before
    public void setup() {
        mockUserDB = mock(userDB.class);

        FragmentScenario<adminProfileDetail> scenario = FragmentScenario.launchInContainer(adminProfileDetail.class);
        scenario.onFragment(fragment -> {
            fragment.usersDB = mockUserDB;
        });
    }

    /**
     * Tests whether the adminProfileDetail fragment is displayed correctly.
     * This ensures the fragment's layout is loaded and visible.
     */
    @Test
    public void testFragmentIsDisplayed() {
        // Check if the fragment is displayed
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of fetching user details.
     * This ensures that the user details are fetched from the database and displayed correctly.
     */
    @Test
    public void testFetchUserDetails() {
        // Mock the task
        Task<DocumentSnapshot> mockGetUserTask = mock(Task.class);

        // Mock the result
        DocumentSnapshot mockUserDoc = mock(DocumentSnapshot.class);
        when(mockGetUserTask.isSuccessful()).thenReturn(true);
        when(mockGetUserTask.getResult()).thenReturn(mockUserDoc);
        when(mockUserDoc.exists()).thenReturn(true);
        when(mockUserDoc.getString("name")).thenReturn("Mock User");
        when(mockUserDoc.getString("email")).thenReturn("mockuser@example.com");
        when(mockUserDoc.getString("phone")).thenReturn("123-456-7890");

        // Setup mock behavior
        when(mockUserDB.getUser(Mockito.anyString())).thenReturn(mockGetUserTask);

        // Set up the fragment scenario and verify user details are fetched and displayed
        FragmentScenario<adminProfileDetail> scenario = FragmentScenario.launchInContainer(adminProfileDetail.class);
        scenario.onFragment(fragment -> {
            fragment.usersDB = mockUserDB;
        });

        onView(withId(R.id.profileName)).check(matches(withText("Name: Mock User")));
        onView(withId(R.id.ProfileEmail)).check(matches(withText("Email: mockuser@example.com")));
        onView(withId(R.id.ProfileNumber)).check(matches(withText("Number: 123-456-7890")));
    }
}
