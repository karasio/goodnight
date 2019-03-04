package com.example.goodnight;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

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

    //variables for data storing

    private static final String PREF = "TestPref";
    SharedPreferences prefPut;
    SharedPreferences.Editor prefEditor;
    SharedPreferences prefGet;

    // other variables necessary in class
    private static final DataHandler ourInstance = new DataHandler();
    private ArrayList<Night> nights = new ArrayList<>();

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

        if (userBedTimeNotification) {
            this.toggle_bedTimeNotif = true;
            this.time_bedTimeNotification = time_bedTimeNotification;
        }
        if (userLogSleepNotification) {
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

    /*

    KATRILLA TYÖN ALLA!
    public void storeData {

        nights.add(new Night(time_ToSleep, time_wakeUp, time_slept, mood, cb_special, cb_napping, cb_exercise);

        prefPut = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        prefEditor = prefPut.edit();



        //prefEditor.putString("onCreate", counterOnCreate.getCounter());
        //prefEditor.putString("onStart", counterOnStart.getCounter());
        //prefEditor.putString("onResume", counterOnResume.getCounter());

        prefEditor.commit();
    }*/
}




/*
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime();
		System.out.println(difference/1000);
 */