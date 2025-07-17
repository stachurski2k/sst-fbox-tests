package pl.silsense.fboxtester.session;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivitySessionManagerBinding;

@AndroidEntryPoint
public class SessionManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_manager);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.session_manager_title);
        }

        SessionManagerViewModel viewModel = new ViewModelProvider(this).get(SessionManagerViewModel.class);
        ActivitySessionManagerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_session_manager);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
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
