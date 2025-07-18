package pl.silsense.fboxtester.session;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.util.Arrays;

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

        viewModel.getOpenFilePickerEvent().observe(this, actionEvent -> {
            if (!actionEvent.isHandled()) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                Uri downloadsUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADownloads");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsUri);
                pickFileLauncher.launch(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO check version
        if(checkSelfPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 234234);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {  // Permission request code
            if (Environment.isExternalStorageManager()) {
                Log.d("TAG", "Permission granted from settings");
                // Proceed with your file picker
            } else {
                Log.d("TAG", "Permission denied");
                Toast.makeText(this, "Permission is required to access files", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 234234) {
            Log.d("TAG", "onRequestPermissionsResult: ODPOWIEDŹ");
        }
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
