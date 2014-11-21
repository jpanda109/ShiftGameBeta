package com.hwooy.shiftgamebeta.levels;

/**
 * Created by jason on 11/14/14.
 * class that instantiates a level based on given input
 */
public class LevelFactory {

    /**
     * ok literally this just returns the necessary level using factory pattern pls
     * @param levelNumber of level to be returned
     * @return the level
     */
    public static Level makeLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return new Level1();
            case 2:
                return new Level2();
            case 3:
                return new Level3();
        }
        return new Level1();
    }
}
