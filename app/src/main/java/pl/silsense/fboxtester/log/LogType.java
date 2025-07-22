package pl.silsense.fboxtester.log;

import androidx.annotation.StringRes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.silsense.fboxtester.R;

@Getter
@AllArgsConstructor
public enum LogType {
    WALL_CORRECT(R.string.log_wall_correct, true),
    WALL_DISPLACEMENT(R.string.log_wall_displacement, true),
    WALL_OTHER(R.string.log_wall_other_error, true),
    THROWER_CORRECT(R.string.log_thrower_correct, false),
    THROWER_EXCITATION(R.string.log_thrower_excitation, false),
    THROWER_NO_LAUNCH(R.string.log_thrower_no_launch, false),
    THROWER_OTHER(R.string.log_thrower_other, false);

    @StringRes
    private final int name;
    private final boolean wallOption;
}
