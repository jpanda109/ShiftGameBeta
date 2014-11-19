package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anna Hwang on 11/18/2014.
 */
public class HorizontalPlatform extends Platform
{
    public enum Direction{
        LEFT, RIGHT
    }

    public Direction dir;

    HorizontalPlatform(float x, float y, float width, float height, float speed, Vector2 begin, Vector2 end)
    {
        super(x, y, width, height, speed, begin, end);
        dir = Direction.LEFT;
    }

    public void setInitialSpeed()
    {
        this.getBody().setLinearVelocity(this.SPEED, 0);
    }

    public void update()
    {
        Vector2 pos = this.body.getPosition();
        if(this.begin.x > pos.x)
        {
            this.getBody().setLinearVelocity(this.SPEED, 0);
        }
        if(this.end.x < pos.x)
        {
            this.getBody().setLinearVelocity(-1 * this.SPEED, 0);
        }

    }
}
