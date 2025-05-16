package com.example.sst_fbox_tests;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class OneTwoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_two);

        findViewById(R.id.button1).setOnClickListener(v -> saveAndReturn("1"));
        findViewById(R.id.button2).setOnClickListener(v -> saveAndReturn("2"));
    }

    private void saveAndReturn(String choice) {
        saveChoice(choice);
        startActivity(new Intent(this, MainChoiceActivity.class));
    }

    private void saveChoice(String choice) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String filename = prefs.getString(MainActivity.CURRENT_FILENAME_KEY, "fallback.txt");

        try (FileOutputStream fos = openFileOutput(filename, MODE_APPEND)) {
            fos.write((choice + ";").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
