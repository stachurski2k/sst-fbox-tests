package pl.silsense.fboxtester.session;

import androidx.documentfile.provider.DocumentFile;

import java.io.Serializable;

public interface Session extends Serializable {

    String getName();

    DocumentFile getFile();
}
