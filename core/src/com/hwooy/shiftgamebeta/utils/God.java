package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by jason on 12/3/14.
 * Singleton class to manage resources for disposable objects
 */
public final class God {

    // PREFERENCE KEYS
    public static final String PREFERENCE_NAME = "com.hwooy.shiftgamebeta.preferences";
    public static final String CURRENT_LEVEL = "CURRENT_LEVEL";
    public static final String STARS_GATHERED_IN_LEVEL = "STARS_GATHERED_IN_LEVEL"; // + levellNumber;
    public static final String TOTAL_STARS = "TOTAL_STARS";
    public static final String MUSIC_ON = "MUSIC_ON";

    public static final String PLAYER_HELL_PATH = "player/hell_player_front.png";
    public static final String PLAYER_HEAVEN_PATH = "player/heaven_player_front.png";
    public static final String TERRAIN_PATH = "blocks/TerrainBOTH.png";
    public static final String TERRAIN_ONE_PATH = "blocks/TerrainONE.png";
    public static final String TERRAIN_TWO_PATH = "blocks/TerrainTWO.png";
    public static final String CRUMBLING_PATH = "blocks/CrumblingBlock.png";
    public static final String LAVA_PATH = "blocks/LavaBlock.png";
    public static final String PORTAL_PATH = "portal/portal.png";
    public static final String STAR_PATH = "star/star.png";
    public static final String HELP_PATH = "buttons/button_help.png";
    public static final String PLAY_PATH = "buttons/button_play.png";
    public static final String SELECT_PATH = "buttons/button_select_level.png";

    public static final boolean DEBUG = true;

    public static final int MAX_LEVEL = 6;

    static final God GOD = new God();
    public final AssetManager assetManager;
    public final SpriteBatch spriteBatch;
    public final BitmapFont font;
    public final Preferences preferences;
    public final ShapeRenderer shapeRenderer;
    public final Box2DDebugRenderer debugRenderer;
    public final World world;
    public final Music music;

    private God() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -10f), false);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Scythuz_Cybernetic Sheep.ogg"));
        loadAllTextures();
        music.setLooping(true);
        playMusic(isMusicOn());
    }

    public static God getInstance() {
        return GOD;
    }

    public Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    private void loadAllTextures() {
        assetManager.load(PLAYER_HELL_PATH, Texture.class);
        assetManager.load(PLAYER_HEAVEN_PATH, Texture.class);
        assetManager.load(TERRAIN_PATH, Texture.class);
        assetManager.load(TERRAIN_ONE_PATH, Texture.class);
        assetManager.load(TERRAIN_TWO_PATH, Texture.class);
        assetManager.load(LAVA_PATH, Texture.class);
        assetManager.load(CRUMBLING_PATH, Texture.class);
        assetManager.load(PORTAL_PATH, Texture.class);
        assetManager.load(STAR_PATH, Texture.class);
        assetManager.load(HELP_PATH, Texture.class);
        assetManager.load(PLAY_PATH, Texture.class);
        assetManager.load(SELECT_PATH, Texture.class);
        assetManager.finishLoading();
    }

    public TiledMap getTiledMap(int levelNumber) {
        assetManager.load("levels/Level" + levelNumber + "_NEW.tmx", TiledMap.class);
        assetManager.finishLoading();
        return assetManager.get("levels/Level" + levelNumber + "_NEW.tmx", TiledMap.class);
    }

    public TiledMap getTutorialMap(int levelNumber) {
        assetManager.load("levels/Tutorials/Tutorial_Level_" + levelNumber + ".tmx", TiledMap.class);
        assetManager.finishLoading();
        return assetManager.get("levels/Tutorials/Tutorial_Level_" + levelNumber + ".tmx", TiledMap.class);
    }

    public int getLevel() {
        return preferences.getInteger(CURRENT_LEVEL, 1);
    }

    public int getTotalStars() {
        return preferences.getInteger(TOTAL_STARS, 0);
    }

    public int getGatheredStars(int levelNumber) {
        return preferences.getInteger(STARS_GATHERED_IN_LEVEL + levelNumber, 0);
    }

    public void updateStarsGathered(int levelNumber, int starsGathered) {
        int prevGathered = preferences.getInteger(STARS_GATHERED_IN_LEVEL + levelNumber, 0);
        System.out.println("Stars gathered in level :" + starsGathered);
        if (starsGathered > prevGathered) {
            System.out.println("new stars value written to prefs");
            preferences.putInteger(STARS_GATHERED_IN_LEVEL + levelNumber, starsGathered);
            preferences.putInteger(TOTAL_STARS, getTotalStars() + starsGathered - prevGathered); // add offset to total stars
        }
    }

    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        debugRenderer.dispose();
        world.dispose();
        music.dispose();
    }

    public void playMusic(boolean play) {
        if (play) {
            music.play();
        } else {
            music.stop();
        }
        preferences.putBoolean(MUSIC_ON, play);
    }

    public void toggleMusic() {
        preferences.putBoolean(MUSIC_ON, !music.isPlaying());
        playMusic(isMusicOn());
    }

    public boolean isMusicOn() {
        return preferences.getBoolean(MUSIC_ON, true);
    }

}
