/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class TShapeSingle extends TShape {

    private Sprite[] squares;
    private Vector2 physicsShapePosition;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 1; // Number of squres in graphic.
    private final String TAG = "Class: TShapeSingle";

    public TShapeSingle(PlayState ps) {
        super(ps);
        tileWidth = 1;
        tileHeight = 1;
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        setFullShape(squares);
        positionSprites();

        height = MainGame.PIXEL_SIZE;
        width = MainGame.PIXEL_SIZE;
    }

    @Override
    protected void positionSprites() {
        spritePositions[0] = getStartingPosition();
        Gdx.app.log(TAG, "spritePositions: " + spritePositions[0]);
        setPositions(spritePositions);
    }
    
    @Override
    public void increment(int pos) {
        spritePositions[0].x = getState().getHorizontalIncrements()[pos];
    }
    
    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    @Override
    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }
}
