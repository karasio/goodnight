package com.example.goodnight;

import android.util.Log;

public class DataHandler {
    // variables for logging sleep
    private String time_ToSleep;
    private String time_wakeUp;
    private String time_awake;
    private String time_slept;
    private int mood;
    private boolean cb_special;
    private boolean cb_napping;
    private boolean cb_exercise;

    // variables for feedback

    // variables for settings
    private String timeDef_toSleep;
    private String timeDef_wakeUp;
    private boolean toggle_bedTimeNotif = false;
    private String time_bedTimeNotification;
    private boolean toggle_logSleepNotif = false;
    private String time_logSleepNotification;

    // other variables necessary in class
    private static final DataHandler ourInstance = new DataHandler();

    private DataHandler() {
    }

    public static DataHandler getInstance() {
        return ourInstance;
    }

    public void setSleepLogging(String time_ToSleep, String time_wakeUp, String time_awake,
                                int mood, boolean cb_special, boolean cb_napping, boolean cb_exercise) {
        this.time_ToSleep = time_ToSleep;
        this.time_wakeUp = time_wakeUp;
        this.time_awake = time_awake;
        this.mood = mood;
        this.cb_special = cb_special;
        this.cb_napping = cb_napping;
        this.cb_exercise = cb_exercise;
    }

    public void setSettings(String timeDef_toSleep, String timeDef_wakeUp,
                            boolean userBedTimeNotification, String time_bedTimeNotification,
                            boolean userLogSleepNotification, String time_logSleepNotification) {
        this.timeDef_toSleep = timeDef_toSleep;
        this.timeDef_wakeUp = timeDef_wakeUp;

        if (userBedTimeNotification != false) {
            this.toggle_bedTimeNotif = true;
            this.time_bedTimeNotification = time_bedTimeNotification;
        }
        if (userLogSleepNotification != false) {
            this.toggle_logSleepNotif = true;
            this.time_logSleepNotification = time_logSleepNotification;
        }

    }

}


/*      AIKAEROTUKSEN LASKEMINEN

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime();
		System.out.println(difference/1000);
 */