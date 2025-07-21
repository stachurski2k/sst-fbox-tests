package pl.silsense.fboxtester.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.silsense.fboxtester.util.ConsumableEvent;

@HiltViewModel
public class MainMenuViewModel extends ViewModel {

    private final MutableLiveData<ConsumableEvent> openSessionManagerEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);
    private final MutableLiveData<ConsumableEvent> openSettingsEvent = new MutableLiveData<>(ConsumableEvent.HANDLED);

    @Inject
    public MainMenuViewModel() {}

    public LiveData<ConsumableEvent> getOpenSessionManagerEvent() {
        return openSessionManagerEvent;
    }

    public LiveData<ConsumableEvent> getOpenSettingsEvent() {
        return openSettingsEvent;
    }

    public void openSessionManager() {
        openSessionManagerEvent.setValue(new ConsumableEvent());
    }

    public void openSettings() {
        openSettingsEvent.setValue(new ConsumableEvent());
    }
}
