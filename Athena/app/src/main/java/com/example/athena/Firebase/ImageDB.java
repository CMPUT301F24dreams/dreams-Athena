package com.example.athena.Firebase;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
/**
 * Class for interacting with Firebase Storage to upload and delete images.
 */
public class ImageDB {
    FirebaseStorage storage;
    private StorageReference storageRef;
    public ImageDB() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
    }
    public UploadTask addImage(String imageID, Uri uri) {
        StorageReference imageRef = storageRef.child("images/" + imageID);
        return imageRef.putFile(uri);
    }
    public void deleteImage(String imageID) {
        StorageReference imageRef = storageRef.child("images/" + imageID);
        imageRef.delete();
    }

    public UploadTask addEventImage(String eventID, Uri imageUri) {
        // Reference to the event's folder in Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance()
                .getReference("events/" + eventID + "/eventPicture.jpg");
        return storageRef.putFile(imageUri);
    }

    public Task<String> getEventImageURL(String eventID) {
        // Reference to the event's image in Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance()
                .getReference("events/" + eventID + "/eventPicture.jpg");

        // Return the task that retrieves the download URL
        return storageRef.getDownloadUrl().continueWith(task -> {
            if (!task.isSuccessful()) {
                throw task.getException(); // Handle any errors
            }
            return task.getResult().toString(); // Return the URL as a string
        });
    }
}
