package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hwooy.shiftgamebeta.utils.Settings;

/**
 * Created by jason on 12/2/14.
 */
public class GameObject {

    public Body body;
    public Texture texture;
    public float width;
    public float height;

    public GameObject(Body body, String texturePath, float width, float height) {
        this.body = body;
        this.texture = Settings.getInstance().getTexture(texturePath);
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(texture,
                (body.getPosition().x - width) * 10,
                (body.getPosition().y - height)* 10,
                width * 20,
                height * 20);
        spriteBatch.end();
    }

    public void dispose() {
        texture.dispose();
    }

}
