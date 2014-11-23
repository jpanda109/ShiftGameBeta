package com.hwooy.shiftgamebeta.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    public static final float LEVEL_GRAVITY = -10f;

    public final ArrayList<Block> hellTerrainObjects;
    public final ArrayList<Block> heavenTerrainObjects;
    public final ArrayList<Star> hellStarObjects;
    public final ArrayList<Star> heavenStarObjects;
    public final Portal portal;
    public final Player player;
    public final ArrayList<Platform> platforms_HELL;
    public final ArrayList<Platform> platforms_HEAVEN;

    public TiledMap tiledMap;
    public OrthogonalTiledMapRenderer tiledMapRenderer;

    /**
     * Default constructor initializing the hellTerrainObjects list
     */
    public Level(int levelNumber) {
        portal = new Portal(5, 20);
        player = new Player(5, 10);

        this.hellTerrainObjects = new ArrayList<Block>();
        this.heavenTerrainObjects = new ArrayList<Block>();

        this.hellStarObjects = new ArrayList<Star>();
        this.heavenStarObjects = new ArrayList<Star>();

        this.platforms_HELL = new ArrayList<Platform>();
        this.platforms_HEAVEN = new ArrayList<Platform>();

        try {
            tiledMap = new TmxMapLoader().load("android/assets/levels/EarlyLevel.tmx");
        } catch (Exception e) {
            tiledMap = new TmxMapLoader().load("android/assets/level/EarlyLevel.tnx");
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        TiledMapTileLayer layer1 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
        TiledMapTileLayer layer2 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 2");
        TiledMapTileLayer layer3 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 3");

        for (int row = 0; row < layer1.getHeight(); row++) {
            for (int col = 0; col < layer1.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer1.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                hellTerrainObjects.add(new Block(col, row));
            }
        }

        for (int row = 0; row < layer2.getHeight(); row++) {
            for (int col = 0; col < layer2.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer2.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                hellTerrainObjects.add(new Block(col, row));
            }
        }

        for (int row = 0; row < layer3.getHeight(); row++) {
            for (int col = 0; col < layer3.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer3.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                hellTerrainObjects.add(new Block(col, row));
            }
        }
    }

}
