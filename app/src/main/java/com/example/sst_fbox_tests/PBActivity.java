package com.example.sst_fbox_tests;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class PBActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb);

        findViewById(R.id.buttonP).setOnClickListener(v -> saveAndGoBack("p"));
        findViewById(R.id.buttonB).setOnClickListener(v -> saveAndGoBack("b"));
    }

    private void saveAndGoBack(String choice) {
        saveChoice(choice);
        startActivity(new android.content.Intent(this, OneTwoActivity.class));
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