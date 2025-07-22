package pl.silsense.fboxtester.log;

import androidx.annotation.StringRes;

import lombok.AllArgsConstructor;
import pl.silsense.fboxtester.R;

@AllArgsConstructor
public enum LogType {
    WALL_CORRECT(R.string.log_wall_correct),
    WALL_DISPLACEMENT(R.string.log_wall_displacement),
    WALL_OTHER(R.string.log_wall_other_error),
    THROWER_CORRECT(R.string.log_thrower_correct),
    THROWER_EXCITATION(R.string.log_thrower_excitation),
    THROWER_NO_LAUNCH(R.string.log_thrower_no_launch),
    THROWER_OTHER(R.string.log_thrower_other);

    @StringRes
    private final int name;
}
