package pl.silsense.fboxtester.stats;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.session.Session;
import pl.silsense.fboxtester.session.SessionRepository;
import pl.silsense.fboxtester.util.ConsumableEvent;

@HiltViewModel
public class StatsViewModel extends ViewModel {

    private final SessionRepository sessionRepository;

    @Getter
    private final MutableLiveData<Optional<Session>> lastSession = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<ConsumableEvent> openImportDialogEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ConsumableEvent> showImportFragment = new MutableLiveData<>(new ConsumableEvent());
    @Getter
    private final MutableLiveData<ConsumableEvent> showStatsDetailsFragment = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<List<Session>> importedSessions = new MutableLiveData<>();

    @Inject
    public StatsViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.lastSession.setValue(sessionRepository.getLastSession());
    }

    public void openImportSessionDialog() {
        openImportDialogEvent.setValue(new ConsumableEvent());
    }

    public void continueLastSession() {
        var session = lastSession.getValue();
        if(session != null && session.isPresent()) {
            importedSessions.setValue(List.of(session.get()));
            showStatsDetailsFragment.setValue(new ConsumableEvent());
        }
    }

    void onFilesSelected(List<DocumentFile> files) {
        List<Session> sessions = new ArrayList<>();
        for (DocumentFile file : files) {
            try {
                sessions.add(sessionRepository.importSessionFromFile(file));
            } catch (Exception exception) {
                Log.e("StatsViewModel", "onFilesSelected: Session import failed", exception);
            }
        }
        importedSessions.setValue(sessions);
        showStatsDetailsFragment.setValue(new ConsumableEvent());
    }
}
