package com.hwooy.shiftgamebeta.block_classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class LavaBlock extends Block {

    public LavaBlock(Body body, BlockType type) {
        super(body, God.TERRAIN_PATH, type);
    }
}