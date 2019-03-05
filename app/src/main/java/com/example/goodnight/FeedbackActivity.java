package com.example.goodnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageView imageView = (ImageView) findViewById(R.id.mood);

        Double average = DataHandler.getInstance().getSumma() / DataHandler.getInstance().getYot();
        String ka = Double.toString(average);

        ((TextView) findViewById(R.id.text_hoursSlept)).setText(ka);

        double mood = DataHandler.getInstance().getMoodSum() / DataHandler.getInstance().getYot();

        if (mood >= 4.5) {
            imageView.setImageResource(R.drawable.happyhappyface);
        } else if (mood >= 3.5 && mood < 4.5) {
            imageView.setImageResource(R.drawable.happyface);
        } else if (mood >= 2.5 && mood < 3.5) {
            imageView.setImageResource(R.drawable.nothappynorsadface);
        } else if (mood >= 1.5 && mood < 2.5) {
            imageView.setImageResource(R.drawable.sadface);
        } else
            imageView.setImageResource(R.drawable.sadsadface);
    }
}
