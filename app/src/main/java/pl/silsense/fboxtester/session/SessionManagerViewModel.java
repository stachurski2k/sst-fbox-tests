package pl.silsense.fboxtester.session;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import pl.silsense.fboxtester.util.ActionEvent;

@HiltViewModel
public class SessionManagerViewModel extends ViewModel {

    @Getter
    private final MutableLiveData<ActionEvent> openFilePickerEvent = new MutableLiveData<>(ActionEvent.HANDLED);

    @Inject
    public SessionManagerViewModel() {}

    public void openNewSessionDialog() {
        //
    }

    public void openImportSessionDialog() {
        openFilePickerEvent.setValue(new ActionEvent());
    }

    public void continueLastSession() {
        //
    }
}
