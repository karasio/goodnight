package com.example.goodnight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonPressed(View view) {
        if (view.getId() == R.id.button_settings) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (view.getId() == R.id.button_feedback) {
            intent = new Intent(this, FeedbackActivity.class);
        } else if (view.getId() == R.id.button_log) {
            intent = new Intent(this, LogActivity.class);
        }
        startActivity(intent);
    }
}
