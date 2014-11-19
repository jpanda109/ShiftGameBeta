package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.hwooy.shiftgamebeta.screens.GameScreen;

import java.util.Queue;

/**
 * Created by jason on 11/17/14.
 * Custom input listener designed to handle touch input. Queues commands to be processed.
 */
public class PlayerInputListener implements GestureDetector.GestureListener {

    GameScreen gameScreen;

    public PlayerInputListener(GameScreen gameScreen) {
        super();
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    /**
     * This is the only touch action currently needed, simply handles vertical swipes to either make the player
     * jump or shift dimension
     * parameter must be kept as is due to it being an overriden method (obviously)
     * @param velocityX horizontal velocity of fling
     * @param velocityY vertical velocity of fling
     * @param button button being held during fling (unneeded for mobile application for obvious reason)
     * @return nothing needed to be returned
     */
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        // WHY IS VELOCITY < 0 SWIPE UP? DUNNO BUT OH WELL
        if (velocityY < 0) {
            gameScreen.flingPlayer(velocityX, -velocityY);
        } else if (Math.abs(velocityX) < Math.abs(velocityY)) {
            gameScreen.playerShiftDimension();
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
}
