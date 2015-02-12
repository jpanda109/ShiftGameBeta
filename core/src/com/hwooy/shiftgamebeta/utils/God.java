package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.hwooy.shiftgamebeta.screens.StartScreen;

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

    //Resource paths (textures)
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
    public static final String MUSIC_PATH = "music/Kubbi - Paracet.mp3";
    public static final String SOUND_ON_PATH = "buttons/on_music_button.png";
    public static final String SOUND_OFF_PATH = "buttons/stopped_music_button.png";
    public static final String FONT_PATH = "fonts/GloriaHallelujah.fnt";
    public static final String STAR_TRANSPARENT_PATH = "star/star_transparent.png";

    //Level maps (tmx)
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
    public static final String LEVEL_NONE = "blocks/levels/level_none.png";

    public static final float WORLD_HEIGHT = 40f;
    public static final float WORLD_WIDTH = 60f;

    public static final boolean DEBUG = true;

    public static final int MAX_LEVEL = 15;

    //Resources for use by other classes
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
    public boolean loaded;

    //The width and height of the display surface
    public static float camHeight = Gdx.graphics.getHeight();
    public static float camWidth = Gdx.graphics.getWidth();

    private God() {
        loaded = false;
        assetManager = new AssetManager();

        //Each loader uses an InternalFileHandleResolver that returns a FileHandle pointing at an internal file
        //Sets assetManager with an AssetLoader for the TiledMap class
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        //SpriteBatch takes the Texture, coordinates, and rectangle it is given
        //Binds the texture if it is not so already (the main texture being used)
        //Collects the geometry (that is mapped to the texture)
        //Submits the collected geometry to be drawn (to OpenGL)
        //Begins collecting geometry for another new texture
        spriteBatch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal(FONT_PATH));
        headerFont = new BitmapFont(Gdx.files.internal(FONT_PATH));

        //Gets the preferences of the User (past info, pref like music)
        //A Preference instance is a hash map holding different values. Stored alongside the game.
        //Android: SharedPreferences
        //Desktop: in a Java Preferences file in a ".prefs" directory
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);

        //Used to render shapes (rect, line, etc)
        shapeRenderer = new ShapeRenderer();
        //Simpler way of rendering objects in the world
        debugRenderer = new Box2DDebugRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_PATH));
        music.setLooping(true);
        playMusic(isMusicOn());

        camHeight = Gdx.graphics.getHeight();
        camWidth = Gdx.graphics.getWidth();

        //Creates the World where all objects are going to be in, sets the "gravity" to -10f in y-dir
        world = new World(new Vector2(0, -10f), false);
        loadAllTextures();
    }

    public static God getInstance() {
        return GOD;
    }

    //Used by external classes to retrieve a Texture
    //Texture paths are static and so can be accessed outside the class, reducing error-prone string handling
    public Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    //Ensures all textures are loaded before the start of the game
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
        assetManager.load(STAR_TRANSPARENT_PATH, Texture.class);

        assetManager.load(HELP_PATH, Texture.class);
        assetManager.load(PLAY_PATH, Texture.class);
        assetManager.load(SELECT_PATH, Texture.class);

        assetManager.load(PAUSE_BUTTON_PATH, Texture.class);
        assetManager.load(RESUME_BUTTON_PATH, Texture.class);
        assetManager.load(RESTART_BUTTON_PATH, Texture.class);
        assetManager.load(QUIT_BUTTON_PATH, Texture.class);
        assetManager.load(SOUND_ON_PATH, Texture.class);
        assetManager.load(SOUND_OFF_PATH, Texture.class);

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
        assetManager.load(LEVEL_NONE, Texture.class);

        assetManager.finishLoading();
        loaded = true;
    }

    //Loads TileMap only when requested & returns the TileMap
    public TiledMap getTiledMap(int levelNumber) {
        assetManager.load("levels/" + levelNumber + ".tmx", TiledMap.class);
        assetManager.finishLoading();
        return assetManager.get("levels/" + levelNumber + ".tmx", TiledMap.class);
    }

    //Gets the unlocked levels; if there is no current level property, returns 1
    public int getUnlockedLevels() {
        return preferences.getInteger(CURRENT_LEVEL, 1);
    }

    //Gets the total stars collected in the game; if there is no current total stars property, returns 0
    public int getTotalStars() {
        return preferences.getInteger(TOTAL_STARS, 0);
    }

    //Gets the number of stars gathered at a specific level;
    //The preference is stored as an key offset from STARS_GATHERED_IN_LEVEL
    //Returns 0 if no gathered stars for the given level
    public int getGatheredStars(int levelNumber) {
        return preferences.getInteger(STARS_GATHERED_IN_LEVEL + levelNumber, 0);
    }

    //updates star counts and levels completed
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

    //Disposing of all allocated resources
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
        spriteBatch.begin();
        if (play) {
            music.play();
            if(loaded) {
                spriteBatch.draw(getTexture(God.SOUND_ON_PATH), StartScreen.soundBounds.x, StartScreen.soundBounds.y);
            }
        } else {
            music.stop();
            spriteBatch.draw(getTexture(God.SOUND_OFF_PATH), StartScreen.soundBounds.x, StartScreen.soundBounds.y);
        }
        preferences.putBoolean(MUSIC_ON, play);
        spriteBatch.end();
    }

    public void toggleMusic() {
        preferences.putBoolean(MUSIC_ON, !music.isPlaying());
        playMusic(isMusicOn());
    }

    public boolean isMusicOn() {
        return preferences.getBoolean(MUSIC_ON, true);
    }

}
