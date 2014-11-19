package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anna Hwang on 11/18/2014.
 */
public class Platform extends GameObject {

    public int SPEED;
    public float PLATFORM_WIDTH;
    public float PLATFORM_HEIGHT;
    Vector2 end, begin;

    Platform(float x, float y, float width, float height, int speed, Vector2 begin, Vector2 end)
    {
        super(x, y, width, height);
        this.SPEED = speed;
        this.end = end;
        this.begin = begin;
        this.PLATFORM_WIDTH = width;
        this.PLATFORM_HEIGHT = height;
    }

    public void update()
    {}
}
