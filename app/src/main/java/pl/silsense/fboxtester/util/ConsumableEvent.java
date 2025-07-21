package pl.silsense.fboxtester.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumableEvent {

    public static final ConsumableEvent HANDLED = new ConsumableEvent(true);

    private boolean handled = false;

    /**
     * @return true if the event was not handled before and is now marked as handled,
     * false if it was already handled.
     */
    public boolean handle() {
        if (handled) {
            return false;
        }
        handled = true;
        return true;
    }
}
