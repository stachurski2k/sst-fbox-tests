package pl.silsense.fboxtester.stats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.ActivityStatsBinding;

@AndroidEntryPoint
public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.stats_title);
        }

        StatsViewModel viewModel = new ViewModelProvider(this).get(StatsViewModel.class);
        ActivityStatsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_stats);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        var pickFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        List<DocumentFile> files = new ArrayList<>();
                        Intent data = result.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri uri = data.getClipData().getItemAt(i).getUri();
                                files.add(DocumentFile.fromSingleUri(this, uri));
                            }
                        } else if (data.getData() != null) {
                            Uri uri = data.getData();
                            files.add(DocumentFile.fromSingleUri(this, uri));
                        }
                        viewModel.onFilesSelected(files);
                    }
                }
        );

        viewModel.getOpenImportDialogEvent().observe(this, event -> {
            if (event.handle()) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/csv"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                pickFileLauncher.launch(intent);
            }
        });

        viewModel.getShowImportFragment().observe(this, event -> {
            if(event.handle()) {
                showFragment(new StatsImportFragment());
            }
        });

        viewModel.getShowStatsDetailsFragment().observe(this, event -> {
            if(event.handle()) {
                showFragment(new StatsDetailsFragment());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(fragment, fragment.getTag())
                .addToBackStack(null)
                .commit();
    }
}
