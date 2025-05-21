package com.example.sst_fbox_tests;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainChoiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choice);

        setupButton(R.id.button1, "1", true);
        setupButton(R.id.button2, "2", true);
        setupButton(R.id.button3, "3", true);
        setupButton(R.id.button4, "4", true);
        setupButton(R.id.buttonA, "a", false);
        setupButton(R.id.buttonB, "b", false);
        setupButton(R.id.buttonC, "c", false);
        setupButton(R.id.buttonD, "d", false);
        setupButton(R.id.buttonE, "e", false);
        setupButton(R.id.buttonF, "f", false);
    }

    private void setupButton(int id, String label, boolean isNumber) {
        Button button = findViewById(id);
        button.setOnClickListener(v -> {
            Intent intent;
            if (isNumber) {
                intent = new Intent(this, PBActivity.class);
            } else {
                intent = new Intent(this, ZeroOneTwoActivity.class);
            }
            // przekazujemy tylko jeden wybór dalej, nie zapisujemy go jeszcze
            intent.putExtra("currentChoices", label + ";");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String savedChoices = getIntent().getStringExtra("finalChoices");
        if (savedChoices != null && !savedChoices.isEmpty()) {
            ChoiceLogger.appendChoices(this, savedChoices);
            getIntent().removeExtra("finalChoices");
        }
    }
}