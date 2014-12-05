package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/3/14.
 */
public class Star extends ShiftObject {

    public static final float STAR_WIDTH = .5f / 2;
    public static final float STAR_HEIGHT = .5f / 2;

    public Star(Body body) {
        super(body, God.STAR_PATH, STAR_WIDTH, STAR_HEIGHT);
    }
}
