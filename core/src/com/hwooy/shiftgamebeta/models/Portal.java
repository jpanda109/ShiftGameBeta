package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/16/14.
 * This object is the main objective of the player - get here and you advance
 */
public class Portal extends GameObject {

    public static final float PORTAL_WIDTH = 1.25f;
    public static final float PORTAL_HEIGHT = 2.5f;

    public Portal(float x, float y) {
        super(x, y, PORTAL_WIDTH, PORTAL_HEIGHT);
    }
}
