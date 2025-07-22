package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.util.ConsumableEvent;
import pl.silsense.fboxtester.util.ObjectEvent;

@HiltViewModel
public class SessionManagerViewModel extends ViewModel {

    private final SessionRepository sessionRepository;

    @Getter
    private final MutableLiveData<Boolean> lastSessionExist;
    @Getter
    private final MutableLiveData<ConsumableEvent> openNewSessionDialogEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ConsumableEvent> openDirectoryPickerEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ConsumableEvent> openFilePickerEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ObjectEvent<Session>> startLoggerActivity = new MutableLiveData<>(ObjectEvent.handled());
    @Getter
    private final MutableLiveData<ConsumableEvent> commonErrorToastEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);

    @Inject
    public SessionManagerViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.lastSessionExist = new MutableLiveData<>(sessionRepository.getLastSession().isPresent());
    }

    public void openCreateNewSessionDialog() {
        if(sessionRepository.getDefaultSessionDirectory().isEmpty()) {
            openDirectoryPickerEvent.setValue(new ConsumableEvent());
        } else {
            var session = sessionRepository.createNewSession();
            if(session.isEmpty()) {
                commonErrorToastEvent.setValue(new ConsumableEvent());
                return;
            }
            sessionRepository.setLastSession(session.get());
            lastSessionExist.setValue(sessionRepository.getLastSession().isPresent());
            startLoggerActivity.setValue(new ObjectEvent<>(session.get()));
        }
    }

    void onDirectorySelected(@NonNull DocumentFile directory) {
        sessionRepository.setDefaultSessionDirectory(directory);
    }

    public void openImportSessionDialog() {
        openFilePickerEvent.setValue(new ConsumableEvent());
    }

    void onFileSelected(@NonNull DocumentFile file) {
        try {
            var imported = sessionRepository.importSessionFromFile(file);
            sessionRepository.setLastSession(imported);
            lastSessionExist.setValue(sessionRepository.getLastSession().isPresent());
            startLoggerActivity.setValue(new ObjectEvent<>(imported));
        } catch (Exception exception) {
            commonErrorToastEvent.setValue(new ConsumableEvent());
        }
    }

    public void continueLastSession() {
        sessionRepository.getLastSession().ifPresent(session -> startLoggerActivity.setValue(new ObjectEvent<>(session)));
    }

    public Optional<Session> getLastSession() {
        return sessionRepository.getLastSession();
    }
}
