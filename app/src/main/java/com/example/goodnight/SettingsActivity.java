package com.example.goodnight;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private TimerTask task;
    private long currentTime;
    private long day = 86400000;            // 24h in milliseconds
    //private long day = 10000;                // debug toistoaika
    private long bedTimeinMillis;
    private long logSleepInMillis;
    private long whichEverInMillis;
    private Integer time1inMinutes = 0;
    private Integer time2inMinutes = 0;
    private int whenToNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        picker3 = (TimePicker) findViewById(R.id.timePicker3);
        picker3.setIs24HourView(true);
        picker4 = (TimePicker) findViewById(R.id.timePicker4);
        picker4.setIs24HourView(true);

        mNotificationHelper = new NotificationHelper(this);
        timer = new Timer();

        //mUIModel = new MainUi(findViewById(R.id.activity_settings));
    }

    public void saveButtonPressed(View view) {

        hour3 = picker3.getHour();
        minute3 = picker3.getMinute();

        hour4 = picker4.getHour();
        minute4 = picker4.getMinute();

        time1 = hour3 + minute3/60.0;
        time1inMinutes = hour3 * 60 + minute3;
        Log.d("notski", "bedtime " + time1);

        time2 = hour4 + minute4/60.0;
        time2inMinutes = hour4 * 60 + minute4;

        if (view.getId() == R.id.button_saveSettings) {

            //Log.d("notski", "sleepnotif " + cb_sleepTimeNotif + " logNotif "+ cb_logSleepNotif);

            // set up notifications
            notificationAtCertainTime();

            // store data and finish
            DataHandler.getInstance().setSettings(cb_sleepTimeNotif, time1, cb_logSleepNotif, time2);
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
                } else {
                    cb_sleepTimeNotif = false;
                    task.cancel();
                }
                break;
            case R.id.checkBox_logSleepNotif:
                if (checked) {
                    cb_logSleepNotif = true;

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
        task.cancel();
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

    public void notificationAtCertainTime() {
        if (cb_sleepTimeNotif || cb_logSleepNotif) {
            if (cb_sleepTimeNotif && cb_logSleepNotif) {
                task = new TimerTask() {
                    @Override
                    public void run() {
                        if (cb_sleepTimeNotif) {

                            bedTimeinMillis = new Long(time1inMinutes);
                            currentTime = System.currentTimeMillis();
                            bedTimeinMillis = currentTime + (bedTimeinMillis * 60000);
                            Log.d("notski", "BD notif in millis " + bedTimeinMillis + " currenttime " + currentTime);

                            bedTimeinMillis = bedTimeinMillis + currentTime;
                            sendNotification(notification_bedtime);
                            //Log.d("notski", "BD notif in millis " + bedTimeinMillis + " currenttime " + currentTime);
                        }
                        if (cb_logSleepNotif) {

                            logSleepInMillis = new Long(time2inMinutes);
                            currentTime = System.currentTimeMillis();
                            logSleepInMillis = currentTime + (logSleepInMillis * 60000);
                            Log.d("notski", "LS notif in millis " + logSleepInMillis + " currenttime " + currentTime);
                            sendNotification(notification_logSleep);
                        }
                    }
                };
                Log.d("notski", "both notifications set");
                timer.scheduleAtFixedRate(task, bedTimeinMillis, day);
                timer.scheduleAtFixedRate(task, logSleepInMillis, day);
            }

            ///////////////////////
            // DEBUG THIS ONE & ONCE IT WORKS, COPY TO OTHERS!!
            //////////////////////
            else if (cb_sleepTimeNotif || cb_logSleepNotif) {
                task = new TimerTask() {
                    @Override
                    public void run() {
                        if (cb_sleepTimeNotif) {

                            int hoursNOW =Calendar.HOUR_OF_DAY + 8;                // timezone difference
                            int minutesNOW = Calendar.MINUTE;
                            Log.d("notski", hoursNOW + ":" + minutesNOW);

                            int timeNow = hoursNOW*60 + minutesNOW;
                            Log.d("notski", "TimeNow " + timeNow);

                            if (timeNow > time1inMinutes) {
                                whenToNotify = (timeNow - time1inMinutes + 1440) * 60000;     // notification wanted next time tomorrow & in milliseconds

                            } else {
                                whenToNotify = (time1inMinutes - timeNow) * 60000;
                            }
                            Log.d("notski", "timedifference in milliseconds " + whenToNotify);
                            whichEverInMillis = new Long(whenToNotify);
                            Log.d("notski", "whichEverInMillis " + whichEverInMillis);

                            sendNotification(notification_bedtime);
//                            time1inMinutes = time1inMinutes * 60000;
//                            whichEverInMillis = Long.valueOf(time1inMinutes.longValue());
//                            Log.d("notski", "time1inminutes as ms&long " + whichEverInMillis);
//                            currentTime = System.currentTimeMillis();
//                            Log.d("notski", "currentTime " + currentTime);
//                            currentTime += whichEverInMillis;
//                            //Log.d("notski", "bedtime notif set");
//                            Log.d("notski", "notif at " + currentTime);

                        }
                        ///////////////////
                        //END DEBUG
                        //////////////////
                        if (cb_logSleepNotif) {
                            sendNotification(notification_logSleep);
                            whichEverInMillis = new Long(time2inMinutes);
                            currentTime = System.currentTimeMillis();
                            whichEverInMillis = currentTime + (whichEverInMillis * 60000);
                            Log.d("notski", "logsleep notif set");
                            Log.d("notski", "LS notif in millis " + whichEverInMillis + " currenttime " + currentTime);

                        }
                    }
                };
                timer.scheduleAtFixedRate(task, 300000, day);
            } else {
                Log.d("notski", "no notifications set");
            }
        }
    }
}


