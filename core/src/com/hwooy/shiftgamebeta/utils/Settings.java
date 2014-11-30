package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by jason on 11/28/14.
 * Settings class for game (duh)
 * holds Preference keys
 */
public class Settings {

    // TODO add asset loading here preferably

    public static final String PREFERENCE_NAME = "com.hwooy.shiftgamebeta.preferences";
    public static final String CURRENT_LEVEL = "CURRENT_LEVEL";

    private static Preferences preferences;
    // eager initialization - no need for lazy as settings is always used
    private static final Settings settingsInstance = new Settings();

    // force singleton
    private Settings() {
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
    }

    public static Settings getInstance() {
        return settingsInstance;
    }

    public int getLevel() {
        return preferences.getInteger(CURRENT_LEVEL, 1);
    }

    public void saveNextLevel(int completedLevelNumber) {
        if (completedLevelNumber <= preferences.getInteger(CURRENT_LEVEL, 1)) {
            preferences.putInteger(CURRENT_LEVEL, preferences.getInteger(CURRENT_LEVEL, 1) + 1);
            preferences.flush();
        }
    }

}
