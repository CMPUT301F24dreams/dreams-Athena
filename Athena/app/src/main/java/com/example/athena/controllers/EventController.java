package com.example.athena.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.athena.dbInfoRetrieval.DBConnector;
import com.example.athena.Roles.User;
import com.example.athena.WaitList.WaitList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EventController {
    private FirebaseFirestore Db;

    public EventController(FirebaseFirestore DBConnection) {
        this.Db = DBConnector.getInstance().getDb();
    }

}