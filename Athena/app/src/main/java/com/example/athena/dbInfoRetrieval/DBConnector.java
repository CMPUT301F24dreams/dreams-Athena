package com.example.athena.dbInfoRetrieval;


import com.google.firebase.firestore.FirebaseFirestore;


/**
 * This class creates a connection to the the Firebase FireStore Database and then returns the connection so that it can be used by the caller
 * It contains a get instance method to allow it to synchronize the connection to the DB and create new connections if necessary
 * It also contains a getDb method that can be called to retrieve a connection to the database
 */
//Logic for synchronizing the connection was derived from copilot:https://copilot.microsoft.com/?msockid=1ce35a3ee9c767ff0e974f22e87366e5, 2024-10-26
public class DBConnector {
    private static DBConnector instance;
    private FirebaseFirestore db;

    private DBConnector() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public FirebaseFirestore getDb() {
        return db;
    }
}