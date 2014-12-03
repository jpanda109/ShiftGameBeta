package com.hwooy.shiftgamebeta.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.hwooy.shiftgamebeta.screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by jason on 12/3/14.
 */
public class ShiftContactListener implements ContactListener {

    GameScreen gameScreen;
    public ArrayList<Body> deadBodies;

    public ShiftContactListener(GameScreen gameScreen)
    {
        super();
        this.gameScreen = gameScreen;
        deadBodies = new ArrayList<Body>();
    }

    @Override
    public void beginContact(Contact contact)
    {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a == null || b == null) return;

        Object a_data = a.getUserData();
        Object b_data = b.getUserData();

        if(a_data != null)
        {
            if(b_data.toString() == "Portal") portalContact();
            //if(b_data.toString().contains("Lava")) gameScreen.restartLevel();
            if(b_data.toString().contains("Crumbling")) deadBodies.add(b.getBody());
        }
        if(b_data != null)
        {
            if(a_data.toString() == "Portal") portalContact();
            //if(a_data.toString().contains("Lava")) gameScreen.restartLevel();
            if(a_data.toString().contains("Crumbling")) deadBodies.add(a.getBody());
        }
    }

    @Override
    public void endContact(Contact contact)
    {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {}

    private void portalContact()
    {
       //gameScreen.nextLevel();
    }
}