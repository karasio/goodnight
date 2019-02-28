package com.example.goodnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    boolean bedtimeNotif;
    boolean logSleepNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void buttonPressed(View view) {
        if (view.getId() == R.id.button_saveSettings) {
            EditText edit_sleepTimeDef = (EditText) findViewById(R.id.edit_sleepTimeDef);
            String string_sleepTimeDef = edit_sleepTimeDef.getText().toString();
            EditText edit_wakeUpDef = (EditText) findViewById(R.id.edit_timeWakeDef);
            String string_wakeUpDef = edit_wakeUpDef.getText().toString();
            onCheckboxClicked(view);
            EditText edit_bedtimeNotif = (EditText) findViewById(R.id.edit_bedtimeNotifTime);
            String string_bedtimeNotif = edit_bedtimeNotif.getText().toString();
            EditText edit_logSleepNotif = (EditText) findViewById(R.id.edit_LogSleepNotifTime);
            String string_logSleepNotif = edit_logSleepNotif.getText().toString();

            DataHandler.getInstance().setSettings(string_sleepTimeDef, string_wakeUpDef,
                    bedtimeNotif, string_bedtimeNotif, logSleepNotif, string_logSleepNotif);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_bedtimeNotif:
                if (checked) {
                    bedtimeNotif = true;
                } else {
                    bedtimeNotif = false;
                    break;
                }
            case R.id.checkBox_logSleepNotif:
                if (checked) {
                    logSleepNotif = true;
                } else {
                    logSleepNotif = false;
                    break;
                }
        }
    }
}
