package com.example.athena.Firebase;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
/**
 * Class for interacting with Firebase Storage to upload and delete images.
 */
/**
 * This class handles image storage operations using Firebase Storage.
 */
public class ImageDB {
    private FirebaseStorage storage;
    private StorageReference storageRef;

    /**
     * Constructor initializes Firebase Storage and its reference.
     */
    public ImageDB() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
    }

    /**
     * Adds an image to Firebase Storage.
     *
     * @param imageID The ID of the image.
     * @param uri The URI of the image file.
     * @return The UploadTask to monitor the upload process.
     */
    public UploadTask addImage(String imageID, Uri uri) {
        StorageReference imageRef = storageRef.child("images/" + imageID);
        return imageRef.putFile(uri);
    }

    /**
     * Deletes an image from Firebase Storage.
     *
     * @param imageID The ID of the image to delete.
     */
    public void deleteImage(String imageID) {
        StorageReference imageRef = storageRef.child("images/" + imageID);
        imageRef.delete();
    }

    /**
     * Adds an event image to Firebase Storage.
     *
     * @param eventID The ID of the event.
     * @param imageUri The URI of the image file.
     * @return The UploadTask to monitor the upload process.
     */
    public UploadTask addEventImage(String eventID, Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance()
                .getReference("events/" + eventID + "/eventPicture.jpg");
        return storageRef.putFile(imageUri);
    }

    /**
     * Retrieves the download URL of an event image from Firebase Storage.
     *
     * @param eventID The ID of the event.
     * @return A Task that retrieves the download URL as a string.
     */
    public Task<String> getEventImageURL(String eventID) {
        StorageReference storageRef = FirebaseStorage.getInstance()
                .getReference("events/" + eventID + "/eventPicture.jpg");

        return storageRef.getDownloadUrl().continueWith(task -> {
            if (!task.isSuccessful()) {
                throw task.getException(); // Handle any errors
            }
            return task.getResult().toString(); // Return the URL as a string
        });
    }
}
