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
import com.hwooy.shiftgamebeta.ShiftGameBeta;
import com.hwooy.shiftgamebeta.levels.Level;
import com.hwooy.shiftgamebeta.levels.LevelFactory;
import com.hwooy.shiftgamebeta.listeners.PlayerContactListener;
import com.hwooy.shiftgamebeta.models.FixtureFactory;
import com.hwooy.shiftgamebeta.models.GameObject;
import com.hwooy.shiftgamebeta.models.Player;
import com.hwooy.shiftgamebeta.ShiftGameBeta;

/**
 * Created by jason on 11/14/14.
 * This is the screen that displays the game itself
 */
public class GameScreen extends ScreenAdapter{

    public enum State {
        RUNNING, PAUSED
    }

    State state;
    ShiftGameBeta game;
    OrthographicCamera cam;
    Level level;
    //LevelRenderer renderer;
    Box2DDebugRenderer renderer;
    Vector3 touchPoint;
    World hell;
    World heaven;
    Player player;
    boolean inHell;

    Application.ApplicationType applicationType;

    /**
     * Constructor for GameScreen which takes in an instance of the game and a level number
     * @param game instance of game
     * @param levelNumber of the level to be played
     */
    public GameScreen(ShiftGameBeta game, int levelNumber) {
        hell = new World(new Vector2(0, -100f), false);
        heaven = new World(new Vector2(0, -100f), false);
        hell.setContactListener(new PlayerContactListener());
        heaven.setContactListener(new PlayerContactListener());

        this.game = game;
        state = State.RUNNING;
        cam = new OrthographicCamera(StartScreen.CAM_WIDTH, StartScreen.CAM_HEIGHT);
        cam.position.set(StartScreen.CAM_WIDTH / 2, StartScreen.CAM_HEIGHT / 2, 0);
        level = LevelFactory.makeLevel(levelNumber);
        FixtureFactory fixtureFactory = new FixtureFactory(level, hell, heaven);
        fixtureFactory.makeFixtures();
        //renderer = new LevelRenderer(level);
        renderer = new Box2DDebugRenderer();
        touchPoint = new Vector3();
        player = level.player;
        inHell = true;

        applicationType = Gdx.app.getType();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                playerShiftDimension();
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (Math.abs(velocityY) > Math.abs(velocityX)) {
                       // WHY IS VELOCITY < 0 SWIPE UP? DUNNO BUT OH WELL
                    if (velocityY < 0) {
                        playerJump();
                    } else {
//                        TODO handle swipe down (shift dimension)
                        playerShiftDimension();
                    }
                }
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                return false;
            }
        }));

    }

    private void handleInput() {
        if (applicationType == Application.ApplicationType.Android || applicationType == Application.ApplicationType.iOS) {
            playerMove(Gdx.input.getAccelerometerY() * 30);
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                playerJump();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                playerMove(Player.RUN_VELOCITY);
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                playerMove(-Player.RUN_VELOCITY);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                playerShiftDimension();
            } else {
                playerStop();
            }

        }

        // handle pauses etc
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//            TODO add real stuff asides from this debug crap
        }
    }

    private void playerJump() {
        if (player.state != Player.State.AIRBORNE) {
            player.state = Player.State.AIRBORNE;
            //player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyForceToCenter(0, 2000, true);
        }
    }

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

    private void playerStop() {
        if (player.state == Player.State.RUNNING) {
            player.state = Player.State.IDLE;
        }
        player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
    }

    private void playerShiftDimension() {
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

    private void updatePlayerState() {
        if (player.getBody().getLinearVelocity().y == 0) {
            player.state = Player.State.RUNNING;
            if (player.getBody().getLinearVelocity().x == 0) {
                player.state = Player.State.IDLE;
            }
        }
    }

    /**
     * updates game screen based on user input
     */
    private void update(float delta) {
        handleInput();
        updatePlayerState();
        if (inHell) {
            hell.step(delta, 6, 2);
        } else {
            heaven.step(delta, 6, 2);
        }
    }

    /**
     * draws needed objects onto the game screen
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
     * renders (updates and draws) onto the game screen
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

}
