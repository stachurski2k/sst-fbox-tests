package pl.silsense.fboxtester.session;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;

public class SessionRepository {

    private static final String LAST_SESSION_FILE = "last_session.txt";
    private static final String DEFAULT_FOLDER_FILE = "default_folder.txt";
    private static final String TAG = "SessionRepository";
    private final Context context;

    @Inject
    public SessionRepository(Context context) {
        this.context = context;
    }

    public Optional<File> getLastSession() {
        File file = new File(context.getFilesDir(), LAST_SESSION_FILE);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Log.i(TAG, "Created new last session file.");
                }
            } catch (IOException e) {
                Log.e(TAG, "Error while creating last session file: " + e.getMessage(), e);
                return Optional.empty();
            }
        }

        try (FileInputStream fis = context.openFileInput(LAST_SESSION_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String lastSessionPath = reader.readLine();
            if (lastSessionPath != null) {
                File sessionFile = new File(lastSessionPath);
                if (sessionFile.exists()) {
                    return Optional.of(sessionFile);
                } else {
                    Log.w(TAG, "Session file no longer exists.");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while reading last session: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void setLastSession(File sessionFile) {
        if (sessionFile == null || !sessionFile.exists()) {
            throw new IllegalArgumentException("Invalid session file.");
        }

        String sessionPath = sessionFile.getAbsolutePath();
        File file = new File(context.getFilesDir(), LAST_SESSION_FILE);

        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    Log.i(TAG, "Created new last session file.");
                } else {
                    Log.e(TAG, "Failed to create last session file.");
                    return;
                }
            }

            try (FileOutputStream fos = context.openFileOutput(LAST_SESSION_FILE, Context.MODE_PRIVATE)) {
                fos.write(sessionPath.getBytes());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while updating last session: " + e.getMessage(), e);
        }
    }

    public Optional<File> getDefaultFolder() {
        File file = new File(context.getFilesDir(), DEFAULT_FOLDER_FILE);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Log.i(TAG, "Created new default folder file.");
                }
            } catch (IOException e) {
                Log.e(TAG, "Error while creating default folder file: " + e.getMessage(), e);
                return Optional.empty();
            }
        }

        try (FileInputStream fis = context.openFileInput(DEFAULT_FOLDER_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String folderPath = reader.readLine();
            if (folderPath != null) {
                File folder = new File(folderPath);
                if (folder.exists() && folder.isDirectory()) {
                    return Optional.of(folder);
                } else {
                    Log.w(TAG, "Default folder no longer exists or is not a directory.");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while reading default folder: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void setDefaultFolder(File folder) {
        if (folder == null || !folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid default folder.");
        }

        String folderPath = folder.getAbsolutePath();
        File file = new File(context.getFilesDir(), DEFAULT_FOLDER_FILE);

        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    Log.i(TAG, "Created new default folder file.");
                } else {
                    Log.e(TAG, "Failed to create default folder file.");
                    return;
                }
            }

            try (FileOutputStream fos = context.openFileOutput(DEFAULT_FOLDER_FILE, Context.MODE_PRIVATE)) {
                fos.write(folderPath.getBytes());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while updating default folder: " + e.getMessage(), e);
        }
    }
}
