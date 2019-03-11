package com.example.goodnight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNights();
        Log.d("appi", "" + DataHandler.getInstance().getNights());
    }

    @Override
    public void onPause() {
        super.onPause();
        saveNights();
    }

    // open activities according to which button was pressed
    public void buttonPressed(View view) {
        if (view.getId() == R.id.button_settings) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (view.getId() == R.id.button_feedback) {
            intent = new Intent(this, FeedbackActivity.class);
        } else if (view.getId() == R.id.button_log) {
            intent = new Intent(this, LogActivity.class);
        } else if (view.getId() == R.id.button_tips) {
            intent = new Intent(this, TipsActivity.class);
        }
        startActivity(intent);
    }

    // Saving data to memory
    private void saveNights() {
        ArrayList<Night> nights = DataHandler.getInstance().getNights();        // hae arraylist singletonilta
        SharedPreferences mPrefs = getSharedPreferences("sleepData", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nights);
        prefsEditor.putString("myJson", json);
        prefsEditor.apply();
        Log.d("appi", "data saved");
    }

    // Loading data from memory
    private void loadNights() {
        ArrayList<Night> savedNights = new ArrayList<Night>();
        SharedPreferences mPrefs = getSharedPreferences("sleepData", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        Type type = new TypeToken<ArrayList<Night>>() {
        }.getType();
        if (gson.fromJson(json, type) == null) {
            DataHandler.getInstance().setNights(savedNights);
            Log.d("appi", "No data saved");
        } else {
            savedNights = gson.fromJson(json, type);
            DataHandler.getInstance().setNights(savedNights);
            Log.d("appi", "data loaded");
        }
    }
}


