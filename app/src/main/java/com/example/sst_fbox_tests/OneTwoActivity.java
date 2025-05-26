package com.example.sst_fbox_tests;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class OneTwoActivity extends AppCompatActivity {
    private String currentChoices;
    private View targetZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_two);

        currentChoices = getIntent().getStringExtra("currentChoices");
        targetZone = findViewById(R.id.targetZone);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> finish());

        FrameLayout rootLayout = findViewById(R.id.rootLayout);
        rootLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int[] location = new int[2];
                targetZone.getLocationOnScreen(location);
                int left = location[0];
                int top = location[1];
                int right = left + targetZone.getWidth();
                int bottom = top + targetZone.getHeight();

                float x = event.getRawX();
                float y = event.getRawY();

                if (x >= left && x <= right && y >= top && y <= bottom) {
                    float normX = x / v.getWidth();
                    float normY = y / v.getHeight();
                    currentChoices += String.format(Locale.getDefault(), "%.3f,%.3f;", normX, normY);
                    Intent intent = new Intent(this, MainChoiceActivity.class);
                    intent.putExtra("finalChoices", currentChoices);
                    startActivity(intent);
                    finish();
                }
            }
            return true;
        });
    }
}