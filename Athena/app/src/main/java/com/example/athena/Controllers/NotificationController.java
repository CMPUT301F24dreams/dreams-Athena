package com.example.athena.Controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Entrant;
import com.example.athena.Models.Event;
import com.example.athena.Models.Notification;
import com.example.athena.Models.User;
import com.example.athena.Models.detailsForNotification;
import com.example.athena.Models.userNotifDetails;
import com.example.athena.Views.NotificationView;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotificationController {
    private List<Notification> notificationList;

    private Context context;
    private String deviceId;

    public NotificationController(Context context, String deviceId) {
        this.context = context;
        this.deviceId = deviceId;
        NotificationView.createNotificationChannel(context);
    }

    public boolean checkValidNotification(detailsForNotification details) {
        boolean invalid1 = details.getUser().isChosenNotif() && Objects.equals(details.getNotifType(), "invited");
        boolean invalid2 = details.getUser().isNotChosenNotif() && Objects.equals(details.getNotifType(), "waiting");

        return !invalid1 && !invalid2;
    }

    public Notification convertToNotification(detailsForNotification details) {
        String title = "BLANK";
        String bodyText = "BLANK";
        String notifType = details.getNotifType();
        if (Objects.equals(notifType, "invited")) {
            bodyText = "Congratulations, you have been invited to " + details.getEventName() + ".";
            title = "Selected for event";
        } else if (Objects.equals(notifType, "waiting")) {
            bodyText = details.getEventName() + " held a lottery, but you were not selected. Please wait for another lottery";
            title = "Not selected for event";
        }
        String activityLink = "placeholder"; // todo: make this actually redirect to the right activity
        Notification newNotif = new Notification(bodyText, title, activityLink);
        return newNotif;
    }

    // Callback for notification data
    public interface NotificationDataCallback {
        void onDataRetrieved(List<detailsForNotification> notifDetailList);
        void onError(Exception e);
    }

    // Unified method to fetch user details and non-notified events
    public void getNotificationData(NotificationDataCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userNotifDetails userDetails = new userNotifDetails();
        List<detailsForNotification> notifDetailsList = new ArrayList<>();

        // Get user details
        Task<DocumentSnapshot> userTask = db.collection("Users").document(deviceId).get();

        // Get non-notified event IDs
        Task<QuerySnapshot> eventIdsTask = db.collection("Users")
                .document(deviceId)
                .collection("Events")
                .whereEqualTo("isNotified", false)
                .get();

        // Wait for both tasks to complete
        Tasks.whenAll(userTask, eventIdsTask).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Process user data
                if (userTask.getResult().exists()) {
                    DocumentSnapshot userDoc = userTask.getResult();
                    userDetails.setUserName(userDoc.getString("name"));
                    userDetails.setChosenNotif(userDoc.getBoolean("chosenNotif"));
                    userDetails.setNotChosenNotif(userDoc.getBoolean("notChosenNotif"));
                }

                // Collect event IDs and status from user's Events subcollection
                List<String> eventIds = new ArrayList<>();
                Map<String, String> eventStatusMap = new HashMap<>();
                for (QueryDocumentSnapshot document : eventIdsTask.getResult()) {
                    String eventId = document.getId();
                    String status = document.getString("status");
                    eventIds.add(eventId);
                    eventStatusMap.put(eventId, status); // Map eventId to status
                }

                if (eventIds.isEmpty()) {
                    return;
                }

                // Fetch event details
                db.collection("Events")
                        .whereIn(FieldPath.documentId(), eventIds)
                        .get()
                        .addOnCompleteListener(eventDetailsTask -> {
                            if (eventDetailsTask.isSuccessful()) {
                                List<Event> events = new ArrayList<>();
                                for (QueryDocumentSnapshot document : eventDetailsTask.getResult()) {
                                    String eventName = document.getString("eventName");
                                    String eventID = document.getId();
                                    String status = eventStatusMap.get(eventID);
                                    detailsForNotification notifDetails = new detailsForNotification(userDetails, eventName, eventID, status);
                                    notifDetailsList.add(notifDetails);
                                }

                                // Return details
                                callback.onDataRetrieved(notifDetailsList);
                            } else {
                                callback.onError(eventDetailsTask.getException());
                            }
                        });
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public void showNotification(Context context, Notification notification) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            NotificationCompat.Builder notificationBuilder = NotificationView.buildNotification(context, notification);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        }
    }
}
