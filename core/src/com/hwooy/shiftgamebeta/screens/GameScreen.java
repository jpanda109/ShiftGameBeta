package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hwooy.shiftgamebeta.block_classes.CrumblingBlock;
import com.hwooy.shiftgamebeta.listeners.PlayerGestureDetector;
import com.hwooy.shiftgamebeta.listeners.PlayerInputListener;
import com.hwooy.shiftgamebeta.listeners.ShiftContactListener;
import com.hwooy.shiftgamebeta.object_classes.Player;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.utils.ObjectFactory;
import com.hwooy.shiftgamebeta.utils.God;
import com.hwooy.shiftgamebeta.viewers.GameRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by jason on 12/3/14.
 * Game Screen(playing the actual game, rendering is in GameRenderer
 */
public class GameScreen extends ScreenAdapter {

    public enum GameState {
        RUNNING, PAUSED, RESTART, NEXT_LEVEL, QUIT
    }
    ScreenManager screenManager;
    public ArrayList<ShiftObject> gameObjects;
    Player player;
    public GameState state;
    GameRenderer gameRenderer;
    int levelNumber;
    Vector3 touchPoint;
    public World world;
    PlayerGestureDetector playerGestureDetector;
    PlayerInputListener playerInputListener;
    ShiftContactListener shiftContactListener;

    public Rectangle pauseBounds;
    public Rectangle resumeBounds;
    public Rectangle restartBounds;
    public Rectangle quitBounds;

    int starsGathered;

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

        pauseBounds = new Rectangle(0, 38, 2, 2);
        resumeBounds = new Rectangle(10, 20, 3, 3);
        restartBounds = new Rectangle(20, 20, 3, 3);
        quitBounds = new Rectangle(30, 20, 3, 3);

        gameRenderer = new GameRenderer(this); // must be initialized after bounds

        starsGathered = 0;
    }

    public void flingPlayer(float xVelocity, float yVelocity) {
        if (player.state == Player.State.IDLE) {
            float maxXVelocity = 25f;
            float maxYVelocity = 50f;
            xVelocity /= 69; //(float) Math.sqrt(Math.abs(xVelocity));
            yVelocity /= 40; //(float) Math.sqrt(Math.abs(yVelocity));
            if (Math.abs(yVelocity) > maxYVelocity) {
                yVelocity *= maxYVelocity / Math.abs(yVelocity);
            }
            if (Math.abs(xVelocity) > maxXVelocity) {
                xVelocity *= maxXVelocity / Math.abs(xVelocity);
            }
            System.out.println(xVelocity + " " + yVelocity);
            player.body.applyLinearImpulse(new Vector2(xVelocity, yVelocity), player.body.getPosition(), true);
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

            switch (state) {
                case RUNNING:
                    if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                        setGameState(GameState.PAUSED);
                    }
                    break;
                case PAUSED:
                    if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
                        setGameState(GameState.RUNNING);
                    }
                    else if (restartBounds.contains(touchPoint.x, touchPoint.y)) {
                        setGameState(GameState.RESTART);
                    }
                    else if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
                        setGameState(GameState.QUIT);
                    }
            }
        }

    }

    private void updateRunning(float delta) {
        world.step(delta, 6, 2);
        updateGameObjects(delta);
        checkPlayerBounds();
    }

    private void updateGameObjects(float delta) {
        Iterator<ShiftObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            ShiftObject object = iterator.next();
            for (Body body : shiftContactListener.crumblingBodies) { // update crumbling block states
                if (object.body == body) {
                    ((CrumblingBlock) object).state = CrumblingBlock.State.CRUMBLING;
                }
            }
            object.update(delta);
            if (object.getClass() == CrumblingBlock.class) { // destroy and remove crumbling blocks
                if (((CrumblingBlock) object).state == CrumblingBlock.State.DEAD) {
                    world.destroyBody(object.body);
                    iterator.remove();
                }
            }
            for (Body body : shiftContactListener.gatheredStars) { // destroy and remove gathered stars
                if (object.body == body) {
                    world.destroyBody(object.body);
                    iterator.remove();
                    ++starsGathered;
                }
            }
        }
        shiftContactListener.crumblingBodies.clear();
    }

    private void checkPlayerBounds() {
        if (player.body.getPosition().x < -1f
                || player.body.getPosition().x > 60
                || player.body.getPosition().y < -1f) {
            setGameState(GameState.RESTART);
        }
    }

    private void updatePaused(float delta) {

    }

    public void update(float delta) {
        handleInput(delta);
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
            case QUIT:
                quitGame();
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
        God.getInstance().updateStarsGathered(levelNumber, starsGathered);
        System.out.println("Current Total Stars: " + God.getInstance().getTotalStars());
        if (levelNumber < God.MAX_LEVEL) {
            screenManager.setGameScreen(++levelNumber);
        } else {
            screenManager.setScreen(ScreenManager.Screens.WIN);
        }
    }

    private void quitGame() {
        screenManager.setScreen(ScreenManager.Screens.START);
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
