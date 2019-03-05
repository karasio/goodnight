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
    private int testiYot; //Kimmon testimuuttuja FeedbackActivitylle
    private double testiSumma; //Kimmon testimuuttuja FeedbackActivitylle
    private int testiMoodS; //Kimmon testimuuttuja FeedbackActivitylle

    // variables for feedback

    // variables for settings
    private double timeDef_toSleep;
    private double timeDef_wakeUp;
    private boolean toggle_bedTimeNotif;
    private double time_bedTimeNotification;
    private boolean toggle_logSleepNotif = false;
    private double time_logSleepNotification;

    //variables for data storing


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

        // Add values to  ArrayList
        nights.add(new Night(time_ToSleep, time_wakeUp, time_slept, mood, cb_special, cb_napping, cb_exercise));
        //DEBUG PRINTING
        Log.d("appi","timeToSleep " + time_ToSleep);
        Log.d("appi", "timeToWakeup " + time_wakeUp);
        Log.d("appi", "timeSlept " + time_slept);
        Log.d("appi", "mood  " + mood);
        Log.d("appi", "cb special " + cb_special);
        Log.d("appi", "cb_napping " + cb_napping);
        Log.d("appi", "cb exer " + cb_exercise);


        Log.d("appi", "Arraylist: " + nights.toString());

        //Kimmon testit
        testiYot++;
        Log.d("appi","nights " + testiYot);
        testiSumma +=time_slept;
        Log.d("appi", "sleepTimeTotal " + testiSumma);
        //Kimmon testit FeedbackActivitylle
        testiMoodS += mood;
        Log.d("appi", "moodSum " + testiMoodS);
        //
    }

    // Kimmon testimetodit FeedbackActivitylle
    public int getYot() {
        return testiYot;
    }
    public Double getSumma() {
        return testiSumma;
    }
    public int getMoodSum () {
        return testiMoodS;
    }
    //


    public void setMood(int mood) {
        this.mood = mood;
        Log.d("appi", "mood " + mood);

        //Kimmon testit FeedbackActivitylle
        testiMoodS += mood;
        Log.d("appi", "moodsumma " + testiMoodS);
        //
    }

    public void setSettings(
                            boolean userBedTimeNotification, double time_bedTimeNotification,
                            boolean userLogSleepNotification, double time_logSleepNotification) {

        if (userBedTimeNotification) {
            this.toggle_bedTimeNotif = true;
            this.time_bedTimeNotification = time_bedTimeNotification;
        }
        if (userLogSleepNotification) {
            this.toggle_logSleepNotif = true;
            this.time_logSleepNotification = time_logSleepNotification;
        }
    }

    public void setNights(ArrayList<Night> nights) {
        this.nights = nights;
    }

    public ArrayList<Night> getNights() {
        return nights;
    }

    //KATRILLA TYÖN ALLA!    /*ÄLÄ POISTA T: KATRI*/
    //koska tämän voi toteuttaa? ei toimi tällaisenaan, kun jotta Nightin voisi lisätä arraylistiin,
    // pitäisi olla kaikissa muuttujissa dataa, eli olla toteutunut setSleepLogging, setMood ja
    //myös checkboxit.

    public void storeData(){

        nights.add(new Night(time_ToSleep, time_wakeUp, time_slept, mood, cb_special, cb_napping, cb_exercise));
    }
}




/*
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime();
		System.out.println(difference/1000);
 */