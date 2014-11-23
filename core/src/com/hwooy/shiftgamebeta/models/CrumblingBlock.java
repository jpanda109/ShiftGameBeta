package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/22/14.
 */
public class CrumblingBlock extends GameObject {

    public enum State {
        IDLE, CRUMBLING
    }

    public CrumblingBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
