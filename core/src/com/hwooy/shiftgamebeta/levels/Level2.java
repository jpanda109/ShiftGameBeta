package com.hwooy.shiftgamebeta.levels;

import com.badlogic.gdx.math.Vector2;
import com.hwooy.shiftgamebeta.models.Block;
import com.hwooy.shiftgamebeta.models.PlatformFactory;

/**
 * Created by Anna Hwang on 11/18/2014.
 *
 * Testing lava or some obstacle that can destroy player
 * TODO OBSOLETE
 */
public class Level2 extends Level{

    Vector2 hell_begin = new Vector2(25, 5);
    Vector2 hell_end = new Vector2(25, 80);

    Vector2 heaven_begin = new Vector2(40, 10);
    Vector2 heaven_end = new Vector2(90, 10);

//float x, float y, float width, float height, int speed_, Direction dir_, Vector2 end)
    public Level2() {
        super(0);
        platforms_HELL.add(PlatformFactory.makePlatform(hell_begin.x, hell_begin.y, 15, 1, 40, hell_begin, hell_end));
        platforms_HEAVEN.add(PlatformFactory.makePlatform(heaven_begin.x, heaven_begin.y, 10, 1, 20, heaven_begin, heaven_end));
        populateLevel();
    }

    public void populateLevel()
    {
        //Populate hell
        populateHell();

        //Populate heaven
        populateHeaven();
    }

    public void populateHell()
    {
        createBounds(true);
    }

    public void populateHeaven()
    {
        createBounds(false);
    }

    public void createBounds(boolean isHell)
    {
        if(isHell)
        {
            for (int i = 0; i < LEVEL_WIDTH; i+= Block.WIDTH * 2) {
                hellTerrainObjects.add(new Block(i, 0));
                hellTerrainObjects.add(new Block(i, LEVEL_HEIGHT));
            }
            for (int i = (int) Block.HEIGHT; i < LEVEL_HEIGHT; i += Block.HEIGHT * 2) {
                hellTerrainObjects.add(new Block(0, i));
                hellTerrainObjects.add(new Block(LEVEL_WIDTH, i));
            }
        }
        else
        {
            for (int i = 0; i < LEVEL_WIDTH; i+= Block.WIDTH * 2) {
                heavenTerrainObjects.add(new Block(i, 0));
                heavenTerrainObjects.add(new Block(i, LEVEL_HEIGHT));
            }
            for (int i = (int) Block.HEIGHT; i < LEVEL_HEIGHT; i += Block.HEIGHT * 2) {
                heavenTerrainObjects.add(new Block(0, i));
                heavenTerrainObjects.add(new Block(LEVEL_WIDTH, i));
            }
        }
    }
}
