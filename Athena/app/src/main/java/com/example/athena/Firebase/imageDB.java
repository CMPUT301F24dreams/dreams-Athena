package com.example.athena.Firebase;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class imageDB {
    FirebaseStorage storage;
    private StorageReference storageRef;
    public imageDB() {
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
}
