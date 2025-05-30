package com.example.sst_fbox_tests;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

public class StatsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats  );

        TextView statsTextView = findViewById(R.id.statsText);
        HashMap<String, Integer> counts = new HashMap<>();
        int total = 0;

        File dir = getFilesDir();
        File[] files = dir.listFiles((d, name) -> name.startsWith("choices_"));

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(": ");
                        if (parts.length == 2) {
                            String[] choices = parts[1].split(";");
                            for (String c : choices) {
                                if (c.equals("p") || c.equals("o") || c.equals("b")) {
                                    counts.put(c, counts.getOrDefault(c, 0) + 1);
                                    total++;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (total == 0) {
            statsTextView.setText("Brak danych do wyświetlenia");
        } else {
            StringBuilder stats = new StringBuilder("Statystyki:\n");
            for (String key : counts.keySet()) {
                int count = counts.get(key);
                float percent = (count * 100f) / total;
                stats.append(String.format(Locale.getDefault(), "%s: %.1f%%\n", key, percent));
            }
            statsTextView.setText(stats.toString());
        }
    }
}
