package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/3/14.
 */
public class Portal extends GameObject {

    public static final float PORTAL_WIDTH = 3f;
    public static final float PORTAL_HEIGHT = 4f;

    public Portal(Body body) {
        super(body, Settings.PORTAL_PATH, PORTAL_WIDTH, PORTAL_HEIGHT);
    }
}
