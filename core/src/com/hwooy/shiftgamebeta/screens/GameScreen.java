package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hwooy.shiftgamebeta.levels.Level;
import com.hwooy.shiftgamebeta.listeners.TheContactListener;
import com.hwooy.shiftgamebeta.listeners.PlayerInputListener;
import com.hwooy.shiftgamebeta.models.*;
import com.hwooy.shiftgamebeta.utils.Settings;
import com.hwooy.shiftgamebeta.viewer.LevelRenderer;

import java.util.ArrayList;

/**
 * Created by jason on 11/14/14.
 * This is the screen that displays the screenManager itself
 */
public class GameScreen extends ScreenAdapter{

    public enum State {
        RUNNING, PAUSED
    }

    LevelRenderer levelRenderer;
    Rectangle pauseBounds;
    ShapeRenderer debugRenderer;
    State state;
    ScreenManager screenManager;
    OrthographicCamera mapCam;
    OrthographicCamera lockedCam;
    public Level level;
    int levelNumber;
    //LevelRenderer renderer;
    Box2DDebugRenderer renderer;
    Vector3 touchPoint;
    public World world;
    public Player player;
    TheContactListener theContactListener;
    PlayerInputListener playerInputListener;
    float lastShifted;
    boolean idleGlitch = false;
    float multiplier;

    Application.ApplicationType applicationType;

    Settings settings;

    float garbageCollect;

    /**
     * Constructor for GameScreen which takes in an instance of the screenManager and a level number
     * @param screenManager instance of screenManager
     * @param levelNumber of the level to be played
     */
    public GameScreen(ScreenManager screenManager, int levelNumber) {
        levelRenderer = new LevelRenderer(this);

        //The pause square
        pauseBounds = new Rectangle(0, 31, 1, 1);

        //Renders the shapes of the objects for debugging purposes
        debugRenderer = new ShapeRenderer();

        //Making heaven and world
        world = new World(new Vector2(0, -30f), false);

        //Settings listeners
        theContactListener = new TheContactListener(this);
        world.setContactListener(theContactListener);

        //Sets the stage
        this.screenManager = screenManager;
        state = State.RUNNING;
        mapCam = new OrthographicCamera();
        mapCam.setToOrtho(false, 48, 32);
        //mapCam = new OrthographicCamera(48, 32);
        //mapCam.position.set(24, 16, 0);
        lockedCam = new OrthographicCamera();
        lockedCam.setToOrtho(false, 480, 320);

        //Making the level itself
        this.levelNumber = levelNumber;
        //level = LevelFactory.makeLevel(levelNumber);

        level = new Level(levelNumber);

        //Making the fixtures for all of the bodies
        FixtureFactory fixtureFactory = new FixtureFactory(level, world);
        fixtureFactory.makeFixtures();

        //Rendering stuff
        //renderer = new LevelRenderer(level);
        renderer = new Box2DDebugRenderer();

        //Private members
        touchPoint = new Vector3();
        lastShifted = 0;
        player = level.player;
        multiplier = 2.5f;

        //Find what device the game is running on
        applicationType = Gdx.app.getType();

        // Using custom inputProcessor to handle screen touches (primarily fling actions for the phone)
        playerInputListener = new PlayerInputListener(this);
        Gdx.input.setInputProcessor(new GestureDetector(playerInputListener));

        settings = Settings.getInstance();

        garbageCollect = 0;
    }

