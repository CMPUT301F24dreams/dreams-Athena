/**
 * CreateEvent Fragment class handles the creation of a new event by the user.
 * It allows the user to fill in event details, upload an event poster, and save
 * the event information to Firebase, including an image upload to Firebase Storage.
 */
package com.example.athena.EntrantAndOrganizerFragments.eventCreation;

import static com.example.athena.EntrantAndOrganizerFragments.ViewProfileFragment.PICK_IMAGE_REQUEST;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.EntrantAndOrganizerFragments.EventDetails;
import com.example.athena.Firebase.EventsDB;
import com.example.athena.Firebase.ImageDB;
import com.example.athena.Firebase.UserDB;
import com.example.athena.Models.Event;
import com.example.athena.Models.QRCode;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.WriterException;

import java.util.Objects;


public class CreateEvent extends Fragment implements DateDialog.datePickerListener {
    private Event event;
    private User user;
    private String userFacility;

    DateDialog datePicker;
    private TextView eventDateText;
    private TextView regStartText;
    private TextView regEndText;

    Uri imageURI = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event, container, false);
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        EventsDB eventsDB = new EventsDB();
        UserDB userDB = new UserDB();

        Bundle bundle = getArguments();
        assert bundle != null;
        String deviceID = bundle.getString("deviceID");

        user = new User();
        userDB.loadUserData(user, deviceID);

        TextView eventNameText = view.findViewById(R.id.eventName);
        datePicker = DateDialog.newInstance(this);
        eventDateText = view.findViewById(R.id.eventDate);
        regStartText = view.findViewById(R.id.regDateStart);
        regEndText = view.findViewById(R.id.regDateEnd);
        TextView descriptionText = view.findViewById(R.id.description);
        TextView participantsText = view.findViewById(R.id.participants);
        CheckBox geoRequireBox = view.findViewById(R.id.geoRequire);
        TextView upload = view.findViewById(R.id.uploadPoster);

        event = new Event();

        Button createEvent = view.findViewById(R.id.createEventButton);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setFacility(user.getFacility());
                event.setOrganizer(deviceID);

                if (event.checkEvent() & imageURI != null) {
                    Task eventAddTask = eventsDB.addEvent(event, deviceID, imageURI);
                    eventAddTask.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                String eventID = (String) task.getResult();
                                Bundle eventIDBundle = new Bundle();
                                eventIDBundle.putString("eventID", eventID);

                                QRCode qrCode = new QRCode();
                                try {
                                    Bitmap qrBitmap = qrCode.createQR(eventID);
                                    String qrCodeUrl = qrCode.encodeBitmapToBase64(qrBitmap);
                                    Log.d("DATA", qrCodeUrl);
                                    // Update the event in Firestore with the QR code URL
                                    eventsDB.updateEventQRCode(eventID, qrCodeUrl);

                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }

                                displayChildFragment(new EventDetails(), eventIDBundle);
                            }
                        }
                    });
                } else {
                    Toast toast = Toast.makeText(getContext(), "One or more fields are invalid.", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

    eventNameText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            event.setEventName(s.toString());
        }
    });

    descriptionText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            event.setEventDescription(s.toString());
        }
    });

    participantsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                event.setMaxParticipants(Integer.valueOf(s.toString()));
            }
        });

    geoRequireBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            event.setGeoRequire(isChecked);
        }
    });

    eventDateText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.setTextField("eventDate");
            datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    });

    regStartText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.setTextField("regStart");
            datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    });

    regEndText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.setTextField("regEnd");
            datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    });

    upload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openGallery();
        }
    });
}

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CreateEvent.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageURI = data.getData();
        }
    }

    @Override
    public void onDateSet(String textField, String date) {
        if (Objects.equals(textField, "eventDate")) {
            event.setEventDate(date);
            eventDateText.setText(date);
        } else if (Objects.equals(textField, "regStart")) {
            event.setStartReg(date);
            regStartText.setText(date);
        } else {
            event.setEndReg(date);
            regEndText.setText(date);
        }
    }

    public void displayChildFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}

