package com.hwooy.shiftgamebeta.screens;

import com.badlogic.gdx.Screen;
import com.hwooy.shiftgamebeta.ShiftGameBeta;

import java.util.Stack;

/**
 * Created by jason on 11/16/14.
 * Manages all the screens in the screenManager so as to not overload the friggin memory
 * self documenting method names (I hope)
 */
public class ScreenManager {

    /**
     * Member Variables
     */
    public ShiftGameBeta game;
    public Stack<Screen> screens;

    /**BEGINNING OF FUNCTIONS--------------------------------------------------------**/

    public enum Screens {
        START, GAME, HELP, LEVELS, WIN
    }

    public ScreenManager(ShiftGameBeta game) {
        this.game = game;
        screens = new Stack<Screen>();
        pushScreen(Screens.START, 0);
    }

    public void setScreen(Screens screen) {
        popScreen();
        pushScreen(screen, 0);
    }

    public void setGameScreen(int level) {
        popScreen();
        pushScreen(Screens.GAME, level);
    }

    private void pushScreen(Screens screen, int level) {
        if (screen == Screens.START) {
            screens.push(new StartScreen(this));
        }  else if (screen == Screens.GAME) {
            screens.push(new GameScreen(this, level));
        } else if (screen == Screens.HELP) {
            screens.push(new HelpScreen(this));
        } else if (screen == Screens.LEVELS) {
            screens.push(new LevelsScreen(this));
        } else if (screen == Screens.WIN) {
            screens.push(new WinScreen(this));
        }
        game.setScreen(screens.peek());
    }

    private void popScreen() {
        Screen screen = screens.pop();
        screen.dispose();
    }
}
