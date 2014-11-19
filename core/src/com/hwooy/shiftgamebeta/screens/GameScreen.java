package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.levels.Level;
import com.hwooy.shiftgamebeta.levels.LevelFactory;
import com.hwooy.shiftgamebeta.listeners.PlayerContactListener;
import com.hwooy.shiftgamebeta.listeners.PlayerInputListener;
import com.hwooy.shiftgamebeta.models.FixtureFactory;
import com.hwooy.shiftgamebeta.models.Player;

/**
 * Created by jason on 11/14/14.
 * This is the screen that displays the screenManager itself
 */
public class GameScreen extends ScreenAdapter{

    public enum State {
        RUNNING, PAUSED
    }

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
        if (applicationType == Application.ApplicationType.Android || applicationType == Application.ApplicationType.iOS) {
            playerMove(Gdx.input.getAccelerometerY() * 30);
        }

        else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                playerJump();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && lastShifted > delta * 10)
            {
                playerShiftDimension();
                lastShifted = 0;
            }

            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                playerMove(Player.RUN_VELOCITY);
            }

            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                playerMove(-Player.RUN_VELOCITY);
            }

            else {
                playerStop();
            }

        }

        // handle pauses etc
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//            TODO add real stuff asides from this debug crap
        }
    }

    /**
     * This method handles the player jumping
     * If player not currently jumping, set to airborne and apply vertical force
     */
    public void playerJump() {
        if (player.state != Player.State.AIRBORNE) {
            player.state = Player.State.AIRBORNE;
            //player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyForceToCenter(0, 2000, true);
        }
    }

    /**
     * moves player in a horizontal direction by applying a linear impulse
     * @param xImpulse linear impulse to be applied to the player
     */
    private void playerMove(float xImpulse) {
        if (xImpulse == 0) {
            return;
        }
        if (xImpulse > 0) {
            player.direction = Player.Direction.RIGHT;
        } else {
            player.direction = Player.Direction.LEFT;
        }
        if (player.state == Player.State.IDLE) {
            player.state = Player.State.RUNNING;
        }
        player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
        player.getBody().applyLinearImpulse(new Vector2(xImpulse, 0), player.getBody().getPosition(), true);
        //player.getBody().applyForceToCenter(xImpulse, 0, true);
        //player.getBody().setLinearVelocity(xVelocity, player.getBody().getLinearVelocity().y);
    }

    /**
     * There's no friction, so to stop, the velocity must be manually set to 0
     * TODO set dampening
     */
    private void playerStop() {
        if (player.state == Player.State.RUNNING) {
            player.state = Player.State.IDLE;
        }
        player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
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
        if (player.getBody().getLinearVelocity().y == 0) {
            player.state = Player.State.RUNNING;
            if (player.getBody().getLinearVelocity().x == 0) {
                player.state = Player.State.IDLE;
            }
        }
    }

    /**
     * updates screenManager screen based on user input
     */
    private void update(float delta) {
        handleInput(delta);
        updatePlayerState();
        if (inHell) {
            hell.step(delta, 6, 2);
        } else {
            heaven.step(delta, 6, 2);
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
