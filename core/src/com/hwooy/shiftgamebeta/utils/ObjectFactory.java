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
    public static final short BIT_TYPE_BOTH = BIT_TYPE_ONE | BIT_TYPE_TWO; // 0011
    public static final short BIT_PLAYER = 8; // 1000

    TiledMap tiledMap;
    Player player;
    MapLayers layers;
    World world;
    ArrayList<ShiftObject> gameObjects;

    public ObjectFactory(TiledMap tiledMap, World world) {
        this.world = world;
        this.tiledMap = tiledMap;
        layers = tiledMap.getLayers();
        gameObjects = new ArrayList<ShiftObject>();
        createWorld();
    }

    private void createWorld() {
        for (MapLayer mapLayer : layers) {
            TiledMapTileLayer layer = (TiledMapTileLayer) mapLayer;
            if (layer.getName().contains("Block")) {
                addBlockFixtures(layer);
            }
            else if (layer.getName().contains("Star")) {
                addStarFixtures(layer);
            }
            else if (layer.getName().contains("Player")) {
                addPlayerFixture(layer);
            }
            else if (layer.getName().contains("Portal")) {
                addPortalFixture(layer);
            }
        }
    }

    private void addBlockFixtures(TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Block.BLOCK_WIDTH, Block.BLOCK_HEIGHT);

        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        Block.BlockType type = Block.BlockType.BOTH;

        if (layer.getName().contains("Both")) {
            fixtureDef.filter.categoryBits = BIT_TYPE_BOTH;
            type = Block.BlockType.BOTH;
        }
        else if (layer.getName().contains("Hell")) {
            fixtureDef.filter.categoryBits = BIT_TYPE_ONE;
            type = Block.BlockType.ONE;
        }
        else if (layer.getName().contains("Heaven")) {
            fixtureDef.filter.categoryBits = BIT_TYPE_TWO;
            type  = Block.BlockType.TWO;
        }

        makeObjects(layer, bodyDef, fixtureDef, "block", type);
        polygonShape.dispose();
    }

    private Block makeBlock(String layerName, Body body, Block.BlockType type) {
        if (layerName.contains("Terrain")) {
            if(type == Block.BlockType.ONE) {
                return new TerrainBlock(body, type, God.TERRAIN_COLOR_1);
            }
            else if(type == Block.BlockType.TWO) {
                return new TerrainBlock(body, type, God.TERRAIN_COLOR_2);
            }
            else {
                return new TerrainBlock(body, type, God.TERRAIN_BOTH);
            }
        } else if (layerName.contains("Lava")) {
            return new LavaBlock(body, type);
        } else if (layerName.contains("Crumbling")) {
            return new CrumblingBlock(body, type);
        } else {
            return new TerrainBlock(body, type, God.TERRAIN_BOTH);
        }
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
        fixtureDef.filter.categoryBits = BIT_TYPE_BOTH;
        fixtureDef.isSensor = true;

        makeObjects(layer, bodyDef, fixtureDef, "star", Block.BlockType.BOTH);
        polygonShape.dispose();
    }

    private void makeObjects(TiledMapTileLayer layer, BodyDef bodyDef, FixtureDef fixtureDef, String obj, Block.BlockType type) {
        for (int col = 0; col < layer.getHeight(); ++col) {
            for (int row = 0; row < layer.getWidth(); ++row) {
                TiledMapTileLayer.Cell cell = layer.getCell(row, col);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                bodyDef.position.set(row + Block.BLOCK_WIDTH, col + Block.BLOCK_HEIGHT);
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef).setUserData(layer.getName());

                if(obj.equals("block")) {
                    gameObjects.add(makeBlock(layer.getName(), body, type));
                }
                else if(obj.equals("star")){
                    gameObjects.add(new Star(body));
                }
                else if(obj.equals("player")){
                    player = new Player(body);
                    gameObjects.add(player);
                    return;
                }
                else if(obj.equals("portal")) {
                    body.setTransform(body.getPosition().x + Portal.PORTAL_WIDTH - Block.BLOCK_WIDTH, body.getPosition().y + Portal.PORTAL_HEIGHT - Block.BLOCK_HEIGHT, 0);
                    gameObjects.add(new Portal(body));
                    return;
                }
            }
        }
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

        makeObjects(layer, bodyDef, fixtureDef, "player", Block.BlockType.BOTH);
        polygonShape.dispose();
    }

    private void addPortalFixture(TiledMapTileLayer layer) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        polygonShape.setAsBox(Portal.PORTAL_WIDTH, Portal.PORTAL_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        fixtureDef.filter.categoryBits = BIT_TYPE_BOTH;

        makeObjects(layer, bodyDef, fixtureDef, "portal", Block.BlockType.BOTH);
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
