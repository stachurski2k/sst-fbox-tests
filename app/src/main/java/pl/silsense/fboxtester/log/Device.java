package pl.silsense.fboxtester.log;

import lombok.Getter;

@Getter
public enum Device {
    WALL_1(true),
    WALL_2(true),
    WALL_3(true),
    WALL_4(true),
    THROWER_1(false),
    THROWER_2(false),
    THROWER_3(false),
    THROWER_4(false),
    THROWER_5(false),
    THROWER_6(false);

    public final boolean wall;

    Device(boolean wall) {
        this.wall = wall;
    }
}
