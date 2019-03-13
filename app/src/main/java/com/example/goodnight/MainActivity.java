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
/**Class that serves as a main menu to other activities. As it is the first activity to start when app is booted is where logged nights from shared preferences are uploaded back to DataHandler.
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 * @version 1.0
 * @since
 */

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    /**
     *Method to set the layout when activity is first created and loading shared preferences to DataHandlers nights arraylist by executing loadNights().
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState. Otherwise it is null.
     * @see DataHandler#setNights(ArrayList)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNights();
        Log.d("appi", "" + DataHandler.getInstance().getNights());
    }
    /**
     * In the case that the app is paused nights arraylist is saved to shared preferences by executing saveNights().
     * @see MainActivity#saveNights()
     */

    @Override
    public void onPause() {
        super.onPause();
        saveNights();
    }

    // open activities according to which button was pressed
    /**
     * on click method for all MainActivity buttons. The method opens a new activity on click and depending on the button which activity is opened.
     * @param view
     */

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
    /**
     * Method for saving nights arraylist from DataHandler instance into shared preferences by creating an arraylist with night objects and same name by copying it from DataHandler. Is used when app is paused.
     * @see DataHandler#getNights()
     * @see MainActivity#onPause()
     */

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

    /**
     * Method for getting nights arraylist from shared preferences by using setNights()
     * @see DataHandler#setNights(ArrayList)
     * @see MainActivity#onCreate(Bundle)
     */

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


