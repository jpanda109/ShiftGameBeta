package com.hwooy.shiftgamebeta.models;

/**
 * Created by jason on 11/14/14.
 * This is the basic building block for terrain
 */
public class TerrainBlock extends GameObject {

    public static final float WIDTH = 1;
    public static final float HEIGHT = 1;

    /**
     * This is a custom type depicting whether the terrain block is of surface or background,
     * e.g. a surface block might contain grass whereas the background block will contain dirt.
     */
    public enum Type {
        SURFACE, BACKGROUND
    }

    Type type;

    /**
     * This is a constructor taking in a position
     * @param x component of position
     * @param y component of position
     */
    public TerrainBlock(float x, float y) {
        super(x, y, WIDTH, HEIGHT);
        this.type = Type.BACKGROUND;
    }

    /**
     * constructor taking in a position and terrain type
     * @param x component of position
     * @param y component of position
     * @param type of terrain
     */
    public TerrainBlock(float x, float y, Type type) {
        super(x, y, WIDTH, HEIGHT);
        this.type = type;
    }
}
