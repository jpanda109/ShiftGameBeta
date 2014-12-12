package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
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
        if (keycode == Input.Keys.D) {
            gameScreen.flingPlayer(300f, 300f);
        }
        if (keycode == Input.Keys.A) {
            gameScreen.flingPlayer(-300f, 300f);
        }
        if (keycode == Input.Keys.W) {
            Vector2 pos = gameScreen.player.body.getPosition();
            gameScreen.flingPlayer((Gdx.input.getX() - 8 * pos.x) * 30, (320 - (Gdx.input.getY() - 8 * pos.y)) * 8);
            System.out.println(320 - (Gdx.input.getY() - pos.y));
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
