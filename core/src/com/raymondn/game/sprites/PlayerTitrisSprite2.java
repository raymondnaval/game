/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.shapes.TShape;
import com.raymondn.game.sprites.shapes.TShapeC;
import com.raymondn.game.sprites.shapes.TShapeStraightThree;
import com.raymondn.game.states.PlayState;
import java.text.DecimalFormat;
import java.util.Random;

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
    private static float oneSecond = 1f;
    public final static float DESCENT_SPEED = oneSecond / 6;
    private String TAG = "Class: PlayerTitrisSprite2";
    private int verticalIncrement = 31; // One below the top.
    private int horizontalIncrement = 0;
    private float executeSpriteDescent = 0.0f;

    public PlayerTitrisSprite2(PlayState state) {
        ps = state;

        tPieces = new TShape[6];
        tPieces[0] = new TShapeStraightThree(ps);
        tPieces[1] = new TShapeC(ps);
//        tPieces[2] = new TShapeBigE(body, ps);
//        tPieces[3] = new TShapePlus(body, ps);
//        tPieces[4] = new TShapeSingle(body, ps);
//        tPieces[5] = new TShapeT(body, ps);

        activeTitris = generateRandomTitris();
    }

    private TShape generateRandomTitris() {
        Random random = new Random();

        return tPieces[random.nextInt(2)];
//        return tPieces[0];
    }

    public void accelerateDescent(boolean isAccelerating) {

    }

    public void rotate() {

    }

    public void pause(boolean isPaused) {

    }

    public void setX(boolean movingRight) {

        // If moving right 
        // && right end of shape is within the well
        // && the open space to the right of the shape is empty
        // && the shape is above the base of the well
        // increment shape to the right.
        if (movingRight
                && horizontalIncrement + activeTitris.getTileWidth() < 10
                && !PlayState.WELL_SPACES[verticalIncrement][horizontalIncrement + activeTitris.getTileWidth()]
                        .isActivatedPhysicsSquare()
                && !PlayState.WELL_SPACES[verticalIncrement + activeTitris.getTileHeight() - 1][horizontalIncrement + activeTitris.getTileWidth()]
                        .isActivatedPhysicsSquare()
                && verticalIncrement > 0) {

            horizontalIncrement++;

            Gdx.app.log(TAG, "horizontalIncrement: " + horizontalIncrement + " activeTitris.getTileWidth(): " + activeTitris.getTileWidth() + " activeTitris.getTileHeight(): " + activeTitris.getTileHeight());
        }

        if (!movingRight && horizontalIncrement > 0 && verticalIncrement > 0) {

            // Check for activated squares to the left.
            if (horizontalIncrement != 0
                    && !PlayState.WELL_SPACES[verticalIncrement][horizontalIncrement - 1]
                            .isActivatedPhysicsSquare()) {
                horizontalIncrement--;
            }

        }
        activeTitris.increment(horizontalIncrement);

    }

    public void update(float dt) {

        // Don't descend sprite until the desired time has passed (DESCENT_SPEED).
        if (executeSpriteDescent < DESCENT_SPEED) {
            executeSpriteDescent += dt;
        } else {
            executeSpriteDescent = 0f; // Reset to 0
            updateVerticalDescent();
        }
    }

    // Set the descent of each sprite.
    private void updateVerticalDescent() {

        float[] yPos = new float[activeTitris.getPositions().length];
        boolean stopDescent = false;
        for (int i = 0; i < activeTitris.getPositions().length; i++) {
            float unit = MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER; // Shorter variable.

            // If lowest point of sprite touches the base of the well or another 
            // sprite, stop the descending sprite.
            if (activeTitris.lowestPoint() == ps.getVerticalIncrements()[0]) {

                // If the position of the shape is equal to its lowest point,
                // stop it at the base of the well. Else stop it above the base
                // of the well.
                if (activeTitris.getPositions()[i].y == activeTitris.lowestPoint()) {
                    yPos[i] = ps.getVerticalIncrements()[0];
                } else {
                    yPos[i] = ps.getVerticalIncrements()[1];
                }

                stopDescent = true;
            } // Else if the space below the descending sprite is an active static
            // body, stop descent.
            else if (verticalIncrement > 0) {
                boolean isActivatedSquare = false;

                // Iterate through columns that match up with descending shape
                // and check for activated physics squares.
                for (int col = horizontalIncrement;
                        col < horizontalIncrement + activeTitris.getTileWidth();
                        col++) {
                    if (PlayState.WELL_SPACES[verticalIncrement - 1][col]
                            .isActivatedPhysicsSquare()) {
                        isActivatedSquare = true;
                    }
                }

                // If the position of the shape is equal to its lowest point,
                // stop it above the activated square. Else stop it one row 
                // above that point.
                if (activeTitris.getPositions()[i].y == activeTitris.lowestPoint()) {
                    yPos[i] = ps.getVerticalIncrements()[verticalIncrement];
                } else {
                    yPos[i] = ps.getVerticalIncrements()[verticalIncrement + 1];
                }

                // If an activated square is below the descending shape, stop
                // descent.
                if (isActivatedSquare) {
                    stopDescent = true;
                }
//                Gdx.app.log(TAG, "i: " + i
//                        + "\ngetPositions()[i].y: " + activeTitris.getPositions()[i].y
//                        + " lowestpoint: " + activeTitris.lowestPoint());

            } else {

                float oneAboveLowest = activeTitris.lowestPoint() + unit;
                float twoAboveLowest = activeTitris.lowestPoint() + (unit * 2);

                // If position is lowest, set yPos to lowest. Else set it one 
                // above the lowest.
                if (activeTitris.getPositions()[i].y == activeTitris.lowestPoint()) {
                    yPos[i] = ps.getVerticalIncrements()[verticalIncrement];
//                } else if(activeTitris.getPositions()[i].y == activeTitris
//                        .roundFloatTo2Decimals(oneAboveLowest)) {
//                    yPos[i] = ps.getVerticalIncrements()[verticalIncrement + 1];
//                    Gdx.app.log(TAG, "else if one");
//                } else if(activeTitris.getPositions()[i].y == activeTitris
//                        .roundFloatTo2Decimals(twoAboveLowest)) {
//                    yPos[i] = ps.getVerticalIncrements()[verticalIncrement + 2];
//                    Gdx.app.log(TAG, "else if 2");
                } else {
                    yPos[i] = ps.getVerticalIncrements()[verticalIncrement + 1];
                }
            }
        }

        activeTitris.setYPositions(yPos);

        // Activate static body when shape stops moving.
        if (stopDescent) {

            // Iterate through each row.
            for (int row = 0; row < PlayState.WELL_SPACES.length; row++) {

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

//                        Gdx.app.log(TAG, "pos: " + p
//                                + " \nactiveTitris.getPositions()[p].x: "
//                                + activeTitrisX
//                                + " \nPlayState.WELL_SPACES[row][col].getXY().x: "
//                                + PlayState.WELL_SPACES[row][col].getXY().x);
                        if (PlayState.WELL_SPACES[row][col].getXY().y
                                == activeTitris.getPositions()[p].y
                                && PlayState.WELL_SPACES[row][col].getXY().x
                                == activeTitrisX) {
                            PlayState.WELL_SPACES[row][col].activatePhysicsSquare(true);
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
