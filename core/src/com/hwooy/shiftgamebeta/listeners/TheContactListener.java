package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by jason on 11/16/14.
 * contact listener
 */
public class TheContactListener implements ContactListener {

    GameScreen gameScreen;
    public ArrayList<Body> bodiesToRemove;

    public TheContactListener(GameScreen gameScreen) {
        super();
        this.gameScreen = gameScreen;
        bodiesToRemove = new ArrayList<Body>();
    }

    /**
     * called whenever two fixtures come into contact with eachother
     * @param contact objects created that contains information about the two fixtures in collision
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // make sure you don't have null pointer exceptions when handling fixtures
        if (fixtureA == null || fixtureB == null) {
            return;
        }

        // If something (player) comes into collision with portal, call portal contacted
        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("Portal")) {
            portalContacted();

        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("Portal")) {
            portalContacted();
        }

        // If something (player) comes into collision with a platform, call portal contacted
        if (fixtureA.getUserData() != null && fixtureA.getUserData().toString().contains("Platform")) {

        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().toString().contains("Portal")) {
        }

        // Player contact with lava
        if (fixtureA.getUserData() != null && fixtureA.getUserData().toString().contains("Lava")) {
            gameScreen.restartLevel();

        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().toString().contains("Lava")) {
            gameScreen.restartLevel();
        }


        // Player contact with crumbling block
        if (fixtureA.getUserData() != null && fixtureA.getUserData().toString().contains("Crumbling")) {
            bodiesToRemove.add(fixtureA.getBody());

        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().toString().contains("Crumbling")) {
            bodiesToRemove.add(fixtureB.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    /**
     * tells gameScreen to go to the next level upon collision as defined in beginContact
     */
    private void portalContacted() {
        gameScreen.nextLevel();
    }
}
