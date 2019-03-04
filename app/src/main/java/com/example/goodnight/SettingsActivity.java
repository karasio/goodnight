package com.example.goodnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

public class SettingsActivity extends AppCompatActivity {
    private TimePicker picker3;
    private TimePicker picker4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        picker3 = (TimePicker) findViewById(R.id.timePicker3);
        picker3.setIs24HourView(true);
        picker4 = (TimePicker) findViewById(R.id.timePicker4);
        picker4.setIs24HourView(true);
    }

    public void defaultSleepingTime () {
        int hour3 = picker3.getHour();
        int minute3 = picker3.getMinute();

        int hour4 = picker4.getHour();
        int minute4 = picker4.getMinute();
    }

    public void buttonPressed(View view) {
        if (view.getId() == R.id.button_saveSettings) {


            //DataHandler.getInstance().setSettings();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_bedtimeNotif:
                if (checked) {
                    //MITÄ KUULUU TEHDÄ EMMÄÄ TIÄ
                } else {
                    // TEE JOTAIN
                    break;
                }
            case R.id.checkBox_logSleepNotif:
                if (checked) {
                    // TEE JOTAIN
                } else {
                    //TEE JOTAIN
                    break;
                }
        }
    }
}
