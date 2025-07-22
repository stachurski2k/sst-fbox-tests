package pl.silsense.fboxtester.logger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.log.Device;
import pl.silsense.fboxtester.log.LogType;
import pl.silsense.fboxtester.session.Session;
import pl.silsense.fboxtester.session.SessionRepository;
import pl.silsense.fboxtester.util.ConsumableEvent;

@HiltViewModel
public class LoggerViewModel extends ViewModel {

    private final SessionRepository sessionRepository;
    @Getter
    private final MutableLiveData<Session> session = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<Device> selectedDevice = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<LogType> selectedType = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<ConsumableEvent> showDeviceMenuFragment = new MutableLiveData<>(new ConsumableEvent());
    @Getter
    private final MutableLiveData<ConsumableEvent> showLogTypeFragment = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ConsumableEvent> showWallPositionFragment = new MutableLiveData<>(ConsumableEvent.HANDLED);

    @Inject
    public LoggerViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    void setSession(DocumentFile file) {
        try {
            Session session = sessionRepository.importSessionFromFile(file);
            this.session.setValue(session);
            Log.d("LoggerViewModel", "setSession: Session loaded!");
        } catch (Exception exception) {
            throw new RuntimeException("Failed to import session from file: " + file.getUri(), exception);
        }
    }

    public void selectDevice(@NonNull Device device) {
        this.selectedDevice.setValue(device);
        showLogTypeFragment.setValue(new ConsumableEvent());
    }

    public void selectLogType(@NonNull LogType type) {
        this.selectedType.setValue(type);
        // TODO if type...
    }
}
