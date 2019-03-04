package com.example.goodnight;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

public class LogActivity extends AppCompatActivity {
    private TimePicker picker1;
    private TimePicker picker2;
    private int mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        picker1 =(TimePicker) findViewById(R.id.timePicker1);
        picker1.setIs24HourView(true);
        picker2 = (TimePicker) findViewById(R.id.timePicker2);
        picker2.setIs24HourView(true);
    }

    // counting sleeping time
    public void sleepingTime(View view) {
        int hour1; //sleeping time
        int minute1; //sleeping time
        if (Build.VERSION.SDK_INT >= 23) { //API version
            hour1 = picker1.getHour();
            minute1 = picker1.getMinute();
        } else {
            hour1 = picker1.getCurrentHour();
            minute1 = picker1.getCurrentMinute();
        }

        int hour2; //wake-up time
        int minute2; //wake-up time
        if (Build.VERSION.SDK_INT >= 23) { //API version
            hour2 = picker2.getHour();
            minute2 = picker2.getMinute();
        } else {
            hour2 = picker2.getCurrentHour();
            minute2 = picker2.getCurrentMinute();
        }

        double sleepT; //
        double time1=hour1 + minute1/60.0;
        double time2=hour2 + minute2/60.0;

        if (hour1>hour2 || (hour1==hour2 && minute1>minute2)) {
            sleepT=(24.0-time1)+time2;
        } else {
            sleepT=time2-time1;
        }
        DataHandler.getInstance().setSleepLogging(time1, time2, sleepT);
    }

    public void moodButtonPressed(View view) {
        if (view.getId() == R.id.button_veryhappy) {
            mood = 5;
        } else if (view.getId() == R.id.button_happy) {
            mood = 4;
        } else if (view.getId() == R.id.button_meh) {
            mood = 3;
        } else if (view.getId() == R.id.button_sad) {
            mood = 2;
        } else if (view.getId() == R.id.button_verysad) {
            mood = 1;
        }
        DataHandler.getInstance().setMood(mood);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_special:
                if (checked) {
                    //MITÄ KUULUU TEHDÄ EMMÄÄ TIÄ
                } else {
                    // TEE JOTAIN
                    break;
                }
            case R.id.checkBox_naps:
                if (checked) {
                    // TEE JOTAIN
                } else {
                    //TEE JOTAIN
                    break;
                }
            case R.id.checkBox_exercise:
                if (checked) {
                    // TEE JOTAIN
                } else {
                    // TEE JOTAIN
                    break;
                }
        }
    }

}
//Sourcecode for TimePicker: https://www.tutlane.com/tutorial/android/android-timepicker-with-examples