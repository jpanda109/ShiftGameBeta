package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.models.Block;
import com.hwooy.shiftgamebeta.models.GameObject;
import com.hwooy.shiftgamebeta.models.Player;
import com.hwooy.shiftgamebeta.models.TerrainBlock;

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 */
public class ObjectFactory {

    public static final short BIT_TYPE_ONE = 1;  // 0001
    public static final short BIT_TYPE_TWO = 2;  // 0010
    public static final short BIT_TYPE_BOTH = 4; // 0100
    public static final short BIT_PLAYER = 8; // 1000

    TiledMap tiledMap;
    Player player;
    MapLayers layers;
    ArrayList<GameObject> gameObjects;

    public ObjectFactory(int levelNumber) {
        tiledMap = Settings.getInstance().getTiledMap(levelNumber);
        layers = tiledMap.getLayers();
        gameObjects = new ArrayList<GameObject>();
    }

    public World createWorld() {
        World world = new World(new Vector2(0, -10f), false);
        for (MapLayer layer : layers) {
            if (layer.getName().contains("Terrain")) {
                addTerrainFixtures(world, (TiledMapTileLayer) layer);
            } else if (layer.getName().contains("Star")) {

            } else if (layer.getName().contains("Player")) {

            } else if (layer.getName().contains("Portal")) {

            } else if (layer.getName().contains("Lava")) {

            } else if (layer.getName().contains("Crumbling")) {

            }
        }

        return world;
    }

    private void addTerrainFixtures(World world, TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.BLOCK_WIDTH, TerrainBlock.BLOCK_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        for (int row = 0; row < layer.getHeight(); ++row) {
            for (int col = 0; col < layer.getWidth(); ++col) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(col, row);
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
                gameObjects.add(new TerrainBlock(body, Block.BlockType.BOTH));
            }
        }

        polygonShape.dispose();
    }

}
