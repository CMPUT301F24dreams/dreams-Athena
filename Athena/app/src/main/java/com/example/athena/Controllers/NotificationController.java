package com.example.athena.Controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.athena.Models.Event;
import com.example.athena.Models.Notification;
import com.example.athena.Models.DetailsForNotification;
import com.example.athena.Models.UserNotifDetails;
import com.example.athena.Views.NotificationView;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
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

/**
 * Controller class responsible for coordinating the pulling of notification data
 * from the database, and creating notification objects
 */
public class NotificationController {
    private List<Notification> notificationList;

    private Context context;
    private String deviceId;

    /**
     * Constructor for the NotificationController class
     * @param context - The context object that the notifications will be created in
     * @param deviceId - the device Id of the current android device
     */
    public NotificationController(Context context, String deviceId) {
        this.context = context;
        this.deviceId = deviceId;
        NotificationView.createNotificationChannel(context);
    }

    /**
     * Checks a notifiation details object for validity based on user preferences
     * @param details - an object containing details and user preferences for a notification
     * @return true if notification is valid, false if invalid
     */
    public boolean checkValidNotification(DetailsForNotification details) {
        boolean invalid1 = !details.getUser().isNotifyIfChosen() && Objects.equals(details.getStatus(), "invited");
        boolean invalid2 = !details.getUser().isNotifyIfNotChosen() && Objects.equals(details.getStatus(), "waiting");
        Log.w("notification", "1: " + invalid1 + " 2: " + invalid2);
        return !invalid1 && !invalid2;
    }

    /**
     * Converts a notification details object into a usable notification object
     * @param details - object containing notification details needed to create a notification object
     * @return the created notification object
     */
    public Notification convertToNotification(DetailsForNotification details) {
        String title = "BLANK";
        String bodyText = "BLANK";
        String notifType = details.getStatus();
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

    /**
     * Shows a notification to the user by building it in android
     * @param context - the context the notification will be sent in
     * @param notification - object containing the details of the new notification
     */
    public void showNotification(Context context, Notification notification) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            NotificationCompat.Builder notificationBuilder = NotificationView.buildNotification(context, notification);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        }
    }
}
