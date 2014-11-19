package com.hwooy.shiftgamebeta.levels;

import com.hwooy.shiftgamebeta.models.*;
import com.hwooy.shiftgamebeta.screens.StartScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 11/14/14.
 * base class for Levels from which other levels are derived
 */
public class Level {

    public static final int LEVEL_BUFFER_HEIGHT = 500;
    public static final int LEVEL_BUFFER_WIDTH = 500;

    public static final int LEVEL_HEIGHT = StartScreen.CAM_HEIGHT;
    public static final int LEVEL_WIDTH = StartScreen.CAM_WIDTH;

    //public static final float LEVEL_HEIGHT = StartScreen.CAM_HEIGHT;
    //public static final float LEVEL_WIDTH = StartScreen.CAM_WIDTH;
    public static final float LEVEL_GRAVITY = -10f;

    public final ArrayList<TerrainBlock> hellTerrainObjects;
    public final ArrayList<TerrainBlock> heavenTerrainObjects;
    public final ArrayList<Star> hellStarObjects;
    public final ArrayList<Star> heavenStarObjects;
    public final Portal portal;
    public final Player player;
    public final ArrayList<Platform> platforms;

    /**
     * Default constructor initializing the hellTerrainObjects list
     */
    public Level() {
        this.hellTerrainObjects = new ArrayList<TerrainBlock>();
        this.heavenTerrainObjects = new ArrayList<TerrainBlock>();
        this.hellStarObjects = new ArrayList<Star>();
        this.heavenStarObjects = new ArrayList<Star>();
        portal = new Portal(135, 10);
        player = new Player(5, 10);
        this.platforms = new ArrayList<Platform>();
    }

}
