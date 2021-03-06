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
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/4/14.
 * TODO CURRENTLY A DEBUG SCREEN, PLS CHANGE
 */
public class WinScreen extends ScreenAdapter {

    public static final int CAM_WIDTH = 480; //Gdx.graphics.getWidth();
    public static final int CAM_HEIGHT = 320; //Gdx.graphics.getHeight();

    ScreenManager screenManager;
    OrthographicCamera cam;
    Rectangle playBounds;
    Rectangle helpBounds;
    Rectangle settingsBounds;
    Vector3 touchPoint;
    God god;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    BitmapFont font;

    /**
     * constructor for the start screen
     * @param screenManager instance of screenManager being played
     */
    public WinScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);

        playBounds = new Rectangle(215, 280, 50, 25);
        helpBounds = new Rectangle(215, 250, 50, 25);
        settingsBounds = new Rectangle(215, 220, 50, 25);

        touchPoint = new Vector3();
        god = God.getInstance();
        shapeRenderer = God.getInstance().shapeRenderer;
        spriteBatch = God.getInstance().spriteBatch;
        font = God.getInstance().font;
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

        God g = God.getInstance();
        spriteBatch.begin();
        spriteBatch.draw(g.getTexture(God.PLAY_PATH), 215, 280);
        spriteBatch.draw(g.getTexture(God.HELP_PATH), 215, 250);
        spriteBatch.draw(g.getTexture(God.SELECT_PATH), 215, 220);

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
