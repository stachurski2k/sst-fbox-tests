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
import java.time.LocalDateTime;
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
    public Optional<Session> createNewSession() {
        Optional<DocumentFile> defaultDirectory = getDefaultSessionDirectory();
        if (defaultDirectory.isEmpty() || !defaultDirectory.get().exists()) {
            throw new IllegalStateException("Default session directory not set or does not exist.");
        }

        String sessionName = generateSessionName();
        DocumentFile sessionFile = defaultDirectory.get().createFile("text/csv", sessionName + ".csv");
        if (sessionFile != null) {
            Session session = new SessionImpl(sessionName, sessionFile, context);
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
            Log.e(TAG, "getLastSession: LAST_SESSION_FILE NOT EXISTS");
            return Optional.empty();
        }
        try (FileInputStream fis = new FileInputStream(file); BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String uriString = reader.readLine();
            if (uriString != null) {
                try {
                    DocumentFile sessionFile = DocumentFile.fromSingleUri(context, android.net.Uri.parse(uriString));
                    if (sessionFile != null && sessionFile.exists()) {
                        return Optional.of(new SessionImpl(sessionFile.getName(), sessionFile, context));
                    }
                } catch (SecurityException e) {
                    Log.w(TAG, "Lost permission for last session URI. Deleting last session file.", e);
                    file.delete();
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
            fos.write(session.getFile().getUri().toString().getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing last session", e);
        }
    }

    @NonNull
    @Override
    public Session importSessionFromFile(@NonNull DocumentFile file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File does not exist or is not a file.");
        }
        String fileName = file.getName();
        if (fileName == null) {
            throw new IllegalArgumentException("File name is null.");
        }
        String sessionName = fileName.endsWith(".csv") ? fileName.substring(0, fileName.length() - 4) : fileName;
        Session session = new SessionImpl(sessionName, file, context);
        setLastSession(session);
        return session;
    }

    private String generateSessionName() {
        LocalDateTime time = LocalDateTime.now();
        return time.getDayOfMonth() + "-" + time.getMonthValue() + "_" + time.getHour() + "-" + time.getMinute() + "-" + time.getSecond() + "_" + time.getYear();
    }
}
