package pl.silsense.fboxtester.stats;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.silsense.fboxtester.session.SessionRepository;

@HiltViewModel
public class StatsViewModel extends ViewModel {

    private final SessionRepository sessionRepository;

    @Inject
    public StatsViewModel(@NonNull SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}
