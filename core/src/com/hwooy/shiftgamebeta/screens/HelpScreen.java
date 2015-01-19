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
 * Created by jason on 12/3/14.
 */
public class HelpScreen extends ScreenAdapter {

    public static final int CAM_WIDTH = 480; //Gdx.graphics.getWidth();
    public static final int CAM_HEIGHT = 270; //Gdx.graphics.getHeight();

    ScreenManager screenManager;
    OrthographicCamera cam;
    Rectangle backBounds;
    Vector3 touchPoint;
    God god;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    BitmapFont font;
    BitmapFont headerFont;

    /**
     * constructor for the start screen
     * @param screenManager instance of screenManager being played
     */
    public HelpScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);

        backBounds = new Rectangle(0, 250, 30, 20);

        touchPoint = new Vector3();
        god = God.getInstance();
        shapeRenderer = God.getInstance().shapeRenderer;
        spriteBatch = God.getInstance().spriteBatch;
        headerFont = God.getInstance().headerFont;
        headerFont.setColor(Color.BLUE);
        headerFont.setScale(1.15f);
        font = God.getInstance().font;
        font.setColor(Color.BLUE);
        font.setScale(.4f);
    }
    /**
     * handles user input response
     */
    public void update() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(backBounds.x, backBounds.y, backBounds.width, backBounds.height);
        shapeRenderer.end();

        spriteBatch.begin();
        headerFont.draw(spriteBatch, "ShiftGame!", 40, 260);
        font.drawWrapped(spriteBatch, "Instructions: Fling the player with your finger to traverse levels and complete obstacles!", 240, 250, 220);
        font.drawWrapped(spriteBatch, "Shift colors to pass through terrain of the same color as you", 240, 140, 220);
        font.drawWrapped(spriteBatch, "This block disappears after a second of touching it", 10, 220, 150);
        font.drawWrapped(spriteBatch, "Don't touch this block! It burns you", 10, 100, 150);

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
