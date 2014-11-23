package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.levels.Level;

import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by jason on 11/15/14.
 * Factory class to populate box2d hell with necessary blocks
 */
public class FixtureFactory {

    public static final short BIT_TERRAIN = 1;
    public static final short BIT_STAR_PORTAL = 2;
    public static final short BIT_PLAYER = 4;

    Level level;
    World hell;
    World heaven;
    BodyDef bodyDef;
    PolygonShape polygonShape;
    FixtureDef fixtureDef;

    /**
     * Constructor for the FixtureFactory taking in the level to be built and worlds in which to build them
     * @param level
     * @param hell
     * @param heaven
     */
    public FixtureFactory(Level level, World hell, World heaven) {
        this.level = level;
        this.hell = hell;
        this.heaven = heaven;
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
        Body body = heaven.createBody(bodyDef);

        for (GameObject object : level.heavenObjects) {
            if (object.getClass().equals(Block.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Fixture fixture = body.createFixture(fixtureDef);
                object.setBody(body);
            }
        }

        body = hell.createBody(bodyDef);
        for (GameObject object : level.hellObjects) {
            if (object.getClass().equals(Block.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
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

        Body body = heaven.createBody(bodyDef);
        for (GameObject object : level.heavenObjects) {
            if (object.getClass().equals(LavaBlock.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData("Lava");
                object.setBody(body);
            }
        }
        body.setUserData("Lava");

        body = hell.createBody(bodyDef);
        for (GameObject object : level.heavenObjects) {
            if (object.getClass().equals(LavaBlock.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData("Lava");
                object.setBody(body);
            }
        }
        body.setUserData("Lava");
    }

    private void makeCrumblingBlocks() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT);
        fixtureDef.shape = polygonShape;

        Body body = heaven.createBody(bodyDef);
        for (GameObject object : level.heavenObjects) {
            if (object.getClass().equals(CrumblingBlock.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData("Crumbling");
                object.setBody(body);
            }
        }
        body.setUserData("Lava");

        body = hell.createBody(bodyDef);
        for (GameObject object : level.heavenObjects) {
            if (object.getClass().equals(CrumblingBlock.class)) {
                polygonShape.setAsBox(Block.WIDTH, Block.HEIGHT, object.position, 0);
                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData("Crumbling");
                object.setBody(body);
            }
        }
        body.setUserData("Lava");
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

        for (GameObject object : level.hellStarObjects) {
            bodyDef.position.set(object.position);
            Body body = hell.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            object.setBody(body);
        }

        for (GameObject object : level.heavenStarObjects) {
            bodyDef.position.set(object.position);
            Body body = heaven.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            object.setBody(body);
        }
    }

    /**
     * creates the player in hell
     */
    public void makePlayerFixture() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 4f;
        bodyDef.position.set(level.player.position);
        Body body = hell.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("Player");
        level.player.setBody(body);
    }

    /**
     * creates the portal in hell
     */
    public void makePortalFixture() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Portal.PORTAL_WIDTH, Portal.PORTAL_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        bodyDef.position.set(level.portal.position);
        Body body = hell.createBody(bodyDef);
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
        for (Platform plat : level.platforms_HEAVEN)
        {
            bodyDef.position.set(plat.position);
            polygonShape.setAsBox(plat.PLATFORM_WIDTH, plat.PLATFORM_HEIGHT);
            Body body = heaven.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData("HeavenPlatform" + Integer.toString(i));

            plat.setBody(body);
            plat.setInitialSpeed();
            ++i;
        }

        for (Platform plat : level.platforms_HELL)
        {
            bodyDef.position.set(plat.position);
            polygonShape.setAsBox(plat.PLATFORM_WIDTH, plat.PLATFORM_HEIGHT);
            Body body = hell.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData("HellPlatform" + Integer.toString(i));

            plat.setBody(body);
            plat.setInitialSpeed();
            ++i;
        }

    }
}
