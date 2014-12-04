package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.hwooy.shiftgamebeta.screens.GameScreen;

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
        System.out.println(keycode);
        if (keycode == Input.Keys.SPACE) {
            System.out.println("hi");
            gameScreen.shiftPlayer();
        }
        return false;
    }
}
