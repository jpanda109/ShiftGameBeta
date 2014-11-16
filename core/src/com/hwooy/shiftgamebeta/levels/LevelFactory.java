package com.hwooy.shiftgamebeta.levels;

/**
 * Created by jason on 11/14/14.
 * class that instantiates a level based on given input
 */
public class LevelFactory {

    public static Level makeLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return new Level1();
        }
        return new Level1();
    }
}
