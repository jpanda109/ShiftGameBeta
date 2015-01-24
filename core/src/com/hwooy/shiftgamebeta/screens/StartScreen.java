package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.hwooy.shiftgamebeta.utils.God;


/**
 * Created by jason on 12/3/14.
 * Start screen (play, settings, help)
 */
public class StartScreen extends ScreenAdapter {

    public static final int CAM_WIDTH = 480; //Gdx.graphics.getWidth();
    public static final int CAM_HEIGHT = 270; //Gdx.graphics.getHeight();

    ScreenManager screenManager;
    OrthographicCamera cam;
    Rectangle playBounds;
    Rectangle helpBounds;
    Rectangle settingsBounds;
    public static Rectangle soundBounds;
    Vector3 touchPoint;
    God god;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    BitmapFont font;
    Texture soundTexture;

    /**
     * constructor for the start screen
     * @param screenManager instance of screenManager being played
     */
    public StartScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);

        playBounds = new Rectangle(215, 220, 50, 25);
        helpBounds = new Rectangle(215, 180, 50, 25);
        settingsBounds = new Rectangle(215, 220, 50, 25);
        soundBounds = new Rectangle(0, 0, 30, 30);

        touchPoint = new Vector3();
        god = God.getInstance();
        shapeRenderer = God.getInstance().shapeRenderer;
        spriteBatch = God.getInstance().spriteBatch;
        font = God.getInstance().font;
        font.setColor(Color.BLUE);
        setSoundTexture();
    }

    private void setSoundTexture() {
        soundTexture = god.isMusicOn() ? god.getTexture(God.SOUND_ON_PATH) : god.getTexture(God.SOUND_OFF_PATH);
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
            } else if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                god.toggleMusic();
                setSoundTexture();
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(soundBounds.x, soundBounds.y, soundBounds.width, soundBounds.height);
        shapeRenderer.end();

        spriteBatch.begin();
        spriteBatch.draw(god.getTexture(God.PLAY_PATH), playBounds.x, playBounds.y);
        spriteBatch.draw(god.getTexture(God.HELP_PATH), helpBounds.x, helpBounds.y);
        spriteBatch.draw(soundTexture, soundBounds.x, soundBounds.y, soundBounds.width, soundBounds.height);
//        spriteBatch.draw(god.getTexture(God.SELECT_PATH), 215, 170);

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
