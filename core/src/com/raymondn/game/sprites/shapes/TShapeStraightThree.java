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
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.text.DecimalFormat;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class TShapeStraightThree extends TShape implements TShapeInterface {

    private Sprite[] squares;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 3; // Number of squares in graphic.
    private final String TAG = "Class: TShapeStraightThree";

    public TShapeStraightThree(PlayState ps) {
        super(ps);
        tileWidth = 3;
        tileHeight = 1;
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        activeBoundaries = new Rectangle[TOTAL_SQUARES];
        setFullShape(squares);
        positionSprites();
        height = MainGame.PIXEL_SIZE;
        width = height * 3;

    }

    @Override
    public void increment(int pos) {
        spritePositions[0].x = getState().getHorizontalIncrements()[pos];
        spritePositions[1].x = spritePositions[0].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        spritePositions[2].x = spritePositions[1].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
    }

    @Override
    public void stop(float bottomSprite) {
        spritePositions[0].y = bottomSprite;
        spritePositions[1].y = bottomSprite;
        spritePositions[2].y = bottomSprite;
    }

    @Override
    protected void positionSprites() {
        spritePositions[0] = getStartingPosition();
        spritePositions[1] = new Vector2(getStartingPosition().x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER),
                getStartingPosition().y);
        spritePositions[2] = new Vector2(spritePositions[1].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER),
                getStartingPosition().y);
        setPositions(spritePositions);
    }

    @Override
    public float getWidth() {
        return width / 3 / MainGame.PIXELS_PER_METER;
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
