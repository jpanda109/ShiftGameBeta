package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/22/14.
 */
public class CrumblingBlock extends Block {

    public enum State {
        IDLE, CRUMBLING
    }

    public CrumblingBlock(float x, float y) {
        super(x, y);
    }
}
