package com.example.a19_notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String NOTIFICATION_CHANNEL_ID = "bsit-bzu";
    private NotificationManager notificationManager;
    private int notificationID = 100;
    private int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Button btnShowNotification = (Button)findViewById(R.id.btnShowNotification);
        btnShowNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showNotification();
            }
        });

        Button btnCancelNotification = (Button)findViewById(R.id.btnCancelNotification);
        btnCancelNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cancelNotification();
            }
        });

        Button btnUpdateNotification = (Button)findViewById(R.id.btnUpdateNotification);
        btnUpdateNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateNotification();
            }
        });
    }

    private void createNotificationChannel() {
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void showNotification() {
        Log.i("Show", "notification");

        // Create an Intent for the activity you want to start
        Intent intent = new Intent(this, ViewNotificationActivity.class);
        // Set the Activity to start in a new, empty task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setTicker("New Message Alert!")
                .setContentTitle("New Message!")
                .setContentText("You've received new message.")
                .setContentInfo("Information")
                .setNumber(++numMessages)
                .setContentIntent(pendingIntent);
        notificationManager.notify(notificationID, builder.build());
    }

    protected void cancelNotification() {
        Log.i("Cancel", "notification");
        notificationManager.cancel(notificationID);
    }

    protected void updateNotification() {
        Log.i("Update", "notification");

        // Create an Intent for the activity you want to start
        Intent intent = new Intent(this, ViewNotificationActivity.class);
        // Set the Activity to start in a new, empty task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setTicker("Update Message Alert!")
                .setContentTitle("Updated Message!")
                .setContentText("You've got updated message.")
                .setContentInfo("Information")
                .setNumber(++numMessages)
                .setContentIntent(pendingIntent);
        /* Update the existing notification using same notification ID */
        notificationManager.notify(notificationID, builder.build());
    }
}