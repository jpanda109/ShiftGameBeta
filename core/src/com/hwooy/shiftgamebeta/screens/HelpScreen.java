package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by jason on 11/22/14.
 */
public class HelpScreen extends ScreenAdapter {

    public static final int CAM_WIDTH = 150; //Gdx.graphics.getWidth();
    public static final int CAM_HEIGHT = 100; //Gdx.graphics.getHeight();
    public static final float BACK_BOUNDS_WIDTH_RATIO = .1f;
    public static final float BACK_BOUNDS_HEIGHT_RATIO = .1f;
    public static final float HELP_BOUNDS_WIDTH_RATIO = .8f;
    public static final float HELP_BOUNDS_HEIGHT_RATIO = .6f;
    public static final float NEXT_HELP_BOUNDS_WIDTH_RATIO = .2f;
    public static final float NEXT_HELP_BOUNDS_HEIGHT_RATIO = .1f;

    ScreenManager screenManager;
    OrthographicCamera cam;
    Rectangle backBounds;
    Rectangle helpBounds;
    Rectangle nextHelpBounds;
    Vector3 touchPoint;
    ShapeRenderer debugRenderer;

    /**
     * constructor for the start screen
     * @param screenManager instance of screenManager being played
     */
    public HelpScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);
        backBounds = new Rectangle(
                0,
                (int) (CAM_HEIGHT * .85),
                (int) (CAM_WIDTH * BACK_BOUNDS_WIDTH_RATIO),
                (int) (CAM_HEIGHT * BACK_BOUNDS_HEIGHT_RATIO));
        helpBounds = new Rectangle(
                (int) (CAM_WIDTH * .1),
                (int) (CAM_HEIGHT * .15),
                (int) (CAM_WIDTH * HELP_BOUNDS_WIDTH_RATIO),
                (int) (CAM_HEIGHT * HELP_BOUNDS_HEIGHT_RATIO));
        nextHelpBounds = new Rectangle(
                (int) (CAM_WIDTH * (1 - NEXT_HELP_BOUNDS_WIDTH_RATIO)),
                0,
                (int) (CAM_WIDTH * NEXT_HELP_BOUNDS_WIDTH_RATIO),
                (int) (CAM_HEIGHT * NEXT_HELP_BOUNDS_HEIGHT_RATIO));
        touchPoint = new Vector3();
        debugRenderer = new ShapeRenderer();
    }

    /**
     * handles user input response
     */
    public void update() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

//            TODO provide actions inputs
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                screenManager.setScreen(ScreenManager.Screens.START);
                //screenManager.setScreen(new GameScreen(screenManager, 1));
            }
            else if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
            }
            else if (nextHelpBounds.contains(touchPoint.x, touchPoint.y)) {
            }
        }
    }

    /**
     * draws shit onto screen
     */
    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();

//        TODO change from shitty colors to real drawings
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(new Color(Color.RED));
        debugRenderer.rect(backBounds.getX(), backBounds.getY(), backBounds.getWidth(), backBounds.getHeight());
        debugRenderer.setColor(new Color(Color.BLUE));
        debugRenderer.rect(nextHelpBounds.getX(), nextHelpBounds.getY(), nextHelpBounds.getWidth(), nextHelpBounds.getHeight());
        debugRenderer.setColor(new Color(Color.GREEN));
        debugRenderer.rect(helpBounds.getX(), helpBounds.getY(), helpBounds.getWidth(), helpBounds.getHeight());
        debugRenderer.end();
    }

    /**
     * renders shit on screen
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        update();
        draw();
    }
    
}
