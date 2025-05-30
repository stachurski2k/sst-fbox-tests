package com.example.sst_fbox_tests;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(v -> {
            ChoiceLogger.startNewSession(this);
            Intent intent = new Intent(this, MainChoiceActivity.class);
            startActivity(intent);
        });

        Button statsButton = findViewById(R.id.buttonStats);
        statsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
        });
    }
}