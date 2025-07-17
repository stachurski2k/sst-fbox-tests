package pl.silsense.fboxtester.settings;

import androidx.annotation.Nullable;

import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.util.ValidationResult;

import java.util.regex.Pattern;

class SettingsValidator {

    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");

    ValidationResult validateServerAddress(@Nullable String serverAddress) {
        if (serverAddress == null) {
            return ValidationResult.invalid(R.string.settings_server_address_valitation_error);
        }

        if (!IP_ADDRESS_PATTERN.matcher(serverAddress).matches()) {
            return ValidationResult.invalid(R.string.settings_server_address_valitation_error);
        }

        return ValidationResult.valid();
    }

    ValidationResult validateServerPort(@Nullable String serverPort) {
        if (serverPort == null) {
            return ValidationResult.invalid(R.string.settings_server_port_valitation_error);
        }

        try {
            int port = Integer.parseInt(serverPort);
            if (port < 1 || port > 65535) {
                return ValidationResult.invalid(R.string.settings_server_port_valitation_error);
            }
        } catch (NumberFormatException e) {
            return ValidationResult.invalid(R.string.settings_server_port_valitation_error);
        }

        return ValidationResult.valid();
    }
}
