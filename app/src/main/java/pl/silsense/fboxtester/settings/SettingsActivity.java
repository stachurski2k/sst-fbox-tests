package pl.vrtechnology.fboxtester.settings;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import pl.vrtechnology.fboxtester.R;
import pl.vrtechnology.fboxtester.databinding.ActivitySettingsBinding;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.settings_title);
        }

        SettingsViewModel viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getSettingsSaveToastEvent().observe(this, event -> {
            Toast.makeText(this, R.string.settings_settings_saved_toast, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
