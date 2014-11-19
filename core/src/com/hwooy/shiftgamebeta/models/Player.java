package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/14/14.
 * This represents the player object, which is controlled by user input
 */
public class Player extends GameObject {

    public enum State {
        IDLE, MOVING
    }

    public static final float PLAYER_WIDTH = 2;
    public static final float PLAYER_HEIGHT = 2;

    public State state;
    public int stars;
    public float stateTime;

    public Player(float x, float y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        state = State.IDLE;
        stateTime = 0;
    }

}
