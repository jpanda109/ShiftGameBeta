package com.hwooy.shiftgamebeta.levels;

import com.hwooy.shiftgamebeta.models.TerrainBlock;

/**
 * Created by Anna Hwang on 11/18/2014.
 *
 * Testing lava or some obstacle that can destroy player
 */
public class Level2 extends Level{

    public Level2() {
        super();
        populateLevel();
    }

    public void populateLevel()
    {
        //Populate hell
        populateHell();
        createBounds(true);

        //Populate heaven
        populateHeaven();
        createBounds(false);

        
    }

    public void populateHell()
    {

    }

    public void populateHeaven()
    {

    }

    public void createBounds(boolean isHell)
    {
        if(isHell)
        {
            for (int i = 0; i < LEVEL_WIDTH; i+= TerrainBlock.WIDTH * 2) {
                hellTerrainObjects.add(new TerrainBlock(i, 0));
                hellTerrainObjects.add(new TerrainBlock(i, LEVEL_HEIGHT));
            }
            for (int i = (int) TerrainBlock.HEIGHT; i < LEVEL_HEIGHT; i += TerrainBlock.HEIGHT * 2) {
                hellTerrainObjects.add(new TerrainBlock(0, i));
                hellTerrainObjects.add(new TerrainBlock(LEVEL_WIDTH, i));
            }
        }
        else
        {
            for (int i = 0; i < LEVEL_WIDTH; i+=TerrainBlock.WIDTH * 2) {
                heavenTerrainObjects.add(new TerrainBlock(i, 0));
                heavenTerrainObjects.add(new TerrainBlock(i, LEVEL_HEIGHT));
            }
            for (int i = (int) TerrainBlock.HEIGHT; i < LEVEL_HEIGHT; i += TerrainBlock.HEIGHT * 2) {
                heavenTerrainObjects.add(new TerrainBlock(0, i));
                heavenTerrainObjects.add(new TerrainBlock(LEVEL_WIDTH, i));
            }
        }
    }
}
