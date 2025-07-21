package pl.silsense.fboxtester.logger;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoggerViewModel extends ViewModel {

    @Inject
    public LoggerViewModel() {
        //
    }
}
