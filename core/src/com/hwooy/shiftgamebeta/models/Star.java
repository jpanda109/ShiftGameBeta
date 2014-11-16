package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/14/14.
 */
public class Star extends GameObject {

    public static final float STAR_WIDTH = 2;
    public static final float STAR_HEIGHT = 2;

    public Star(int x, int y) {
        super(x, y, STAR_WIDTH, STAR_HEIGHT);
    }
}
