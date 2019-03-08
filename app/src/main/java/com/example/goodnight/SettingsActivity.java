package com.example.goodnight;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private TimePicker picker3;
    private TimePicker picker4;
    private boolean cb_sleepTimeNotif;
    private boolean cb_logSleepNotif;
    private double time1, time2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        picker3 = (TimePicker) findViewById(R.id.timePicker3);
        picker3.setIs24HourView(true);
        picker4 = (TimePicker) findViewById(R.id.timePicker4);
        picker4.setIs24HourView(true);
    }
    // SIIRSIN TIMEPICKERIT TÄNNE, JOTTA NE SAA TRIGGATTUA TOHON SAVE-BUTTONIIN,
    // SAMA MINKÄ TEIN LOGACTIVITYSSÄ, ET SAADAAN KAIKKI KAMA MUUTTUJIIN JA MUISTIIN YHDESSÄ METODISSA
    public void saveButtonPressed(View view) {

        int hour3 = picker3.getHour();
        int minute3 = picker3.getMinute();

        int hour4 = picker4.getHour();
        int minute4 = picker4.getMinute();

        time1 = hour3 + minute3/60.0;
        time2 = hour4 + minute4/60.0;


        if (view.getId() == R.id.button_saveSettings) {
            DataHandler.getInstance().setSettings(cb_sleepTimeNotif, time1, cb_logSleepNotif, time2);
            Log.d("kake", "sleepnotif " + cb_sleepTimeNotif + " logNotif "+ cb_logSleepNotif);
            finish();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox_bedtimeNotif:
                if (checked) {
                    cb_sleepTimeNotif = true;
                } else {
                    cb_sleepTimeNotif = false;
                }
                break;
            case R.id.checkBox_logSleepNotif:
                if (checked) {
                    cb_logSleepNotif = true;
                } else {
                    cb_logSleepNotif = false;
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

    public void resetButtonPressed(View view) {
        DataHandler.getInstance().eraseNights();
        saveNights();
        Log.d("appi", "Data erased");
        finish();
    }
}


