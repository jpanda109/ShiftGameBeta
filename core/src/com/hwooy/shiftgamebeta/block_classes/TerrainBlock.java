package com.hwooy.shiftgamebeta.block_classes;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class TerrainBlock extends Block {

    public TerrainBlock(Body body, Block.BlockType type) {
        super(body, God.TERRAIN_PATH, type);
        texture = God.getInstance().getTexture(God.TERRAIN_PATH);
    }
}
