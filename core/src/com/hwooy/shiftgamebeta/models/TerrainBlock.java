package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/3/14.
 */
public class TerrainBlock extends Block {

    public TerrainBlock(Body body, Block.BlockType type) {
        super(body, type);
        texture = Settings.getInstance().getTexture(Settings.TERRAIN_PATH);
    }
}
