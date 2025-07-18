package pl.silsense.fboxtester.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.time.Instant;

import lombok.Getter;
import pl.silsense.fboxtester.R;

@Getter
public final class WallLogEntry extends LogEntry {

    private final Type type;
    @Nullable
    private final WallPosition wallPosition;

    public WallLogEntry(@NonNull Instant timestamp, @NonNull Device device, @NonNull Type type, @Nullable WallPosition wallPosition) {
        super(timestamp, device);
        this.type = type;
        this.wallPosition = wallPosition;
    }

    public enum Type {
        CORRECT(R.string.log_wall_correct),
        DISPLACEMENT(R.string.log_wall_displacement),
        OTHER(R.string.log_wall_other_error);

        @StringRes
        public final int name;

        Type(int name) {
            this.name = name;
        }
    }
}
