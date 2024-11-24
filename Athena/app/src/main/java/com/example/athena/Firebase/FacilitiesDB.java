package com.example.athena.Firebase;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.athena.Models.Facility;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles database operations for the facilities collection in Firestore.
 */
public class FacilitiesDB {
    private FirebaseFirestore db;
    private CollectionReference facilitiesCollection;

    public FacilitiesDB() {
        // Initialize Firestore and set the facilities collection
        this.db = FirebaseFirestore.getInstance();
        this.facilitiesCollection = db.collection("Facilities");
    }

    // Adds a new facility to the Facilities collection
    public Task<DocumentReference> addFacility(HashMap<String, Object> facilityData) {
        return facilitiesCollection.add(facilityData);
    }

    public Task addFacility(Facility facility){
        return facilitiesCollection.add(facility);
    }

    // Retrieves a specific facility by its ID
    public Task<DocumentSnapshot> getFacility(String facilityId) {
        return facilitiesCollection.document(facilityId).get();
    }

    public void updateFacilityID(String facilityID) {
        Map<String, Object> data = new HashMap<>();
        data.put("facilityID", facilityID);
        facilitiesCollection.document(facilityID).set(data, SetOptions.merge());
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
