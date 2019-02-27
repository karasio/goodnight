package com.example.goodnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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
