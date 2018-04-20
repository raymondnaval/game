/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.util.Random;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerTitrisSprite {

    private TextureRegion activeTitris;
    private float scale = .88f / MainGame.PIXELS_PER_METER;
    private float titrisWidth = 16 / MainGame.PIXELS_PER_METER, titrisHeight = 16 / MainGame.PIXELS_PER_METER;
    private TextureRegion[] titrisPieces;
    private float stateTimer;
    private boolean doneDescending = false;
    private Rectangle bounds;
    private Texture titris;
    private Vector2 position;
    private int currentIncrement = 0;
    private float[] increments = new float[10];
    private final String TAG = "Class: PlayerTetrisSprite";

    public PlayerTitrisSprite(PlayState state) {

        // Titris pieces.
        titris = new Texture(Gdx.files.internal("titris_test.png"));
        titrisPieces = new TextureRegion[]{
            new TextureRegion(titris, 0, 0, 16, 16),
            new TextureRegion(titris, 16, 0, 16, 16),
            new TextureRegion(titris, 32, 0, 16, 16)
        };

        activeTitris = generateTitrisPiece();

        position = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER);
        bounds = new Rectangle(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER, activeTitris.getRegionWidth(), activeTitris.getRegionHeight());

        Gdx.app.log("playertetrissprite", "titris width: " + titris.getWidth());

        // Horizontal increments in well.
        increments[0] = MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER;
        increments[1] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10))) / MainGame.PIXELS_PER_METER;
        increments[2] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 2))) / MainGame.PIXELS_PER_METER;
        increments[3] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 3))) / MainGame.PIXELS_PER_METER;
        increments[4] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 4))) / MainGame.PIXELS_PER_METER;
        increments[5] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 5))) / MainGame.PIXELS_PER_METER;
        increments[6] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 6))) / MainGame.PIXELS_PER_METER;
        increments[7] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 7))) / MainGame.PIXELS_PER_METER;
        increments[8] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 8))) / MainGame.PIXELS_PER_METER;
        increments[9] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 9))) / MainGame.PIXELS_PER_METER;
    }

    private TextureRegion generateTitrisPiece() {

        return titrisPieces[new Random().nextInt(titrisPieces.length)];
    }

    public void update(float dt) {

        // If not descending, generate new piece.
        if (position.y > MainGame.WELL_DEPTH - (activeTitris.getRegionHeight() / MainGame.PIXELS_PER_METER)) {
            position.y -= scale;
            bounds.setPosition(position.x, position.y);
        } else {
            
            // Stop the image at the base of the well.
            position.y = MainGame.WELL_DEPTH - (activeTitris.getRegionHeight() / MainGame.PIXELS_PER_METER);
            
            doneDescending = true;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return position.x;
    }

    public float getTitrisWidth() {
        return titrisWidth;
    }

    public float getTitrisHeight() {
        return titrisHeight;
    }

    public void setX(boolean isRight) {

        // If not at the bottom of the well, enable left and right movement.
        if (!doneDescending) {
            if (isRight) {
                if (currentIncrement < 9) {
                    currentIncrement++;
                    position.x = increments[currentIncrement];
                }
            } else {
                if (currentIncrement > 0) {
                    currentIncrement--;
                    position.x = increments[currentIncrement];
                }
            }
            Gdx.app.log("increment", "" + currentIncrement);
        }
    }

    public void accelerateDescent(boolean faster) {
        if (!doneDescending) {
            if (faster) {
                scale = 1.6f / MainGame.PIXELS_PER_METER;
            } else {
                scale = 0.88f / MainGame.PIXELS_PER_METER;
            }
        }
    }

    public boolean isDoneDescending() {
        return doneDescending;
    }

    public float getY() {
        return position.y;
    }

    public TextureRegion getTitrisPiece() {
        return activeTitris;
    }
}
