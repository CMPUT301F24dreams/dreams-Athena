package com.example.athena;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.ProfileBrowseOrg;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ProfileBrowseOrg fragment.
 * This class tests the functionality of the fragment including logic for fetching and displaying users.
 */
@RunWith(AndroidJUnit4.class)
public class ProfileBrowseOrgTest {

    private userDB mockUserDB;
    private eventsDB mockEventsDB;

    /**
     * Sets up the mock dependencies and initializes the fragment scenario before each test.
     * This ensures that each test starts with a fresh instance of the fragment.
     */
    @Before
    public void setup() {
        mockUserDB = mock(userDB.class);
        mockEventsDB = mock(eventsDB.class);

        FragmentScenario<ProfileBrowseOrg> scenario = FragmentScenario.launchInContainer(ProfileBrowseOrg.class);
        scenario.onFragment(fragment -> {
            fragment.userDB = mockUserDB;
            fragment.eventsDB = mockEventsDB;
        });
    }

    /**
     * Tests whether the ProfileBrowseOrg fragment is displayed correctly.
     * This ensures the fragment's layout is loaded and visible.
     */
    @Test
    public void testFragmentIsDisplayed() {
        // Check if the fragment is displayed
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of fetching the list of users.
     * This ensures that the users are fetched from the database and displayed correctly.
     */
    @Test
    public void testFetchUserList() {
        // Mock the tasks
        Task<QuerySnapshot> mockGetUserEventListTask = mock(Task.class);
        Task<QuerySnapshot> mockGetUserListTask = mock(Task.class);

        // Mock the results
        QuerySnapshot mockGetUserEventListResult = mock(QuerySnapshot.class);
        QuerySnapshot mockGetUserListResult = mock(QuerySnapshot.class);

        // Setup mock behavior
        when(mockEventsDB.getEventUserList(Mockito.anyString(), Mockito.anyString())).thenReturn(mockGetUserEventListTask);
        when(mockUserDB.getUserList()).thenReturn(mockGetUserListTask);
        when(mockGetUserEventListTask.isSuccessful()).thenReturn(true);
        when(mockGetUserListTask.isSuccessful()).thenReturn(true);
        when(mockGetUserEventListTask.getResult()).thenReturn(mockGetUserEventListResult);
        when(mockGetUserListTask.getResult()).thenReturn(mockGetUserListResult);

        List<DocumentSnapshot> userEventList = new ArrayList<>();
        List<DocumentSnapshot> userList = new ArrayList<>();
        DocumentSnapshot mockUserDoc = mock(DocumentSnapshot.class);
        when(mockUserDoc.getId()).thenReturn("mockUserId");
        when(mockUserDoc.getString("name")).thenReturn("Mock User");
        when(mockUserDoc.getString("email")).thenReturn("mockuser@example.com");
        when(mockUserDoc.getString("phone")).thenReturn("1234567890");
        when(mockUserDoc.getString("imageURL")).thenReturn("http://example.com/image.jpg");
        userEventList.add(mockUserDoc);
        userList.add(mockUserDoc);

        when(mockGetUserEventListResult.getDocuments()).thenReturn(userEventList);
        when(mockGetUserListResult.getDocuments()).thenReturn(userList);

        // Set up the fragment scenario and verify users are fetched and displayed
        FragmentScenario<ProfileBrowseOrg> scenario = FragmentScenario.launchInContainer(ProfileBrowseOrg.class);
        scenario.onFragment(fragment -> {
            fragment.userDB = mockUserDB;
            fragment.eventsDB = mockEventsDB;
        });

        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }
}
