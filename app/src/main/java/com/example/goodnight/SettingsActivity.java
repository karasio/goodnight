package com.example.goodnight;

import android.app.Notification;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsActivity extends AppCompatActivity {
    private TimePicker picker3;
    private TimePicker picker4;
    private boolean cb_sleepTimeNotif;
    private boolean cb_logSleepNotif;
    private double time1, time2;
    private int hour3, hour4, minute3, minute4;

    //Notification variables
    private int notification_logSleep = 1100;
    private int notification_bedtime = 1101;
    private NotificationHelper mNotificationHelper;
    private Timer timer;
    private TimerTask taskBedtime;
    private TimerTask taskLogSleep;
    private long day = 86400000;            // 24h in milliseconds
    //private long day = 10000;                // debug toistoaika
    private long bedTimeinMillis;
    private long logSleepInMillis;
    private int time1inMinutes = 0;
    private int time2inMinutes = 0;
    private int whenToNotify;
    private int hoursNow;
    private int minutesNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        picker3 = (TimePicker) findViewById(R.id.timePicker3);
        picker3.setIs24HourView(true);
        picker4 = (TimePicker) findViewById(R.id.timePicker4);
        picker4.setIs24HourView(true);

        // get current time
        hoursNow = picker3.getHour();
        minutesNow = picker3.getMinute();

        //find notificationtimes from memory
        loadNotificationTimes();
        Log.d("notski", "bedtime in minutes " + time1inMinutes + " logsleepinMinutes " + time2inMinutes);

//        if (time1inMinutes > 0) {
//            Log.d("notski", "BT " + cb_sleepTimeNotif);
//            notificationAtCertainTime();
//        }
//        if (time2inMinutes > 0) {
//            Log.d("notski", "LS " + cb_logSleepNotif);
//            notificationAtCertainTime();
//        }
        // create necessary objects for notifications
        mNotificationHelper = new NotificationHelper(this);
        timer = new Timer();
    }

    public void saveButtonPressed(View view) {
        // get user input according to checkbox booleans

        if(cb_sleepTimeNotif) {
            // get notification time for bedtime
            hour3 = picker3.getHour();
            minute3 = picker3.getMinute();
            // convert given time values to double in hours & minutes
            time1 = hour3 + minute3/60.0;
            time1inMinutes = hour3 * 60 + minute3;
        }
        if (cb_logSleepNotif) {
            // get notification time for log sleep
            hour4 = picker4.getHour();
            minute4 = picker4.getMinute();
            // convert given time values to double in hours & minutes
            time2 = hour4 + minute4 / 60.0;
            time2inMinutes = hour4 * 60 + minute4;
        }

        // once save button is pressed
        if (view.getId() == R.id.button_saveSettings) {

            // set up notifications & save notification times permanently
            if (cb_sleepTimeNotif || cb_logSleepNotif) {
                notificationAtCertainTime();
                saveNotificationTimes();
            }

            // pass data to DataHandler and finish activity
            DataHandler.getInstance().setSettings(cb_sleepTimeNotif, time1, cb_logSleepNotif, time2);
            finish();
        }
    }
    // Checkbox click listener
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox_bedtimeNotif:
                if (checked) {
                    cb_sleepTimeNotif = true;
                    Log.d("notski", "cb_bedtime true");
                }
                else {
                    cb_sleepTimeNotif = false;
//                    Log.d("notski", "cb_bedtime false");
//                    taskBedtime.cancel();
//                    Log.d("notski", "bt notif cancelled");
//                    saveNotificationTimes();
                }
                break;

            case R.id.checkBox_logSleepNotif:
                if (checked) {
                    cb_logSleepNotif = true;
                    Log.d("notski", "cb_logSleep true");
                }
                else {
                    cb_logSleepNotif = false;
//                    Log.d("notski", "cb_logSleep false");
//                    taskLogSleep.cancel();
//                    Log.d("notski", "bt notif cancelled");
//                    saveNotificationTimes();
                }
                break;
        }
    }

    // get notification time&boolean values from SharedPreferences
    public void loadNotificationTimes() {
        SharedPreferences prefGet = getSharedPreferences("notificationValues", MODE_PRIVATE);
        time1inMinutes = prefGet.getInt("sleepNotif", 0);
        cb_sleepTimeNotif = prefGet.getBoolean("cbSleepNotif", false);
        bedTimeinMillis = prefGet.getLong("BTinMillis", 0);
        time2inMinutes = prefGet.getInt("logSleepNotif", 0);
        cb_logSleepNotif = prefGet.getBoolean("cbLogSleep", false);
        logSleepInMillis = prefGet.getLong("LSinMillis", 0);
    }

    // save notification time & boolean values to SharedPreferences
    public void saveNotificationTimes() {
        SharedPreferences prefPut = getSharedPreferences("notificationValues", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt("sleepNotif", time1inMinutes);
        prefEditor.putBoolean("cbSleepNotif", cb_sleepTimeNotif);
        prefEditor.putLong("BTinMillis", bedTimeinMillis);
        prefEditor.putInt("logSleepNotif", time2inMinutes);
        prefEditor.putBoolean("cbLogSleep", cb_logSleepNotif);
        prefEditor.putLong("LSinMillis", logSleepInMillis);
        prefEditor.commit();

    }

    // saving method for reset button (only used to store empty arraylist to SharedPreferences)
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

    // erase ArrayList & reset values of instance variables & save default values to SharedPreferences
    public void resetButtonPressed(View view) {
        DataHandler.getInstance().eraseNights();
        saveNights();
        time1inMinutes = 0;
        time2inMinutes = 0;
        cb_sleepTimeNotif = false;
        cb_logSleepNotif = false;
        saveNotificationTimes();
        //taskBedtime.cancel();
        //taskLogSleep.cancel();
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
                            getString(R.string.notification_common),
                            getString(R.string.notification_logSleep));
        }
        if (notificationBuilder != null) {
            mNotificationHelper.notify(id, notificationBuilder);
        }
    }

    // calculate when to send notification
    public void countNotificationTime() {
        Log.d("notski", "entering countNotificationTime");
        // convert to current time to minutes
        int timeCurrent = hoursNow * 60 + minutesNow;

        // check if notification should be pushed today or tomorrow:
        // 1440 = 24 hours in minutes, 60000 = 1 minute in milliseconds
        if (cb_sleepTimeNotif) {
            if (timeCurrent >= time1inMinutes) {
                whenToNotify = (1440 - (timeCurrent - time1inMinutes)) * 60000;
            } else {
                whenToNotify = (time1inMinutes - timeCurrent) * 60000;
            }
            bedTimeinMillis = whenToNotify;
            Log.d("notski", "BD notif time calculated " + bedTimeinMillis);
        }

        if (cb_logSleepNotif) {
            if (timeCurrent >= time2inMinutes) {
                whenToNotify = (1440 - (timeCurrent - time2inMinutes)) * 60000;
            } else {
                whenToNotify = (time2inMinutes - timeCurrent) * 60000;
            }
            logSleepInMillis = whenToNotify;
            Log.d("notski", "LS notif time calculated");
        }
    }

    // method for pushing the notification
    public void notificationAtCertainTime() {
        Log.d("notski", "entering notificationAtCertainTime");
        countNotificationTime();

        //which notification or both
        if (cb_sleepTimeNotif) {
            taskBedtime = new TimerTask() {
                @Override
                public void run() {
                    sendNotification(notification_bedtime);
                    Log.d("notski", "notification sent");
                }
            };

        }
        if (cb_logSleepNotif) {
            taskLogSleep = new TimerTask() {
                @Override
                public void run() {
                    sendNotification(notification_logSleep);
                    Log.d("notski", "notification sent");
                }
            };
        }
        if (cb_sleepTimeNotif) {
            timer.scheduleAtFixedRate(taskBedtime, bedTimeinMillis, day);
        }
        if (cb_logSleepNotif) {
            timer.scheduleAtFixedRate(taskLogSleep, logSleepInMillis, day);
        }
    }
}


