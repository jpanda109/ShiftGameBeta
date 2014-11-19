package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Anna Hwang on 11/18/2014.
 *
 * Factory for platforms
 */
public class PlatformFactory
{

    public enum Direction {
        VERTICAL, HORIZONTAL
    }

    public int SPEED;
    public Direction dir;
    /**
     * Platform constructor.
     * @param x the bottom-left corner
     * @param y the bottom-left corner
     */
    public Platform()
    {};

    public Platform makePlatform(float x, float y, float width, float height, int speed_, Direction dir_)
    {
        super(x, y, width, height);
        this.SPEED = speed_;
        dir = dir_;
    }

}
