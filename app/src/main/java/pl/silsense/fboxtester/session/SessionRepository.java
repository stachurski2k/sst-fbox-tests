package pl.silsense.fboxtester.session;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {

    private static final String FILE_NAME = "sessions.txt";
    private static final String TAG = "SessionRepository";
    private final Context context;

    public SessionRepository(Context context) {
        this.context = context;
    }

    public File createSession(String sessionName) {
        if (sessionName == null || sessionName.isEmpty()) {
            throw new IllegalArgumentException("Session name cannot be empty.");
        }

        if (sessionExists(sessionName)) {
            throw new IllegalArgumentException("A session with this name already exists.");
        }

        File downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsDir == null) {
            throw new IllegalStateException("Unable to access the Downloads directory.");
        }

        File sessionFile = new File(downloadsDir, sessionName + ".csv");
        try {
            if (sessionFile.createNewFile()) {
                addSessionToFile(sessionName, sessionFile.getAbsolutePath());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while creating session: " + e.getMessage(), e);
        }

        return sessionFile;
    }

    public void importSession(File sessionFile) {
        if (sessionFile == null || !sessionFile.exists()) {
            throw new IllegalArgumentException("Session file does not exist.");
        }

        String sessionName = sessionFile.getName().replace(".csv", "");

        if (!sessionExists(sessionName)) {
            addSessionToFile(sessionName, sessionFile.getAbsolutePath());
        }
    }

    public List<String> getSessions() {
        List<String> sessionNames = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String sessionName = line.split(",")[0];
                sessionNames.add(sessionName);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while reading sessions: " + e.getMessage(), e);
        }
        return sessionNames;
    }

    public String getSession(String sessionName) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(sessionName)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while fetching session: " + e.getMessage(), e);
        }
        return null;
    }

    private void addSessionToFile(String sessionName, String sessionPath) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND)) {
            String sessionData = sessionName + "," + sessionPath + "\n";
            fos.write(sessionData.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error while adding session to file: " + e.getMessage(), e);
        }
    }

    private boolean sessionExists(String sessionName) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String existingSessionName = line.split(",")[0];
                if (existingSessionName.equals(sessionName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while checking session existence: " + e.getMessage(), e);
        }
        return false;
    }
}
