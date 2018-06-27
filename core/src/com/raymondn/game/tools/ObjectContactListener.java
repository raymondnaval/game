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
import com.raymondn.game.sprites.PlayerTitrisSprite2;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class ObjectContactListener implements ContactListener {

    private final String TAG = "Class: ObjectContactListener";
    PlayerTitrisSprite2 pts;

    public ObjectContactListener(PlayerTitrisSprite2 pts) {
        this.pts = pts;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "bottom_edge" && fixB.getUserData() == "bottom_well") {
//            pts.changeToStopped();
        }
        
        if (fixA.getUserData() == "bottom_edge" && fixB.getUserData() == "titris") {
//            pts.changeToStopped();
        }

        if (fixA.getUserData() == "titris" && fixB.getUserData() == "titris") {
//            Gdx.app.log(TAG, "fixA.getUserData: " + fixA.getUserData() + " fixb: " + fixB.getUserData());
//            pts.changeToStopped();
        }

        if (fixA.getUserData() == "right_well" && fixB.getUserData() == "titris") {
//            pts.isTouchingRightWall(true);
        }

        if (fixA.getUserData() == "side_well" && fixB.getUserData() == "titris") {
//            pts.isTouchingLeftWall(true);
        }
        Gdx.app.log(TAG, "fixA.getUserData: " + fixA.getUserData() + " fixb: " + fixB.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "right_well" && fixB.getUserData() == "titris") {
//            pts.isTouchingRightWall(false);
        }

        if (fixA.getUserData() == "side_well" && fixB.getUserData() == "titris") {
            Gdx.app.log(TAG, "not touching left wall");
//            pts.isTouchingLeftWall(false);
        }
        Gdx.app.log(TAG, "end contact -- fixA.getUserData: " + fixA.getUserData() + " fixb: " + fixB.getUserData());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
