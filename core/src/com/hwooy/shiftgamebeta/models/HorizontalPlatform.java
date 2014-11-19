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

    public void update()
    {
        Vector2 pos = this.body.getPosition();
        if(this.begin.x > pos.x && dir == Direction.LEFT)
        {
            dir = Direction.RIGHT;
            this.getBody().setLinearVelocity(this.SPEED, 0);
        }
        else if(this.end.x < pos.x && dir == Direction.RIGHT)
        {
            dir = Direction.LEFT;
            this.getBody().setLinearVelocity(-1 * this.SPEED, 0);
        }
    }
}
