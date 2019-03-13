package com.example.goodnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
/**Class to display results from user inputted data
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 * @version 1.0
 */

public class FeedbackActivity extends AppCompatActivity {
    /**
     * *Method to set the layout when activity is first created and complete calculations for best sleep from how many hours per night and if naps and exercising had part in it.
     * The calculations gets their starting values from DataHandlers nights arraylist and once calculations are done and results are valid they are displayed in the easy to comprehend UI.
     * The data put on the screen are: Average sleeping time, Average mood and Best mood rates according to hours slept, naps and exercising on best mood rate.
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState. Otherwise it is null.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageView imageView = (ImageView) findViewById(R.id.mood);

        //SHOW AVERAGE SLEEPING TIME & MOOD
        double howManyNights = DataHandler.getInstance().getNights().size();
        double moodAvg;
        int moodBest = 0;
        //int moodBestIndex = 0;
        int moodBestSum = 0;
        int howManyNightsWithBestMood = 0;
        double howManyHoursWithBestMood = 0;
        //boolean cb_exercise;
        double boolExercise = 0;
        int exerciseAvg;
        //boolean cb_napping;
        double boolNapping = 0;
        int nappingAvg;


        // if there is data in arraylist to show
        if (howManyNights > 0) {
            double howManyHours = 0;
            double moodSum = 0;

            for(int i = 0; i< howManyNights; i++) {
                howManyHours += DataHandler.getInstance().getNight(i).getTime_slept();
                moodSum += DataHandler.getInstance().getNight(i).getMood();

            }
            double hourAvg = howManyHours / howManyNights;
            moodAvg = moodSum / howManyNights;
            String textHourAvg = String.format("%.2f", hourAvg);
            ((TextView) findViewById(R.id.text_hoursSlept)).setText(textHourAvg);
        }
        // if no data found in arraylist
        else {
            ((TextView) findViewById(R.id.text_hoursSlept)).setText("0.0");
            moodAvg = 3.0;
        }

        if (moodAvg >= 4.5) {
            imageView.setImageResource(R.drawable.happyhappyface);
        } else if (moodAvg >= 3.5 && moodAvg < 4.5) {
            imageView.setImageResource(R.drawable.happyface);
        } else if (moodAvg >= 2.5 && moodAvg < 3.5) {
            imageView.setImageResource(R.drawable.nothappynorsadface);
        } else if (moodAvg >= 1.5 && moodAvg < 2.5) {
            imageView.setImageResource(R.drawable.sadface);
        } else
            imageView.setImageResource(R.drawable.sadsadface);

        // SHOW SPECS FOR BEST NIGHT

        // if arraylist is empty, show default values
        if (howManyNights <= 0) {
            ((TextView) findViewById(R.id.text_hoursSleptAvgBest)).setText("0.00");
            ((TextView) findViewById(R.id.text_napping)).setText("Should you be taking naps? ");
            ((TextView) findViewById(R.id.text_exercising)).setText("How about some exercise?");

        }
        // // find out which one is the best night
        else {
            // find out best mood value
            for (int i = 0; i < howManyNights; i++) {
                if (DataHandler.getInstance().getNight(i).getMood() >= moodBest) {
                    moodBest = DataHandler.getInstance().getNight(i).getMood();
                }
            }
            // find out stats on night with best mood values and when cb_special is false
            for (int i = 0; i < howManyNights; i++) {
                if (DataHandler.getInstance().getNight(i).getMood() == moodBest && !DataHandler.getInstance().getNight(i).isCB_SPECIAL()) {
                    moodBestSum += DataHandler.getInstance().getNight(i).getMood();
                    howManyNightsWithBestMood++;
                    howManyHoursWithBestMood += DataHandler.getInstance().getNight(i).getTime_slept();
                    if (DataHandler.getInstance().getNight(i).isCb_exercise()) {
                        boolExercise += 1;
                    }
                    if (DataHandler.getInstance().getNight(i).isCb_napping()) {
                        boolNapping += 1;
                    }
                }
            }

            // set text how many hours slept on best night
            if (howManyHoursWithBestMood > 0) {
                double best = howManyHoursWithBestMood / howManyNightsWithBestMood;
                String bestHours = String.format("%.2f", best);
                ((TextView) findViewById(R.id.text_hoursSleptAvgBest)).setText(bestHours);

                // set text according to booleans for training and napping
                exerciseAvg = (int)Math.round((boolExercise / howManyNightsWithBestMood) * 100.0);
                Log.d("appi", "Exercise " + exerciseAvg);
                nappingAvg = (int)Math.round((boolNapping / howManyNightsWithBestMood) * 100.0);
                Log.d("appi", "Napping " + nappingAvg);

                ((TextView) findViewById(R.id.text_napping)).setText(nappingAvg + "% daily napping");
                ((TextView) findViewById(R.id.text_exercising)).setText(exerciseAvg + "% daily exercising");
            } else {
                ((TextView) findViewById(R.id.text_hoursSleptAvgBest)).setText("0.00");
                ((TextView) findViewById(R.id.text_napping)).setText("Should you be taking naps? ");
                ((TextView) findViewById(R.id.text_exercising)).setText("How about some exercise?");
            }
        }
    }
}
