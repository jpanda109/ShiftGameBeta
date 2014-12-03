package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/3/14.
 */
public class Star extends GameObject {

    public static final float STAR_WIDTH = 1f;
    public static final float STAR_HEIGHT = 1f;

    public Star(Body body) {
        super(body, Settings.STAR_PATH, STAR_WIDTH, STAR_HEIGHT);
    }
}
