package com.hwooy.shiftgamebeta.levels;

import com.hwooy.shiftgamebeta.models.TerrainBlock;

/**
 * Created by jason on 11/14/14.
 * Level1
 */
public class Level1 extends Level {

    public Level1() {
        super();
        populateLevel();
    }

    public void populateLevel() {
        // populate hell
        for (int i = 0; i < LEVEL_WIDTH; i+=TerrainBlock.WIDTH * 2) {
            hellTerrainObjects.add(new TerrainBlock(i, 0));
            hellTerrainObjects.add(new TerrainBlock(i, LEVEL_HEIGHT));
        }
        for (int i = (int) TerrainBlock.HEIGHT; i < LEVEL_HEIGHT; i += TerrainBlock.HEIGHT * 2) {
            hellTerrainObjects.add(new TerrainBlock(0, i));
            hellTerrainObjects.add(new TerrainBlock(LEVEL_WIDTH, i));
            hellTerrainObjects.add(new TerrainBlock(50, i));
        }

        // populate heaven
        for (int i = 0; i < LEVEL_WIDTH; i+=TerrainBlock.WIDTH * 2) {
            heavenTerrainObjects.add(new TerrainBlock(i, 0));
            heavenTerrainObjects.add(new TerrainBlock(i, LEVEL_HEIGHT));
        }
        for (int i = (int) TerrainBlock.HEIGHT; i < LEVEL_HEIGHT; i += TerrainBlock.HEIGHT * 2) {
            heavenTerrainObjects.add(new TerrainBlock(0, i));
            heavenTerrainObjects.add(new TerrainBlock(LEVEL_WIDTH, i));
            heavenTerrainObjects.add(new TerrainBlock(100, i));
        }

        // create player
        portal.position.set(125, 20);
        player.position.set(25, 40);

    }

}
