package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by jason on 11/14/14.
 * A very basic object that has a position and size (bounds). This is the object from which other objects
 * in the game will be inherited.
 * Useful for factory pattern.
 */
public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    Body body;

    /**
     * constructor for StaticObject
     * @param x left margin
     * @param y bottom margin
     * @param width width of object
     * @param height height of object
     */
    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x + width, y + height);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
}
