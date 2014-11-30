package com.hwooy.shiftgamebeta.viewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.hwooy.shiftgamebeta.levels.Level;
import com.hwooy.shiftgamebeta.models.GameObject;
import com.hwooy.shiftgamebeta.models.Player;
import com.hwooy.shiftgamebeta.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jason on 11/14/14.
 * THis is a renderer for levels
 */
public class LevelRenderer {

    private Rectangle pauseBounds;

    GameScreen gameScreen;
    private OrthographicCamera mapCam;
    private OrthographicCamera lockedCam;
    private ShapeRenderer debugRengerer;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Texture playerTexture;
    private SpriteBatch spriteBatch;

    public LevelRenderer(GameScreen gameScreen) {
        pauseBounds = new Rectangle(0, 31, 1, 1);

        this.gameScreen = gameScreen;
        this.mapCam = new OrthographicCamera();
        mapCam.setToOrtho(false, 48, 32);
        this.lockedCam = new OrthographicCamera();
        lockedCam.setToOrtho(false, 480, 320);
        debugRengerer = new ShapeRenderer();
        box2DDebugRenderer = new Box2DDebugRenderer();

        spriteBatch = new SpriteBatch();
        playerTexture = new Texture(Gdx.files.internal("android/assets/player.png"));
    }

    public void render() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        Player player = gameScreen.player;
        spriteBatch.draw(playerTexture, (player.getBody().getPosition().x - Player.PLAYER_WIDTH) * 10,
                (player.getBody().getPosition().y - Player.PLAYER_HEIGHT)* 10,
                Player.PLAYER_WIDTH * 20, Player.PLAYER_HEIGHT * 20);
        spriteBatch.end();

        gameScreen.level.tiledMapRenderer.setView(lockedCam);
        gameScreen.level.tiledMapRenderer.render();

        box2DDebugRenderer.render(gameScreen.world, mapCam.combined);

        debugRengerer.setProjectionMatrix(mapCam.combined);
        debugRengerer.begin(ShapeRenderer.ShapeType.Filled);
        debugRengerer.setColor(Color.BLACK);
        debugRengerer.rect(pauseBounds.x, pauseBounds.y, pauseBounds.width, pauseBounds.height);
        debugRengerer.end();
        mapCam.update();
    }


}
