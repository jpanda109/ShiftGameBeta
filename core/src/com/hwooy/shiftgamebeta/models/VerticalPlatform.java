package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anna Hwang on 11/18/2014.
 */
public class VerticalPlatform extends Platform {

    public enum Direction {
        UP, DOWN
    }

    public Direction dir;

    VerticalPlatform(float x, float y, float width, float height, int speed, Vector2 bottom, Vector2 top)
    {
        super(x, y, width, height, speed, bottom, top);
        dir = Direction.UP;
    }

    public void update()
    {
        Vector2 pos = this.body.getPosition();
        if(this.begin.y > pos.y && dir == Direction.DOWN)
        {
            dir = Direction.UP;
            this.getBody().setLinearVelocity(0, this.SPEED);
        }
        else if(this.end.y < pos.y && dir == Direction.UP)
        {
            dir = Direction.DOWN;
            this.getBody().setLinearVelocity(0, -1 * this.SPEED);
        }
    }
}
