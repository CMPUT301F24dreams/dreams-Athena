package com.example.athena.Firebase;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.athena.Models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles database operations for the users collection in Firestore.
 */
public class  userDB {
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


    public void deleteOrgFacility(String deviceID) {
         db.collection("Users").document(deviceID).update("facility", FieldValue.delete());

    }

    public void saveNotifSetting (String userID, String setting, Boolean isChecked){
        db.collection("Users").document(userID).update(setting,isChecked);
    }

    public void saveUserDetail(User user,String userID){
        db.collection("Users").document(userID).update("name",user.getName());
        db.collection("Users").document(userID).update("email",user.getEmail());
        db.collection("Users").document(userID).update("phone",user.getPhone());
    }

    public Task<QuerySnapshot> getUserList() {
        return usersCollection.get();
    }

    public void updateOrgEvents(String deviceID, String eventID) {
        db.collection("Users/" + deviceID + "/OrgEvents").document(eventID).set(new HashMap<>() {});
    }

    public void updateOrgFacilities(String deviceID, String facilityID) {
        db.collection("Users").document(deviceID).update("facility", facilityID);
    }


    /**
     * updates the users status to invited on the users events sub-collection
     * @param eventID the events id
     * @param deviceID the users id
     */
    public void changeEventStatusInvited(String eventID, String deviceID){
        db.collection("Users/"+ deviceID +"/Events").document(eventID).update("status","invited");
    }

    public void deleteUser(String userID){
        usersCollection.document(userID).delete();
    }

    /**
     * updates the users status to accepted on the users events sub-collection
     * @param eventID the events id
     * @param deviceID the users id
     */
    public void changeEventStatusAccepted(String eventID, String deviceID){
        db.collection("Users/"+ deviceID +"/Events").document(eventID).update("status","accepted");
    }

    /**
     * updates the users status to declined on the users events sub-collection
     * @param eventID the events id
     * @param deviceID the users id
     */
    public void changeEventStatusDeclined(String eventID, String deviceID){
        db.collection("Users/"+ deviceID +"/Events").document(eventID).update("status","declined");
    }

    public Task<QuerySnapshot> getOrganizerEvent(String deviceID){
        Task ueRef = db.collection("Users/" + deviceID + "/OrgEvents").get();
        return ueRef;
    }
    public Task<Void> saveURLToUser(UploadTask uploadTask, String deviceID) {
        TaskCompletionSource<Void> changeURLSource = new TaskCompletionSource<>();
        Task<Void> changeTask = changeURLSource.getTask();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child("images/" + deviceID);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri URL = task.getResult();
                Map<String, Object> data = new HashMap<>();
                data.put("imageURL", URL.toString());
                usersCollection.document(deviceID).set(data, SetOptions.merge());
                changeURLSource.setResult(null);
            }
        });
        return changeTask;
    }

    // Retrieves a specific user by their ID
    public Task<DocumentSnapshot> getUser(String deviceId) {
        return usersCollection.document(deviceId).get();
    }

    // Updates an existing user's data
    public void resetImage(String deviceID) {
        Map<String, Object> data = new HashMap<>();
        data.put("imageURL", "NULL");
        usersCollection.document(deviceID).set(data, SetOptions.merge());
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

