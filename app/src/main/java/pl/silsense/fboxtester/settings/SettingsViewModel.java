package pl.silsense.fboxtester.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;
    private final SettingsValidator validator = new SettingsValidator();

    @Getter
    private final MutableLiveData<String> serverAddress = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Integer> serverAddressError = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<String> serverPort = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Integer> serverPortError = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> serverSendDataEnabled = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<Boolean> canSaveSettings = new MutableLiveData<>(false);
    @Getter
    private final MutableLiveData<Void> settingsSaveToastEvent = new MutableLiveData<>();

    @Inject
    SettingsViewModel(@NonNull SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;

        serverAddress.setValue(settingsRepository.getServerAddress());
        serverPort.setValue(String.valueOf(settingsRepository.getServerPort()));
        serverSendDataEnabled.setValue(settingsRepository.isAutoSendDataEnabled());
    }

    public void validateData() {
        boolean isServerAddressValid = validateServerAddress(serverAddress.getValue());
        boolean isServerPortValid = validateServerPort(serverPort.getValue());

        boolean isAddressChanged = !Objects.equals(serverAddress.getValue(), settingsRepository.getServerAddress());
        boolean isPortChanged = !Objects.equals(serverPort.getValue(), String.valueOf(settingsRepository.getServerPort()));
        boolean isServerSendDataChanged = !Objects.equals(serverSendDataEnabled.getValue(), settingsRepository.isAutoSendDataEnabled());

        canSaveSettings.setValue((isServerAddressValid && isServerPortValid) && (isAddressChanged || isPortChanged || isServerSendDataChanged));
    }

    private boolean validateServerAddress(String address) {
        var result = validator.validateServerAddress(address);
        if(result.isValid()) {
            serverAddressError.postValue(null);
            return true;
        }
        serverAddressError.postValue(result.getErrorMessage());
        return false;
    }

    private boolean validateServerPort(String port) {
        var result = validator.validateServerPort(port);
        if(result.isValid()) {
            serverPortError.setValue(null);
            return true;
        }
        serverPortError.setValue(result.getErrorMessage());
        return false;
    }

    public void saveSettings() {
        if (Boolean.TRUE.equals(canSaveSettings.getValue())) {
            settingsRepository.setServerAddress(serverAddress.getValue());
            settingsRepository.setServerPort(Integer.parseInt(Objects.requireNonNull(serverPort.getValue())));
            settingsRepository.setAutoSendDataEnabled(Boolean.TRUE.equals(serverSendDataEnabled.getValue()));

            serverAddress.setValue(settingsRepository.getServerAddress());
            serverPort.setValue(String.valueOf(settingsRepository.getServerPort()));
            serverSendDataEnabled.setValue(settingsRepository.isAutoSendDataEnabled());

            validateData();
            settingsSaveToastEvent.setValue(null);
        }
    }
}
