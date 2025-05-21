package com.example.sst_fbox_tests;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OneTwoActivity extends AppCompatActivity {
    private String currentChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_two);

        currentChoices = getIntent().getStringExtra("currentChoices");

        findViewById(R.id.button1).setOnClickListener(v -> finishChoices("1"));
        findViewById(R.id.button2).setOnClickListener(v -> finishChoices("2"));
        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    }

    private void finishChoices(String choice) {
        currentChoices += choice + ";";
        Intent intent = new Intent(this, MainChoiceActivity.class);
        intent.putExtra("finalChoices", currentChoices);
        startActivity(intent);
        finish();
    }
}