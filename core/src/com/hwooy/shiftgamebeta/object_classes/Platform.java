package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
* Created by Anna Hwang on 12/3/2014.
*/
public class Platform extends ShiftObject {

    public float SPEED;
    public float PLATFORM_WIDTH;
    public float PLATFORM_HEIGHT;

    public Vector2 v1;
    public Vector2 v2;

    public Platform(Body body, String texturePath, float width, float height, float plat_w, float plat_h, float speed, Vector2 v1, Vector2 v2)
    {
        super(body, texturePath, width, height);
        this.SPEED = speed;
        this.v1 = v1;
        this.v2 = v2;
        this.PLATFORM_HEIGHT = plat_h;
        this.PLATFORM_WIDTH = plat_w;
    }

    public void update()
    {}
    public void setInitialSpeed()
    {}
}
