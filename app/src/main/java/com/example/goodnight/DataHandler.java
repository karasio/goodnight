package com.example.goodnight;

import android.util.Log;

public class DataHandler {
    // variables for logging sleep
    private double time_ToSleep;
    private double time_wakeUp;
    private double time_awake;
    private double time_slept;
    private int mood;
    private boolean cb_special;
    private boolean cb_napping;
    private boolean cb_exercise;
    private int maara=0;

    // variables for feedback

    // variables for settings
    private double timeDef_toSleep;
    private double timeDef_wakeUp;
    private boolean toggle_bedTimeNotif = false;
    private double time_bedTimeNotification;
    private boolean toggle_logSleepNotif = false;
    private double time_logSleepNotification;

    // other variables necessary in class
    private static final DataHandler ourInstance = new DataHandler();

    private DataHandler() {
    }

    public static DataHandler getInstance() {
        return ourInstance;
    }

    public void setSleepLogging(double time_ToSleep, double time_wakeUp, double time_slept) {
        this.time_ToSleep = time_ToSleep;
        this.time_wakeUp = time_wakeUp;
        this.time_slept = time_slept;
        Log.d("appi","timeToSleep " + time_ToSleep);
        Log.d("appi", "timeToWakeup " + time_wakeUp);
        Log.d("appi", "timeSlept " + time_slept);
    }

    public void setMood(int mood) {
        this.mood = mood;
        Log.d("appi", "mood " + mood);
    }

    public void setSettings(double timeDef_toSleep, double timeDef_wakeUp,
                            boolean userBedTimeNotification, double time_bedTimeNotification,
                            boolean userLogSleepNotification, double time_logSleepNotification) {
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
    public void increment() {
        this.maara++;
    }

    public int getMaara() {
        return this.maara;
    }
    public void setMaara(int luku) {
        this.maara = luku;
    }

}


/*
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime();
		System.out.println(difference/1000);
 */