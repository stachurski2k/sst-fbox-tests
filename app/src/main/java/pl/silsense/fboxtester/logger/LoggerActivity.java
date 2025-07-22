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
import androidx.lifecycle.ViewModelProvider;

import java.time.Instant;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivityLoggerBinding;
import pl.silsense.fboxtester.log.Device;
import pl.silsense.fboxtester.log.LogEntry;
import pl.silsense.fboxtester.log.LogType;
import pl.silsense.fboxtester.log.WallPosition;

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

        viewModel.getSession().observe(this, session -> {
            if(session != null) {
                session.log(new LogEntry(Instant.now(), Device.THROWER_1, LogType.THROWER_CORRECT, new WallPosition(0.2f, 0.123f)));
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
}
