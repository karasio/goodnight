package com.example.goodnight;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
/**Class to distribute data from <code>LogActivity</code> to <code>FeedbackActivity</code>.
 * @author Toni Ruoranen
 * @author Katri Rasio
 * @author Kimmo Perälä
 * @version 1.0

 */

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
    private boolean cb_logSleepNotif;

    // other variables necessary in class
    private static final DataHandler ourInstance = new DataHandler();
    private ArrayList<Night> nights = new ArrayList<>();

    //constructor for singleton class

    /**
     *  Singleton instance constructor. It is private so other classes can't access it and create new instances.
     */
    private DataHandler() {
    }
    /**
     *Method that acts as a key to access data in <code>DataHandler</code> for other classes.
     * @return       private static final DataHandler ourInstance
     */

    public static DataHandler getInstance() {
        return ourInstance;
    }


    // set values from LogActivity to variables
    /**
     * Auxiliary method for <code>storeData</code> that bring variables to <code>Datahandler </code>class from <code>LogActivity</code> before adding to <code>nights</code> arraylist.
     * The time spent sleeping is calculated from time_ToSleep and time_wakeUp with the following logic. If time_ToSleep is greater than time_wakeUp time_slept is (24.0-time_ToSleep)+time_wakeUp. If time_wakeUp is greater then math goes as such. time_wakeUp- time_ToSleep.
     *
     * @param time_ToSleep                  double value inputted at <code>LogActivity</code> for going to sleep, input is picked from sliders that contain only valid values
     * @param time_wakeUp                   double value user logged for waking up after that night at LogActivity, input is picked from sliders that contain only valid values
     * @param time_slept                    Double value calculated at LogActivity in saveButtonPressed method, only invalid value is 0.00 and it is stopped from being inserted at the method saveButtonPressed. The time that user slept during the night
     * @param mood                          Integer value chosen in <code>LogActivity#moodButtonPressed</code> from mood radiogroup. Values range from 1-5.Value specifies how well the user feels after sleeping
     * @param cb_special                    boolean value from 'special situation?' checkbox, returns true if checked and false when not checked
     * @param cb_napping                    boolean value from any naps checkbox, returns true if checked and false when not checked
     * @param cb_exercise                   boolean value from 'any exercise' checkbox, returns true if checked and false when not checked
     * @see DataHandler#storeData()
     * @see Night#Night(double, double, double, int, boolean, boolean, boolean)
     * @see LogActivity#saveButtonPressed(View)
     * @see LogActivity#onCheckboxClicked(View)
     * @see LogActivity#moodButtonPressed(View)
     */

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
    }

    // set values from Settings to variables
    /**
     *This method is used in <code>SettingsActivity</code> class to save data to singleton
     * @param userBedTimeNotification           boolean value from class <code>SettingsActivity</code> method <code>onCheckBoxClicked</code> that is in checkbox, checks if user wants bedtime notification
     * @param time_bedTimeNotification          gets double value from class <code>SettingsActivity</code> and method <code>onCreate</code>the time for the bedtime notification, picked from slider that contains only valid values.
     * @param userLogSleepNotification          boolean value from class <code>SettingsActivity</code> method <code>onCheckBoxClicked</code> that is in checkbox, checks if user wants logging notification
     * @param time_logSleepNotification         gets double value from class <code>SettingsActivity</code> and method <code>onCreate</code> time for the logging sleep times notification, picked from slider that contains only valid values.
     * @see SettingsActivity#saveButtonPressed(View)
     * @see SettingsActivity#onCheckboxClicked(View)
     */

    public void setSettings(
            boolean userBedTimeNotification, double time_bedTimeNotification,
            boolean userLogSleepNotification, double time_logSleepNotification) {

        if (userBedTimeNotification) {
            this.cb_bedTimeNotif = true;
            double time_bedTimeNotification1 = time_bedTimeNotification;
        }
        if (userLogSleepNotification) {
            this.cb_logSleepNotif = true;
            double time_logSleepNotification1 = time_logSleepNotification;
        }
        Log.d("notski", "bedtime " + time_bedTimeNotification + " " + cb_bedTimeNotif);
        Log.d("notski", "logsleep " + time_logSleepNotification + " " + cb_logSleepNotif);
    }

    // setter for ArrayList including data from logSleep
    /**
     * This is an auxiliary method for <code>eraseNights</code> and <code>loadNights</code> by copying <code>Night</code> object containing arraylist to our <code>nights</code> arraylist and if the copied arraylist is empty, it resets <code>private arraylist nights</code>  in practise.
     * @param nights        arraylist containing <code>Night</code> objects
     * @see MainActivity#loadNights()
     * @see DataHandler#eraseNights()
     */

    public void setNights(ArrayList<Night> nights) {
        this.nights = nights;
    }

    // getter for nights-ArrayList
    /**
     * Specific method to call the entire arraylist <code>nights</code> from ourInstance. Useful for example to combine with other methods to get arraylist size.
     * @return          the complete arraylist <code>nights</code>
     */

    public ArrayList<Night> getNights() {
        return nights;
    }

    // add values from LogSleep activity to ArrayList

    /**
     * Makes a new entry into the <code>nights</code> arraylist as a new <code>Night</code> object in <code>LogActivity</code>. Variables get values from <code>setSleepLogging</code> method.
     * @see LogActivity#saveButtonPressed(View)
     * @see DataHandler#setSleepLogging(double, double, double, int, boolean, boolean, boolean)
     * @see Night#Night(double, double, double, int, boolean, boolean, boolean)
     */

    public void storeData(){

        nights.add(new Night(time_ToSleep, time_wakeUp, time_slept, mood, cb_special, cb_napping, cb_exercise));
        Log.d("appi", "" + nights.toString());
    }

    // getter for a specific night
    /**
    * Pulls up a specific night to get access to other data from it by using the arraylist index number by granting access to Night object methods.
    * @param index      Integer to get a specific <code>Night</code> from <code>nights</code> arraylist.
    * @return           A specific <code>Night</code> object which is on the <code>nights</code> arraylist spot that integer index specifies.
     *
     */

    public Night getNight(int index) {
        Night night = this.nights.get(index);
        return night;
    }

    // delete ArrayList nights permanently
    /**
     *  Resets the arraylist <code>nights </code>by creating a new empty arraylist and replacing the old arraylist with that. Used in <code>SettingsActivity</code>.
     * @see DataHandler#setNights(ArrayList)
     * @see SettingsActivity#resetButtonPressed(View)
     */

    public void eraseNights() {
        ArrayList<Night> emptyNights = new ArrayList<>();
        setNights(emptyNights);
    }
}
