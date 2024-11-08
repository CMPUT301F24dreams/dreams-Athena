package com.example.athena.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.example.athena.Controllers.NotificationController;
import com.example.athena.Models.Notification;
import com.example.athena.Models.detailsForNotification;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.Nullable;

public class NotificationService extends Service {
    // Service to periodically check the database for new notifications
    private Handler handler;
    private String deviceId;
    private NotificationController notificationController;
    private FirebaseFirestore db;
    private final int Interval = 45000; // 45 seconds

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Pull deviceId from intent and initialize controller and db
        this.deviceId = intent.getStringExtra("deviceId");
        this.notificationController = new NotificationController(this, deviceId);
        this.db = FirebaseFirestore.getInstance();

        // Start the periodic notification checker
        this.handler = new Handler(Looper.getMainLooper());
        startNotificationChecker();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // not binding to anything
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void startNotificationChecker() {
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationController.getNotificationData(new NotificationController.NotificationDataCallback() {
                    @Override
                    public void onDataRetrieved(List<detailsForNotification> notifDetailList) {
                        for (detailsForNotification details : notifDetailList) {
                            // check that the user allows notifications of this type
                            if (notificationController.checkValidNotification(details)) {
                                // convert notification details into a notification object
                                Notification notification = notificationController.convertToNotification(details);

                                // show the notification object
                                notificationController.showNotification(NotificationService.this, notification);

                                // update notified status in db
                                notificationController.updateNotifiedStatus(details.getEventId());
                            }
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                // reschedules the task
                handler.postDelayed(this, Interval);
            }
        }, Interval); // initial delay
    }
}
