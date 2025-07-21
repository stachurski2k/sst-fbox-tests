package pl.silsense.fboxtester.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;

class SessionRepositoryImpl implements SessionRepository {

    private static final String LAST_SESSION_FILE = "last_session.txt";
    private static final String DEFAULT_FOLDER_FILE = "default_folder.txt";
    private static final String TAG = "SessionRepository";
    private final Context context;

    @Inject
    public SessionRepositoryImpl(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Optional<DocumentFile> getDefaultSessionDirectory() {
        File file = new File(context.getFilesDir(), DEFAULT_FOLDER_FILE);
        if (!file.exists()) {
            return Optional.empty();
        }
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String uriString = reader.readLine();
            if (uriString != null) {
                DocumentFile directory = DocumentFile.fromTreeUri(context, android.net.Uri.parse(uriString));
                if (directory != null && directory.exists()) {
                    return Optional.of(directory);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading default session directory", e);
        }
        return Optional.empty();
    }

    @Override
    public void setDefaultSessionDirectory(@NonNull DocumentFile directory) {
        File file = new File(context.getFilesDir(), DEFAULT_FOLDER_FILE);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(directory.getUri().toString().getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing default session directory", e);
        }
    }

    @NonNull
    @Override
    public Optional<Session> createNewSession(@NonNull String sessionName) {
        Optional<DocumentFile> defaultDirectory = getDefaultSessionDirectory();
        if (defaultDirectory.isEmpty() || !defaultDirectory.get().exists()) {
            throw new IllegalStateException("Default session directory not set or does not exist.");
        }

        DocumentFile sessionDir = defaultDirectory.get().createDirectory(sessionName);
        if (sessionDir != null) {
            Session session = new SessionImpl(sessionName, sessionDir);
            setLastSession(session);
            return Optional.of(session);
        }
        return Optional.empty();
    }

    @NonNull
    @Override
    public Optional<Session> getLastSession() {
        File file = new File(context.getFilesDir(), LAST_SESSION_FILE);
        if (!file.exists()) {
            return Optional.empty();
        }
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String uriString = reader.readLine();
            if (uriString != null) {
                DocumentFile directory = DocumentFile.fromTreeUri(context, android.net.Uri.parse(uriString));
                if (directory != null && directory.exists()) {
                    return Optional.of(new SessionImpl(directory.getName(), directory));
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading last session", e);
        }
        return Optional.empty();
    }

    @Override
    public void setLastSession(@NonNull Session session) {
        File file = new File(context.getFilesDir(), LAST_SESSION_FILE);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(session.getDirectory().getUri().toString().getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing last session", e);
        }
    }

    @NonNull
    @Override
    public Session importSessionFromFile(@NonNull DocumentFile file) throws Exception {
        // To be implemented
        return null;
    }
}
