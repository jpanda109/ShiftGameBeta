package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anna Hwang on 11/18/2014.
 */
public class VerticalPlatform extends Platform {

    VerticalPlatform(float x, float y, float width, float height, int speed, Vector2 begin, Vector2 end)
    {
        super(x, y, width, height, speed, begin, end);
    }

//    void update()
//    {
//        this.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
//    }
}
