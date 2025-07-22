package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.util.Optional;

public interface SessionRepository {

    @NonNull
    Optional<DocumentFile> getDefaultSessionDirectory();

    void setDefaultSessionDirectory(@NonNull DocumentFile directory);

    @NonNull
    Optional<Session> createNewSession();

    @NonNull
    Optional<Session> getLastSession();

    void setLastSession(@NonNull Session session);

    @NonNull
    Session importSessionFromFile(@NonNull DocumentFile file) throws Exception;
}
