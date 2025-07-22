package pl.silsense.fboxtester.log;

import androidx.annotation.NonNull;

import java.time.Instant;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class LogEntry {

    private final Instant timestamp;
    private final Device device;
    private final LogType type;
    @Nullable
    private final WallPosition wallPosition;

    public LogEntry(@NonNull Instant timestamp, @NonNull Device device, @NonNull LogType type) {
        this.timestamp = timestamp;
        this.device = device;
        this.type = type;
        this.wallPosition = null;
    }
}
