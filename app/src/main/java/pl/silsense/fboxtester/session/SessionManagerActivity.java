package pl.silsense.fboxtester.session;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivitySessionManagerBinding;

@AndroidEntryPoint
public class SessionManagerActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickFileLauncher;
    private ActivityResultLauncher<String> permissionLauncher;

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

        pickFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            File file = new File(getRealPathFromURI(uri));
                            // viewModel.onFileSelected(file);
                        }
                    }
                }
        );

        viewModel.getOpenFolderPickerEvent().observe(this, actionEvent -> {
            if (!actionEvent.isHandled()) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                Uri downloadsUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADownloads");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsUri);
                pickFileLauncher.launch(intent);
            }
        });

        viewModel.getOpenFilePickerEvent().observe(this, actionEvent -> {
            if (!actionEvent.isHandled()) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("text/csv");
                Uri downloadsUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADownloads");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsUri);
                pickFileLauncher.launch(intent);
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

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {DocumentsContract.Document.COLUMN_DISPLAY_NAME};
        String path = null;
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DISPLAY_NAME);
            cursor.moveToFirst();
            path = cursor.getString(columnIndex);
            cursor.close();
        }
        return path;
    }
}
