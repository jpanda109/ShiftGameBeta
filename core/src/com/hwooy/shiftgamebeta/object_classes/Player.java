package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class Player extends ShiftObject {

    public static final float PLAYER_WIDTH = 1f;
    public static final float PLAYER_HEIGHT = 1f;

    public enum State {
        IDLE, MOVING
    }
    public State state;

    public Player(Body body) {
        super(body, God.PLAYER_PATH, PLAYER_WIDTH, PLAYER_HEIGHT);
        state = State.IDLE;
    }

    @Override
    public void update(float delta) {
        if (Math.abs(body.getLinearVelocity().x) <= .0001f && Math.abs(body.getLinearVelocity().y) <= .0001f) {
            state = State.IDLE;
        } else {
            state = State.MOVING;
        }
    }

}
