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

/**Class to enable or disable notifications and pick the time for said notifications and to reset gathered data of nights slept arraylist in DataHandler arraylist nights
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 */
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
    private long bedTimeinMillis;
    private long logSleepInMillis;
    private int bedtimeInMinutes = 0;
    private int logSleepTimeInMinutes = 0;
    private int hoursNow;
    private int minutesNow;

    /**
     * Method to set the layout when activity is first created and binding timepicker widgets to variables that are easier to manipulate.
     * Pulls old notification times and if they were enabled from shared preferences into other variables.
     * Creates new Timer for countNotificationTime method.
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState. Otherwise it is null.
     * @see SettingsActivity#loadNotificationTimes()
     */
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

        // create necessary objects for notifications
        mNotificationHelper = new Notifications(this);
        timer = new Timer();
    }

    /**
     * Method to save notification times by saving time picker variables if checkboxes returns true and convert values to hours and minutes to be more usable.
     * Method utilises other methods to save variables into SharedPreferences, DataHandler and telling Android when notifications are due.
     * Method switches back to MainActivity once saving is done.
     * @param view View that uses this method
     * @see SettingsActivity#saveNotificationTimes()
     * @see DataHandler#setSettings(boolean, double, boolean, double)
     * @see SettingsActivity#notificationAtCertainTime()
     */
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

    /**
     * Onclick method for checkboxes to get boolean values from checkboxes to variables. Checkbox enables notifications when true is returned and disabed if returns false.
     * The data is then given to saveButtonPressed where it is given to singleton and also to shared preferences because it is updated at the end of that method.
     * @param view Checkbox views that use this method and value is the one that was clicked
     * @see SettingsActivity#saveButtonPressed(View)
     */
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

    /**
     * Method to return values from SharedPreferences back to instance variables if app was closed.
     * Method is called when activity is first created.
     * @see SettingsActivity#onCreate(Bundle)
     */
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

    /**
     * Method to save notification time & boolean values to SharedPreferences.
     * Utilized in saveButtonPressed method.
     * @see SettingsActivity#saveButtonPressed(View)
     */
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

    /**
     * Auxiliary method for reset method as this method is only utilized in that method.
     * Stores an empty arraylist to SharedPreferences.
     * @see SettingsActivity#resetButtonPressed(View)
     */
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

    /**
     * Erases Arraylist & resets values of instance variables in class and saves reset values to SharedPreferences.
     * Method switches back to MainActivity once resetting is done
     * @param view button view that utilizes this method
     */
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

    /**Auxiliary method for notificationAtCertainTime.
     * Send activity notifications. Utilizes Notifications class to send notification to android notification bar.
     * Defines also what message is in the notification
     *
     * @param id Notifications ID value
     * @see Notifications#getNotificationBedtime(String, String)
     * @see Notifications#getNotificationLogSleep(String, String)
     * @see Notifications#notify(int, Notification.Builder)
     * @see SettingsActivity#notificationAtCertainTime()
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

    /**Auxiliary method for notificationAtCertainTime
     * Method to calculate when notification should pop up on phone from instance variables.
     * @see SettingsActivity#notificationAtCertainTime()
     */
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

    /**
     * Method that ties other methods together and determines if notification is to be sent.
     * Method first checks boolean values to determine which notification to send or if both are to be sent.
     * Then this method utilizes other method to push notification to Android and tells Android also at what time notification is to pop up.
     * Utilises methods countNotificationTime to calculate when notification is due and sendNotification to push the job to Android and tells the notification message.
     * @see SettingsActivity#countNotificationTime()
     * @see SettingsActivity#sendNotification(int)
     * @see SettingsActivity#saveButtonPressed(View)
     */
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


