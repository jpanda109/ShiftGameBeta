package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    // global variables
    public static final int CURRENT_MAX_LEVEL = 3;

    // PATHS
    public static final String GENERAL_PATH = "android/assets/";
    public static final String LEVELS_PATH = "android/assets/levels/";
    public static final String PLAYER_PATH = "android/assets/player.png";

    private final Preferences preferences;
    private AssetManager assetManager;

    private SpriteBatch spriteBatch;

    private boolean debug;

    // eager initialization - no need for lazy as settings is always used
    private static final Settings settingsInstance = new Settings();

    // force singleton
    private Settings() {
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        // always load level 1 as default
        assetManager.load(LEVELS_PATH + "Level" + 1 + ".tmx", TiledMap.class);
        assetManager.finishLoading();

        spriteBatch = new SpriteBatch();

        debug = false;
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

    // TODO im too sleepy but fix so you don't need to reload every time
    public TiledMap loadTiledMap(int levelNumber) {
        TiledMap tiledMap;
        try {
            tiledMap = assetManager.get(LEVELS_PATH + "Level" + levelNumber + ".tmx");
        } catch (Exception e) {
            try {
                assetManager.load(LEVELS_PATH + "Level" + levelNumber + ".tmx", TiledMap.class);
                assetManager.finishLoading();
                tiledMap = assetManager.get(LEVELS_PATH + "Level" + levelNumber + ".tmx");
            } catch (Exception e2) {
                tiledMap = assetManager.get(LEVELS_PATH + "Level" + CURRENT_MAX_LEVEL + ".tmx");
            }
        }
        return tiledMap;
    }

    public Texture loadPlayerTexture() {
        assetManager.load(PLAYER_PATH, Texture.class);
        assetManager.finishLoading();
        return assetManager.get(PLAYER_PATH);
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    // i hate memory leaks
    public void dispose() {
        assetManager.dispose();
    }

    public boolean isDebug() {
        return debug;
    }

}
