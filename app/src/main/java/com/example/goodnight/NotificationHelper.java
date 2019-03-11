/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.goodnight;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

/** Class to manage notification channels, and create notifications. */
class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    public static final String GOODNIGHT_CHANNEL = "goodnight";

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param context The application context
     */
    public NotificationHelper(Context context) {
        super(context);

        // Create the channel object with the unique ID FOLLOWERS_CHANNEL
        NotificationChannel goodnightChannel =
                new NotificationChannel(
                        GOODNIGHT_CHANNEL,
                        getString(R.string.notification_channel_goodnight),
                        NotificationManager.IMPORTANCE_DEFAULT);

        // Configure the channel's initial settings
        goodnightChannel.setLightColor(Color.GREEN);
        goodnightChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        // Submit the notification channel object to the notification manager
        getNotificationManager().createNotificationChannel(goodnightChannel);

    }

    /**
     * Get a bedtime notification
     *
     * @param title the title of the notification
     * @param body the body text for the notification
     * @return A Notification.Builder configured with the selected channel and details
     */
    public Notification.Builder getNotificationBedtime(String title, String body) {
        return new Notification.Builder(getApplicationContext(), GOODNIGHT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);
    }

    /**
     * Get a log sleep notification
     *
     * @param title Title for notification.
     * @param body Message for notification.
     * @return A Notification.Builder configured with the selected channel and details
     */
    public Notification.Builder getNotificationLogSleep(String title, String body) {
        return new Notification.Builder(getApplicationContext(), GOODNIGHT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
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
     * Get the small icon for this app
     *
     * @return The small icon resource id
     */
    private int getSmallIcon() {

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

// https://github.com/googlecodelabs/notification-channels-java used as a reference for setting up notifications