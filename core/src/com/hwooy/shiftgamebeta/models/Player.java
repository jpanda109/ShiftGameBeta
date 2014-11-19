package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/14/14.
 * This represents the player object, which is controlled by user input
 */
public class Player extends GameObject {

    public enum State {
        IDLE, RUNNING, AIRBORNE
    }

    public enum Direction {
        LEFT, RIGHT
    }

    public static final float JUMP_VELOCITY = 10;
    public static final float RUN_VELOCITY = 100;
    public static final float PLAYER_WIDTH = 2;
    public static final float PLAYER_HEIGHT = 2;

    public  State state;
    public  Direction direction;
    public int stars;
    public float stateTime;

    public Player(float x, float y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        state = State.IDLE;
        direction = Direction.RIGHT;
        stateTime = 0;
    }

}
