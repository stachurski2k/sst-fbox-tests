package pl.silsense.fboxtester.log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.time.Instant;

import lombok.Getter;
import pl.silsense.fboxtester.R;

@Getter
public final class ThrowerLogEntry extends LogEntry {

    private final Type type;

    public ThrowerLogEntry(@NonNull Instant timestamp, @NonNull Device device, @NonNull Type type) {
        super(timestamp, device);
        this.type = type;
    }

    public enum Type {
        CORRECT(R.string.log_thrower_correct),
        EXCITATION(R.string.log_thrower_excitation),
        NO_LAUNCH(R.string.log_thrower_no_launch),
        OTHER(R.string.log_thrower_other);

        @StringRes public final int name;

        Type(int name) {
            this.name = name;
        }
    }
}
