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
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.PlayerTitrisSprite;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class ObjectContactListener implements ContactListener {

    private final String TAG = "Class: ObjectContactListener";
    PlayerTitrisSprite pts;

    public ObjectContactListener(PlayerTitrisSprite pts) {
        this.pts = pts;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "bottom_well" && fixB.getUserData() == "titris") {
            pts.changeToStopped();

        }

        if (fixA.getUserData() == "titris" && fixB.getUserData() == "titris") {
            Gdx.app.log(TAG, "fixA.getUserData: " + fixA.getUserData() + " fixb: " + fixB.getUserData());
            pts.changeToStopped();
        }

        if (fixA.getUserData() == "right_well" && fixB.getUserData() == "titris") {
            pts.isTouchingRightWall(true);
        }

        if (fixA.getUserData() == "side_well" && fixB.getUserData() == "titris") {
            pts.isTouchingLeftWall(true);
        }
        Gdx.app.log(TAG, "fixA.getUserData: " + fixA.getUserData() + " fixb: " + fixB.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "right_well" && fixB.getUserData() == "titris") {
            pts.isTouchingRightWall(false);
        }

        if (fixA.getUserData() == "side_well" && fixB.getUserData() == "titris") {
            pts.isTouchingLeftWall(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
