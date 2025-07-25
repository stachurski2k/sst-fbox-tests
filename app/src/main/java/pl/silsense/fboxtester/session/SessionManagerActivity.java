package pl.silsense.fboxtester.session;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivitySessionManagerBinding;
import pl.silsense.fboxtester.logger.LoggerActivity;

@AndroidEntryPoint
public class SessionManagerActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickFolderLauncher;
    private ActivityResultLauncher<Intent> pickFileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        pickFolderLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            DocumentFile folder = DocumentFile.fromTreeUri(this, uri);
                            if(folder != null && folder.isDirectory()) {
                                viewModel.onDirectorySelected(folder);
                            } else {
                                Toast.makeText(this, R.string.session_manager_directory_selection_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        pickFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            DocumentFile file = DocumentFile.fromSingleUri(this, uri);
                            if(file != null && file.isFile()) {
                                viewModel.onFileSelected(file);
                            } else {
                                Toast.makeText(this, R.string.session_manager_directory_selection_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        viewModel.getOpenDirectoryPickerEvent().observe(this, actionEvent -> {
            if (actionEvent.handle()) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                pickFolderLauncher.launch(intent);
            }
        });

        viewModel.getOpenFilePickerEvent().observe(this, actionEvent -> {
            if (actionEvent.handle()) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/csv"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                pickFileLauncher.launch(intent);
            }
        });

        viewModel.getCommonErrorToastEvent().observe(this, event -> {
            if (event.handle()) {
                Toast.makeText(this, R.string.session_manager_common_error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getStartLoggerActivity().observe(this, event -> {
            if (event.handle()) {
                Session session = event.getContent();
                Intent intent = new Intent(this, LoggerActivity.class);
                intent.putExtra(LoggerActivity.EXTRA_SESSION_FILE_URI, session.getFile().getUri());
                startActivity(intent);
            }
        });

        viewModel.getLastSessionExist().observe(this, exists -> {
            viewModel.getLastSession().ifPresent(session -> binding.textViewSessionManagerLastSessionDescription.setText(getResources().getString(R.string.session_manager_last_session_name, session.getName())));
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
