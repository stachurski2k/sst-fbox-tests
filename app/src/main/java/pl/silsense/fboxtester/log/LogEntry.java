package pl.silsense.fboxtester.log;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

import lombok.Getter;
import lombok.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ThrowerLogEntry.class, name = "W"),
        @JsonSubTypes.Type(value = WallLogEntry.class, name = "T")
})
@Getter
public abstract class LogEntry {

    protected final Instant timestamp;
    protected final Device device;

    protected LogEntry(@NonNull Instant timestamp, @NonNull Device device) {
        this.timestamp = timestamp;
        this.device = device;
    }
}
