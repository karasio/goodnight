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
    // variables for activity_settings.xml and calculations
    private TimePicker pickerBedtime;
    private TimePicker pickerLogSleepTime;
    private boolean cb_sleepTimeNotif;
    private boolean cb_logSleepNotif;
    private double bedTimeInHours, logSleepTimeinHours;

    //Notification variables
    private final int NOTIFICATION_LOGSLEEP = 1100;
    private final int NOTIFICATION_BEDTIME = 1101;
    private Notifications mNotificationHelper;
    private Timer timer;
    private TimerTask taskBedtime;
    private TimerTask taskLogSleep;
    //private long day = 10000;                // debug toistoaika
    private long bedTimeinMillis;
    private long logSleepInMillis;
    private int bedtimeInMinutes = 0;
    private int logSleepTimeInMinutes = 0;
    private int hoursNow;
    private int minutesNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        pickerBedtime = (TimePicker) findViewById(R.id.timePicker3);
        pickerBedtime.setIs24HourView(true);
        pickerLogSleepTime = (TimePicker) findViewById(R.id.timePicker4);
        pickerLogSleepTime.setIs24HourView(true);

        // get current time
        hoursNow = pickerBedtime.getHour();
        minutesNow = pickerBedtime.getMinute();

        //find notificationtimes from memory
        loadNotificationTimes();
        Log.d("notski", "bedtime in minutes " + bedtimeInMinutes + " logsleepinMinutes " + logSleepTimeInMinutes);

//        if (bedtimeInMinutes > 0) {
//            Log.d("notski", "BT " + cb_sleepTimeNotif);
//            notificationAtCertainTime();
//        }
//        if (logSleepTimeInMinutes > 0) {
//            Log.d("notski", "LS " + cb_logSleepNotif);
//            notificationAtCertainTime();
//        }
        // create necessary objects for notifications
        mNotificationHelper = new Notifications(this);
        timer = new Timer();
    }

    public void saveButtonPressed(View view) {
        // get user input according to checkbox booleans

        if(cb_sleepTimeNotif) {
            // get notification time for bedtime
            int hourBedtime = pickerBedtime.getHour();
            int minuteBedtime = pickerBedtime.getMinute();
            // convert given time values to double in hours & minutes
            bedTimeInHours = hourBedtime + minuteBedtime /60.0;
            bedtimeInMinutes = hourBedtime * 60 + minuteBedtime;
        }
        if (cb_logSleepNotif) {
            // get notification time for log sleep
            int hourLogSleepTime = pickerLogSleepTime.getHour();
            int minuteLogSleepTime = pickerLogSleepTime.getMinute();
            // convert given time values to double in hours & minutes
            logSleepTimeinHours = hourLogSleepTime + minuteLogSleepTime / 60.0;
            logSleepTimeInMinutes = hourLogSleepTime * 60 + minuteLogSleepTime;
        }

        // once save button is pressed
        if (view.getId() == R.id.button_saveSettings) {

            // set up notifications & save notification times permanently
            if (cb_sleepTimeNotif || cb_logSleepNotif) {
                notificationAtCertainTime();
                saveNotificationTimes();
            }

            // pass data to DataHandler and finish activity
            DataHandler.getInstance().setSettings(cb_sleepTimeNotif, bedTimeInHours, cb_logSleepNotif, logSleepTimeinHours);
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
    private void loadNotificationTimes() {
        SharedPreferences prefGet = getSharedPreferences("notificationValues", MODE_PRIVATE);
        bedtimeInMinutes = prefGet.getInt("sleepNotif", 0);
        cb_sleepTimeNotif = prefGet.getBoolean("cbSleepNotif", false);
        bedTimeinMillis = prefGet.getLong("BTinMillis", 0);
        logSleepTimeInMinutes = prefGet.getInt("logSleepNotif", 0);
        cb_logSleepNotif = prefGet.getBoolean("cbLogSleep", false);
        logSleepInMillis = prefGet.getLong("LSinMillis", 0);
    }

    // save notification time & boolean values to SharedPreferences
    private void saveNotificationTimes() {
        SharedPreferences prefPut = getSharedPreferences("notificationValues", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt("sleepNotif", bedtimeInMinutes);
        prefEditor.putBoolean("cbSleepNotif", cb_sleepTimeNotif);
        prefEditor.putLong("BTinMillis", bedTimeinMillis);
        prefEditor.putInt("logSleepNotif", logSleepTimeInMinutes);
        prefEditor.putBoolean("cbLogSleep", cb_logSleepNotif);
        prefEditor.putLong("LSinMillis", logSleepInMillis);
        prefEditor.apply();

    }

    // saving method for reset button (only used to store empty arraylist to SharedPreferences)
    private void saveNights() {
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
        bedtimeInMinutes = 0;
        logSleepTimeInMinutes = 0;
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
     * @param id Notifications ID value
     */
    private void sendNotification(int id) {
        Notification.Builder notificationBuilder = null;
        if (id == NOTIFICATION_BEDTIME) {
            notificationBuilder =
                    mNotificationHelper.getNotificationBedtime(
                            getString(R.string.notification_channel_goodnight),
                            getString(R.string.notification_bedtime));
        }
        if (id == NOTIFICATION_LOGSLEEP) {
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
    private void countNotificationTime() {
        Log.d("notski", "entering countNotificationTime");
        // convert to current time to minutes
        int timeCurrent = hoursNow * 60 + minutesNow;

        // check if notification should be pushed today or tomorrow:
        // 1440 = 24 hours in minutes, 60000 = 1 minute in milliseconds
        int whenToNotify;
        if (cb_sleepTimeNotif) {
            if (timeCurrent >= bedtimeInMinutes) {
                whenToNotify = (1440 - (timeCurrent - bedtimeInMinutes)) * 60000;
            } else {
                whenToNotify = (bedtimeInMinutes - timeCurrent) * 60000;
            }
            bedTimeinMillis = whenToNotify;
            Log.d("notski", "BD notif time calculated " + bedTimeinMillis);
        }

        if (cb_logSleepNotif) {
            if (timeCurrent >= logSleepTimeInMinutes) {
                whenToNotify = (1440 - (timeCurrent - logSleepTimeInMinutes)) * 60000;
            } else {
                whenToNotify = (logSleepTimeInMinutes - timeCurrent) * 60000;
            }
            logSleepInMillis = whenToNotify;
            Log.d("notski", "LS notif time calculated");
        }
    }

    // method for pushing the notification
    private void notificationAtCertainTime() {
        Log.d("notski", "entering notificationAtCertainTime");
        countNotificationTime();

        //which notification or both
        if (cb_sleepTimeNotif) {
            taskBedtime = new TimerTask() {
                @Override
                public void run() {
                    sendNotification(NOTIFICATION_BEDTIME);
                    Log.d("notski", "notification sent");
                }
            };
        }
        if (cb_logSleepNotif) {
            taskLogSleep = new TimerTask() {
                @Override
                public void run() {
                    sendNotification(NOTIFICATION_LOGSLEEP);
                    Log.d("notski", "notification sent");
                }
            };
        }
        // 24h in milliseconds
        long day = 86400000;
        if (cb_sleepTimeNotif) {
            timer.scheduleAtFixedRate(taskBedtime, bedTimeinMillis, day);
        }
        if (cb_logSleepNotif) {
            timer.scheduleAtFixedRate(taskLogSleep, logSleepInMillis, day);
        }
    }
}


