package com.example.sst_fbox_tests;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class StatsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        TextView statsView = findViewById(R.id.statsText);
        HashMap<String, Integer> counts = new HashMap<>();
        counts.put("p", 0);
        counts.put("b", 0);
        counts.put("o", 0);

        int total = 0;

        try {
            String[] files = fileList();
            for (String file : files) {
                if (file.startsWith("choices_20250527_082930") && file.endsWith(".txt")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(file)));
                    String line;
                    while ((line = reader.readLine()) != null) {

                    }
                    reader.close();
                }
            }
        } catch (Exception e) {
            statsView.setText("Błąd: " + e.getMessage());
            return;
        }

        if (total == 0) {
            statsView.setText("Brak danych do wyświetlenia.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String key : counts.keySet()) {
                int count = counts.get(key);
                int percent = (int) Math.round((count * 100.0) / total);
                sb.append(key).append(" = ").append(percent).append("%\n");
            }
            statsView.setText(sb.toString());
        }
    }
}