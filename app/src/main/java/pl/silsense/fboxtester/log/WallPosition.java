package pl.silsense.fboxtester.log;

import lombok.Getter;

// 0, 0 - left, top
// 1, 0 - right, top
// 0, 1 - left, bottom
// 1, 1 - right bottom
@Getter
public final class WallPosition {

    // 0 .0- 1.0
    private final float x;
    private final float y;

    public WallPosition(float x, float y) {
        if(x < 0.0f || x > 1.0f || y < 0.0f || y > 1.0f) {
            throw new IllegalArgumentException("x and y must be between 0.0 and 1.0");
        }
        this.x = x;
        this.y = y;
    }
}
