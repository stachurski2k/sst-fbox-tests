package pl.silsense.fboxtester.log;

import androidx.annotation.StringRes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.silsense.fboxtester.R;

@Getter
@AllArgsConstructor
public enum Device {
    WALL_1(true, R.string.logger_wall_1_name),
    WALL_2(true, R.string.logger_wall_2_name),
    WALL_3(true, R.string.logger_wall_3_name),
    WALL_4(true, R.string.logger_wall_4_name),
    THROWER_1(false, R.string.logger_thrower_1_name),
    THROWER_2(false, R.string.logger_thrower_2_name),
    THROWER_3(false, R.string.logger_thrower_3_name),
    THROWER_4(false, R.string.logger_thrower_4_name),
    THROWER_5(false, R.string.logger_thrower_5_name),
    THROWER_6(false, R.string.logger_thrower_6_name);

    public final boolean wall;
    @StringRes
    public final int name;
}
