package com.example.athena;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.viewMyCreatedEventsFragment;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
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
 * Unit tests for the viewMyCreatedEventsFragment fragment.
 * This class tests the functionality of the fragment including UI interactions
 * and the logic for fetching and displaying the list of events.
 */
@RunWith(AndroidJUnit4.class)
public class ViewMyCreatedEventsFragmentTest {

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

        FragmentScenario<viewMyCreatedEventsFragment> scenario = FragmentScenario.launchInContainer(viewMyCreatedEventsFragment.class);
        scenario.onFragment(fragment -> {
            fragment.userDB = mockUserDB;
            fragment.eventsDB = mockEventsDB;
        });
    }

    /**
     * Tests whether the viewMyCreatedEventsFragment is displayed correctly.
     * This ensures the fragment's layout is loaded and visible.
     */
    @Test
    public void testFragmentIsDisplayed() {
        // Check if the fragment is displayed
        onView(withId(R.id.organizer_events_view)).check(matches(isDisplayed()));
    }

    /**
     * Tests the functionality of fetching the list of events.
     * This ensures that the events are fetched from the database and displayed correctly.
     */
    @Test
    public void testFetchEventList() {
        // Mock the tasks
        Task<QuerySnapshot> mockGetOrgEventsTask = mock(Task.class);
        Task<QuerySnapshot> mockGetEventListTask = mock(Task.class);

        // Mock the results
        QuerySnapshot mockGetOrgEventsResult = mock(QuerySnapshot.class);
        QuerySnapshot mockGetEventListResult = mock(QuerySnapshot.class);

        // Setup mock behavior
        when(mockUserDB.getOrganizerEvent(Mockito.anyString())).thenReturn(mockGetOrgEventsTask);
        when(mockEventsDB.getEventsList()).thenReturn(mockGetEventListTask);
        when(mockGetOrgEventsTask.isSuccessful()).thenReturn(true);
        when(mockGetEventListTask.isSuccessful()).thenReturn(true);
        when(mockGetOrgEventsTask.getResult()).thenReturn(mockGetOrgEventsResult);
        when(mockGetEventListTask.getResult()).thenReturn(mockGetEventListResult);

        List<DocumentSnapshot> orgEventsList = new ArrayList<>();
        List<DocumentSnapshot> eventsList = new ArrayList<>();
        DocumentSnapshot mockEventDoc = mock(DocumentSnapshot.class);
        when(mockEventDoc.getId()).thenReturn("mockEventId");
        when(mockEventDoc.getString("eventName")).thenReturn("Mock Event");
        when(mockEventDoc.getString("imageURL")).thenReturn("http://example.com/image.jpg");
        orgEventsList.add(mockEventDoc);
        eventsList.add(mockEventDoc);

        when(mockGetOrgEventsResult.getDocuments()).thenReturn(orgEventsList);
        when(mockGetEventListResult.getDocuments()).thenReturn(eventsList);

        // Set up the fragment scenario and verify events are fetched and displayed
        FragmentScenario<viewMyCreatedEventsFragment> scenario = FragmentScenario.launchInContainer(viewMyCreatedEventsFragment.class);
        scenario.onFragment(fragment -> {
            fragment.userDB = mockUserDB;
            fragment.eventsDB = mockEventsDB;
        });

        onView(withId(R.id.organizer_event_listview)).check(matches(isDisplayed()));
    }
}
