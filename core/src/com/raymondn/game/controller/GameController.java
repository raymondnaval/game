/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.raymondn.game.sprites.PlayerShooterSprite;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class GameController extends ControllerAdapter {

    private Controller controller;
    private String TAG = "Class -- GameController";
    private PlayerShooterSprite pss;
    private boolean buttonHeldDown = false;

    public GameController(Controller controller, PlayerShooterSprite sprite) {
        this.controller = controller;
        pss = sprite;
    }

    @Override
    public void connected(Controller controller) {
        Gdx.app.log(TAG, "controller connected: " + controller.getName());
    }

    @Override
    public void disconnected(Controller controller) {
        Gdx.app.log(TAG, "controller disconnected: " + controller.getName());
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        Gdx.app.log(TAG, "buttonDown: " + buttonCode);
        switch (buttonCode) {
            case 1:
                pss.jump();
                break;
            case 4:
                pss.moveLeft(true);
                break;
            case 5:
                pss.moveRight(true);
                break;
            default:
                Gdx.app.log(TAG, "button not used: " + buttonCode);
                break;
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
//        Gdx.app.log(TAG, "buttonUp: " + buttonCode);
        switch (buttonCode) {
            case 1:

                break;
            case 4:
                pss.moveLeft(false);
                break;
            case 5:
                pss.moveRight(false);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        Gdx.app.log(TAG, "axisCode: " + axisCode + " value: " + value);
        switch (axisCode) {
            case 2:
                if (value <= -0.99f) {
                    pss.moveLeft(true);
                    Gdx.app.log(TAG, "move left");
                } else if(value >= -0.004f && value < 1.0f) {
                    pss.moveLeft(false);
                    pss.moveRight(false);
                    Gdx.app.log(TAG, "move right: false ");
                } else if(value >= 1.0f) {
                    pss.moveRight(true);
                }
                break;
            case 3:
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        Gdx.app.log(TAG, "povCode: " + povCode + " value: " + value);
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        Gdx.app.log(TAG, "hello");
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        Gdx.app.log(TAG, "hello");
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        Gdx.app.log(TAG, "hello");
        return false;
    }

}
