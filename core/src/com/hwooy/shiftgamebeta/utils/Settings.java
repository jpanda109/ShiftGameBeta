package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by jason on 12/3/14.
 */
public final class Settings {

    // PREFERENCE KEYS
    public static final String PREFERENCE_NAME = "com.hwooy.shiftgamebeta.preferences";
    public static final String CURRENT_LEVEL = "CURRENT_LEVEL";

    public static final String PLAYER_PATH = "player/player_front.png";
    public static final String TERRAIN_PATH = "blocks/terrain.png";
    public static final String PORTAL_PATH = "portal/portal.png";
    public static final String STAR_PATH = "star/star.png";

    static final Settings settings = new Settings();
    public final AssetManager assetManager;
    public final SpriteBatch spriteBatch;
    public final BitmapFont font;
    public final Preferences preferences;
    public final ShapeRenderer shapeRenderer;

    private Settings() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        shapeRenderer = new ShapeRenderer();
        loadAllTextures();
    }

    public static Settings getInstance() {
        return settings;
    }

    public Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    private void loadAllTextures() {
        assetManager.load(PLAYER_PATH, Texture.class);
        assetManager.load(TERRAIN_PATH, Texture.class);
        assetManager.load(PORTAL_PATH, Texture.class);
        assetManager.load(STAR_PATH, Texture.class);
        assetManager.finishLoading();
    }

    public TiledMap getTiledMap(int levelNumber) {
        assetManager.load("levels/Level" + levelNumber + ".tmx", TiledMap.class);
        assetManager.finishLoading();
        return assetManager.get("levels/Level" + levelNumber + ".tmx", TiledMap.class);
    }

    public int getLevel() {
        return preferences.getInteger(CURRENT_LEVEL, 1);
    }

    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }

}
