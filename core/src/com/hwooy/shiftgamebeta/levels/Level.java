package com.hwooy.shiftgamebeta.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hwooy.shiftgamebeta.models.*;
import com.hwooy.shiftgamebeta.utils.Settings;

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

    public ArrayList<GameObject> gameObjects;
    public ArrayList<Star> hellStarObjects;
    public Portal portal;
    public Player player;
    public ArrayList<Platform> platforms_HELL;

    public TiledMap tiledMap;

    /**
     * Default constructor initializing the gameObjects list
     */
    public Level(int levelNumber) {
        this.gameObjects = new ArrayList<GameObject>();

        this.hellStarObjects = new ArrayList<Star>();

        this.platforms_HELL = new ArrayList<Platform>();

        tiledMap = Settings.getInstance().loadTiledMap(levelNumber);
        addObjects();

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
        addToArrayList(tempArrayList, mapTileLayer);

        if (layerName.contains(WORLD_HELL)) {
            for (GameObject gameObject : tempArrayList) {
                gameObject.collisionType = GameObject.CollisionType.HELL;
            }
        } else if (layerName.contains(WORLD_HEAVEN)) {
            for (GameObject gameObject : tempArrayList) {
                gameObject.collisionType = GameObject.CollisionType.HEAVEN;
            }
        }
        gameObjects.addAll(tempArrayList);
    }

    private void addToArrayList(ArrayList<GameObject> arrayList, TiledMapTileLayer mapTileLayer) {
        for (int row = 0; row < mapTileLayer.getHeight(); row++) {
            for (int col = 0; col < mapTileLayer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = mapTileLayer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                String objectName = mapTileLayer.getName();
                if (objectName.contains(OBJECT_CRUMBLING)) {
                    arrayList.add(new CrumblingBlock(col, row));
                } else if (objectName.contains(OBJECT_LAVA)) {
                    arrayList.add(new LavaBlock(col, row));
                } else if (objectName.contains(OBJECT_PLAYER)) {
                    player = new Player(col, row);
                } else if (objectName.contains(OBJECT_PORTAL)) {
                    portal = new Portal(col, row);
                } else {
                    arrayList.add(new Block(col, row));
                }
            }
        }
    }

}
