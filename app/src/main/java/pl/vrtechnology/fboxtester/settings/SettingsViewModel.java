package pl.vrtechnology.fboxtester.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class SettingsViewModel extends ViewModel {

    @Inject
    SettingsViewModel(@NonNull SettingsRepository settingsRepository) {
        //
    }
}
