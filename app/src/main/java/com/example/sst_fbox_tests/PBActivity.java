package com.example.sst_fbox_tests;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PBActivity extends AppCompatActivity {
    private String currentChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb);

        currentChoices = getIntent().getStringExtra("currentChoices");

        findViewById(R.id.buttonP).setOnClickListener(v -> proceed("p"));
        findViewById(R.id.buttonB).setOnClickListener(v -> proceed("b"));
        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    }

    private void proceed(String choice) {
        currentChoices += choice + ";";
        Intent intent = new Intent(this, OneTwoActivity.class);
        intent.putExtra("currentChoices", currentChoices);
        startActivity(intent);
    }
}