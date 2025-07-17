package pl.silsense.fboxtester.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainMenuViewModel extends ViewModel {

    private final MutableLiveData<Void> openSessionManagerEvent = new MutableLiveData<>();
    private final MutableLiveData<Void> openSettingsEvent = new MutableLiveData<>();

    @Inject
    public MainMenuViewModel() {}

    public LiveData<Void> getOpenSessionManagerEvent() {
        return openSessionManagerEvent;
    }

    public LiveData<Void> getOpenSettingsEvent() {
        return openSettingsEvent;
    }

    public void openSessionManager() {
        openSessionManagerEvent.setValue(null);
    }

    public void openSettings() {
        openSettingsEvent.setValue(null);
    }
}
