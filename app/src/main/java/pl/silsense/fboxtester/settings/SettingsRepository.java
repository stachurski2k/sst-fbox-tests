package pl.silsense.fboxtester.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SettingsRepository {

    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_SERVER_ADDRESS = "server_address";
    private static final String KEY_SERVER_PORT = "server_port";
    private static final String KEY_SERVER_AUTO_SEND_DATA = "server_auto_send_data";


    private static final String DEFAULT_SERVER_ADDRESS = "172.20.0.226";
    private static final int DEFAULT_SERVER_PORT = 8080;
    private static final boolean DEFAULT_SERVER_AUTO_SEND_DATA = true;

    private final SharedPreferences sharedPreferences;

    @Inject
    public SettingsRepository(@ApplicationContext @NonNull Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getServerAddress() {
        return sharedPreferences.getString(KEY_SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
    }

    public void setServerAddress(String address) {
        sharedPreferences.edit().putString(KEY_SERVER_ADDRESS, address).apply();
        EventBus.getDefault().post(new SettingsChangedEvent());
    }

    public int getServerPort() {
        return sharedPreferences.getInt(KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
    }

    public void setServerPort(int port) {
        sharedPreferences.edit().putInt(KEY_SERVER_PORT, port).apply();
        EventBus.getDefault().post(new SettingsChangedEvent());
    }

    public boolean isAutoSendDataEnabled() {
        return sharedPreferences.getBoolean(KEY_SERVER_AUTO_SEND_DATA, DEFAULT_SERVER_AUTO_SEND_DATA);
    }

    public void setAutoSendDataEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_SERVER_AUTO_SEND_DATA, enabled).apply();
        EventBus.getDefault().post(new SettingsChangedEvent());
    }

    public static class SettingsChangedEvent {}
}
