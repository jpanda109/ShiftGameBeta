package com.hwooy.shiftgamebeta.viewers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.screens.GameScreen;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class GameRenderer {

    GameScreen gameScreen;
    public OrthographicCamera guiCam;
    ShapeRenderer shapeRenderer;

    SpriteBatch spriteBatch;

    Box2DDebugRenderer debugRenderer;

    public Rectangle pauseBounds;
    public Rectangle resumeBounds;
    public Rectangle restartBounds;
    public Rectangle quitBounds;

    public GameRenderer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        guiCam = new OrthographicCamera(60, 40);
        guiCam.position.set(29.5f, 19.5f, 0f);
        //guiCam.setToOrtho(false, 60, 40);
        //spriteBatch = Settings.getInstance().spriteBatch;
        spriteBatch = God.getInstance().spriteBatch;
        shapeRenderer = God.getInstance().shapeRenderer;
        debugRenderer =  God.getInstance().debugRenderer;

        this.pauseBounds = gameScreen.pauseBounds;
        resumeBounds = gameScreen.resumeBounds;
        restartBounds = gameScreen.restartBounds;
        quitBounds = gameScreen.quitBounds;
    }

    private void drawRunning() {
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(pauseBounds.x, pauseBounds.y, pauseBounds.width, pauseBounds.height);
        shapeRenderer.end();
    }

    private void drawPaused() {
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(resumeBounds.x, resumeBounds.y, resumeBounds.width, resumeBounds.height);
        shapeRenderer.rect(restartBounds.x, restartBounds.y, restartBounds.width, restartBounds.height);
        shapeRenderer.rect(quitBounds.x, quitBounds.y, quitBounds.width, quitBounds.height);
        shapeRenderer.end();
    }

    public void render() {
        GL20 gl = Gdx.gl20;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(gameScreen.world, guiCam.combined);

        spriteBatch.begin();
        for (ShiftObject shiftObject : gameScreen.gameObjects) {
            shiftObject.render(spriteBatch);
        }
        spriteBatch.end();

        switch (gameScreen.state) {
            case RUNNING:
                drawRunning();
                break;
            case PAUSED:
                drawPaused();
                break;
        }

        guiCam.update();
    }
}
