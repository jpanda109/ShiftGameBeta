package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anna Hwang on 11/18/2014.
 *
 * Factory for platforms
 */
public class PlatformFactory
{

    public PlatformFactory()
    {}

    public static Platform makePlatform(float x, float y, float width, float height, int speed_, Vector2 begin, Vector2 end)
    {
        Platform plat;
        if(y != end.y)
        {
            plat = new VerticalPlatform(x, y, width, height, speed_, begin, end);
        }
        else //Horizontal
        {
            plat = new HorizontalPlatform(x, y, width, height, speed_, begin, end);
        }

        return plat;
    }

}
