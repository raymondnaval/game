/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.raymondn.game.sprites.PlayerShooterSprite;
import com.raymondn.game.sprites.PlayerTitrisSprite2;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class KeyboardController extends InputAdapter {
    
    private String TAG = "Class -- GameController";
    private PlayerShooterSprite pss;
    private PlayerTitrisSprite2 pts;
    
    public KeyboardController(PlayerShooterSprite pss, PlayerTitrisSprite2 pts) {
        this.pss = pss;
        this.pts = pts;
    }
    
    public KeyboardController(PlayerTitrisSprite2 pts, PlayerShooterSprite pss) {
        this.pss = pss;
        this.pts = pts;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.F) {
            pss.shoot();
        }
        return false;
    }
}
