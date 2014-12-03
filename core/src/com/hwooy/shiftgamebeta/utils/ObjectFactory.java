package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.block_classes.TerrainBlock;
import com.hwooy.shiftgamebeta.block_classes.Block;
import com.hwooy.shiftgamebeta.object_classes.Player;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.screens.GameScreen;

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
    World world;
    ArrayList<ShiftObject> gameObjects;

    public ObjectFactory(int levelNumber) {
        tiledMap = Settings.getInstance().getTiledMap(levelNumber);
        layers = tiledMap.getLayers();
        gameObjects = new ArrayList<ShiftObject>();
        createWorld();
    }

    private void createWorld() {
        world = new World(new Vector2(0, -10f), false);
        for (MapLayer layer : layers) {
            if (layer.getName().contains("Block")) {
                addTerrainFixtures(world, (TiledMapTileLayer) layer);
            } else if (layer.getName().contains("Star")) {

            } else if (layer.getName().contains("Player")) {

            } else if (layer.getName().contains("Portal")) {

            } else if (layer.getName().contains("Lava")) {

            } else if (layer.getName().contains("Crumbling")) {

            }
        }
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

        System.out.println("hi");
        for (int row = 0; row < layer.getHeight(); ++row) {
            for (int col = 0; col < layer.getWidth(); ++col) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(col, row);
                Body body = world.createBody(bodyDef);
                if (layer.getName().contains("Both")) {
                    fixtureDef.filter.categoryBits = BIT_TYPE_BOTH;
                    body.createFixture(fixtureDef);
                    gameObjects.add(new TerrainBlock(body, Block.BlockType.BOTH));
                } else if (layer.getName().contains("Hell")) {
                    fixtureDef.filter.categoryBits = BIT_TYPE_ONE;
                    body.createFixture(fixtureDef);
                    gameObjects.add(new TerrainBlock(body, Block.BlockType.ONE));

                } else if (layer.getName().contains("Heaven")) {
                    fixtureDef.filter.categoryBits = BIT_TYPE_TWO;
                    body.createFixture(fixtureDef);
                    gameObjects.add(new TerrainBlock(body, Block.BlockType.TWO));
                }
            }
        }

        polygonShape.dispose();
    }

    public ArrayList<ShiftObject> getGameObjects() {
        return gameObjects;
    }

    public World getWorld() {
        return world;
    }

}
