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

    VerticalPlatform(float x, float y, float width, float height, float speed, Vector2 bottom, Vector2 top)
    {
        super(x, y, width, height, speed, bottom, top);
        dir = Direction.UP;
    }

    public void setInitialSpeed()
    {
        this.getBody().setLinearVelocity(0, this.SPEED);
    }

    public void update()
    {
        //dis da position of da body man u understand rite
        Vector2 pos = this.body.getPosition();

        //ok begin is the bottom most coordinates it could go to
        if(this.begin.y > pos.y)
        {
            this.getBody().setLinearVelocity(0, this.SPEED);
        }

        //end is the top most coordinate it could go to
        if(this.end.y < pos.y)
        {
            this.getBody().setLinearVelocity(0, -1 * this.SPEED);
        }
    }
}
