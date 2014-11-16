package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by jason on 11/16/14.
 * contact listener
 */
public class PlayerContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA == null || fixtureB == null) {
            return;
        }
        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("Portal")) {
            System.out.println("contact");
        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("Portal")) {
            System.out.println("contact");
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
}
