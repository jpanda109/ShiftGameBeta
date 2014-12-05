package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hwooy.shiftgamebeta.listeners.PlayerGestureDetector;
import com.hwooy.shiftgamebeta.listeners.PlayerInputListener;
import com.hwooy.shiftgamebeta.listeners.ShiftContactListener;
import com.hwooy.shiftgamebeta.object_classes.Player;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.utils.ObjectFactory;
import com.hwooy.shiftgamebeta.utils.God;
import com.hwooy.shiftgamebeta.viewers.GameRenderer;

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 * Game Screen(playing the actual game, rendering is in GameRenderer
 */
public class GameScreen extends ScreenAdapter {

    public enum GameState {
        RUNNING, PAUSED, RESTART, NEXT_LEVEL
    }
    ScreenManager screenManager;
    public ArrayList<ShiftObject> gameObjects;
    Player player;
    GameState state;
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    int levelNumber;
    Vector3 touchPoint;
    public World world;
    PlayerGestureDetector playerGestureDetector;
    PlayerInputListener playerInputListener;
    ShiftContactListener shiftContactListener;

    public GameScreen(ScreenManager screenManager, int levelNumber) {
        this.screenManager = screenManager;
        this.levelNumber = levelNumber;
        state = GameState.RUNNING;

        // initialize world, fill it with objects and set the contact listener
        world = God.getInstance().world;
        shiftContactListener = new ShiftContactListener(this);
        world.setContactListener(shiftContactListener);
        ObjectFactory objectFactory = new ObjectFactory(levelNumber, world);
        gameObjects = objectFactory.getGameObjects();
        player = objectFactory.getPlayer();

        // set custom input processors
        playerGestureDetector = new PlayerGestureDetector(this);
        playerInputListener = new PlayerInputListener(this);
        InputMultiplexer mux = new InputMultiplexer();
        mux.addProcessor(new GestureDetector(playerGestureDetector));
        mux.addProcessor(playerInputListener);
        Gdx.input.setInputProcessor(mux);

        touchPoint = new Vector3();
        gameRenderer = new GameRenderer(this);
        shapeRenderer = God.getInstance().shapeRenderer;
    }

    public void flingPlayer(float xVelocity, float yVelocity) {
        if (player.state == Player.State.IDLE) {
            player.body.applyForceToCenter(xVelocity, yVelocity, true);
        }
    }

    // shift player collision type
    public void shiftPlayer() {
        Fixture playerFixture = player.body.getFixtureList().get(0);
        Filter filter = playerFixture.getFilterData();
        filter.maskBits = (short) (filter.maskBits ^ ObjectFactory.BIT_TYPE_BOTH);
        playerFixture.setFilterData(filter);
    }

    private void handleInput(float delta) {

        if (Gdx.input.justTouched()) {
            gameRenderer.guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (gameRenderer.pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                nextLevel();
            }
        }

    }

    private void updateRunning(float delta) {
        handleInput(delta);
        world.step(delta, 6, 2);
    }

    private void updatePaused(float delta) {

    }

    public void update(float delta) {
        switch (state) {
            case RUNNING:
                updateRunning(delta);
                break;
            case PAUSED:
                updatePaused(delta);
                break;
            case RESTART:
                restartLevel();
                break;
            case NEXT_LEVEL:
                nextLevel();
                break;
        }
    }

    public void draw() {
        gameRenderer.render();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    public void setGameState(GameState state) {
        this.state = state;
    }

    private void restartLevel() {
        screenManager.setGameScreen(levelNumber);
    }

    private void nextLevel() {
        if (levelNumber < God.MAX_LEVEL) {
            screenManager.setGameScreen(++levelNumber);
        } else {
            screenManager.setScreen(ScreenManager.Screens.WIN);
        }
    }

    @Override
    public void dispose() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        super.dispose();
    }


}
