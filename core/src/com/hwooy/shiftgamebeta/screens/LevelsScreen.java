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

        float x = 20f;
        float y = 200f;

        spriteBatch.begin();
        for (int i = 0; i < God.MAX_LEVEL / 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                rectangles.add(new Rectangle(x, y, 85f, 42.5f));
                x += 90f;
            }
            y -= 80f;
            x = 20f;
        }

        touchPoint = new Vector3();
        debugRenderer = new ShapeRenderer();
        spriteBatch.end();
    }
    /**
     * handles user input response
     */
    public void update() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            int levelNumber = 1;
            for (Rectangle rectangle : rectangles) {
                if (rectangle.contains(touchPoint.x, touchPoint.y) &&
                    levelNumber <= god.getUnlockedLevels())
                {
                    screenManager.setGameScreen(levelNumber);
                    break;
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
            if (God.DEBUG) {
                debugRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            }
            float x = rectangle.x;
            float y = rectangle.y;

            if (levelNumber <= god.getUnlockedLevels()) {
                switch (levelNumber) {
                    case 1:
                        spriteBatch.draw(god.getTexture(God.LEVEL_1_PATH), x, y, 85f, 42.5f);
                        break;
                    case 2:
                        spriteBatch.draw(god.getTexture(God.LEVEL_2_PATH), x, y, 85f, 42.5f);
                        break;
                    case 3:
                        spriteBatch.draw(god.getTexture(God.LEVEL_3_PATH), x, y, 85f, 42.5f);
                        break;
                    case 4:
                        spriteBatch.draw(god.getTexture(God.LEVEL_4_PATH), x, y, 85f, 42.5f);
                        break;
                    case 5:
                        spriteBatch.draw(god.getTexture(God.LEVEL_5_PATH), x, y, 85f, 42.5f);
                        break;
                    case 6:
                        spriteBatch.draw(god.getTexture(God.LEVEL_6_PATH), x, y, 85f, 42.5f);
                        break;
                    case 7:
                        spriteBatch.draw(god.getTexture(God.LEVEL_7_PATH), x, y, 85f, 42.5f);
                        break;
                    case 8:
                        spriteBatch.draw(god.getTexture(God.LEVEL_8_PATH), x, y, 85f, 42.5f);
                        break;
                    case 9:
                        spriteBatch.draw(god.getTexture(God.LEVEL_9_PATH), x, y, 85f, 42.5f);
                        break;
                    case 10:
                        spriteBatch.draw(god.getTexture(God.LEVEL_10_PATH), x, y, 85f, 42.5f);
                        break;
                    case 11:
                        spriteBatch.draw(god.getTexture(God.LEVEL_11_PATH), x, y, 85f, 42.5f);
                        break;
                    case 12:
                        spriteBatch.draw(god.getTexture(God.LEVEL_12_PATH), x, y, 85f, 42.5f);
                        break;
                    case 13:
                        spriteBatch.draw(god.getTexture(God.LEVEL_13_PATH), x, y, 85f, 42.5f);
                        break;
                    case 14:
                        spriteBatch.draw(god.getTexture(God.LEVEL_14_PATH), x, y, 85f, 42.5f);
                        break;
                    case 15:
                        spriteBatch.draw(god.getTexture(God.LEVEL_15_PATH), x, y, 85f, 42.5f);
                        break;
                }
            }
            else {
                spriteBatch.draw(god.getTexture(God.LEVEL_NONE), x, y, 85f, 42.5f);
            }
            if(god.getGatheredStars(levelNumber) > 0) {
                spriteBatch.draw(god.getTexture(God.STAR_PATH), x + 32.5f, y - 21.25f, 16, 16);
            } else {
                spriteBatch.draw(god.getTexture(God.STAR_TRANSPARENT_PATH), x + 32.5f, y - 21.25f, 16, 16);
            }
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
