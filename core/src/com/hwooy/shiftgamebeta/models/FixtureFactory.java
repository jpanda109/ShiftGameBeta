package com.hwooy.shiftgamebeta.models;

import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.levels.Level;

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

    public FixtureFactory(Level level, World hell, World heaven) {
        this.level = level;
        this.hell = hell;
        this.heaven = heaven;
        bodyDef = new BodyDef();
        polygonShape = new PolygonShape();
        fixtureDef = new FixtureDef();
    }

    public void makeFixtures() {
        makeHellTerrainFixtures();
        makeHeavenTerrainFixtures();
        makeHellStarFixtures();
        makeHeaveStarFixtures();
        makePlayerFixture();
        makePortalFixture();
    }

    /**
     * adds fixtures matching terrain description into the box2d hell
     */
    private void makeHellTerrainFixtures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.WIDTH, TerrainBlock.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_TERRAIN;

        for (GameObject object : level.hellTerrainObjects) {
            bodyDef.position.set(object.position);
            Body body = hell.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setFriction(0);
            object.setBody(body);
        }
    }

    private void makeHeavenTerrainFixtures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.WIDTH, TerrainBlock.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_TERRAIN;

        for (GameObject object : level.heavenTerrainObjects) {
            bodyDef.position.set(object.position);
            Body body = heaven.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setFriction(0);
            object.setBody(body);
        }
    }

    private void makeHellStarFixtures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.WIDTH, TerrainBlock.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_STAR_PORTAL;

        for (GameObject object : level.hellStarObjects) {
            bodyDef.position.set(object.position);
            Body body = hell.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setFriction(0);
            object.setBody(body);
        }
    }

    private void makeHeaveStarFixtures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(TerrainBlock.WIDTH, TerrainBlock.HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_STAR_PORTAL;

        for (GameObject object : level.heavenStarObjects) {
            bodyDef.position.set(object.position);
            Body body = heaven.createBody(bodyDef);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setFriction(0);
            object.setBody(body);
        }
    }

    public void makePlayerFixture() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        polygonShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_TERRAIN;
        bodyDef.position.set(level.player.position);
        Body body = hell.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("Player");
        level.player.setBody(body);
    }

    public void makePortalFixture() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        polygonShape.setAsBox(Portal.PORTAL_WIDTH, Portal.PORTAL_HEIGHT);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_STAR_PORTAL;
        //fixtureDef.filter.maskBits = 0;
        fixtureDef.isSensor = true;
        bodyDef.position.set(level.portal.position);
        Body body = heaven.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("Portal");
        level.portal.setBody(body);
    }
}
