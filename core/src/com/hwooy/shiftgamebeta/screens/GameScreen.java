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
import com.hwooy.shiftgamebeta.utils.ObjectFactory;
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
    public ArrayList<ShiftObject> gameObjects;
    GameState state;
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    int levelNumber;
    Vector3 touchPoint;
    World world;
    PlayerGestureDetector playerGestureDetector;
    ShiftContactListener shiftContactListener;

    public GameScreen(ScreenManager screenManager, int levelNumber) {
        this.screenManager = screenManager;
        this.levelNumber = levelNumber;
        state = GameState.RUNNING;

        ObjectFactory objectFactory = new ObjectFactory(levelNumber);
        world = objectFactory.getWorld();
        gameObjects = objectFactory.getGameObjects();
        //playerGestureDetector = new PlayerGestureDetector();
        //shiftContactListener = new ShiftContactListener();
        //world.setContactListener(shiftContactListener);
        touchPoint = new Vector3();
        gameRenderer = new GameRenderer(this);
        shapeRenderer = Settings.getInstance().shapeRenderer;
    }

    public void update(float delta) {
        world.step(delta, 6, 2);
    }

    public void draw() {
        gameRenderer.render();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }



}
