package pl.silsense.fboxtester.stats;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.log.Device;
import pl.silsense.fboxtester.log.LogEntry;
import pl.silsense.fboxtester.log.LogType;
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

    private List<LogEntry> getAllLogEntriesFromImportedSessions() {
        List<Session> sessions = importedSessions.getValue();
        if(sessions == null) {
            return new ArrayList<>();
        }
        List<LogEntry> entries = new ArrayList<>();
        for (Session session : sessions) {
            entries.addAll(session.readAll());
        }
        return entries;
    }

    /**
     * @return content for text view in statistics details fragment.
     */
    // TODO implement
    public String getStatisticsContent() {
        List<LogEntry> logEntries = getAllLogEntriesFromImportedSessions();
        if(getAllLogEntriesFromImportedSessions().isEmpty()) {
            return "Brak danych.";
        }

        StringBuilder content = new StringBuilder();

        // walls
        content.append("Ściana 1\n");
        content.append(wallDetails(logEntries, Device.WALL_1));
        content.append("Ściana 2\n");
        content.append(wallDetails(logEntries, Device.WALL_2));
        content.append("Ściana 3\n");
        content.append(wallDetails(logEntries, Device.WALL_3));
        content.append("Ściana 4\n");
        content.append(wallDetails(logEntries, Device.WALL_4));
        content.append("\n");

        // thrower
        content.append("Wyrzutnik 1\n");
        content.append(throwerDetails(logEntries, Device.THROWER_1));
        content.append("Wyrzutnik 2\n");
        content.append(throwerDetails(logEntries, Device.THROWER_2));
        content.append("Wyrzutnik 3\n");
        content.append(throwerDetails(logEntries, Device.THROWER_3));
        content.append("Wyrzutnik 4\n");
        content.append(throwerDetails(logEntries, Device.THROWER_4));
        content.append("Wyrzutnik 5\n");
        content.append(throwerDetails(logEntries, Device.THROWER_5));
        content.append("Wyrzutnik 6\n");
        content.append(throwerDetails(logEntries, Device.THROWER_6));

        return content.toString();
    }

    @SuppressLint("DefaultLocale")
    private String wallDetails(List<LogEntry> entries, Device wall) {
        int sum = 0;
        Map<LogType, Integer> logCount = new HashMap<>();

        for (LogEntry entry : entries) {
            if (entry.getDevice() == wall) {
                sum++;
                logCount.put(entry.getType(), logCount.getOrDefault(entry.getType(), 0) + 1);
            }
        }

        if (sum == 0) {
            return "Brak statystyk.\n";
        }

        StringBuilder content = new StringBuilder();
        content.append(" - Suma: ").append(sum).append("\n");

        float correctPercentage = 100f * logCount.getOrDefault(LogType.WALL_CORRECT, 0) / sum;
        float displacementPercentage = 100f * logCount.getOrDefault(LogType.WALL_DISPLACEMENT, 0) / sum;
        float otherPercentage = 100f * logCount.getOrDefault(LogType.WALL_OTHER, 0) / sum;

        content.append(" - Poprawnie: ").append(String.format("%.2f", correctPercentage)).append("%\n");
        content.append(" - Przesunięcie: ").append(String.format("%.2f", displacementPercentage)).append("%\n");
        content.append(" - Inny błąd: ").append(String.format("%.2f", otherPercentage)).append("%\n");

        return content.append("\n").toString();
    }

    private String throwerDetails(List<LogEntry> entries, Device wall) {
        //
        return "Metoda wymaga implementacji\n";
    }
}
