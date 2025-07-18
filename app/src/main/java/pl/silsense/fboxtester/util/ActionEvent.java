package pl.silsense.fboxtester.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionEvent {
    public static final ActionEvent HANDLED = new ActionEvent(true);
    private boolean handled = false;
}
