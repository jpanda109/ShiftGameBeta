package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.hwooy.shiftgamebeta.listeners.PlayerGestureDetector;
import com.hwooy.shiftgamebeta.listeners.ShiftContactListener;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.utils.Settings;
import com.hwooy.shiftgamebeta.viewers.GameRenderer;

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 */
public class GameScreen extends ScreenAdapter {

    public enum GameState {
        RUNNING, PAUSED
    }
    ScreenManager screenManager;
    ArrayList<ShiftObject> gameObjects;
    GameState state;
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    OrthographicCamera guiCam;
    int levelNumber;
    Vector3 touchPoint;
    World world;
    PlayerGestureDetector playerGestureDetector;
    ShiftContactListener shiftContactListener;

    public GameScreen(ScreenManager screenManager, int levelNumber) {
        this.screenManager = screenManager;
        this.levelNumber = levelNumber;

        gameObjects = new ArrayList<ShiftObject>();
        state = GameState.RUNNING;
        world = new World(new Vector2(0, -10f), false);
        //playerGestureDetector = new PlayerGestureDetector();
        //shiftContactListener = new ShiftContactListener();
        //world.setContactListener(shiftContactListener);
        touchPoint = new Vector3();
        guiCam = new OrthographicCamera(480, 320);
        gameRenderer = new GameRenderer();
        shapeRenderer = Settings.getInstance().shapeRenderer;
    }



}
