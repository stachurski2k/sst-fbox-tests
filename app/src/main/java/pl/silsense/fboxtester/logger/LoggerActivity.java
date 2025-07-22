package pl.silsense.fboxtester.logger;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivityLoggerBinding;

@AndroidEntryPoint
public class LoggerActivity extends AppCompatActivity {

    public static final String EXTRA_SESSION_FILE_URI = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);

        Uri sessionUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            sessionUri = getIntent().getParcelableExtra(EXTRA_SESSION_FILE_URI, Uri.class);
        } else {
            sessionUri = getIntent().getParcelableExtra(EXTRA_SESSION_FILE_URI);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.logger_title);
        }

        LoggerViewModel viewModel = new ViewModelProvider(this).get(LoggerViewModel.class);
        viewModel.setSession(DocumentFile.fromSingleUri(this, Objects.requireNonNull(sessionUri)));
        ActivityLoggerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_logger);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getShowDeviceMenuFragment().observe(this, event -> {
            if(event.handle()) {
                showFragment(new DevicesFragment());
            }
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

    private void showFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_logger_fragment, fragment);
        transaction.commit();
    }
}
