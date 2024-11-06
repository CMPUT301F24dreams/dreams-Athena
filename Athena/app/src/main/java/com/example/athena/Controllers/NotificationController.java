package com.example.athena.Controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.athena.Models.Notification;
import com.example.athena.Views.NotificationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class NotificationController {
    private List<Notification> notificationList;

    private Context context;
    private String deviceId;

    public NotificationController(Context context, String deviceId) {
        this.context = context;
        this.deviceId = deviceId;
        NotificationView.createNotificationChannel(context);
    }

    public void checkNotifications(FirebaseFirestore db) {
        // query the database for new notifications with device id
        db.collection("Notifications")
                .whereEqualTo("deviceID", this.deviceId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // extract variables
                            String title = document.getString("title");
                            String bodyText = document.getString("bodyText");
                            String activityLink = document.getString("activityLink");
                            Notification notificationNew = new Notification(this.deviceId, bodyText, title, activityLink);
                            // show notification
                            showNotification(context, notificationNew);
                            // delete from database
                            db.collection("Notifications").document(document.getId())
                                    .delete()
                                    .addOnFailureListener(failure -> {
                                        // handle error
                                    });
                        }
                    } else {
                        // error getting documents
                    }
                });
        // TODO
        // if notification list is needed:
        // - remove notification deletion logic
        // - update the notification list
        // - notify the notification activity that it has been changed, if update happens
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
