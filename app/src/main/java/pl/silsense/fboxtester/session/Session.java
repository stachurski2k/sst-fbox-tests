package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.io.Serializable;
import java.util.List;

import pl.silsense.fboxtester.log.LogEntry;

public interface Session extends Serializable {

    @NonNull
    String getName();

    @NonNull
    DocumentFile getFile();

    void log(LogEntry logEntry);

    @NonNull
    List<LogEntry> readAll();
}
