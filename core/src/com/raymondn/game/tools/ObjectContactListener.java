/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class ObjectContactListener implements ContactListener {

    private final String TAG = "Class: ObjectContactListener";
    private boolean stop = false;
    
    public boolean stopDescent() {
        return stop;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        
        if (fixA.getUserData() == "bottom_well" && fixB.getUserData() == "titris") {
            stop = true;
        }
        
        if (fixA.getUserData() == "titris" && fixB.getUserData() == "titris") {
            Fixture activeTitris = fixB;
            Fixture stoppedTitris = fixA;
            stop = true;
            Gdx.app.log(TAG, "fixA.getUserData: " + fixA.getUserData() + " fixb: " + fixB.getUserData());
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
