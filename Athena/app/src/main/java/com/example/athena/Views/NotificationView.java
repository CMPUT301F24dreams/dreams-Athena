package com.example.athena.Views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.athena.Models.Notification;
import com.example.athena.Models.detailsForNotification;
import com.example.athena.R;
// View class responsible for creating and building notifications in the app.

public class NotificationView {

    private static final String channelID = "DEFAULT_CHANNEL";

    public static void createNotificationChannel(Context context) {
        // create the notification channel if build version requires it
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelID,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Default channel for all athena notifications");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static NotificationCompat.Builder buildNotification(Context context, Notification notification) {
        return new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.events_logo) // placeholder for now, we can decide on a logo later
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBodyText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
    }
}
