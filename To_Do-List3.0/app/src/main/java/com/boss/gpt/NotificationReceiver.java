package com.boss.gpt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.boss.To_Do_List.MainActivity;
import com.boss.To_Do_List.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract notification details from the Intent
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int notificationId = intent.getIntExtra("notificationId", 0);

        // Call the method to show the notification
        showNotification(context, title, message, notificationId);
    }

    // Method to show the notification
    private void showNotification(Context context, String title, String message, int notificationId) {
        // Create a NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "todo_notification_channel",
                    "To-Do Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "todo_notification_channel")
                .setSmallIcon(R.drawable.notification_bell_svgrepo_com) // Set your icon here
                .setContentTitle("Reminder \n"+title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(notificationId, builder.build());
    }
}