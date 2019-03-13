package com.example.goodnight;

import android.os.Bundle;

/**Class of night objects for <code>DataHandler</code> to create and store in its private arraylist <code>private ArrayList<Night> nights</code> and inherit night object methods and values for comparison purposes in <code>FeedbackActivity</code> class.
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 * @version 1.0
 */

public class Night {
    private final double TIME_TOSLEEP;
    private final double TIME_WAKEUP;
    private final double TIME_SLEPT;
    private final int MOOD;
    private final boolean CB_SPECIAL;
    private final boolean CB_NAPPING;
    private final boolean CB_EXERCISE;


    /**
     * Constructor for night object which holds all the data from one specfic object. Night objects are stored in DataHandler singleton.
     * The time spent sleeping is calculated from time_ToSleep and time_wakeUp with the following logic.
     * If time_ToSleep is greater than time_wakeUp time_slept is (24.0-time_ToSleep)+time_wakeUp.
     * If time_wakeUp is greater then math goes as such. time_wakeUp- time_ToSleep.
     *
     *  @param TIME_TOSLEEP                  double value inputted at <code>LogActivity</code> for going to sleep, input is picked from sliders that contain only valid values
     *  @param time_wakeUp                   double value user logged for waking up after that night at LogActivity, input is picked from sliders that contain only valid values
     *  @param time_slept                    Double value calculated at LogActivity in saveButtonPressed method, only invalid value is 0.00 and it is stopped from being inserted at the method saveButtonPressed. The time that user slept during the night.
     *  @param mood                          Integer value chosen in <code>LogActivity#moodButtonPressed</code> from mood radiogroup. Values range from 1-5.Value specifies how well the user feels after sleeping
     *  @param cb_special                    boolean value from 'special situation?' checkbox, returns true if checked and false when not checked
     *  @param cb_napping                    boolean value from any naps checkbox, returns true if checked and false when not checked
     *  @param cb_exercise                   boolean value from 'any exercise' checkbox, returns true if checked and false when not checked
     * @see DataHandler#storeData()
     * @see DataHandler#nights
     */

    public Night(double TIME_TOSLEEP, double time_wakeUp, double time_slept,
                 int mood, boolean cb_special, boolean cb_napping, boolean cb_exercise) {
        this.TIME_TOSLEEP = TIME_TOSLEEP;
        this.TIME_WAKEUP = time_wakeUp;
        this.TIME_SLEPT = time_slept;
        this.MOOD = mood;
        this.CB_SPECIAL = cb_special;
        this.CB_NAPPING = cb_napping;
        this.CB_EXERCISE = cb_exercise;
    }
    /**
     * Method to get hours and minutes user slept that particular night for feedback calculations
     * @return double value of time slept on one particular night
     * @see FeedbackActivity#onCreate(Bundle)
     */

    public double getTime_slept() {
        return TIME_SLEPT;
    }
    /**
     *Method to return mood values for feedbackActivity to compare.
     * @return integer value MOOD of how well the user slept
     * @see FeedbackActivity#onCreate(Bundle)
     */

    public int getMood() {
        return MOOD;
    }
    /**
     *Method to get 'Special situations' for a specific night for calculations to FeedbackActivity.
     * @return  boolean value of whether special situations checkbox from LogActivity is checked by boolean value of true or false.
     * @see FeedbackActivity#onCreate(Bundle)
     */

    public boolean isCB_SPECIAL() {
        return CB_SPECIAL;
    }
    /**
     *Method to get 'Any Napping' for a specific night for calculations to FeedbackActivity.
     * @return boolean value of whether 'Any Napping' checkbox from LogActivity is checked by boolean value of true or false.
     * @see FeedbackActivity#onCreate(Bundle)
     */


    public boolean isCb_napping() {
        return CB_NAPPING;
    }
    /**
     * Method to get 'Any exercise' for a specific night for calculations to FeedbackActivity.
     * @return  boolean value of whether 'Any exercise' checkbox from LogActivity is checked by boolean value of true or false.
     * @see FeedbackActivity#onCreate(Bundle)
     */

    public boolean isCb_exercise() {
        return CB_EXERCISE;
    }
    /**
     * Method to get all the data from a single night object.
     * @return all data from specified night object in string
     *
     */

    @Override
    public String toString() {
        return "Night{" +
                "TIME_TOSLEEP=" + TIME_TOSLEEP +
                ", TIME_WAKEUP=" + TIME_WAKEUP +
                ", TIME_SLEPT=" + TIME_SLEPT +
                ", MOOD=" + MOOD +
                ", CB_SPECIAL=" + CB_SPECIAL +
                ", CB_NAPPING=" + CB_NAPPING +
                ", CB_EXERCISE=" + CB_EXERCISE +
                '}';
    }
}
