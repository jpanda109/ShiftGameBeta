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
        }
        return new Level1();
    }
}
