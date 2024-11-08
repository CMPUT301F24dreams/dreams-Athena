package com.example.athena;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.truth.content.IntentSubject;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;

import com.example.athena.EntrantAndOrganizerFragments.adminProfileBrowse;
import com.example.athena.R;
import com.example.athena.Models.User;
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

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AdminProfileBrowseTest {

    @Before
    public void setup() {
        // Initialize fragment scenario
        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class);
    }

    @Test
    public void testListViewIsDisplayed() {
        // Check if ListView is displayed
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    @Test
    public void testFetchUserList() {
        // Mock userDB to return a mock Task<QuerySnapshot>
        userDB mockUserDB = mock(userDB.class);
        Task<QuerySnapshot> mockTask = mock(Task.class);
        QuerySnapshot mockQuerySnapshot = mock(QuerySnapshot.class);
        when(mockUserDB.getUserList()).thenReturn(mockTask);
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockTask.getResult()).thenReturn(mockQuerySnapshot);

        // Create a list of mock users
        List<DocumentSnapshot> mockDocuments = new ArrayList<>();
        DocumentSnapshot mockDocument = mock(DocumentSnapshot.class);
        when(mockDocument.getString("name")).thenReturn("John Doe");
        when(mockDocument.getString("email")).thenReturn("john.doe@example.com");
        when(mockDocument.getString("phone")).thenReturn("123-456-7890");
        when(mockDocument.getString("imageURL")).thenReturn("https://example.com/image.jpg");
        mockDocuments.add(mockDocument);
        when(mockQuerySnapshot.getDocuments()).thenReturn(mockDocuments);

        // Launch fragment with mock userDB
        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class);
        scenario.onFragment(fragment -> {
            fragment.userDB = mockUserDB;
            fragment.onViewCreated(new View(getApplicationContext()), null);
        });

        // Check if user list is populated correctly
        onView(withId(R.id.myEventList))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testListItemClickNavigatesToUserProfile() {
        // Add a user to the list and check if clicking navigates to profile
        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class);
        scenario.onFragment(fragment -> {
            User user = new User("John Doe", "john.doe@example.com", "123-456-7890", "https://example.com/image.jpg");
            fragment.users.add(user);
            fragment.usersID.add("userID123");
        });

        // Perform click on the list item
        onView(withId(R.id.myEventList))
                .perform(ViewActions.click());

        // Check if the fragment for detailed user profile is displayed
        onView(withId(R.id.content_frame))
                .check(matches(isDisplayed()));
    }
}
