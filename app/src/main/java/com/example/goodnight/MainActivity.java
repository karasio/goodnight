package com.example.goodnight;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    SharedPreferences prefPut;
    SharedPreferences Prefs;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences prefGet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNights();
        Log.d("appi", "" + DataHandler.getInstance().getNights());


    }

    @Override
    public void onPause() {
        super.onPause();
        saveNights();
    }

    public void buttonPressed(View view) {
        if (view.getId() == R.id.button_settings) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (view.getId() == R.id.button_feedback) {
            intent = new Intent(this, FeedbackActivity.class);
        } else if (view.getId() == R.id.button_log) {
            intent = new Intent(this, LogActivity.class);
        } else if (view.getId() == R.id.button_tips) {
            intent = new Intent(this, TipsActivity.class);
        }
        startActivity(intent);
    }

    // ARRAYLISTIN TALLENNUS MUISTIIN
    public void saveNights() {
        ArrayList<Night> nights = DataHandler.getInstance().getNights();        // hae arraylist singletonilta
        SharedPreferences mPrefs = getSharedPreferences("sleepData", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nights);
        prefsEditor.putString("myJson", json);
        prefsEditor.apply();
        Log.d("appi", "data saved");
    }

    // ARRAYLISTIN HAKU MUISTISTA
    public void loadNights() {
        ArrayList<Night> savedNights = new ArrayList<Night>();
        SharedPreferences mPrefs = getSharedPreferences("sleepData", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        Type type = new TypeToken<ArrayList<Night>>() {
        }.getType();
        if (gson.fromJson(json, type) == null) {
            DataHandler.getInstance().setNights(savedNights);
            Log.d("appi", "No data saved");
        } else {
            savedNights = gson.fromJson(json, type);
            DataHandler.getInstance().setNights(savedNights);
            Log.d("appi", "data loaded");
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


//    private void sendNotification(int id) {
//        Notification.Builder notificationBuilder = null;
//        if (id == notification_bedtime) {
//            notificationBuilder = mNotificationHelper.getNotificationBedtime(
//                            getString(R.string.notification_common),
//                            getString(R.string.notification_bedtime));
//        }
//        if (id == notification_logSleep) {
//            notificationBuilder = mNotificationHelper.getNotificationLogSleep(
//                                getString(R.string.notification_common),
//                                getString(R.string.notification_logSleep));
//        }
//        if (notificationBuilder != null) {
//            mNotificationHelper.notify(id, notificationBuilder);
//        }
//    }
//
//    /** Send Intent to load system Notification Settings for this app. */
//    private void goToNotificationSettings() {
//        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//        startActivity(i);
//    }
//    /**
//     * Send intent to load system Notification Settings UI for a particular channel.
//     *
//     * @param channel Name of channel to configure
//     */
//    private void goToNotificationChannelSettings(String channel) {
//        // Skeleton method to be completed later
//    }
//
//    public void whichNotification() {
//        if (DataHandler.getInstance().isCb_bedTimeNotif()) {
//            sendNotification(notification_bedtime);
//        }
//        if (DataHandler.getInstance().isCb_logSleepNotif()) {
//            sendNotification(notification_logSleep);
//        }
//    }
//
//    // ANOTHER APPROACH
//    public static String NOTIFICATION_ID = "notification_id";
//    public static String NOTIFICATION = "notification";
//
//    @Override
//    public void onReceive(final Context context, Intent intent) {
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification notification = intent.getParcelableExtra(NOTIFICATION);
//        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
//        notificationManager.notify(notificationId, notification);
//    }

}


