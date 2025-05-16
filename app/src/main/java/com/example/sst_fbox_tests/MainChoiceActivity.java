package com.example.sst_fbox_tests;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choice);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        addDateLineToFile();

        findViewById(R.id.button1).setOnClickListener(v -> handleChoice("1", PBActivity.class));
        findViewById(R.id.button2).setOnClickListener(v -> handleChoice("2", PBActivity.class));
        findViewById(R.id.button3).setOnClickListener(v -> handleChoice("3", PBActivity.class));
        findViewById(R.id.button4).setOnClickListener(v -> handleChoice("4", PBActivity.class));

        findViewById(R.id.buttonA).setOnClickListener(v -> handleChoice("a", ZeroOneTwoActivity.class));
        findViewById(R.id.buttonB).setOnClickListener(v -> handleChoice("b", ZeroOneTwoActivity.class));
        findViewById(R.id.buttonC).setOnClickListener(v -> handleChoice("c", ZeroOneTwoActivity.class));
        findViewById(R.id.buttonD).setOnClickListener(v -> handleChoice("d", ZeroOneTwoActivity.class));
        findViewById(R.id.buttonE).setOnClickListener(v -> handleChoice("e", ZeroOneTwoActivity.class));
        findViewById(R.id.buttonF).setOnClickListener(v -> handleChoice("f", ZeroOneTwoActivity.class));
    }

    private void handleChoice(String choice, Class<?> nextActivity) {
        saveChoice(choice);
        startActivity(new Intent(this, nextActivity));
    }

    private void addDateLineToFile() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String filename = prefs.getString(MainActivity.CURRENT_FILENAME_KEY, "fallback.txt");

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String dateLine = "\n=== SESSION STARTED: " + timestamp + " ===\n";

        try (FileOutputStream fos = openFileOutput(filename, MODE_APPEND)) {
            fos.write(dateLine.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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