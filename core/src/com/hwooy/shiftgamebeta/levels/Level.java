package com.hwooy.shiftgamebeta.levels;

import com.hwooy.shiftgamebeta.models.*;
import com.hwooy.shiftgamebeta.screens.StartScreen;

import java.util.ArrayList;

/**
 * Created by jason on 11/14/14.
 * base class for Levels from which other levels are derived
 */
public class Level {

    public static final int LEVEL_BUFFER_HEIGHT = 500;
    public static final int LEVEL_BUFFER_WIDTH = 500;

    public static final int LEVEL_HEIGHT = 32;
    public static final int LEVEL_WIDTH = 48;

    //public static final float LEVEL_HEIGHT = StartScreen.CAM_HEIGHT;
    //public static final float LEVEL_WIDTH = StartScreen.CAM_WIDTH;
    public static final float LEVEL_GRAVITY = -10f;

    public final ArrayList<Block> hellTerrainObjects;
    public final ArrayList<Block> heavenTerrainObjects;
    public final ArrayList<Star> hellStarObjects;
    public final ArrayList<Star> heavenStarObjects;
    public final Portal portal;
    public final Player player;
    public final ArrayList<Platform> platforms_HELL;
    public final ArrayList<Platform> platforms_HEAVEN;

    /**
     * Default constructor initializing the hellTerrainObjects list
     */
    public Level() {
        this.hellTerrainObjects = new ArrayList<Block>();
        this.heavenTerrainObjects = new ArrayList<Block>();

        this.hellStarObjects = new ArrayList<Star>();
        this.heavenStarObjects = new ArrayList<Star>();

        portal = new Portal(135, 10);
        player = new Player(5, 10);

        this.platforms_HELL = new ArrayList<Platform>();
        this.platforms_HEAVEN = new ArrayList<Platform>();
    }

}
