package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.God;

/**
 * Created by jason on 12/2/14.
 */
public class ShiftObject {

    public Body body;
    public Texture texture;
    public float width;
    public float height;

    public float multiplierX;
    public float multiplierY;

    public ShiftObject(Body body, String texturePath, float width, float height) {
        this.body = body;
        this.texture = God.getInstance().getTexture(texturePath);
        this.width = width;
        this.height = height;
        multiplierY = God.camHeight / 40;
        multiplierX = God.camWidth / 60;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture,
                        (body.getPosition().x - width) * multiplierX,
                        (body.getPosition().y - height) * multiplierY,
                        width * multiplierX * 2,
                        height * multiplierY * 2);
    }

    public void update(float delta) {

    }

    public void dispose() {
        texture.dispose();
    }

}
