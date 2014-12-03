package com.hwooy.shiftgamebeta.viewers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.screens.GameScreen;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/3/14.
 */
public class GameRenderer {

    GameScreen gameScreen;
    OrthographicCamera guiCam;
    ShapeRenderer shapeRenderer;
    Rectangle pauseBounds;

    SpriteBatch spriteBatch;

    public GameRenderer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 48, 32);
        //spriteBatch = Settings.getInstance().spriteBatch;
        spriteBatch = new SpriteBatch();
        shapeRenderer = Settings.getInstance().shapeRenderer;
        pauseBounds = new Rectangle(0, 31, 2, 2);
    }

    public void render() {
        GL20 gl = Gdx.gl20;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        for (ShiftObject shiftObject : gameScreen.gameObjects) {
            shiftObject.render(spriteBatch);
        }
        spriteBatch.end();
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(pauseBounds.x, pauseBounds.y, pauseBounds.width, pauseBounds.height);
        shapeRenderer.end();
        guiCam.update();
    }
}