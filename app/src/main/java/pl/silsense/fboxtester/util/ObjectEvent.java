package pl.silsense.fboxtester.util;

import lombok.Getter;

@Getter
public class ObjectEvent<T> extends ConsumableEvent {

    private final T content;

    public ObjectEvent(T content) {
        this.content = content;
    }

    public ObjectEvent(T content, boolean isConsumed) {
        super(isConsumed);
        this.content = content;
    }

    public static <C> ObjectEvent<C> handled() {
        return new ObjectEvent<>(null);
    }
}
