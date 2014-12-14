package com.hwooy.shiftgamebeta.viewers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.screens.GameScreen;
import com.hwooy.shiftgamebeta.utils.God;

import java.util.Random;

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

    public Texture pauseTexture;
    public Texture resumeTexture;
    public Texture restartTexture;
    public Texture quitTexture;

    public float red;
    public float green;
    public float blue;

    public GameRenderer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        guiCam = new OrthographicCamera(60, 40);
        guiCam.position.set(30f, 20f, 0f);
        //guiCam.setToOrtho(false, 60, 40);
        //spriteBatch = Settings.getInstance().spriteBatch;
        spriteBatch = God.getInstance().spriteBatch;
        shapeRenderer = God.getInstance().shapeRenderer;
        debugRenderer =  God.getInstance().debugRenderer;

        this.pauseBounds = gameScreen.pauseBounds;
        resumeBounds = gameScreen.resumeBounds;
        restartBounds = gameScreen.restartBounds;
        quitBounds = gameScreen.quitBounds;

        pauseTexture = God.getInstance().getTexture(God.PAUSE_BUTTON_PATH);
        resumeTexture = God.getInstance().getTexture(God.RESUME_BUTTON_PATH);
        restartTexture = God.getInstance().getTexture(God.RESTART_BUTTON_PATH);
        quitTexture = God.getInstance().getTexture(God.QUIT_BUTTON_PATH);

        Random rand = new Random();
        red = rand.nextFloat() * 30 + 235;
        green = rand.nextFloat() * 30 + 235;
        blue = rand.nextFloat() * 30 + 235;

    }

    private void drawRunning() {
        /*
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(pauseBounds.x, pauseBounds.y, pauseBounds.width, pauseBounds.height);
        shapeRenderer.end();

*/
        spriteBatch.begin();
        spriteBatch.draw(pauseTexture, (pauseBounds.x) * 8, (pauseBounds.y )* 8, pauseBounds.width * 8, pauseBounds.height * 8);
        spriteBatch.end();
    }

    private void drawPaused() {
        /*
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(resumeBounds.x, resumeBounds.y, resumeBounds.width, resumeBounds.height);
        shapeRenderer.rect(restartBounds.x, restartBounds.y, restartBounds.width, restartBounds.height);
        shapeRenderer.rect(quitBounds.x, quitBounds.y, quitBounds.width, quitBounds.height);
        shapeRenderer.end();
        */

        spriteBatch.begin();
        spriteBatch.draw(resumeTexture, (resumeBounds.x) * 8, (resumeBounds.y) * 8, resumeBounds.width * 8, resumeBounds.height * 8);
        spriteBatch.draw(restartTexture, (restartBounds.x) * 8, (restartBounds.y )* 8, restartBounds.width * 8, restartBounds.height * 8);
        spriteBatch.draw(quitTexture, (quitBounds.x) * 8, (quitBounds.y )* 8, quitBounds.width * 8, quitBounds.height * 8);
        spriteBatch.end();
    }

    public void render() {
        GL20 gl = Gdx.gl20;
        gl.glClearColor(red / 255f, green / 255f, blue / 255f, 1);
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
