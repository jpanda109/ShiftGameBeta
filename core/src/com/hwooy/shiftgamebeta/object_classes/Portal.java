package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class Portal extends ShiftObject {

    public static final float PORTAL_WIDTH = 2f / 2;
    public static final float PORTAL_HEIGHT = 4f / 2;

    public Portal(Body body) {
        super(body, God.PORTAL_PATH, PORTAL_WIDTH, PORTAL_HEIGHT);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {}
}
