package com.example.athena.Views;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.athena.Controllers.NotificationController;
import com.example.athena.Models.Notification;
import com.example.athena.Models.DetailsForNotification;
import com.example.athena.Models.UserNotifDetails;
import com.example.athena.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * class responsible for creating and building notifications in the app.
  */


public class NotificationView {

    private static final String channelID = "DEFAULT_CHANNEL";
    private String deviceId;

    public NotificationView(String deviceId) {
        this.deviceId = deviceId;
    }

    public static void createNotificationChannel(Context context) {
        // create the notification channel if build version requires it
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelID,
                    "DEFAULT_CHANNEL",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Default channel for all athena notifications");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Callback for getNotificationData method, needed in order to return data
     */
    public interface NotificationDataCallback {
        void onDataRetrieved(DetailsForNotification notifDetatils);
        void onError(Exception e);
    }

    /**
     * Method to check the firestore database for users that need to be notified about
     * changes in their registered events, then gather data needed to build a notification
     * and return it
     * @param callback - callback object that helps return data to the callee
     */
    public void getNotificationData(String eventId, String status, NotificationView.NotificationDataCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get user details
        Task<DocumentSnapshot> userTask = db.collection("Users")
                .document(this.deviceId)
                .get();

        // Get event details
        Task<DocumentSnapshot> eventTask = db.collection("Events")
                .document(eventId)
                .get();

        // Wait for both tasks to complete
        Tasks.whenAll(userTask, eventTask).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                // Process user data
                UserNotifDetails userDetails = new UserNotifDetails();
                if (userTask.getResult() != null && userTask.getResult().exists()) {
                    DocumentSnapshot userDoc = userTask.getResult();
                    userDetails.setUserName(userDoc.getString("name"));
                    userDetails.setNotifyIfChosen(Boolean.TRUE.equals(userDoc.getBoolean("notifyIfChosen")));
                    userDetails.setNotifyIfNotChosen(Boolean.TRUE.equals(userDoc.getBoolean("notifyIfNotChosen")));
                }

                // Process event data
                if (eventTask.getResult() != null && eventTask.getResult().exists()) {
                    DocumentSnapshot eventDoc = eventTask.getResult();
                    String eventName = eventDoc.getString("eventName");

                    // Create the notification details object
                    DetailsForNotification notifDetails = new DetailsForNotification(
                            userDetails,
                            eventName,
                            eventId,
                            status
                    );

                    // Return the notification details in the callback
                    callback.onDataRetrieved(notifDetails);
                } else {
                    // Event not found
                    callback.onDataRetrieved(null);
                }
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static NotificationCompat.Builder buildNotification(Context context, Notification notification) {
        return new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.events_logo) // placeholder for now, we can decide on a logo later
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBodyText())
                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX);
    }
}
