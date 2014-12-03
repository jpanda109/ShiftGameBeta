package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.hwooy.shiftgamebeta.utils.Settings;


/**
 * Created by jason on 12/3/14.
 */
public class StartScreen extends ScreenAdapter {

    public static final int CAM_WIDTH = 480; //Gdx.graphics.getWidth();
    public static final int CAM_HEIGHT = 320; //Gdx.graphics.getHeight();

    public static final float PLAY_BOUNDS_WIDTH_RATIO = .5f;
    public static final float PLAY_BOUNDS_HEIGHT_RATIO = .2f;
    public static final float HELP_BOUNDS_WIDTH_RATIO = .5f;
    public static final float HELP_BOUNDS_HEIGHT_RATIO = .2f;
    public static final float SETTINGS_BOUNDS_WIDTH_RATIO = .2f;
    public static final float SETTINGS_BOUNDS_HEIGHT_RATIO = .2f;

    ScreenManager screenManager;
    OrthographicCamera cam;
    Rectangle playBounds;
    Rectangle helpBounds;
    Rectangle settingsBounds;
    Vector3 touchPoint;
    Settings settings;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    BitmapFont font;

    /**
     * constructor for the start screen
     * @param screenManager instance of screenManager being played
     */
    public StartScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);

        playBounds = new Rectangle(
                (int) (CAM_WIDTH * PLAY_BOUNDS_WIDTH_RATIO / 2),
                (int) (CAM_HEIGHT * .7),
                (int) (CAM_WIDTH * PLAY_BOUNDS_WIDTH_RATIO),
                (int) (CAM_HEIGHT * PLAY_BOUNDS_HEIGHT_RATIO));
        helpBounds = new Rectangle(
                (int) (CAM_WIDTH * HELP_BOUNDS_WIDTH_RATIO / 2),
                (int) (CAM_HEIGHT * .4),
                (int) (CAM_WIDTH * HELP_BOUNDS_WIDTH_RATIO),
                (int) (CAM_HEIGHT * HELP_BOUNDS_HEIGHT_RATIO));
        settingsBounds = new Rectangle(
                0,
                0,
                (int) (CAM_WIDTH * SETTINGS_BOUNDS_WIDTH_RATIO),
                (int) (CAM_HEIGHT * SETTINGS_BOUNDS_HEIGHT_RATIO));

        touchPoint = new Vector3();
        settings = Settings.getInstance();
        shapeRenderer = Settings.getInstance().shapeRenderer;
        spriteBatch = Settings.getInstance().spriteBatch;
        font = Settings.getInstance().font;
        font.setColor(Color.BLUE);
    }
    /**
     * handles user input response
     */
    public void update() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if      (playBounds.contains(touchPoint.x, touchPoint.y)) {
                screenManager.setScreen(ScreenManager.Screens.LEVELS);
            }
            else if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
                screenManager.setScreen(ScreenManager.Screens.HELP);
            }
            else if (settingsBounds.contains(touchPoint.x, touchPoint.y)) {
                // TODO THIS IS DEBUG PLS CHANGE LATER
                screenManager.setScreen(ScreenManager.Screens.START);
            }
        }
    }
    /**
     * draws shit onto screen
     */
    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(new Color(Color.RED));
        shapeRenderer.rect(playBounds.getX(), playBounds.getY(), playBounds.getWidth(), playBounds.getHeight());

        shapeRenderer.setColor(new Color(Color.BLUE));
        shapeRenderer.rect(settingsBounds.getX(), settingsBounds.getY(), settingsBounds.getWidth(), settingsBounds.getHeight());

        shapeRenderer.setColor(new Color(Color.GREEN));
        shapeRenderer.rect(helpBounds.getX(), helpBounds.getY(), helpBounds.getWidth(), helpBounds.getHeight());

        shapeRenderer.end();

        spriteBatch.begin();
        font.draw(spriteBatch, "Play Game", 200, 250);
        font.draw(spriteBatch, "Settings", 5, 40);
        font.draw(spriteBatch, "Help", 200, 180);

        spriteBatch.end();
    }

    /**
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
