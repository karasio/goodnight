package com.example.goodnight;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.gson.Gson;
import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {
    private TimePicker picker1;
    private TimePicker picker2;
    private int mood;
    double sleepT;
    double time1;
    double time2;
    boolean cb_special = false;
    boolean cb_napping = false;
    boolean cb_exercise = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        picker1 =(TimePicker) findViewById(R.id.timePicker1);
        picker1.setIs24HourView(true);
        picker2 = (TimePicker) findViewById(R.id.timePicker2);
        picker2.setIs24HourView(true);
    }

    // GETTING VALUES WHEN SAVE BUTTON IS PRESSED
    public void saveButtonPressed(View view) {
        TextView tv = (TextView)findViewById(R.id.warningTime);
        // getting time values from time pickers
        int hour1 = picker1.getHour();
        int minute1= picker1.getMinute();

        int hour2 = picker2.getHour();
        int minute2 = picker2.getMinute();

        time1 = hour1 + minute1/60.0;
        time2 = hour2 + minute2/60.0;

        if (hour1>hour2 || (hour1==hour2 && minute1>minute2)) {
            sleepT=(24.0-time1)+time2;
        } else {
            sleepT=time2-time1;
        }

        //Debug printing
        Log.d("logactivity","time1 " + time1);
        Log.d("logactivity", "time2 " + time2);
        Log.d("logactivity", "timeSlept " + sleepT);
        Log.d("logactivity", "mood  " + mood);
        Log.d("logactivity", "cb special " + cb_special);
        Log.d("logactivity", "cb_napping " + cb_napping);
        Log.d("logactivity", "cb exer " + cb_exercise);

        if (view.getId() == R.id.button_saveLog) {
            if (time1 == time2 && mood == 0) {
                tv.setText("Pick the time and the mood!");
            } else if (time1 == time2) {
                tv.setText("Pick the time!");
            } else if (mood == 0) {
                tv.setText("Pick the mood!");
            } else {
                DataHandler.getInstance().setSleepLogging(time1, time2, sleepT, mood, cb_special, cb_napping, cb_exercise);
                DataHandler.getInstance().storeData();
                saveNights();
                finish();
            }
        }
    }

    // mood button listener
    public void moodButtonPressed(View view) {
        RadioGroup rGroup = findViewById(R.id.mood_radiogroup);
        RadioButton checkedRadioButton = rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        if (checkedRadioButton == findViewById(R.id.button_veryhappy)) {
            mood = 5;
        } else if (checkedRadioButton == findViewById(R.id.button_happy)) {
            mood = 4;
        } else if (checkedRadioButton == findViewById(R.id.button_meh)) {
            mood = 3;
        } else if (checkedRadioButton == findViewById(R.id.button_sad)) {
            mood = 2;
        } else if (checkedRadioButton == findViewById(R.id.button_verysad)) {
            mood = 1;
        }
    }

    //checkbox listener
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_special:
                if (checked) {
                    cb_special = true;
                } else {
                    cb_special = false;
                }
                break;
            case R.id.checkBox_naps:
                if (checked) {
                    cb_napping = true;
                } else {
                    cb_napping = false;
                }
                break;
            case R.id.checkBox_exercise:
                if (checked) {
                    cb_exercise = true;
                } else {
                    cb_exercise = false;
                }
                break;
        }
    }

    // ARRAYLISTIN TALLENNUS MUISTIIN /*ÄLÄ POISTA T: KATRI*/
    public void saveNights() {
        ArrayList<Night> nights = DataHandler.getInstance().getNights();
        SharedPreferences mPrefs = getSharedPreferences("sleepData", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nights);
        prefsEditor.putString("myJson", json);
        prefsEditor.apply();
        Log.d("appi", "data saved");
    }

}
//Sourcecode for TimePicker: https://www.tutlane.com/tutorial/android/android-timepicker-with-examples