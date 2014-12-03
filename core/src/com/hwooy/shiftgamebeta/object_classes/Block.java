package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;

/**
 * Created by jason on 12/3/14.
 */
public class Block extends ShiftObject {

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