    /**
     * This method handles some (but not all, input is also handled through the playerInputListener) input, including
     * key presses and accelerometer actions
     */
    private void handleInput(float delta) {
        lastShifted += delta;

        //If the device is such that *does not* have touch capabilities
        if (applicationType != Application.ApplicationType.Android &&
            applicationType != Application.ApplicationType.iOS &&
            Gdx.input.isKeyPressed(Input.Keys.SPACE) &&
            lastShifted > delta * 10) {
                playerShiftDimension();
                lastShifted = 0;
        }

        // Handles touch events
        if (Gdx.input.justTouched()) {
            mapCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            //If the pause button was touched... pause the game
            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                state = State.PAUSED;
            }
        }
    }

    //player.state == Player.State.IDLE ||
    public void flingPlayer(float xImpulse, float yImpulse) {
        if (Math.abs(player.getBody().getLinearVelocity().y) < .001f &&
                Math.abs(player.getBody().getLinearVelocity().x) < .001f) {
            //System.out.println("moving");
            player.state = Player.State.MOVING;

            player.getBody().applyForceToCenter(multiplier * xImpulse, multiplier * yImpulse, true);
            idleGlitch = true;
        }
    }

    /**
     * Switched dimensional layer of the player by creating a body in the other world and destroying the previous one
     */
    public void playerShiftDimension() {
        Fixture playerFixture = player.getBody().getFixtureList().get(0);
        Filter filter = playerFixture.getFilterData();
        filter.maskBits = (short) ((~filter.maskBits) & FixtureFactory.BIT_BOTH);
        playerFixture.setFilterData(filter);
    }

    /**
     * Updates player state from airborne to either idle or running based on horizontal velocity
     */
    private void updatePlayerState() {
        Vector2 vel = player.getBody().getLinearVelocity();
        if (vel.x == 0 && vel.y == 0) {
            if (!idleGlitch) {
                //System.out.println(player.getBody().getLinearVelocity());
                player.state = Player.State.IDLE;
            }

            idleGlitch = false;
        }
    }

    private void updateCrumblingBlocks(float delta) {
        ArrayList<GameObject> objectsToRemove = new ArrayList<GameObject>();
        for (GameObject object : level.gameObjects) {
            if (object.getClass() == CrumblingBlock.class) {
                CrumblingBlock crumblingBlock = (CrumblingBlock) object;
                for (Body body : theContactListener.bodiesToRemove) {
                    if (object.getBody().equals(body)) {
                        crumblingBlock.state = CrumblingBlock.State.CRUMBLING;
                    }
                }
                crumblingBlock.update(delta);
                if (crumblingBlock.crumblingStateTime >= 1f) {
                    objectsToRemove.add(object);
                    world.destroyBody(crumblingBlock.getBody());
                }
            }
        }
        for (GameObject object : objectsToRemove) {
            level.gameObjects.remove(object);
        }
        theContactListener.bodiesToRemove.clear();
    }

    /**
     * updates screenManager screen based on user input
     */
    private void update(float delta) {
        switch (state) {
            case RUNNING:
                updateRunning(delta);
                return;
            case PAUSED:
                updatePaused();
                return;
        }
    }

    /**
     * updates the screen as if the player is actively playing
     * @param delta
     */
    private void updateRunning(float delta) {
        handleInput(delta);
        updatePlayerState();

        for(Platform plat: level.platforms_HELL){
            plat.update();
        }
        world.step(delta, 6, 2);

        updateCrumblingBlocks(delta);

        if (player.getBody().getPosition().x > 48 + Player.PLAYER_WIDTH/2
                || player.getBody().getPosition().x < 0 - Player.PLAYER_WIDTH/2
                || player.getBody().getPosition().y < 0 - Player.PLAYER_HEIGHT/2) {
            restartLevel();
        }
    }

    /**
     * updates based on if player has paused the game
     */
    private void updatePaused() {
        if (Gdx.input.justTouched())
        {
            mapCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                state = State.RUNNING;
            }
        }
    }

    /**
     * draws needed objects onto the screenManager screen
     */
    private void draw() {
        levelRenderer.render();
    }

    /**
     * renders (updates and draws) onto the screenManager screen
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        update(delta);
        draw();
        garbageCollect += delta;
    }


    /**
     * do you really need an explanation
     */
    public void restartLevel() {
        settings.dispose();
        screenManager.setGameScreen(levelNumber);
    }
    /**
     * Upon reaching the goal, moves player to the next level.
     */
    public void nextLevel() {
        settings.dispose();
        settings.saveNextLevel(levelNumber);
        screenManager.setGameScreen(++levelNumber);
    }

}
