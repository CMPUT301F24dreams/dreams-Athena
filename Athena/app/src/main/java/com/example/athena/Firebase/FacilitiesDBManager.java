package com.example.athena.Firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

/**
 * This class handles database operations for the facilities collection in Firestore.
 */
public class FacilitiesDBManager {
    private FirebaseFirestore db;
    private CollectionReference facilitiesCollection;

    public FacilitiesDBManager() {
        // Initialize Firestore and set the facilities collection
        this.db = DBConnector.getInstance().getDb();
        this.facilitiesCollection = db.collection("Facilities");
    }

    // Adds a new facility to the Facilities collection
    public Task<DocumentReference> addFacility(HashMap<String, Object> facilityData) {
        return facilitiesCollection.add(facilityData);
    }

    // Retrieves a specific facility by its ID
    public Task<DocumentSnapshot> getFacility(String facilityId) {
        return facilitiesCollection.document(facilityId).get();
    }

    // Updates an existing facility with new data
    public Task<Void> updateFacility(String facilityId, HashMap<String, Object> updatedData) {
        return facilitiesCollection.document(facilityId).update(updatedData);
    }

    // Deletes a specific facility by its ID
    public Task<Void> deleteFacility(String facilityId) {
        return facilitiesCollection.document(facilityId).delete();
    }

    // Retrieves all facilities, can be used to get a list of all facilities
    public Task<QuerySnapshot> getAllFacilities() {
        return facilitiesCollection.get();
    }
}
