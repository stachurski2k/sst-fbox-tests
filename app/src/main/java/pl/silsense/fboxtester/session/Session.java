package pl.silsense.fboxtester.session;

import androidx.documentfile.provider.DocumentFile;

public interface Session {
    String getName();

    DocumentFile getDirectory();
}
