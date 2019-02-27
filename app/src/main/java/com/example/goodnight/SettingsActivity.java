package com.example.goodnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void buttonPressed(View view) {
        if (view.getId() == R.id.button_saveSettings) {
            EditText editText = (EditText) findViewById(R.id.edit_sleepTimeDef);
            String message = editText.getText().toString();
            Log.d("UNIAPPI", "AIKA ON MUOTOA " + message);
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
