package com.example.goodnight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    SharedPreferences prefPut;
    SharedPreferences Prefs;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences prefGet;
    //private static final String PREF = "TestPref"; /*ÄLÄ POISTA T: KATRI*/
    Context context = MainActivity.this; /*ÄLÄ POISTA T: KATRI*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Night> savedNights = new ArrayList<Night>();
        SharedPreferences mPrefs = context.getSharedPreferences("sleepData", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            savedNights = new ArrayList<Night>();
        } else {
            Type type = new TypeToken<ArrayList<Night>>() {
            }.getType();
            savedNights = gson.fromJson(json, type);
        }

        //Retrieve the values
//        Gson gson = new Gson();
//        String jsonText = Prefs.getString("sleepData", null);
//        String[] text = gson.fromJson(jsonText, String[].class);  //EDIT: gso to gson

        DataHandler.getInstance().setNights(loadNights(context)); /*ÄLÄ POISTA T: KATRI*/
    }

    @Override
    public void onPause() {
        super.onPause();

        ArrayList<Night> nights = DataHandler.getInstance().getNights();

        SharedPreferences mPrefs = context.getSharedPreferences("sleepData", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nights);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();

        //saveNights(context, DataHandler.getInstance().getNights()); /*ÄLÄ POISTA T: KATRI*/
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

    // ARRAYLISTIN TALLENNUS MUISTIIN /*ÄLÄ POISTA T: KATRI*/
    public static void saveNights(Context context, ArrayList<Night> nights) {
        SharedPreferences mPrefs = context.getSharedPreferences("sleepData", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nights);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    // ARRAYLISTIN HAKU MUISTISTA /*ÄLÄ POISTA T: KATRI*/
    public static ArrayList<Night> loadNights(Context context) {
        ArrayList<Night> savedNights = new ArrayList<Night>();
        SharedPreferences mPrefs = context.getSharedPreferences("sleepData", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            savedNights = new ArrayList<Night>();
        } else {
            Type type = new TypeToken<ArrayList<Night>>() {
            }.getType();
            savedNights = gson.fromJson(json, type);
        }

        return savedNights;
    }
}
