package com.example.athena.Roles;

import android.media.Image;

import com.example.athena.Event.Event;
import com.example.athena.dbInfoRetrieval.DBConnector;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 *This class represents users in the application
 * and implements the roles of the different types of users in the application
 */
public class User{
    private String name;
    private Boolean isAdmin;
    private Boolean isOrganizer;
    private Boolean isEntrant;
    private String email;
    private Integer phoneNumber;
    private Image profilePicture;
    private ArrayList<Event> invitedEvents;
    private ArrayList<Event> events;
    private Boolean sendNotification;

    public User(String name, Boolean isAdmin, Boolean isEntrant, Boolean isOrginizer, String email) {
        this.name = name;
        this.isEntrant = isEntrant;
        this.isAdmin = isAdmin;
        this.isOrganizer = isOrginizer;
        this.email = email;
        this.sendNotification = Boolean.TRUE;
        this.events = new ArrayList<Event>();
        this.invitedEvents = new ArrayList<Event>();
    }

    public User(String userID) {
        FirebaseFirestore db = DBConnector.getInstance().getDb();
        DocumentReference docRef = db.collection("Users").document(userID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User newUser = documentSnapshot.toObject(User.class);
                name = newUser.getName();
                email = newUser.getEmail();
                isAdmin = newUser.getAdmin();
                isEntrant = newUser.getEntrant();
                isOrganizer = newUser.getOrganizer();
                sendNotification = newUser.getSendNotification();
                phoneNumber = newUser.getPhoneNumber();
                profilePicture = newUser.getProfilePicture();
            }
        });

        List<DocumentSnapshot> pending = db.collection("Users").document(userID)
                .collection("Events")
                .whereEqualTo("status","chosen")
                .get().getResult().getDocuments();
        for (DocumentSnapshot x: pending){
            Event event = db.collection("Events").document(x.get("eventID").toString()).get().getResult().toObject(Event.class);
            this.invitedEvents.add(event);
        }

        //grabs all documents in user events and converts them into event class and then stores them in arrayList
        List<DocumentSnapshot> event = db.collection("Users").document(userID).collection("Events").get().getResult().getDocuments();
        for (DocumentSnapshot i: event){
            this.events.add(i.toObject(Event.class));
        }
    }

    public boolean getSendNotification() {
        return sendNotification;
    }

    public String getName() {
        return name;
    }


    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Event> getInvitedEvents() {
        return invitedEvents;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getOrganizer() {
        return isOrganizer;
    }

    public void setOrganizer(Boolean organizer) {
        isOrganizer = organizer;
    }

    public Boolean getEntrant() {
        return isEntrant;
    }

    public void setEntrant(Boolean entrant) {
        isEntrant = entrant;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }


}
