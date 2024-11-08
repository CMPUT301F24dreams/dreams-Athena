package com.example.athena;

import static com.example.athena.EntrantAndOrganizerFragments.viewProfileFragment.PICK_IMAGE_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.athena.EntrantAndOrganizerFragments.createEvent;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.imageDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.UploadTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(AndroidJUnit4.class)
public class createEventTest {

    @Mock
    private eventsDB mockEventsDB;
    @Mock
    private userDB mockUserDB;
    @Mock
    private imageDB mockImageDB;
    @Mock
    private Event mockEvent;
    @Mock
    private Bundle mockBundle;
    @Mock
    private FragmentManager mockFragmentManager;
    @Mock
    private FragmentTransaction mockFragmentTransaction;
    @Mock
    private ImageView mockImageView;

    private createEvent fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new createEvent();
        fragment.imageView = mockImageView;
    }

    /**
     * Test the onCreateView method to ensure it correctly inflates the layout.
     */
    @Test
    public void testOnCreateView() {
        // Arrange
        LayoutInflater inflater = mock(LayoutInflater.class);
        ViewGroup container = mock(ViewGroup.class);
        View mockView = mock(View.class);
        when(inflater.inflate(R.layout.create_event, container, false)).thenReturn(mockView);

        // Act
        View view = fragment.onCreateView(inflater, container, mockBundle);

        // Assert
        assertEquals(mockView, view);
    }

    /**
     * Test the onViewCreated method to ensure it correctly sets up the event creation process.
     */
    @Test
    public void testOnViewCreated() {
        // Arrange
        View mockView = mock(View.class);
        TextView mockEventNameText = mock(TextView.class);
        TextView mockEventDateText = mock(TextView.class);
        TextView mockRegStartText = mock(TextView.class);
        TextView mockRegEndText = mock(TextView.class);
        TextView mockFacilityText = mock(TextView.class);
        TextView mockDescriptionText = mock(TextView.class);
        TextView mockParticipantsText = mock(TextView.class);
        CheckBox mockGeorequireText = mock(CheckBox.class);
        Button mockCreateEventButton = mock(Button.class);
        TextView mockUploadText = mock(TextView.class);

        when(mockView.findViewById(R.id.userName)).thenReturn(mockEventNameText);
        when(mockView.findViewById(R.id.eventDate)).thenReturn(mockEventDateText);
        when(mockView.findViewById(R.id.regDateStart)).thenReturn(mockRegStartText);
        when(mockView.findViewById(R.id.regDateEnd)).thenReturn(mockRegEndText);
        when(mockView.findViewById(R.id.facilityID)).thenReturn(mockFacilityText);
        when(mockView.findViewById(R.id.description)).thenReturn(mockDescriptionText);
        when(mockView.findViewById(R.id.participants)).thenReturn(mockParticipantsText);
        when(mockView.findViewById(R.id.geoRequire)).thenReturn(mockGeorequireText);
        when(mockView.findViewById(R.id.createEventButton)).thenReturn(mockCreateEventButton);
        when(mockView.findViewById(R.id.uploadPoster)).thenReturn(mockUploadText);

        // Act
        fragment.onViewCreated(mockView, mockBundle);

        // Assert
        verify(mockCreateEventButton).setOnClickListener(any(View.OnClickListener.class));
        verify(mockUploadText).setOnClickListener(any(View.OnClickListener.class));
    }

    /**
     * Test the openGallery method to ensure it correctly starts the gallery intent.
     */
    @Test
    public void testOpenGallery() {
        // Arrange
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);

        // Act
        fragment.openGallery();

        // Assert
        verify(fragment).startActivityForResult(intentCaptor.capture(), eq(PICK_IMAGE_REQUEST));
        Intent capturedIntent = intentCaptor.getValue();
        assertEquals(Intent.ACTION_PICK, capturedIntent.getAction());
        assertEquals(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, capturedIntent.getData());
    }

    /**
     * Test the onActivityResult method to ensure it correctly handles the selected image
     * and updates the ImageView.
     */
    @Test
    public void testOnActivityResult() {
        // Arrange
        Uri mockUri = mock(Uri.class);
        Intent mockIntent = mock(Intent.class);
        when(mockIntent.getData()).thenReturn(mockUri);

        // Act
        fragment.onActivityResult(PICK_IMAGE_REQUEST, Activity.RESULT_OK, mockIntent);

        // Assert
        verify(mockImageView).setImageURI(mockUri);
    }

    /**
     * Test the displayChildFragment method to ensure it correctly replaces the fragment.
     */
    @Test
    public void testDisplayChildFragment() {
        // Arrange
        Fragment mockFragment = mock(Fragment.class);
        Bundle mockBundle = mock(Bundle.class);
        when(fragment.getParentFragmentManager()).thenReturn(mockFragmentManager);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockFragmentTransaction);

        // Act
        fragment.displayChildFragment(mockFragment, mockBundle);

        // Assert
        verify(mockFragment).setArguments(mockBundle);
        verify(mockFragmentTransaction).replace(R.id.content_frame, mockFragment);
        verify(mockFragmentTransaction).commit();
    }
}
