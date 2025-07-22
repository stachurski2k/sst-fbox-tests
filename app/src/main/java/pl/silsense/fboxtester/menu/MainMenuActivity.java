package pl.silsense.fboxtester.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivityMainMenuBinding;
import pl.silsense.fboxtester.session.SessionManagerActivity;
import pl.silsense.fboxtester.settings.SettingsActivity;
import pl.silsense.fboxtester.stats.StatsActivity;

@AndroidEntryPoint
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MainMenuViewModel viewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        ActivityMainMenuBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);
        binding.setViewModel(viewModel);

        viewModel.getOpenSettingsEvent().observe(this, event -> {
            if(event.handle()) {
                startActivity(SettingsActivity.class);
            }
        });

        viewModel.getOpenSessionManagerEvent().observe(this, event -> {
            if(event.handle()) {
                startActivity(SessionManagerActivity.class);
            }
        });

        viewModel.getOpenStatsEvent().observe(this, event -> {
            if(event.handle()) {
                startActivity(StatsActivity.class);
            }
        });
    }

    private void startActivity(@NonNull Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}