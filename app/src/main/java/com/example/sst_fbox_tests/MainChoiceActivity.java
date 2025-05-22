package com.example.sst_fbox_tests;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainChoiceActivity extends AppCompatActivity {
    private StringBuilder currentChoices = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choice);

        setupButton(R.id.button1, "1");
        setupButton(R.id.button2, "2");
        setupButton(R.id.button3, "3");
        setupButton(R.id.button4, "4");
        setupButton(R.id.buttonA, "a");
        setupButton(R.id.buttonB, "b");
        setupButton(R.id.buttonC, "c");
        setupButton(R.id.buttonD, "d");
        setupButton(R.id.buttonE, "e");
        setupButton(R.id.buttonF, "f");
    }

    private void setupButton(int id, String label) {
        Button button = findViewById(id);
        button.setOnClickListener(v -> {
            currentChoices.append(label).append(";");
            if (label.matches("[1-4]")) {
                Intent intent = new Intent(this, PBActivity.class);
                intent.putExtra("currentChoices", currentChoices.toString());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ZeroOneTwoActivity.class);
                intent.putExtra("currentChoices", currentChoices.toString());
                startActivity(intent);
            }
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
