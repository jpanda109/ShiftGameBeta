package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/14/14.
 * This is the basic building block for terrain
 */
public class Block extends GameObject {

    public static final float WIDTH = .5f;
    public static final float HEIGHT = .5f;

    /**
     * This is a custom type depicting whether the terrain block is of surface or background,
     * e.g. a surface block might contain grass whereas the background block will contain dirt.
     */
    public enum Type {
        SURFACE, BACKGROUND, LAVA
    }

    Type type;

    /**
     * This is a constructor taking in a position
     * @param x component of position
     * @param y component of position
     */
    public Block(float x, float y) {
        super(x, y, WIDTH, HEIGHT);
        this.type = Type.BACKGROUND;
    }
}
