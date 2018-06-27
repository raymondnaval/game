/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.shapes.TShape;
import com.raymondn.game.sprites.shapes.TShapeStraightThree;
import com.raymondn.game.states.PlayState;
import java.text.DecimalFormat;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerTitrisSprite2 {

    private TShape activeTitris;
    private boolean paused = false;
    private boolean doneDescending = false;
    private PlayState ps;
    private TShape[] tPieces;
    private double oneSecond = 1000000000.0;
    private float DESCENT_SPEED = (float) oneSecond * 3 / 4;
    private String TAG = "Class: PlayerTitrisSprite2";
    private int verticalIncrement = 32;

    public PlayerTitrisSprite2(PlayState state) {
        ps = state;

        tPieces = new TShape[6];
        tPieces[0] = new TShapeStraightThree(ps);
//        tPieces[1] = new TShapeC(body, ps);
//        tPieces[2] = new TShapeBigE(body, ps);
//        tPieces[3] = new TShapePlus(body, ps);
//        tPieces[4] = new TShapeSingle(body, ps);
//        tPieces[5] = new TShapeT(body, ps);

        activeTitris = generateRandomTitris();
    }

    private TShape generateRandomTitris() {
        return tPieces[0];
    }

    public void accelerateDescent(boolean isAccelerating) {

    }

    public void rotate() {

    }

    public void pause(boolean isPaused) {

    }

    public void setX(boolean movingRight) {

    }

    public void update(float dt) {

        // Control speed of sprite's descent.
        long start = System.nanoTime();
        long end = System.nanoTime();
        while (end - start < DESCENT_SPEED) {
            end = System.nanoTime();
        }

        // Set the descent of each sprite.
        Vector2[] pos = new Vector2[activeTitris.getPositions().length];
        boolean stopDescent = false;
        for (int i = 0; i < activeTitris.getPositions().length; i++) {
            pos[i] = new Vector2();
            pos[i].x = activeTitris.getPositions()[i].x;

            // If lowest point of sprite touches the base of the well, stop the
            // sprite.
            if (activeTitris.lowestPoint() == ps.getVerticalIncrements()[0]) {
                pos[i].y = ps.getVerticalIncrements()[0];
                stopDescent = true;

            } else {
//                pos[i].y = activeTitris.getPositions()[i].y - (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
                pos[i].y = ps.getVerticalIncrements()[verticalIncrement];
            }
        }

        activeTitris.setPositions(pos);
//        Gdx.app.log(TAG, "update -- pos: " + pos[0].toString());

        // Activate static body when shape stops moving.
        if (stopDescent) {

            // Iterate through each row.
//            for (int row = 0; row < PlayState.WELL_SPACES.length; row++) {
            for (int row = 0; row < 1; row++) {
                
                // Iterate through each column.
                for (int col = 0; col < PlayState.WELL_SPACES[0].length; col++) {
                    
                    // Iterate through each sprite position.
                    for (int p = 0; p < activeTitris.getPositions().length; p++) {
                        
                        

                        // If the y coordinate of the shape match the y 
                        // coordinate of the well space, activate physics square.
                        
                        // Round position of descending sprite value to 2 
                        // decimal places.
                        DecimalFormat df = new DecimalFormat("###.##");
                        float activeTitrisX = Float.valueOf(df.format(activeTitris.getPositions()[p].x));
                        
                        Gdx.app.log(TAG, "pos: " + p + 
                                " \nactiveTitris.getPositions()[p].x: " 
                                + activeTitrisX
                            + " \nPlayState.WELL_SPACES[row][col].getXY().x: " 
                                + PlayState.WELL_SPACES[row][col].getXY().x);
                        
                        if (PlayState.WELL_SPACES[row][col].getXY().y
                                == activeTitris.getPositions()[p].y
                                && PlayState.WELL_SPACES[row][col].getXY().x
                                == activeTitrisX) {
                            PlayState.WELL_SPACES[row][p].activatePhysicsSquare(true);
                        }
                        
                    }

                }
            }
            doneDescending = true;
        } else {
            verticalIncrement--;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public float getTitrisWidth() {
        return activeTitris.getWidth();
    }

    public float getTitrisHeight() {
        return activeTitris.getHeight();
    }

    public boolean isDoneDescending() {
        return doneDescending;
    }

    public Vector2[] getXY() {
        return activeTitris.getPositions();
    }

    public Sprite[] getTitrisPiece() {
        return activeTitris.getFullShape();
    }

    public float getOriginX() {
        return activeTitris.getOriginX();
    }

    public float getOriginY() {
        return activeTitris.getOriginY();
    }

    public float getScaleX() {
        return activeTitris.getScaleX();
    }

    public float getScaleY() {
        return activeTitris.getScaleY();
    }

    public float getRotation() {
        return activeTitris.getRotation();
    }
}
