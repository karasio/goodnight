package com.example.goodnight;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageView imageView = (ImageView) findViewById(R.id.mood);

        //SHOW AVERAGE SLEEPING TIME & MOOD
        double howManyNights = DataHandler.getInstance().getNights().size();
        double moodAvg;
        int moodBest = 0;
        int moodBestIndex = 0;
        int moodBestSum = 0;
        int howManyNightsWithBestMood = 0;
        double howManyHoursWithBestMood = 0;
        boolean cb_exercise;
        int boolExercise = 0;
        boolean cb_napping;
        int boolNapping = 0;

        // if there is data in arrayist to show
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
                    //moodBestIndex = i;
                }
            }
            // find out stats on night with best mood values
            for (int i = 0; i < howManyNights; i++) {
                if (DataHandler.getInstance().getNight(i).getMood() == moodBest) {
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

//            // check booleans for training & napping
//            cb_exercise = DataHandler.getInstance().getNight(moodBestIndex).isCb_exercise();
//            cb_napping = DataHandler.getInstance().getNight(moodBestIndex).isCb_napping();

            // set text how many hours slept on best night
            double best = howManyHoursWithBestMood / howManyNightsWithBestMood;
            String bestHours = String.format("%.2f", best);
            ((TextView) findViewById(R.id.text_hoursSleptAvgBest)).setText(bestHours);

            // set text according to booleans for training and napping
            int exerciseAvg = boolExercise / howManyNightsWithBestMood;
            Log.d("appi", "Exercise " + exerciseAvg);
            int nappingAvg = boolNapping / howManyNightsWithBestMood;
            Log.d("appi", "Napping " + nappingAvg);
            if (exerciseAvg >= 0.5) {
                ((TextView) findViewById(R.id.text_exercising)).setText("Yay you've been working out!");
            } else {
                ((TextView) findViewById(R.id.text_exercising)).setText("No exercise this time mate.");
            }
            if (nappingAvg >= 0.5) {
                ((TextView) findViewById(R.id.text_napping)).setText("You've been napping! Is that wise?");
            } else {
                ((TextView) findViewById(R.id.text_napping)).setText("No napping, well done you!");
            }
        }
    }
}
