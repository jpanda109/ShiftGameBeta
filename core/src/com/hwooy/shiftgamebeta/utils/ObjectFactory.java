package com.hwooy.shiftgamebeta.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.block_classes.CrumblingBlock;
import com.hwooy.shiftgamebeta.block_classes.LavaBlock;
import com.hwooy.shiftgamebeta.block_classes.TerrainBlock;
import com.hwooy.shiftgamebeta.block_classes.Block;
import com.hwooy.shiftgamebeta.object_classes.Player;
import com.hwooy.shiftgamebeta.object_classes.Portal;
import com.hwooy.shiftgamebeta.object_classes.ShiftObject;
import com.hwooy.shiftgamebeta.object_classes.Star;

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 */
public class ObjectFactory {

    public static final short BIT_TYPE_ONE = 1;  // 0001
    public static final short BIT_TYPE_TWO = 2;  // 0010
    public static final short BIT_TYPE_BOTH = BIT_TYPE_ONE | BIT_TYPE_TWO; // 0100
    public static final short BIT_PLAYER = 8; // 1000

    TiledMap tiledMap;
    Player player;
    MapLayers layers;
    World world;
    ArrayList<ShiftObject> gameObjects;

    public ObjectFactory(int levelNumber, World world) {
        this.world = world;
        tiledMap = God.getInstance().getTiledMap(levelNumber);
        layers = tiledMap.getLayers();
        gameObjects = new ArrayList<ShiftObject>();
        createWorld();
    }

    private void createWorld() {
        for (MapLayer mapLayer : layers) {
            TiledMapTileLayer layer = (TiledMapTileLayer) mapLayer;
            if (layer.getName().contains("Terrain")) {
                addTerrainFixtures(layer);
            } else if (layer.getName().contains("Star")) {
                addStarFixtures(layer);
            } else if (layer.getName().contains("Player")) {
                addPlayerFixture(layer);
            } else if (layer.getName().contains("Portal")) {
                addPortalFixture(layer);
            } else if (layer.getName().contains("Lava")) {
            } else if (layer.getName().contains("Crumbling")) {
                System.out.println("hi");
                addCrumblingFixtures(layer);

            }
        }
    }

    private void addTerrainFixtures(TiledMapTileLayer layer) {
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

    private void addStarFixtures(TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Star.STAR_WIDTH, Star.STAR_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        fixtureDef.isSensor = true;

        for (int row = 0; row < layer.getHeight(); ++row) {
            for (int col = 0; col < layer.getWidth(); ++col) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(col, row);
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
                gameObjects.add(new Star(body));
            }
        }

        polygonShape.dispose();
    }

    private void addPlayerFixture(TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        fixtureDef.filter.maskBits = BIT_TYPE_ONE;
        fixtureDef.filter.categoryBits = BIT_PLAYER;

        for (int row = 0; row < layer.getHeight(); ++row) {
            for (int col = 0; col < layer.getWidth(); ++col) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(col, row);
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
                player = new Player(body);
                gameObjects.add(player);
                polygonShape.dispose();
                return;
            }
        }

        polygonShape.dispose();
    }

    private void addPortalFixture(TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        fixtureDef.filter.categoryBits = BIT_TYPE_BOTH;

        for (int row = 0; row < layer.getHeight(); ++row) {
            for (int col = 0; col < layer.getWidth(); ++col) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(col, row);
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef).setUserData("Portal");
                gameObjects.add(new Portal(body));
                polygonShape.dispose();
                return;
            }
        }

        polygonShape.dispose();
    }

    private void addCrumblingFixtures(TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        polygonShape.setAsBox(Block.BLOCK_WIDTH, Block.BLOCK_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        fixtureDef.filter.categoryBits = BIT_TYPE_BOTH;

        for (int row = 0; row < layer.getHeight(); ++row) {
            for (int col = 0; col < layer.getWidth(); ++col) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(col, row);
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef).setUserData("Crumbling");
                gameObjects.add(new CrumblingBlock(body, Block.BlockType.BOTH));
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

    public Player getPlayer() {
        return player;
    }

}
