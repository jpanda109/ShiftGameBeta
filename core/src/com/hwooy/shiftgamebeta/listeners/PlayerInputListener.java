package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.hwooy.shiftgamebeta.screens.GameScreen;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/4/14.
 */
public class PlayerInputListener extends InputAdapter {

    GameScreen gameScreen;

    public PlayerInputListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            gameScreen.shiftPlayer();
        }

        // dev cheats
        if (God.DEBUG) {
            if (keycode == Input.Keys.RIGHT) {
                gameScreen.setGameState(GameScreen.GameState.NEXT_LEVEL);
            }
        }
        return false;
    }
}
