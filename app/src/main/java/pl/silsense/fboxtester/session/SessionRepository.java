package pl.silsense.fboxtester.session;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import javax.inject.Inject;

public class SessionRepository {

    private static final String LAST_SESSION_FILE = "last_session.txt";
    private static final String TAG = "SessionRepository";
    private final Context context;

    @Inject
    public SessionRepository(Context context) {
        this.context = context;
    }

    public File getLastSession() {
        File file = new File(context.getFilesDir(), LAST_SESSION_FILE);
        if (!file.exists()) {
            Log.w(TAG, "Last session file does not exist.");
            return null;
        }

        try (FileInputStream fis = context.openFileInput(LAST_SESSION_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String lastSessionPath = reader.readLine();
            if (lastSessionPath != null) {
                return new File(lastSessionPath);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while reading last session: " + e.getMessage(), e);
        }

        return null;
    }

    public void setLastSession(File sessionFile) {
        if (sessionFile == null || !sessionFile.exists()) {
            throw new IllegalArgumentException("Invalid session file.");
        }

        String sessionPath = sessionFile.getAbsolutePath();

        try (FileOutputStream fos = context.openFileOutput(LAST_SESSION_FILE, Context.MODE_PRIVATE)) {
            fos.write(sessionPath.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error while updating last session: " + e.getMessage(), e);
        }
    }
}
