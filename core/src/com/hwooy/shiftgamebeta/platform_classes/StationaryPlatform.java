package com.hwooy.shiftgamebeta.platform_classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.object_classes.Platform;

/**
 * Created by Anna Hwang on 12/3/2014.
 */
public class StationaryPlatform  extends Platform {

    public StationaryPlatform(Body body, String texturePath, float width, float height, float plat_w, float plat_h)
    {
        super(body, texturePath, width, height, plat_w, plat_h, 0, new Vector2(), new Vector2());
    }

    public void setInitialSpeed()
    {
        //No-op
    }

    public void update()
    {
        //No-op
    }
}
