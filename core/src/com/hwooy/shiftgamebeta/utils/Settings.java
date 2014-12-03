package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jason on 12/3/14.
 */
public final class Settings {

    public static final String PLAYER_PATH = "player.png";
    public static final String TERRAIN_PATH = "terrain.png";
    public static final String PORTAL_PATH = "portal.png";
    public static final String STAR_PATH = "star.png";

    static final Settings settings = new Settings();
    public final AssetManager assetManager;
    public final SpriteBatch spriteBatch;

    private Settings() {
        assetManager = new AssetManager();
        spriteBatch = new SpriteBatch();
        loadAllAssets();

    }

    public static Settings getInstance() {
        return settings;
    }

    public Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
    }

    private void loadAllAssets() {
        assetManager.load(PLAYER_PATH, Texture.class);
        assetManager.load(TERRAIN_PATH, Texture.class);
        assetManager.load(PORTAL_PATH, Texture.class);
        assetManager.load(STAR_PATH, Texture.class);
        assetManager.finishLoading();
    }
}
