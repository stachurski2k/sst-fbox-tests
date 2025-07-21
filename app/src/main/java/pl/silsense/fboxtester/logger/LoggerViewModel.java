package pl.silsense.fboxtester.logger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.silsense.fboxtester.session.Session;
import pl.silsense.fboxtester.session.SessionRepository;

@HiltViewModel
public class LoggerViewModel extends ViewModel {

    private final SessionRepository sessionRepository;
    private final MutableLiveData<Session> session = new MutableLiveData<>();

    @Inject
    public LoggerViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public LiveData<Session> getSession() {
        return session;
    }

    void setSession(DocumentFile file) {
        try {
            Session session;
            if (file.isDirectory()) {
                session = sessionRepository.getLastSession().orElseThrow(() -> new IllegalStateException("No last session found"));
            } else {
                session = sessionRepository.importSessionFromFile(file);
            }
            this.session.setValue(session);
            Log.d("LoggerViewModel", "setSession: Session loaded!");
        } catch (Exception exception) {
            throw new RuntimeException("Failed to import session from file: " + file.getUri(), exception);
        }
    }
}
