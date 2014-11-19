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
import com.hwooy.shiftgamebeta.levels.Level;
import com.hwooy.shiftgamebeta.levels.LevelFactory;
import com.hwooy.shiftgamebeta.listeners.PlayerContactListener;
import com.hwooy.shiftgamebeta.listeners.PlayerInputListener;
import com.hwooy.shiftgamebeta.models.FixtureFactory;
import com.hwooy.shiftgamebeta.models.Platform;
import com.hwooy.shiftgamebeta.models.Player;

/**
 * Created by jason on 11/14/14.
 * This is the screen that displays the screenManager itself
 */
public class GameScreen extends ScreenAdapter{

    public enum State {
        RUNNING, PAUSED
    }

    Rectangle pauseBounds;
    ShapeRenderer debugRenderer;
    State state;
    ScreenManager screenManager;
    OrthographicCamera cam;
    Level level;
    int levelNumber;
    //LevelRenderer renderer;
    Box2DDebugRenderer renderer;
    Vector3 touchPoint;
    World hell;
    World heaven;
    Player player;
    boolean inHell;
    PlayerContactListener playerContactListener;
    PlayerInputListener playerInputListener;
    float lastShifted;

    Application.ApplicationType applicationType;

    /**
     * Constructor for GameScreen which takes in an instance of the screenManager and a level number
     * @param screenManager instance of screenManager
     * @param levelNumber of the level to be played
     */
    public GameScreen(ScreenManager screenManager, int levelNumber) {

        pauseBounds = new Rectangle(0, 85, 15, 15);
        debugRenderer = new ShapeRenderer();

        lastShifted = 0;

        hell = new World(new Vector2(0, -100f), false);
        heaven = new World(new Vector2(0, -100f), false);
        playerContactListener = new PlayerContactListener(this);
        hell.setContactListener(playerContactListener);
        heaven.setContactListener(playerContactListener);

        this.screenManager = screenManager;
        state = State.RUNNING;
        cam = new OrthographicCamera(StartScreen.CAM_WIDTH, StartScreen.CAM_HEIGHT);
        cam.position.set(StartScreen.CAM_WIDTH / 2, StartScreen.CAM_HEIGHT / 2, 0);
        this.levelNumber = levelNumber;
        level = LevelFactory.makeLevel(levelNumber);
        FixtureFactory fixtureFactory = new FixtureFactory(level, hell, heaven);
        fixtureFactory.makeFixtures();
        //renderer = new LevelRenderer(level);
        renderer = new Box2DDebugRenderer();
        touchPoint = new Vector3();
        player = level.player;
        inHell = true;

        applicationType = Gdx.app.getType();

        // Using custom inputProcessor to handle screen touches (primarily fling actions for the phone)
        playerInputListener = new PlayerInputListener(this);
        Gdx.input.setInputProcessor(new GestureDetector(playerInputListener));

    }

    /**
     * This method handles some (but not all, input is also handled through the playerInputListener) input, including
     * key presses and accelerometer actions
     */
    private void handleInput(float delta) {
        lastShifted += delta;
        if (applicationType != Application.ApplicationType.Android && applicationType != Application.ApplicationType.iOS) {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && lastShifted > delta * 10)
            {
                playerShiftDimension();
                lastShifted = 0;
            }
        }

        // handle pauses etc
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//            TODO add real stuff asides from this debug crap
            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                state = State.PAUSED;
            }
        }
    }

    public void flingPlayer(float xImpulse, float yImpulse) {
        player.state = Player.State.MOVING;
        int multiplier = 3;
        player.getBody().applyForceToCenter(multiplier * xImpulse, multiplier * yImpulse, true);
    }

    /**
     * Switched dimensional layer of the player by creating a body in the other world and destroying the previous one
     */
    public void playerShiftDimension() {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
        bodyDef.position.set(player.getBody().getPosition());
        bodyDef.linearVelocity.set(player.getBody().getLinearVelocity());
        Body body;
        if (inHell) {
            body = heaven.createBody(bodyDef);
            hell.destroyBody(player.getBody());
            inHell = false;
        } else {
            body = hell.createBody(bodyDef);
            heaven.destroyBody(player.getBody());
            inHell = true;
        }
        body.createFixture(fixtureDef);
        player.setBody(body);
    }

    /**
     * Updates player state from airborne to either idle or running based on horizontal velocity
     */
    private void updatePlayerState() {
        if (player.getBody().getLinearVelocity().y == 0 && player.getBody().getLinearVelocity().x == 0) {
            player.state = Player.State.IDLE;
        }
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

    private void updateRunning(float delta) {
        handleInput(delta);
        updatePlayerState();
        for(Platform plat: level.platforms)
        {
            plat.update();
        }

        if (inHell) {
            hell.step(delta, 6, 2);
        } else {
            heaven.step(delta, 6, 2);
        }
    }

    private void updatePaused() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                state = State.RUNNING;
            }
        }
    }

    /**
     * draws needed objects onto the screenManager screen
     */
    private void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (inHell) {
            renderer.render(hell, cam.combined);
        } else {
            renderer.render(heaven, cam.combined);
        }
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        debugRenderer.setColor(Color.BLACK);
        debugRenderer.rect(pauseBounds.x, pauseBounds.y, pauseBounds.width, pauseBounds.height);
        debugRenderer.end();
        cam.update();
    }

    /**
     * renders (updates and draws) onto the screenManager screen
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    /**
     * Upon reaching the goal, moves player to the next level.
     */
    public void nextLevel() {
        screenManager.setGameScreen(++levelNumber);
    }

}
