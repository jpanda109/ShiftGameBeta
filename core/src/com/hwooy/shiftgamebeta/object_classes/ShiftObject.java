package com.hwooy.shiftgamebeta.object_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/2/14.
 */
public class ShiftObject {

    public Body body;
    public Texture texture;
    public float width;
    public float height;

    public ShiftObject(Body body, String texturePath, float width, float height) {
        this.body = body;
        this.texture = Settings.getInstance().getTexture(texturePath);
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture,
                        (body.getPosition().x - width) * 10,
                        (body.getPosition().y - height) * 10);//,
                        //width * 10,
                        //height * 10);
    }

    public void dispose() {
        texture.dispose();
    }

}
