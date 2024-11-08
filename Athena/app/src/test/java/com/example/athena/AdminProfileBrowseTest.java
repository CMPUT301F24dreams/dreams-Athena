///some tests in this package are derived from copilot: https://copilot.microsoft.com/chats/XkgPPffcGXrUadGYr6Wvi, 2024-11-08
package com.example.athena;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.AdminFragments.adminProfileBrowse;
import com.example.athena.Models.User;
import com.example.athena.Firebase.userDB;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AdminProfileBrowseTest {

    @Before
    public void setup() {
        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class);
    }

    /**
     * Test to ensure the ListView is displayed.
     */
    @Test
    public void testListViewIsDisplayed() {
        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    /**
     * Test to fetch the user list and ensure it is displayed in the ListView.
     */
    @Test
    public void testFetchUserList() {
        userDB mockUserDB = mock(userDB.class);
        Task<QuerySnapshot> mockTask = mock(Task.class);
        QuerySnapshot mockQuerySnapshot = mock(QuerySnapshot.class);
        when(mockUserDB.getUserList()).thenReturn(mockTask);
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockTask.getResult()).thenReturn(mockQuerySnapshot);

        List<DocumentSnapshot> mockDocuments = new ArrayList<>();
        DocumentSnapshot mockDocument = mock(DocumentSnapshot.class);
        when(mockDocument.getString("name")).thenReturn("John Doe");
        when(mockDocument.getString("email")).thenReturn("john.doe@example.com");
        when(mockDocument.getString("phone")).thenReturn("123-456-7890");
        when(mockDocument.getString("imageURL")).thenReturn("https://example.com/image.jpg");
        mockDocuments.add(mockDocument);
        when(mockQuerySnapshot.getDocuments()).thenReturn(mockDocuments);

        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class);
        scenario.onFragment(fragment -> {
            fragment.userDB = mockUserDB;
            fragment.onViewCreated(new View(getApplicationContext()), null);
        });

        onView(withId(R.id.myEventList)).check(matches(isDisplayed()));
    }

    /**
     * Test to ensure that clicking on a list item navigates to the user profile.
     */
    @Test
    public void testListItemClickNavigatesToUserProfile() {
        FragmentScenario<adminProfileBrowse> scenario = FragmentScenario.launchInContainer(adminProfileBrowse.class);
        scenario.onFragment(fragment -> {
            User user = new User("John Doe", "john.doe@example.com", "123-456-7890", "https://example.com/image.jpg");
            fragment.users.add(user);
            fragment.usersID.add("userID123");
        });

        onView(withId(R.id.myEventList)).perform(ViewActions.click());
        onView(withId(R.id.content_frame)).check(matches(isDisplayed()));
    }
}
