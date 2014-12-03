package com.hwooy.shiftgamebeta.block_classes;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.object_classes.Block;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/3/14.
 */
public class TerrainBlock extends Block {

    public TerrainBlock(Body body, Block.BlockType type) {
        super(body, Settings.TERRAIN_PATH, type);
        texture = Settings.getInstance().getTexture(Settings.TERRAIN_PATH);
    }
}
