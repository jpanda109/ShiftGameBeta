package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class Player extends ShiftObject {

    public static final float PLAYER_WIDTH = 2f / 2;
    public static final float PLAYER_HEIGHT = 2f / 2;

    public final Texture playerHellTexture;
    public final Texture playerHeavenTexture;

    public enum State {
        IDLE, MOVING
    }
    public State state;

    public Player(Body body) {
        super(body, God.PLAYER_HEAVEN_PATH, PLAYER_WIDTH, PLAYER_HEIGHT);
        playerHellTexture = God.getInstance().getTexture(God.PLAYER_HELL_PATH);
        playerHeavenTexture = God.getInstance().getTexture(God.PLAYER_HEAVEN_PATH);
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

    public void playerShift() {
        texture = (texture == playerHellTexture ? playerHeavenTexture : playerHellTexture);
    }

}
