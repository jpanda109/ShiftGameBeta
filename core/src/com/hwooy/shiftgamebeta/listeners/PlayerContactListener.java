package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.screens.GameScreen;

/**
 * Created by jason on 11/16/14.
 * contact listener
 */
public class PlayerContactListener implements ContactListener {

    GameScreen gameScreen;

    public PlayerContactListener(GameScreen gameScreen) {
        super();
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA == null || fixtureB == null) {
            return;
        }
        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("Portal")) {
            portalContacted();
        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("Portal")) {
            portalContacted();
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

    private void portalContacted() {
        gameScreen.nextLevel();
    }
}
