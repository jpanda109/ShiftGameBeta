package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Screen;
import com.hwooy.shiftgamebeta.ShiftGameBeta;

import java.util.Stack;

/**
 * Created by jason on 11/16/14.
 * Manages all the screens in the screenManager so as to not overload the fucking memory
 */
public class ScreenManager {

    public ShiftGameBeta game;
    public Stack<Screen> screens;

    public enum Screens {
        START, GAME
    }

    public ScreenManager(ShiftGameBeta game) {
        this.game = game;
        screens = new Stack<Screen>();
        pushScreen(Screens.START, 0);
    }

    public void pushScreen(Screens screen, int level) {
        if (screen == Screens.START) {
            screens.push(new StartScreen(this));
        } else if (screen == Screens.GAME) {
            screens.push(new GameScreen(this, level));
        }
        game.setScreen(screens.peek());
    }

    public void popScreen() {
        Screen screen = screens.pop();
        screen.dispose();
    }

    public void setScreen(Screens screen) {
        popScreen();
        pushScreen(screen, 0);
    }

    public void setGameScreen(int level) {
        popScreen();
        pushScreen(Screens.GAME, level);
    }
}
