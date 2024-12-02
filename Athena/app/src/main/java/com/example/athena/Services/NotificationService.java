package com.example.athena.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.example.athena.Controllers.NotificationController;
import com.example.athena.Views.NotificationView;
import com.example.athena.Models.Notification;
import com.example.athena.Models.DetailsForNotification;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Service class that runs in the background and runs the logic for checking and
 * building notifications
 */
public class NotificationService extends Service {
    // Service to listen for changes in the database
    private NotificationView notificationView;
    private NotificationController notificationController;
    private String deviceId;

    /**
     * Called at the beginning of the service's lifetime, begins the notification checker loop
     * @param intent - contains data needed by the service
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // initialize controller, view, and deviceId
        if (intent != null && intent.hasExtra("deviceId")) {
            this.deviceId = intent.getStringExtra("deviceId");
        } else {
            Log.w("notificationService", "DeviceId could not be retrieved");
        }
//        Log.w("notificationService", this.deviceId);
        this.notificationView = new NotificationView(this.deviceId);
        this.notificationController = new NotificationController(this, this.deviceId);

        // Start the periodic notification checker
        startNotificationListener();

        // Restarts the service if it is killed at any point
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // not binding to anything
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Starts the service listener checking for notifications and sending them
     */
    private void startNotificationListener() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "Users/" + this.deviceId + "/Events";
        CollectionReference userEventsRef = db.collection(path);

        getNotificationsOnStart();

        userEventsRef.addSnapshotListener((snapshots, exception) -> {
            if (exception != null) {
                Log.w("notificationListener", "Error in listener", exception);
                return;
            }

            if (snapshots != null && !snapshots.isEmpty()) {
                for (DocumentChange change : snapshots.getDocumentChanges()) {
                    if (change.getType() == DocumentChange.Type.MODIFIED) {
                        DocumentSnapshot userEvent = change.getDocument();
                        Boolean isNotified = userEvent.getBoolean("isNotified");

                        if (Boolean.FALSE.equals(isNotified)) {
                            // call notification function
                            String status = userEvent.getString("status");
                            String eventId = userEvent.getId();
                            Log.w("notificationListener", "processing eventId: "+eventId);
                            handleNotificationEvent(eventId, status, userEvent.getReference());
                        }
                    }

                }
            }
        });
    }

    public void getNotificationsOnStart() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "Users/" + this.deviceId + "/Events";
        CollectionReference userEventsRef = db.collection(path);

        userEventsRef.whereEqualTo("isNotified", false).get()
                .addOnSuccessListener(QuerySnapshot -> {
                    if (!QuerySnapshot.isEmpty()) {
                        for (DocumentSnapshot userEvent : QuerySnapshot.getDocuments()) {
                            String status = userEvent.getString("status");
                            String eventId = userEvent.getId();
                            handleNotificationEvent(eventId, status, userEvent.getReference());
                        }
                    }
                }).addOnFailureListener(e -> Log.w("notifiationListener", "error getting notification data"));
    }

    public void handleNotificationEvent(String eventId, String status, DocumentReference eventRef) {
        notificationView.getNotificationData(eventId, status, new NotificationView.NotificationDataCallback() {
            @Override
            public void onDataRetrieved(DetailsForNotification notifDetatils) {
                boolean valid = notificationController.checkValidNotification(notifDetatils);
                if (valid) {

                    Notification notification = notificationController.convertToNotification(notifDetatils);
                    serviceShowNotification(notification);

                    // set isNotified back to true
                    eventRef.update("isNotified", true)
                            .addOnSuccessListener(aVoid -> Log.w("notificationListener", "isNotified reset to false"))
                            .addOnFailureListener(err -> Log.w("notificationListener", "Failed to reset isNotified", err));
                } else {
                    Log.w("notificationListener", "invalid notification");
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d("handleNotificationEvent","Error occurred:" + e);
            }
        });
    }

    public void serviceShowNotification(Notification notification) {
        notificationController.showNotification(this, notification);
    }
}
