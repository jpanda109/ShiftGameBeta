package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jason on 12/3/14.
 */
public final class Settings {

    // PREFERENCE KEYS
    public static final String PREFERENCE_NAME = "com.hwooy.shiftgamebeta.preferences";
    public static final String CURRENT_LEVEL = "CURRENT_LEVEL";

    public static final String PLAYER_PATH = "player.png";
    public static final String TERRAIN_PATH = "terrain.png";
    public static final String PORTAL_PATH = "portal.png";
    public static final String STAR_PATH = "star.png";

    static final Settings settings = new Settings();
    public final AssetManager assetManager;
    public final SpriteBatch spriteBatch;
    public final BitmapFont font;
    public final Preferences preferences;
    public final ShapeRenderer shapeRenderer;

    private Settings() {
        assetManager = new AssetManager();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        shapeRenderer = new ShapeRenderer();
        loadAllAssets();
    }

    public static Settings getInstance() {
        return settings;
    }

    public Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    private void loadAllAssets() {
        assetManager.load(PLAYER_PATH, Texture.class);
        assetManager.load(TERRAIN_PATH, Texture.class);
        assetManager.load(PORTAL_PATH, Texture.class);
        assetManager.load(STAR_PATH, Texture.class);
        assetManager.finishLoading();
    }

    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }

    public int getLevel() {
        return preferences.getInteger(CURRENT_LEVEL, 1);
    }
}
