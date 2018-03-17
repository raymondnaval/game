/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author raymondnaval
 */
public class UserSprite {

    private Rectangle bounds;
    private Vector2 position, velocity;
    private Animation userAnimation;
    private Texture texture;
    private boolean enemyColliding = false, staticObjectColliding = false;
    public static final int MOVE = 10;

    public UserSprite(String userSprite, int x, int y) {
        position = new Vector2(x, y);
        texture = new Texture(userSprite);
        // userAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        velocity = new Vector2();
    }

    // Change to updateX and create updateY.
    public void update(float deltaTime) {
//        userAnimation.update(deltaTime);
        velocity.scl(deltaTime);
        position.x += MOVE;
        position.add(position.x, position.y);
        System.out.println("move sprite");
    }
    
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }
    
    public float getX() {
        return position.x;
    }
    
    public float getY() {
        return position.y;
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    public void dispose() {
        texture.dispose();
    }
}
