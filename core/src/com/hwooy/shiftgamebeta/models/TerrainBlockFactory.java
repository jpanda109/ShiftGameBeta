package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anna Hwang on 11/19/2014.
 */
public class TerrainBlockFactory {

    public TerrainBlockFactory()
    {}

    public static TerrainBlock TerrainBlockFactory(float x, float y, TerrainBlock.Type type)
    {
        TerrainBlock block;
        if(type == TerrainBlock.Type.BACKGROUND)
        {
            block = new TerrainBlock(x, y);
        }
        else if(type == TerrainBlock.Type.SURFACE)
        {
            block = new SurfaceBlock(x, y);
        }

        else if(type == TerrainBlock.Type.LAVA)
        {
            block = new LavaBlock(x, y);
        }

        else{
            block = new TerrainBlock(x, y);
        }

        return block;
    }
}
