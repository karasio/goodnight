package com.example.goodnight;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

/** Class to manage notification channels, and create notifications. */
class Notifications extends ContextWrapper {
    private NotificationManager mNotificationManager;
    public static final String GOODNIGHT_CHANNEL = "goodnight";

    /**
     * Create notification channel for app
     *
     * @param context The application context
     */
    public Notifications(Context context) {
        super(context);

        // Create the channel object with the unique ID FOLLOWERS_CHANNEL
        NotificationChannel goodnightChannel =
                new NotificationChannel(
                        GOODNIGHT_CHANNEL,
                        getString(R.string.notification_channel_goodnight),
                        NotificationManager.IMPORTANCE_DEFAULT);

        // Configure the channel's initial settings
        goodnightChannel.setLightColor(Color.RED);
        goodnightChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        // Submit the notification channel object to the notification manager
        getNotificationManager().createNotificationChannel(goodnightChannel);
    }

    /**
     * Build a bedtime notification
     *
     * @param title the title of the notification
     * @param body the body text for the notification
     * @return A Notification.Builder configured with the selected channel and details
     */
    public Notification.Builder getNotificationBedtime(String title, String body) {
        return new Notification.Builder(getApplicationContext(), GOODNIGHT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getIcon())
                .setAutoCancel(true);
    }

    /**
     * Build a log sleep notification
     *
     * @param title Title for notification.
     * @param body Message for notification.
     * @return A Notification.Builder configured with the selected channel and details
     */
    public Notification.Builder getNotificationLogSleep(String title, String body) {
        return new Notification.Builder(getApplicationContext(), GOODNIGHT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getIcon())
                .setAutoCancel(true);
    }

    /**
     * Send a notification.
     *
     * @param id The ID of the notification
     * @param notification The notification object
     */
    public void notify(int id, Notification.Builder notification) {
        getNotificationManager().notify(id, notification.build());
    }

    /**
     * Define app icon in notification
     *
     * @return The small icon resource id
     */
    private int getIcon() {

        return android.R.drawable.btn_star;
    }

    /**
     * Get the notification mNotificationManager.
     *
     * <p>Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }
}

// Class done with Google Codelabs tutorial https://codelabs.developers.google.com/codelabs/notification-channels-java/index.html?index=..%2F..index#0
// modified from https://github.com/googlecodelabs/notification-channels-java