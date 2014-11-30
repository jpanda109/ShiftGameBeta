package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by jason on 11/28/14.
 * Settings class for game (duh)
 * holds Preference keys, loads necessary assets
 * Yeah it's a singleton come at me
 */
public class Settings {

    // PREFERENCE KEYS
    public static final String PREFERENCE_NAME = "com.hwooy.shiftgamebeta.preferences";
    public static final String CURRENT_LEVEL = "CURRENT_LEVEL";

    // PATHS
    public static final String LEVEL_PATH = "android/assets/levels/";

    private final Preferences preferences;
    private AssetManager assetManager;

    // eager initialization - no need for lazy as settings is always used
    private static final Settings settingsInstance = new Settings();

    // force singleton
    private Settings() {
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        assetManager = new AssetManager();
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

    // TODO im too sleepy but fix so you don't need to reload everytime
    public TiledMap loadTiledMap(int levelNumber) {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(LEVEL_PATH + "Level" + levelNumber + ".tmx", TiledMap.class);
        assetManager.load(LEVEL_PATH + "Level" + 1 + ".tmx", TiledMap.class);
        assetManager.finishLoading();
        TiledMap tiledMap;
        try {
            tiledMap = assetManager.get(LEVEL_PATH + "Level" + levelNumber + ".tmx");
        } catch (Exception e) {
            tiledMap = assetManager.get(LEVEL_PATH + "Level" + levelNumber + ".tmx");
        }
        return tiledMap;
    }

    // i hate memory leaks
    public void dispose() {
        assetManager.dispose();
    }

}
