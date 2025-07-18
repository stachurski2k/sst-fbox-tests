package pl.silsense.fboxtester.session;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pl.silsense.fboxtester.log.LogEntry;

public class Session {

    private final File sessionFile;
    private final Lock lock = new ReentrantLock();

    public Session(@NonNull File sessionFile) {
        this.sessionFile = sessionFile;
        if (!sessionFile.exists()) {
            throw new IllegalStateException("Session file must exist.");
        }
    }

    public void log(@NonNull LogEntry logEntry) {
        lock.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sessionFile, true))) {
            writer.write(logEntry.toString());
            writer.newLine();
        } catch (IOException exception) {
            Log.e("Session", "readAll: error occurred while logging session file: ", exception);
        } finally {
            lock.unlock();
        }
    }

    public CompletableFuture<List<LogEntry>> readAll() {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            List<LogEntry> logEntries = null;
            try (BufferedReader reader = new BufferedReader(new FileReader(sessionFile))) {
                logEntries = LogEntryCsvConverter.fromCsv(reader);
            } catch (IOException exception) {
                Log.e("Session", "readAll: error occurred while reading session file: ", exception);
            } finally {
                lock.unlock();
            }
            return logEntries;
        });
    }
}
