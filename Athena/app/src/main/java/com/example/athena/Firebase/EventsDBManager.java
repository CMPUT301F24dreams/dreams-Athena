package com.example.athena.Firebase;

import androidx.annotation.Nullable;

import com.example.athena.Models.Event;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles database operations for events collection, interacting with the Firestore database.
 */
public class EventsDBManager {
    private FirebaseFirestore db;
    private CollectionReference eventsCollection;

    public EventsDBManager(){
        // Initialize the Firestore connection and specify the collection
        this.db = DBConnector.getInstance().getDb();
        this.eventsCollection= db.collection("Events");
    }

    // Adds a new event to the Events collection
    public Task<DocumentReference> addEvent(HashMap<String, Object> eventData){
        return eventsCollection.add(eventData);
    }

    // Retrieves a specific event by its ID
    public Task<DocumentSnapshot> getEvent(String eventId){
        return eventsCollection.document(eventId).get();
    }

    public ArrayList<Event> getEventList(ArrayList<String> eventIDList){
        ArrayList<Event> events = new ArrayList<>();
        ListenerRegistration eventListener = eventsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshots, @Nullable FirebaseFirestoreException error) {
                if (querySnapshots != null) {
                    events.clear();
                    for (QueryDocumentSnapshot doc : querySnapshots) {
                        if (eventIDList.contains(doc.getId())) {
                            Object eventName = doc.get("eventName");
                            assert eventName != null;
                            events.add(new Event(eventName.toString()));
                        }
                    }
                }
            }
        });
        return events;
    }

    // Updates an existing event with new data
    public Task<Void> updateEvent(String eventId, HashMap<String, Object> updatedData){
        return eventsCollection.document(eventId).update(updatedData);
    }

    // Deletes a specific event by its ID
    public Task<Void> deleteEvent(String eventId){
        return eventsCollection.document(eventId).delete();
    }

    //Add Entrant to Waitlist
    public Task<DocumentReference> addEntrantToWaitlist(String eventId, HashMap<String, Object> entrantData) {
        return eventsCollection.document(eventId).collection("Waitlist").add(entrantData);
    }

    //Get Waitlist for an Event
    public Task<QuerySnapshot> getWaitlist(String eventId) {
        return eventsCollection.document(eventId).collection("Waitlist").get();
    }

    //Update Entrant Status to Waitlist
    public Task<Void> updateEntrantStatus(String eventId, String entrantId, HashMap<String,Object> statusData){
        return eventsCollection.document(eventId).collection("Waitlist").document(entrantId).update(statusData);
    }

    //Delete Entrant from Waitlist
    public Task<Void> deleteEntrantFromWaitlist(String eventId, String entrantId) {
        return eventsCollection.document(eventId).collection("Waitlist").document(entrantId).delete();
    }

    //Update Event Summary
    public Task<Void> updateEventSummary(String eventId, HashMap<String, Object> summaryData) {
        return eventsCollection.document(eventId).collection("eventSummary").document("summary").set(summaryData);
    }

    //Get Event Summary
    public Task<DocumentSnapshot> getEventSummary(String eventId) {
        return eventsCollection.document(eventId).collection("eventSummary").document("summary").get();
    }


}
