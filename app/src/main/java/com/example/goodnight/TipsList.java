package com.example.goodnight;

import android.os.Bundle;


import java.util.ArrayList;
/**Class to distribute data to TipsActivity and TipDetailsActivity
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 * @version 1.0
 */

public class TipsList {
    private final ArrayList<Tip> tips;
    private static final TipsList ourInstance = new TipsList();
    /**
     *Method that acts as a key to access data in <code>TipsActivity</code> and <code>TipDetailsActivity</code> through enabling method.
     * @return      <code>private static final TipsList ourInstance</code>
     */

    public static TipsList getInstance() {
        return ourInstance;
    }

    /**
     * Constructor for Tip object arraylist tips.
     * @see              Tip#Tip(String, String)
     * @see              TipDetailsActivity#onCreate(Bundle)
     */

    private TipsList() {
        tips = new ArrayList<Tip>();
        tips.add(new Tip("Need of sleep", "Adult needs generally from 7 to 9 hours of sleep every day. For some people it is appropriate to sleep between 6 to 10 hours. It is not recommended to sleep less than 6 or more than 10 hours per day. For people over 65 years old, recommendation is 7 to 8 hours per night"));
        tips.add(new Tip("Better sleep times", "Try to sleep long enough. Don't eat too late or too heavy. Exercising is good for you but not for sleep if done too late. If it's winter, consider using light therapy lamp."));
        tips.add(new Tip("Increase the quality","Go to bed only when you're tired. If you can't fall asleep in 15 minutes, get up for a while. Get up at the same time every morning. Don't take naps."));
    }
    /**
     * Method to get entire arraylist which is 3 objects and all their data. Used in TipsActivity for listview.
     * @return                 arraylist tips which holds tip objects
     * @see TipsActivity#onCreate(Bundle)
     */

    public ArrayList<Tip> getTips() {
        return tips;
    }
    /**
     *Method for getting a tip object specified by variable index from tips arraylist index.
     * @param index             integer variable that gets its value in TipDetailsActivity from intent that gets its value from onItemClickListener in TipsActivity.
     * @return                  a single tip object from tips arraylist which position in the arraylist index is the same integer as the parameter integer index.
     * @see TipDetailsActivity#onCreate(Bundle)
     * @see Tip#Tip(String, String)
     */

    public Tip getTip (int index) {
        return tips.get(index);
    }
}
