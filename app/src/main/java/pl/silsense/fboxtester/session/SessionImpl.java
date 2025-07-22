package pl.silsense.fboxtester.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.silsense.fboxtester.log.LogEntry;
import pl.silsense.fboxtester.log.LogEntryCsvConverter;

class SessionImpl implements Session {

    private final String name;
    private final DocumentFile file;
    private final Context context;

    public SessionImpl(@NonNull String name, @NonNull DocumentFile file, @NonNull Context context) {
        this.name = name;
        this.file = file;
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public DocumentFile getFile() {
        return file;
    }

    @Override
    public void log(LogEntry logEntry) {
        String csvLog = LogEntryCsvConverter.toCsv(logEntry);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.getContentResolver().openOutputStream(file.getUri())))) {
            writer.write(csvLog);
        } catch (IOException exception) {
            Log.e("Session", "log: ", exception);
        }
    }

    @NonNull
    @Override
    public List<LogEntry> readAll() {
        List<LogEntry> logEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getContentResolver().openInputStream(file.getUri())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry logEntry = LogEntryCsvConverter.fromCsv(line);
                logEntries.add(logEntry);
            }
        } catch (IOException exception) {
            Log.e("Session", "readAll: ", exception);
        }

        return logEntries.isEmpty() ? Collections.emptyList() : logEntries;
    }
}
