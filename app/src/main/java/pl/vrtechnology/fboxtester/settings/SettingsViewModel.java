package pl.vrtechnology.fboxtester.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class SettingsViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    @Inject
    SettingsViewModel(@NonNull SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }
}
