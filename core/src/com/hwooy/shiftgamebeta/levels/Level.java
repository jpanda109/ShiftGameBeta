package com.hwooy.shiftgamebeta.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hwooy.shiftgamebeta.models.*;

import java.util.ArrayList;

/**
 * Created by jason on 11/14/14.
 * base class for Levels from which other levels are derived
 */
public class Level {

    public static final String LEVEL_PATH = "android/assets/levels/";

    // layer keys
    public static final String WORLD_BOTH = "Both";
    public static final String WORLD_HELL = "Hell";
    public static final String WORLD_HEAVEN = "Heaven";
    public static final String OBJECT_PORTAL = "Portal";
    public static final String OBJECT_BLOCK = "Block";
    public static final String OBJECT_PLAYER = "Player";
    public static final String OBJECT_LAVA = "LavaBlock";
    public static final String OBJECT_CRUMBLING = "CrumblingBlock";

    public static final int LEVEL_BUFFER_HEIGHT = 500;
    public static final int LEVEL_BUFFER_WIDTH = 500;

    public static final int LEVEL_HEIGHT = 32;
    public static final int LEVEL_WIDTH = 48;

    public static final float LEVEL_GRAVITY = -10f;

    public ArrayList<GameObject> hellTerrainObjects;
    public ArrayList<GameObject> heavenTerrainObjects;
    public ArrayList<Star> hellStarObjects;
    public ArrayList<Star> heavenStarObjects;
    public Portal portal;
    public Player player;
    public ArrayList<Platform> platforms_HELL;
    public ArrayList<Platform> platforms_HEAVEN;

    public TiledMap tiledMap;
    public OrthogonalTiledMapRenderer tiledMapRenderer;

    /**
     * Default constructor initializing the hellTerrainObjects list
     */
    public Level(int levelNumber) {
        portal = new Portal(5, 20);
        player = new Player(5, 10);

        this.hellTerrainObjects = new ArrayList<GameObject>();
        this.heavenTerrainObjects = new ArrayList<GameObject>();

        this.hellStarObjects = new ArrayList<Star>();
        this.heavenStarObjects = new ArrayList<Star>();

        this.platforms_HELL = new ArrayList<Platform>();
        this.platforms_HEAVEN = new ArrayList<Platform>();

        try {
            tiledMap = new TmxMapLoader().load(LEVEL_PATH + "Level" + levelNumber + ".tmx");
        } catch (Exception e) {
            tiledMap = new TmxMapLoader().load(LEVEL_PATH + "Level" + 1 + ".tmx");
        }
        addObjects();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    }

    /**
     * gets objects from TiledMap and adds to proper ArrayList
     */
    private void addObjects() {
        MapLayers mapLayers = tiledMap.getLayers();

        for (MapLayer mapLayer : mapLayers) {
            addObjectToWorld((TiledMapTileLayer) mapLayer);
        }
    }

    private void addObjectToWorld(TiledMapTileLayer mapTileLayer) {
        ArrayList<GameObject> tempArrayList = new ArrayList<GameObject>();
        String layerName = mapTileLayer.getName();
        if (layerName.contains(OBJECT_BLOCK)) {
            addToArrayList(tempArrayList, mapTileLayer, OBJECT_BLOCK);
        } else if (layerName.contains(OBJECT_CRUMBLING)) {
            addToArrayList(tempArrayList, mapTileLayer, OBJECT_CRUMBLING);
        }

        if (layerName.contains(WORLD_BOTH)) {
            hellTerrainObjects.addAll(tempArrayList);
            heavenTerrainObjects.addAll(tempArrayList);
        } else if (layerName.contains(WORLD_HELL)) {
            hellTerrainObjects.addAll(tempArrayList);
        } else if (layerName.contains(WORLD_HEAVEN)) {
            heavenTerrainObjects.addAll(tempArrayList);
        }
    }

    private void addToArrayList(ArrayList<GameObject> arrayList, TiledMapTileLayer mapTileLayer, String objectKey) {
        for (int row = 0; row < mapTileLayer.getHeight(); row++) {
            for (int col = 0; col < mapTileLayer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = mapTileLayer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                if (objectKey.equals(OBJECT_BLOCK)) {
                    arrayList.add(new Block(col, row));
                } else if (objectKey.equals(OBJECT_CRUMBLING)) {
                    arrayList.add(new CrumblingBlock(col, row));
                } else if (objectKey.equals(OBJECT_LAVA)) {
                    arrayList.add(new LavaBlock(col, row));
                } else if (objectKey.equals(OBJECT_PLAYER)) {
                    player = new Player(col, row);
                    arrayList.add(new Player(col, row));
                } else if (objectKey.equals(OBJECT_PORTAL)) {
                    portal = new Portal(col, row);
                    arrayList.add(new Portal(col, row));
                }
            }
        }
    }

}
