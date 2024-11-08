package com.example.athena;

import static org.mockito.Mockito.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.OrgChooseNumDialog;
import com.example.athena.EntrantAndOrganizerFragments.OrganizerEntrantOperations;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class OrganizerEntrantOperationsTest {

    @Mock
    private eventsDB mockEventDB;
    @Mock
    private userDB mockUserDB;
    @Mock
    private Event mockEvent;
    @Mock
    private Bundle mockBundle;
    @Mock
    private FragmentManager mockFragmentManager;

    private OrganizerEntrantOperations fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new OrganizerEntrantOperations();
        fragment.eventDB = mockEventDB;
        fragment.userDB = mockUserDB;
        fragment.event = mockEvent;
        fragment.bundle = mockBundle;
    }

    /**
     * Test the choseEntrants method to ensure it correctly updates the database
     * with the selected number of entrants.
     */
    @Test
    public void testChoseEntrants() {
        // Arrange
        when(mockEvent.getWaitList().getInvited()).thenReturn(new ArrayList<>(Arrays.asList("user1", "user2")));
        doNothing().when(mockEventDB).moveUserID(anyString(), anyString(), anyString(), anyString());
        doNothing().when(mockUserDB).changeEventStatusInvited(anyString(), anyString());

        // Act
        fragment.choseEntrants(2);

        // Assert
        verify(mockEventDB, times(2)).moveUserID(eq("pending"), eq("invited"), anyString(), anyString());
        verify(mockUserDB, times(2)).changeEventStatusInvited(anyString(), anyString());
    }

    /**
     * Test the showDialog method to ensure it correctly displays the dialog.
     */
    @Test
    public void testShowDialog() {
        // Arrange
        OrgChooseNumDialog mockDialog = mock(OrgChooseNumDialog.class);
        doNothing().when(mockDialog).show(any(FragmentManager.class), anyString());

        // Act
        fragment.showDialog();

        // Assert
        verify(mockDialog).show(any(FragmentManager.class), eq("ChoseNumEnt"));
    }

    /**
     * Test the displayChildFragment method to ensure it correctly sets the arguments
     * and displays the child fragment.
     */
    @Test
    public void testDisplayChildFragment() {
        // Arrange
        Fragment mockFragment = mock(Fragment.class);
        when(mockFragment.getArguments()).thenReturn(mockBundle);

        // Act
        fragment.displayChildFragment(mockFragment);

        // Assert
        verify(mockFragment).setArguments(mockBundle);
        verify(mockFragmentManager).beginTransaction().replace(R.id.content_frame, mockFragment).commit();
    }
}
