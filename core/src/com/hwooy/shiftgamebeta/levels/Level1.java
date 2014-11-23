package com.hwooy.shiftgamebeta.levels;

import com.hwooy.shiftgamebeta.models.Block;

/**
 * Created by jason on 11/14/14.
 * Level1
 * TODO OBSOLETE
 */
public class Level1 extends Level {

    public Level1() {
        super(0);
        populateLevel();
    }

    /**
     * populates level with the necessary terrain blocks and etc
     */
    public void populateLevel() {
        // populate hell
        for (int i = 0; i < LEVEL_WIDTH; i+= Block.WIDTH * 2) {
            hellObjects.add(new Block(i, 0));
            hellObjects.add(new Block(i, LEVEL_HEIGHT));
        }
        for (int i = (int) Block.HEIGHT; i < LEVEL_HEIGHT; i += Block.HEIGHT * 2) {
            hellObjects.add(new Block(0, i));
            hellObjects.add(new Block(LEVEL_WIDTH, i));
            hellObjects.add(new Block(50, i));
        }

        // populate heaven
        for (int i = 0; i < LEVEL_WIDTH; i+= Block.WIDTH * 2) {
            heavenObjects.add(new Block(i, 0));
            heavenObjects.add(new Block(i, LEVEL_HEIGHT));
        }
        for (int i = (int) Block.HEIGHT; i < LEVEL_HEIGHT; i += Block.HEIGHT * 2) {
            heavenObjects.add(new Block(0, i));
            heavenObjects.add(new Block(LEVEL_WIDTH, i));
            heavenObjects.add(new Block(100, i));
        }

        // create player
        portal.position.set(42, 2);
        player.position.set(2, 4);

    }

}
