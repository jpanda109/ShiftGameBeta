package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.levels.Level;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import java.util.ArrayList;

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
        makeStarFixtures();
        makePlayerFixture();
        makePortalFixture();
        makePlatformFixture();
    }

    /**
     * creates terrain for both worlds
     */
    private void makeTerrainFixtures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.WIDTH, TerrainBlock.HEIGHT);
        fixtureDef.shape = polygonShape;

        for (GameObject object : level.heavenTerrainObjects) {
            bodyDef.position.set(object.position);
            Body body = heaven.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setFriction(0);
            object.setBody(body);
        }

        for (GameObject object : level.hellTerrainObjects) {
            bodyDef.position.set(object.position);
            Body body = hell.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            object.setBody(body);
        }
    }

    /**
     * creates stars for both worlds
     */
    private void makeStarFixtures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.WIDTH, TerrainBlock.HEIGHT);
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
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
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
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        fixtureDef.shape = polygonShape;

        int i = 0;
        for (Platform plat : level.platforms)
        {
            bodyDef.position.set(plat.position);
            polygonShape.setAsBox(plat.PLATFORM_WIDTH, plat.PLATFORM_HEIGHT);
            Body body = hell.createBody(bodyDef);
            fixtureDef.friction = 1.0f;
            fixtureDef.isSensor = false;
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData("Platform" + Integer.toString(i));


            plat.setBody(body);
            plat.getBody().setLinearVelocity(0, plat.SPEED);
            ++i;
        }

    }
}
