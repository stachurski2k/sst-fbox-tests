package com.example.sst_fbox_tests;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class OneTwoActivity extends AppCompatActivity {
    private String currentChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_two);

        currentChoices = getIntent().getStringExtra("currentChoices");

        View touchView = findViewById(R.id.touchView);
        touchView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                finishWithCoordinates(x, y);
                return true;
            }
            return false;
        });

        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    }

    private void finishWithCoordinates(int x, int y) {
        String coord = "(" + x + "," + y + ");";
        currentChoices += coord;
        Intent intent = new Intent(this, MainChoiceActivity.class);
        intent.putExtra("finalChoices", currentChoices);
        startActivity(intent);
        finish();
    }
}