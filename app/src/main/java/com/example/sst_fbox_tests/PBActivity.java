package com.example.sst_fbox_tests;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class PBActivity extends AppCompatActivity {
    private String currentChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb);

        currentChoices = getIntent().getStringExtra("currentChoices");


        Button buttonP = findViewById(R.id.buttonP);
        Button buttonB = findViewById(R.id.buttonB);
        Button buttonO = findViewById(R.id.buttonO);
        Button buttonBack = findViewById(R.id.buttonBack);


        buttonP.setOnClickListener(v -> {
            // p -> przejdź do ekranu wybierania miejsc
            currentChoices += "p;";
            Intent intent = new Intent(this, OneTwoActivity.class);
            intent.putExtra("currentChoices", currentChoices);
            startActivity(intent);
            finish();
        });

        buttonB.setOnClickListener(v -> {
            // b -> wróć do głównego wyboru
            currentChoices += "b;";
            Intent intent = new Intent(this, MainChoiceActivity.class);
            intent.putExtra("finalChoices", currentChoices);
            startActivity(intent);
            finish();
        });

        buttonO.setOnClickListener(v -> {
            // o -> wróć do głównego wyboru
            currentChoices += "o;";
            Intent intent = new Intent(this, MainChoiceActivity.class);
            intent.putExtra("finalChoices", currentChoices);
            startActivity(intent);
            finish();
        });

        buttonBack.setOnClickListener(v -> finish());
    }
}