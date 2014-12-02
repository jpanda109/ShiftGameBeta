package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.levels.Level;

import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by jason on 11/15/14.
 * Factory class to populate box2d world with necessary blocks
 */
public class FixtureFactory {

    // note to self
    // bool collide = (filterA.maskBits & filterB.categoryBits != 0 &&
    //                  filterA.categoryBits & filterB.maskBits != 0)
    public static final short BIT_BOTH = 3; // 0000 0000 0000 0011
    public static final short BIT_HELL = 1; // 0000 0000 0000 0001
    public static final short BIT_HEAVEN = 2; // 0000 0000 0000 0010
    public static final short BIT_PLAYER = 4; // 0000 0000 0000 0100

    Level level;
    World world;
    BodyDef bodyDef;
    PolygonShape polygonShape;
    FixtureDef fixtureDef;

    /**
     * Constructor for the FixtureFactory taking in the level to be built and worlds in which to build them
     * @param level
     * @param world
     */
    public FixtureFactory(Level level, World world) {
        this.level = level;
        this.world = world;
        bodyDef = new BodyDef();
        polygonShape = new PolygonShape();
        fixtureDef = new FixtureDef();
    }

    /**
     * aggregate for all the fixtures created
     */
    public void makeFixtures() {
        makeTerrainFixtures();
        makeLavaBlocks();
        makeCrumblingBlocks();
        makeStarFixtures();
        makePlayerFixture();
        makePortalFixture();
        makePlatformFixture();
        polygonShape.dispose();
    }

    /**
     * creates terrain for both worlds
     */
    private void makeTerrainFixtures() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        for (GameObject object : level.gameObjects) {
            if (object.getClass().equals(Block.class)) {
                switch (object.collisionType) {
                    case BOTH:
                        fixtureDef.filter.categoryBits = BIT_BOTH;
                        break;
                    case HELL:
                        fixtureDef.filter.categoryBits = BIT_HELL;
                        break;
                    case HEAVEN:
                        fixtureDef.filter.categoryBits = BIT_HEAVEN;
                        break;
                }
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Body body = world.createBody(bodyDef);
                Fixture fixture = body.createFixture(fixtureDef);
                object.setBody(body);
            }
        }
    }

    private void makeLavaBlocks() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_BOTH;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        for (GameObject object : level.gameObjects) {
            if (object.getClass().equals(LavaBlock.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Body body = world.createBody(bodyDef);
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData("Lava");
                object.setBody(body);
            }
        }
    }

    private void makeCrumblingBlocks() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_BOTH;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        for (GameObject object : level.gameObjects) {
            if (object.getClass().equals(CrumblingBlock.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Body body = world.createBody(bodyDef);
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData("Crumbling");
                object.setBody(body);
            }
        }
    }

    /**
     * creates stars for both worlds
     */
    private void makeStarFixtures() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_BOTH;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        for (GameObject object : level.hellStarObjects) {
            bodyDef.position.set(object.position);
            Body body = world.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            object.setBody(body);
        }
    }

    /**
     * creates the player in world
     */
    public void makePlayerFixture() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_HELL;
        bodyDef.position.set(level.player.position);
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("Player");
        level.player.setBody(body);
    }

    /**
     * creates the portal in world
     */
    public void makePortalFixture() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Portal.PORTAL_WIDTH, Portal.PORTAL_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BIT_BOTH;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        bodyDef.position.set(level.portal.position);
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("Portal");
        level.portal.setBody(body);
    }

    public void makePlatformFixture(){
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 100.0f;
        fixtureDef.isSensor = false;

        int i = 0;

        for (Platform plat : level.platforms_HELL)
        {
            bodyDef.position.set(plat.position);
            polygonShape.setAsBox(plat.PLATFORM_WIDTH, plat.PLATFORM_HEIGHT);
            Body body = world.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData("HellPlatform" + Integer.toString(i));

            plat.setBody(body);
            plat.setInitialSpeed();
            ++i;
        }

    }
}
