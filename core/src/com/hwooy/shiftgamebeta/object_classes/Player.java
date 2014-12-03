package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/3/14.
 */
public class Player extends ShiftObject {

    public static final float PLAYER_WIDTH = 2f;
    public static final float PLAYER_HEIGHT = 2f;

    public enum State {
        IDLE, MOVING
    }
    public State state;

    public Player(Body body) {
        super(body, Settings.PLAYER_PATH, PLAYER_WIDTH, PLAYER_HEIGHT);
        state = State.IDLE;
    }
}
