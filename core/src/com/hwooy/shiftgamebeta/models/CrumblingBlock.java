package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/22/14.
 * A block that crumbles, or self destructs after a short amount of time after being contacted
 */
public class CrumblingBlock extends Block {

    public static final float CRUMBLING_FINISHED = 1f;

    public enum State {
        IDLE, CRUMBLING
    }
    public State state;

    public int crumblingStateTime;

    public CrumblingBlock(float x, float y) {
        super(x, y);
        state = State.CRUMBLING;
        crumblingStateTime = 0;
    }

    public void update(float delta) {
        if (state == State.CRUMBLING) {
            crumblingStateTime += delta;
            if (crumblingStateTime >= CRUMBLING_FINISHED) {
                body.getWorld().destroyBody(body);
            }
        }
    }

}
