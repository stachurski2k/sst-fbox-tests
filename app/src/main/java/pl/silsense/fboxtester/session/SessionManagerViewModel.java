package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.util.ActionEvent;

@HiltViewModel
public class SessionManagerViewModel extends ViewModel {

    private final SessionRepository sessionRepository;

    @Getter
    private final MutableLiveData<Boolean> lastSessionExist;
    @Getter
    private final MutableLiveData<ActionEvent> openNewSessionDialogEvent = new MutableLiveData<>(ActionEvent.HANDLED);
    @Getter
    private final MutableLiveData<ActionEvent> openFilePickerEvent = new MutableLiveData<>(ActionEvent.HANDLED);

    @Inject
    public SessionManagerViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.lastSessionExist = new MutableLiveData<>(sessionRepository.getLastSession().isPresent());
    }

    public void openNewSessionDialog() {
        openNewSessionDialogEvent.setValue(new ActionEvent());
    }

    public void openImportSessionDialog() {
        openFilePickerEvent.setValue(new ActionEvent());
    }

    public void continueLastSession() {
        if(Boolean.TRUE.equals(lastSessionExist.getValue())) {
            // folder picker?
        }
    }
}
