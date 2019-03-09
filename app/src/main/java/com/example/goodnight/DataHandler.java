package com.example.goodnight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ViewDebug;

import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {
    // variables for logging sleep
    private double time_ToSleep;
    private double time_wakeUp;
    private double time_slept;
    private int mood;
    private boolean cb_special;
    private boolean cb_napping;
    private boolean cb_exercise;

    // variables for settings
    private boolean cb_bedTimeNotif;
    private double time_bedTimeNotification;
    private boolean cb_logSleepNotif;
    private double time_logSleepNotification;

    // other variables necessary in class
    private static final DataHandler ourInstance = new DataHandler();
    private ArrayList<Night> nights = new ArrayList<>();

    private DataHandler() {
    }

    public static DataHandler getInstance() {
        return ourInstance;
    }

    public void setSleepLogging(double time_ToSleep, double time_wakeUp, double time_slept, int mood, boolean cb_special, boolean cb_napping, boolean cb_exercise) {
        this.time_ToSleep = time_ToSleep;
        this.time_wakeUp = time_wakeUp;
        this.time_slept = time_slept;
        this.mood = mood;
        this.cb_special = cb_special;
        this.cb_napping = cb_napping;
        this.cb_exercise = cb_exercise;

        //DEBUG PRINTING
        Log.d("appi","timeToSleep " + time_ToSleep);
        Log.d("appi", "timeToWakeup " + time_wakeUp);
        Log.d("appi", "timeSlept " + time_slept);
        Log.d("appi", "mood  " + mood);
        Log.d("appi", "cb special " + cb_special);
        Log.d("appi", "cb_napping " + cb_napping);
        Log.d("appi", "cb exer " + cb_exercise);


        Log.d("appi", "Arraylist: " + nights.toString());
    }

    public void setSettings(
            boolean userBedTimeNotification, double time_bedTimeNotification,
            boolean userLogSleepNotification, double time_logSleepNotification) {

        if (userBedTimeNotification) {
            this.cb_bedTimeNotif = true;
            this.time_bedTimeNotification = time_bedTimeNotification;
        }
        if (userLogSleepNotification) {
            this.cb_logSleepNotif = true;
            this.time_logSleepNotification = time_logSleepNotification;
        }
        Log.d("kake", "bedtime " + time_bedTimeNotification + " " + cb_bedTimeNotif);
        Log.d("kake", "logsleep " + time_logSleepNotification + " " + cb_logSleepNotif);
    }

    public boolean isCb_bedTimeNotif() {
        return cb_bedTimeNotif;
    }

    public boolean isCb_logSleepNotif() {
        return cb_logSleepNotif;
    }

    public void setNights(ArrayList<Night> nights) {
        this.nights = nights;
    }

    public ArrayList<Night> getNights() {
        return nights;
    }

    public void storeData(){

        nights.add(new Night(time_ToSleep, time_wakeUp, time_slept, mood, cb_special, cb_napping, cb_exercise));
        Log.d("appi", "" + nights.toString());
    }

    public Night getNight(int index) {
        Night night = this.nights.get(index);
        return night;
    }

    public void eraseNights() {
        ArrayList<Night> emptyNights = new ArrayList<>();
        setNights(emptyNights);
    }

}

/*
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime();
		System.out.println(difference/1000);
 */