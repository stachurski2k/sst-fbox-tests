package pl.silsense.fboxtester.session;

import androidx.documentfile.provider.DocumentFile;

class SessionImpl implements Session {

    private final String name;
    private final DocumentFile directory;

    public SessionImpl(String name, DocumentFile file) {
        this.name = name;
        this.directory = file;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DocumentFile getDirectory() {
        return directory;
    }
}
