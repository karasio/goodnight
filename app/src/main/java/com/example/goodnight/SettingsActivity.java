package com.example.goodnight;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {
    private TimePicker picker3;
    private TimePicker picker4;
    private boolean cb_sleepTimeNotif;
    private boolean cb_logSleepNotif;
    private double time1, time2;

    //Notification variables
    private int notification_logSleep = 1100;
    private int notification_bedtime = 1101;
    private NotificationHelper mNotificationHelper;

    /*
     * A view model for interacting with the UI elements.
     */
    //private MainUi mUIModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        picker3 = (TimePicker) findViewById(R.id.timePicker3);
        picker3.setIs24HourView(true);
        picker4 = (TimePicker) findViewById(R.id.timePicker4);
        picker4.setIs24HourView(true);

        mNotificationHelper = new NotificationHelper(this);
        //mUIModel = new MainUi(findViewById(R.id.activity_settings));
    }

    public void saveButtonPressed(View view) {

        int hour3 = picker3.getHour();
        int minute3 = picker3.getMinute();

        int hour4 = picker4.getHour();
        int minute4 = picker4.getMinute();

        time1 = hour3 + minute3/60.0;
        time2 = hour4 + minute4/60.0;


        if (view.getId() == R.id.button_saveSettings) {
            DataHandler.getInstance().setSettings(cb_sleepTimeNotif, time1, cb_logSleepNotif, time2);
            Log.d("kake", "sleepnotif " + cb_sleepTimeNotif + " logNotif "+ cb_logSleepNotif);
            finish();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox_bedtimeNotif:
                if (checked) {
                    cb_sleepTimeNotif = true;
                    sendNotification(notification_bedtime);
                } else {
                    cb_sleepTimeNotif = false;
                }
                break;
            case R.id.checkBox_logSleepNotif:
                if (checked) {
                    cb_logSleepNotif = true;
                    sendNotification(notification_logSleep);
                } else {
                    cb_logSleepNotif = false;
                }
                break;
        }
    }

    // ARRAYLISTIN TALLENNUS MUISTIIN /*ÄLÄ POISTA T: KATRI*/
    public void saveNights() {
        ArrayList<Night> nights = DataHandler.getInstance().getNights();
        SharedPreferences mPrefs = getSharedPreferences("sleepData", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nights);
        prefsEditor.putString("myJson", json);
        prefsEditor.apply();
        Log.d("appi", "data saved");
    }

    public void resetButtonPressed(View view) {
        DataHandler.getInstance().eraseNights();
        saveNights();
        Log.d("appi", "Data erased");
        finish();
    }

    // NOTIFICATIONS

    /**
     * Send activity notifications.
     *
     * @param id The ID of the notification to create
     */
    private void sendNotification(int id) {
        Notification.Builder notificationBuilder = null;
        if (id == notification_bedtime) {
            notificationBuilder =
                    mNotificationHelper.getNotificationBedtime(
                            getString(R.string.notification_channel_goodnight),
                            getString(R.string.notification_bedtime));
        }
        if (id == notification_logSleep) {
            notificationBuilder =
                    mNotificationHelper.getNotificationLogSleep(
                            getString(R.string.notification_channel_goodnight),
                            getString(R.string.notification_logSleep));
        }
        if (notificationBuilder != null) {
            mNotificationHelper.notify(id, notificationBuilder);
        }
    }

    /** Send Intent to load system Notification Settings for this app. */
    private void goToNotificationSettings() {
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.
     *
     * @param channel Name of channel to configure
     */
    private void goToNotificationChannelSettings(String channel) {
        // Skeleton method to be completed later
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }

    /**
     * View model for interacting with Activity UI elements. (Keeps core logic for sample separate.)
     */
//    class MainUi implements View.OnClickListener {
//
//        private MainUi(View root) {
//
//            // Setup the buttons
//            (root.findViewById(R.id.birthday_follower_button)).setOnClickListener(this);
//            (root.findViewById(R.id.life_follower_button)).setOnClickListener(this);
//            (root.findViewById(R.id.follower_channel_settings_button)).setOnClickListener(this);
//            (root.findViewById(R.id.friend_dm_button)).setOnClickListener(this);
//            (root.findViewById(R.id.coworker_dm_button)).setOnClickListener(this);
//            (root.findViewById(R.id.dm_channel_settings_button)).setOnClickListener(this);
//            (root.findViewById(R.id.go_to_settings_button)).setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.birthday_follower_button:
//                    sendNotification(NOTIFICATION_FOLLOW);
//                    break;
//                case R.id.life_follower_button:
//                    sendNotification(NOTIFICATION_UNFOLLOW);
//                    break;
//                case R.id.follower_channel_settings_button:
//                    goToNotificationChannelSettings("");
//                    break;
//                case R.id.friend_dm_button:
//                    sendNotification(NOTIFICATION_DM_FRIEND);
//                    break;
//                case R.id.coworker_dm_button:
//                    sendNotification(NOTIFICATION_DM_COWORKER);
//                    break;
//                case R.id.dm_channel_settings_button:
//                    goToNotificationChannelSettings("");
//                    break;
//                case R.id.go_to_settings_button:
//                    goToNotificationSettings();
//                    break;
//                default:
//                    Log.e(TAG, getString(R.string.error_click));
//                    break;
//            }
//        }
//    }

}


