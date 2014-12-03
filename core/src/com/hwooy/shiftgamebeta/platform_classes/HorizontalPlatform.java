package com.hwooy.shiftgamebeta.platform_classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.object_classes.Platform;

/**
 * Created by Anna Hwang on 12/3/2014.
 */
public class HorizontalPlatform extends Platform {

    public HorizontalPlatform(Body body, String texturePath, float width, float height, float plat_w, float plat_h, float speed, Vector2 v1, Vector2 v2)
    {
        super(body, texturePath, width, height, plat_w, plat_h, speed, v1, v2);
    }

    public void setInitialSpeed()
    {
        this.body.setLinearVelocity(this.SPEED, 0);
    }

    public void update()
    {
        Vector2 pos = this.body.getPosition();
        if(this.v1.x > pos.x)
        {
            this.body.setLinearVelocity(this.SPEED, 0);
        }
        if(this.v2.x < pos.x)
        {
            this.body.setLinearVelocity(-1*this.SPEED, 0);
        }
    }
}
