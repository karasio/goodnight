package com.example.goodnight;

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

        if (DataHandler.getInstance().getYot() != 0) {
            double average = (double) DataHandler.getInstance().getSumma() / DataHandler.getInstance().getYot();
            String aver = String.format("%.2f", average);
            ((TextView) findViewById(R.id.text_hoursSlept)).setText(aver);
        } else ((TextView) findViewById(R.id.text_hoursSlept)).setText("0.0");

        double moodavg=3.0;
        if (DataHandler.getInstance().getYot() != 0) {
            moodavg = (double) DataHandler.getInstance().getMoodSum() / DataHandler.getInstance().getYot();
        }


        if (moodavg >= 4.5) {
            imageView.setImageResource(R.drawable.happyhappyface);
        } else if (moodavg >= 3.5 && moodavg < 4.5) {
            imageView.setImageResource(R.drawable.happyface);
        } else if (moodavg >= 2.5 && moodavg < 3.5) {
            imageView.setImageResource(R.drawable.nothappynorsadface);
        } else if (moodavg >= 1.5 && moodavg < 2.5) {
            imageView.setImageResource(R.drawable.sadface);
        } else
            imageView.setImageResource(R.drawable.sadsadface);
    }
}
