package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by jason on 12/3/14.
 */
public class Block extends GameObject {

    public static final float BLOCK_WIDTH = 1f;
    public static final float BLOCK_HEIGHT = 1f;

    // collision type of block
    public enum BlockType {
        ONE, TWO, BOTH
    }
    public BlockType type;

    public Block(Body body, String texturePath, BlockType type) {
        super(body, texturePath, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.type = type;
    }
}
