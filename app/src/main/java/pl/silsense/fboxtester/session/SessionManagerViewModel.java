package pl.silsense.fboxtester.session;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SessionManagerViewModel extends ViewModel {

    @Inject
    public SessionManagerViewModel() {}

    public void openNewSessionDialog() {
        //
    }

    public void openImportSessionDialog() {
        //
    }

    public void continueLastSession() {
        //
    }
}
