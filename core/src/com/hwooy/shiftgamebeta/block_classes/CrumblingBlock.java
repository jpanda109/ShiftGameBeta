package com.hwooy.shiftgamebeta.block_classes;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class CrumblingBlock extends Block {

    public enum State {
        IDLE, CRUMBLING, DEAD
    }
    public State state;
    public float crumblingTime;

    public CrumblingBlock(Body body, Block.BlockType type) {
        super(body, God.TERRAIN_PATH, type);
        state = State.IDLE;
        crumblingTime = 0;
    }

    @Override
    public void update(float delta) {
        if (state == State.CRUMBLING) {
            crumblingTime += delta;
        }
        if (crumblingTime > 1f) {
            state = State.DEAD;
        }
    }
}
