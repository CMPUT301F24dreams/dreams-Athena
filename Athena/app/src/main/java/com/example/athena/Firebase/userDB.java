package com.example.athena.Firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles database operations for the users collection in Firestore.
 */
public class userDB {
    private FirebaseFirestore db;
    private CollectionReference usersCollection;

    public userDB() {
        // Initialize Firestore and set the users collection
        this.db = FirebaseFirestore.getInstance();
        this.usersCollection = db.collection("Users");
    }

    public Task<QuerySnapshot> getUserEvents(String deviceID) {
        Task ueRef = db.collection("Users/" + deviceID + "/Events").get();
        return ueRef;
    }

    public void updateOrgEvents(String deviceID, String eventID) {
        db.collection("Users/" + deviceID + "/OrgEvents").document(eventID).set(new HashMap<>() {});
    }

    // Adds a new user to the Users collection
    public Task<Void> addUser(String deviceID, HashMap<String, Object> userData) {
        return usersCollection.document(deviceID).set(userData);
    }

    // Retrieves a specific user by their ID
    public Task<DocumentSnapshot> getUser(String userId) {
        return usersCollection.document(userId).get();
    }

    // Updates an existing user's data
    public Task<Void> updateUser(String userId, HashMap<String, Object> updatedData) {
        return usersCollection.document(userId).update(updatedData);
    }

    // Deletes a specific user by their ID
    public Task<Void> deleteUser(String userId) {
        return usersCollection.document(userId).delete();
    }

    // Retrieves all users, which can be useful for admin functionality
    public Task<QuerySnapshot> getAllUsers() {
        return usersCollection.get();
    }

    // Adds a role to a user's roles array (e.g., entrant, admin, organizer)
    public Task<Void> addRoleToUser(String userId, String newRole) {
        return usersCollection.document(userId).update("roles", FieldValue.arrayUnion(newRole));
    }

    // Removes a role from a user's roles array
    public Task<Void> removeRoleFromUser(String userId, String roleToRemove) {
        return usersCollection.document(userId).update("roles", FieldValue.arrayRemove(roleToRemove));
    }
    // Checks if the user has the admin role
    public Task<Boolean> isAdmin(String userId) {
        return getUser(userId).continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot userDocument = task.getResult();
                List<String> roles = (List<String>) userDocument.get("roles");
                return roles != null && roles.contains("admin");
            }
            return false;
        });
    }

    // Checks if the user has the entrant role
    public Task<Boolean> isEntrant(String userId) {
        return getUser(userId).continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot userDocument = task.getResult();
                List<String> roles = (List<String>) userDocument.get("roles");
                return roles != null && roles.contains("entrant");
            }
            return false;
        });
    }

    // Checks if the user has the organizer role
    public Task<Boolean> isOrganizer(String userId) {
        return getUser(userId).continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot userDocument = task.getResult();
                List<String> roles = (List<String>) userDocument.get("roles");
                return roles != null && roles.contains("organizer");
            }
            return false;
        });
    }


}

