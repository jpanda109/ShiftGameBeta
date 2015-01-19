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

    public static final String PLAYER_COLOR_1_PATH = "blocks/player/player_blue.png";
    public static final String PLAYER_COLOR_2_PATH = "blocks/player/player_green.png";
    public static final String TERRAIN_BOTH = "blocks/Terrain/terrain_purple.png";
    public static final String TERRAIN_COLOR_1 = "blocks/Terrain/terrain_green.png";
    public static final String TERRAIN_COLOR_2 = "blocks/Terrain/terrain_blue.png";
    public static final String CRUMBLING_PATH = "blocks/crumbling_block.png";
    public static final String LAVA_PATH = "blocks/lava.png";
    public static final String PORTAL_PATH = "portal/portal.png";
    public static final String STAR_PATH = "star/star.png";
    public static final String HELP_PATH = "buttons/button_help.png";
    public static final String PLAY_PATH = "buttons/button_play.png";
    public static final String SELECT_PATH = "buttons/button_select_level.png";
    public static final String PAUSE_BUTTON_PATH = "buttons/Pause_Button.png";
    public static final String RESUME_BUTTON_PATH = "buttons/Play_Button.png";
    public static final String RESTART_BUTTON_PATH = "buttons/Restart_Button.png";
    public static final String QUIT_BUTTON_PATH = "buttons/Power_Button.png";
    public static final String MUSIC_PATH = "music/Scythuz_Cybernetic Sheep.ogg";
    public static final String FONT_PATH = "fonts/GloriaHallelujah.fnt";

    public static final String LEVEL_1_PATH = "blocks/levels/level_1.png";
    public static final String LEVEL_2_PATH = "blocks/levels/level_2.png";
    public static final String LEVEL_3_PATH = "blocks/levels/level_3.png";
    public static final String LEVEL_4_PATH = "blocks/levels/level_4.png";
    public static final String LEVEL_5_PATH = "blocks/levels/level_5.png";
    public static final String LEVEL_6_PATH = "blocks/levels/level_6.png";
    public static final String LEVEL_7_PATH = "blocks/levels/level_7.png";
    public static final String LEVEL_8_PATH = "blocks/levels/level_8.png";
    public static final String LEVEL_9_PATH = "blocks/levels/level_9.png";
    public static final String LEVEL_10_PATH = "blocks/levels/level_10.png";
    public static final String LEVEL_11_PATH = "blocks/levels/level_11.png";
    public static final String LEVEL_12_PATH = "blocks/levels/level_12.png";
    public static final String LEVEL_13_PATH = "blocks/levels/level_13.png";
    public static final String LEVEL_14_PATH = "blocks/levels/level_14.png";
    public static final String LEVEL_15_PATH = "blocks/levels/level_15.png";

    public static final float WORLD_HEIGHT = 40f;
    public static final float WORLD_WIDTH = 60f;

    public static final boolean DEBUG = true;

    public static final int MAX_LEVEL = 15;

    static final God GOD = new God();
    public final AssetManager assetManager;
    public final SpriteBatch spriteBatch;
    public final BitmapFont font;
    public final BitmapFont headerFont;
    public final Preferences preferences;
    public final ShapeRenderer shapeRenderer;
    public final Box2DDebugRenderer debugRenderer;
    public final World world;
    public final Music music;

    public static float camHeight = Gdx.graphics.getHeight();
    public static float camWidth = Gdx.graphics.getWidth();

    private God() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(FONT_PATH));
        headerFont = new BitmapFont(Gdx.files.internal(FONT_PATH));
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -10f), false);
        music = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_PATH));
        loadAllTextures();
        music.setLooping(true);
        // music was pipssing me off so i turned it off, turn it back on later
        playMusic(isMusicOn());
        camHeight = Gdx.graphics.getHeight();
        camWidth = Gdx.graphics.getWidth();
    }

    public static God getInstance() {
        return GOD;
    }

    public Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    private void loadAllTextures() {
        assetManager.load(PLAYER_COLOR_1_PATH, Texture.class);
        assetManager.load(PLAYER_COLOR_2_PATH, Texture.class);

        assetManager.load(TERRAIN_BOTH, Texture.class);
        assetManager.load(TERRAIN_COLOR_1, Texture.class);
        assetManager.load(TERRAIN_COLOR_2, Texture.class);

        assetManager.load(LAVA_PATH, Texture.class);
        assetManager.load(CRUMBLING_PATH, Texture.class);
        assetManager.load(PORTAL_PATH, Texture.class);
        assetManager.load(STAR_PATH, Texture.class);

        assetManager.load(HELP_PATH, Texture.class);
        assetManager.load(PLAY_PATH, Texture.class);
        assetManager.load(SELECT_PATH, Texture.class);

        assetManager.load(PAUSE_BUTTON_PATH, Texture.class);
        assetManager.load(RESUME_BUTTON_PATH, Texture.class);
        assetManager.load(RESTART_BUTTON_PATH, Texture.class);
        assetManager.load(QUIT_BUTTON_PATH, Texture.class);

        assetManager.load(LEVEL_1_PATH, Texture.class);
        assetManager.load(LEVEL_2_PATH, Texture.class);
        assetManager.load(LEVEL_3_PATH, Texture.class);
        assetManager.load(LEVEL_4_PATH, Texture.class);
        assetManager.load(LEVEL_5_PATH, Texture.class);
        assetManager.load(LEVEL_6_PATH, Texture.class);
        assetManager.load(LEVEL_7_PATH, Texture.class);
        assetManager.load(LEVEL_8_PATH, Texture.class);
        assetManager.load(LEVEL_9_PATH, Texture.class);
        assetManager.load(LEVEL_10_PATH, Texture.class);
        assetManager.load(LEVEL_11_PATH, Texture.class);
        assetManager.load(LEVEL_12_PATH, Texture.class);
        assetManager.load(LEVEL_13_PATH, Texture.class);
        assetManager.load(LEVEL_14_PATH, Texture.class);
        assetManager.load(LEVEL_15_PATH, Texture.class);

        assetManager.finishLoading();
    }

    public TiledMap getTiledMap(int levelNumber) {
        assetManager.load("levels/" + levelNumber + ".tmx", TiledMap.class);
        assetManager.finishLoading();
        return assetManager.get("levels/" + levelNumber + ".tmx", TiledMap.class);
    }

    public TiledMap getTutorialMap(int levelNumber) {
        assetManager.load("levels/Tutorials/Tutorial_Level_" + levelNumber + ".tmx", TiledMap.class);
        assetManager.finishLoading();

        return assetManager.get("levels/Tutorials/Tutorial_Level_" + levelNumber + ".tmx", TiledMap.class);
    }

    public int getUnlockedLevels() {
        return preferences.getInteger(CURRENT_LEVEL, 1);
    }

    public int getTotalStars() {
        return preferences.getInteger(TOTAL_STARS, 0);
    }

    public int getGatheredStars(int levelNumber) {
        return preferences.getInteger(STARS_GATHERED_IN_LEVEL + levelNumber, 0);
    }

    public void saveProgress(int levelNumber, int starsGathered) {
        int prevGathered = preferences.getInteger(STARS_GATHERED_IN_LEVEL + levelNumber, 0);
        if (starsGathered > prevGathered) {
            preferences.putInteger(STARS_GATHERED_IN_LEVEL + levelNumber, starsGathered);
            preferences.putInteger(TOTAL_STARS, getTotalStars() + starsGathered - prevGathered); // add offset to total stars
        }
        if (levelNumber > getUnlockedLevels() && levelNumber < MAX_LEVEL) {
            preferences.putInteger(CURRENT_LEVEL, ++levelNumber);
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
