package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.util.ConsumableEvent;

@HiltViewModel
public class SessionManagerViewModel extends ViewModel {

    private final SessionRepository sessionRepository;

    @Getter
    private final MutableLiveData<Boolean> lastSessionExist;
    @Getter
    private final MutableLiveData<ConsumableEvent> openNewSessionDialogEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ConsumableEvent> openFolderPickerEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    @Getter
    private final MutableLiveData<ConsumableEvent> openFilePickerEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);

    @Inject
    public SessionManagerViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.lastSessionExist = new MutableLiveData<>(sessionRepository.getLastSession().isPresent());
    }

    public void openNewSessionDialog() {
        //openNewSessionDialogEvent.setValue(new ActionEvent());
        openFolderPickerEvent.setValue(new ConsumableEvent());
    }

    public void openImportSessionDialog() {
        openFilePickerEvent.setValue(new ConsumableEvent());
    }

    public void continueLastSession() {
        if(Boolean.TRUE.equals(lastSessionExist.getValue())) {
            // folder picker?
        }
    }
}
