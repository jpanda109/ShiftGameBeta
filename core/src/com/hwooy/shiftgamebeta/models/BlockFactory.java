package com.hwooy.shiftgamebeta.models;

/**
 * Created by Anna Hwang on 11/19/2014.
 */
public class BlockFactory {

    public BlockFactory()
    {}

    public static Block BlockFactory(float x, float y, Block.Type type)
    {
        Block block;
        if(type == Block.Type.BACKGROUND)
        {
            block = new Block(x, y);
        }
        else if(type == Block.Type.SURFACE)
        {
            block = new SurfaceBlock(x, y);
        }

        else if(type == Block.Type.LAVA)
        {
            block = new LavaBlock(x, y);
        }

        else{
            block = new Block(x, y);
        }

        return block;
    }
}
