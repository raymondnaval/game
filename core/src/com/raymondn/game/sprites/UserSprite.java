/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private float moveSpeed = 1.0f;
    public static final float DEFAULT_SPEED = 1.0f;
    public static final int SPACE_IN_FRONT = 20;

    public UserSprite(String userSprite, int x, int y) {
        position = new Vector2(x, y);
        texture = new Texture(userSprite);
        // userAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        velocity = new Vector2();
        
    }
    
    public void setMoveSpeed(float move) {
        moveSpeed = move;
    }

    public void updateX(float deltaTime, boolean moveRight) {
//        userAnimation.update(deltaTime);
        velocity.scl(deltaTime);
        if (moveRight) {
            position.x += moveSpeed;
        } else {
            position.x -= moveSpeed;
        }
        position.set(position.x, position.y);
        System.out.println("move sprite: " + position.x);
    }

    public void updateY(float deltaTime) {
    }

    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    public float getX() {
        return position.x;
    }
    
    public float getCenterX() {
        return position.x + (texture.getWidth()/2);
    }
    
    public float getEndX() {
        return position.x + texture.getWidth();
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
