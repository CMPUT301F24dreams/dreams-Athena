package com.example.athena.dbConnector;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.athena.Roles.Entrant;
import com.example.athena.Roles.OrganizerViewEntrant;
import com.example.athena.WaitList.WaitList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Connects to the data base and retrieves info pertaining to Waitlists
 */
public class WaitlistDBConnector {

    private FirebaseFirestore Db;

    public WaitlistDBConnector(FirebaseFirestore DBConnection){
        this.Db = DBConnection;
    }


    public WaitList getListOfEntrants(String eventTitle){
        WaitList eventWaitList = new WaitList();

        CollectionReference entrantsRef = Db.collection
                                        ("events")
                                        .document(eventTitle)
                                        .collection("waitlist");


        entrantsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String entrantID = document.getString("entrantID");
                        boolean notificationSent = document.getBoolean("notificationSent");
                        String status = document.getString("status");
                        eventWaitList.add(new OrganizerViewEntrant(entrantID, status, notificationSent));
                        // Use the data as needed
                        Log.d("Entrant", "ID: " + entrantID + ", Notification Sent: " + notificationSent + ", Status: " + status);
                    }
                } else {
                    Log.d("Entrants", "Error getting documents: ", task.getException());
                }
            }
        });

        return eventWaitList;
    }


}
