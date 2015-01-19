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

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 */
public class LevelsScreen extends ScreenAdapter {

    public static final int CAM_WIDTH = 480; //Gdx.graphics.getWidth();
    public static final int CAM_HEIGHT = 270; //Gdx.graphics.getHeight();
    ScreenManager screenManager;
    OrthographicCamera cam;
    Vector3 touchPoint;
    ShapeRenderer debugRenderer;
    ArrayList<Rectangle> rectangles;
    God god;
    SpriteBatch spriteBatch;
    BitmapFont font;
    /**
     * constructor for the start screen
     * @param screenManager instance of screenManager being played
     */
    public LevelsScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);
        god = God.getInstance();
        rectangles = new ArrayList<Rectangle>();
        spriteBatch = god.spriteBatch;
        font = God.getInstance().font;

        int x = 20;
        int y = 240;

        for (int i = 0; i < God.MAX_LEVEL / 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                rectangles.add(new Rectangle(x, y, 20, 20));
                x += 40;
            }
            y -= 40;
            x = 20;
        }
        for (int i = 0; i < God.MAX_LEVEL % 5; ++i) {
            rectangles.add(new Rectangle(x, y, 20, 20));
            x += 40;
        }
        touchPoint = new Vector3();
        debugRenderer = new ShapeRenderer();
    }
    /**
     * handles user input response
     */
    public void update() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            int levelNumber = 1;
            for (Rectangle rectangle : rectangles) {
                if (rectangle.contains(touchPoint.x, touchPoint.y)) {
                    if (levelNumber <= god.getUnlockedLevels()) {
                        screenManager.setGameScreen(levelNumber);
                        break;
                    }
                }
                ++levelNumber;
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

        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.BLACK);
        spriteBatch.begin();
        int levelNumber = 1;
        for (Rectangle rectangle : rectangles) {
            debugRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            font.draw(spriteBatch, Integer.toString(God.getInstance().getGatheredStars(levelNumber)), rectangle.x, rectangle.y);
            ++levelNumber;
        }
        spriteBatch.end();
        debugRenderer.end();
    }
    /**
     * renders shit on screen
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        update();
        draw();
    }

}
