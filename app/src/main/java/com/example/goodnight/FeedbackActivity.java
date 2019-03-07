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
        } else {
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


    }
}
