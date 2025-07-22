package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.util.Collections;
import java.util.List;

import pl.silsense.fboxtester.log.LogEntry;

class SessionImpl implements Session {

    private final String name;
    private final DocumentFile file;

    public SessionImpl(String name, DocumentFile file) {
        this.name = name;
        this.file = file;
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
        //
    }

    @NonNull
    @Override
    public List<LogEntry> readAll() {
        return Collections.emptyList();
    }
}
