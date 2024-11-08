package com.example.athena;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.userViewAttendingEventsFragment;
import com.example.athena.EntrantAndOrganizerFragments.userViewNotisChose;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.example.athena.WaitList.UserInviteArrayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class userViewAttendingEventsFragmentTest {

    @Mock
    private eventsDB mockEventsDB;
    @Mock
    private userDB mockUserDB;
    @Mock
    private Event mockEvent;
    @Mock
    private Bundle mockBundle;
    @Mock
    private FragmentManager mockFragmentManager;
    @Mock
    private ListView mockListView;
    @Mock
    private UserInviteArrayAdapter mockAdapter;

    private userViewAttendingEventsFragment fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new userViewAttendingEventsFragment();
        fragment.eventsDB = mockEventsDB;
        fragment.userDB = mockUserDB;
        fragment.events = new ArrayList<>();
        fragment.deviceID = "testDeviceID";
    }

    /**
     * Test the acceptInvite method to ensure it correctly updates the database
     * and the adapter when an invite is accepted.
     */
    @Test
    public void testAcceptInvite() {
        // Arrange
        fragment.events.add(mockEvent);
        doNothing().when(mockEvent).moveUser(anyString(), eq("accepted"));
        doNothing().when(mockEventsDB).moveUserID(anyString(), anyString(), anyString(), anyString());
        doNothing().when(mockUserDB).changeEventStatusAccepted(anyString(), anyString());
        doNothing().when(mockAdapter).remove(any(Event.class));
        doNothing().when(mockAdapter).notifyDataSetChanged();

        // Act
        fragment.acceptInvite(0);

        // Assert
        verify(mockEvent).moveUser(eq("testDeviceID"), eq("accepted"));
        verify(mockEventsDB).moveUserID(eq("invited"), eq("accepted"), eq("testDeviceID"), anyString());
        verify(mockUserDB).changeEventStatusAccepted(anyString(), eq("testDeviceID"));
        verify(mockAdapter).remove(mockEvent);
        verify(mockAdapter).notifyDataSetChanged();
    }

    /**
     * Test the declineInvite method to ensure it correctly updates the database
     * and the adapter when an invite is declined.
     */
    @Test
    public void testDeclineInvite() {
        // Arrange
        fragment.events.add(mockEvent);
        doNothing().when(mockEvent).moveUser(anyString(), eq("declined"));
        doNothing().when(mockEventsDB).moveUserID(anyString(), anyString(), anyString(), anyString());
        doNothing().when(mockUserDB).changeEventStatusDeclined(anyString(), anyString());
        doNothing().when(mockAdapter).remove(any(Event.class));
        doNothing().when(mockAdapter).notifyDataSetChanged();

        // Act
        fragment.declineInvite(0);

        // Assert
        verify(mockEvent).moveUser(eq("testDeviceID"), eq("declined"));
        verify(mockEventsDB).moveUserID(eq("invited"), eq("declined"), eq("testDeviceID"), anyString());
        verify(mockUserDB).changeEventStatusDeclined(anyString(), eq("testDeviceID"));
        verify(mockAdapter).remove(mockEvent);
        verify(mockAdapter).notifyDataSetChanged();
    }

    /**
     * Test the onCreateView method to ensure it correctly inflates the layout
     * and initializes the ListView.
     */
    @Test
    public void testOnCreateView() {
        // Arrange
        LayoutInflater inflater = mock(LayoutInflater.class);
        ViewGroup container = mock(ViewGroup.class);
        View mockView = mock(View.class);
        when(inflater.inflate(R.layout.user_my_notis_fragment, container, false)).thenReturn(mockView);
        when(mockView.findViewById(R.id.user_notifications_listview)).thenReturn(mockListView);

        // Act
        View view = fragment.onCreateView(inflater, container, mockBundle);

        // Assert
        assertEquals(mockView, view);
        assertEquals(mockListView, fragment.invites);
    }

    /**
     * Test the onViewCreated method to ensure it correctly sets up the ListView
     * and loads the events.
     */
    @Test
    public void testOnViewCreated() {
        // Arrange
        View mockView = mock(View.class);
        when(mockView.findViewById(R.id.user_notifications_listview)).thenReturn(mockListView);
        when(mockUserDB.getUserEvents(anyString())).thenReturn(mock(Task.class));
        when(mockEventsDB.getEventsList()).thenReturn(mock(Task.class));
        when(Tasks.whenAll(any(Task.class), any(Task.class))).thenReturn(mock(Task.class));

        // Act
        fragment.onViewCreated(mockView, mockBundle);

        // Assert
        verify(mockListView).setAdapter(any(UserInviteArrayAdapter.class));
        verify(mockListView).setClickable(true);
    }

    /**
     * Test the showDialog method to ensure it correctly displays the dialog.
     */
    @Test
    public void testShowDialog() {
        // Arrange
        Bundle bundle = new Bundle();
        userViewNotisChose mockDialog = mock(userViewNotisChose.class);
        doNothing().when(mockDialog).show(any(FragmentManager.class), anyString());

        // Act
        fragment.showDialog(bundle);

        // Assert
        verify(mockDialog).setArguments(bundle);
        verify(mockDialog).setTargetFragment(fragment, 0);
        verify(mockDialog).show(any(FragmentManager.class), eq("accept_decline_dialog"));
    }
}
