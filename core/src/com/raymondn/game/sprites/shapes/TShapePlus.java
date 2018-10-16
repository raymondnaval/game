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
public class TShapePlus extends TShape {

    private Sprite[] squares;
    private Vector2 physicsShapePosition;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 5; // Number of squres in graphic.
    private final String TAG = "Class: TShapePlus";

    public TShapePlus(PlayState ps) {
        super(ps);
        tileWidth = 3;
        tileHeight = 3;
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        setFullShape(squares);
        positionSprites();
        height = MainGame.PIXEL_SIZE * 3;
        width = MainGame.PIXEL_SIZE * 3;
    }

    @Override
    protected void positionSprites() {
        // oneUnit is the length of one square.
        float oneUnit = MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER;

        spritePositions[0] = new Vector2(getStartingPosition().x,
                getStartingPosition().y
                - oneUnit);
        spritePositions[1] = new Vector2(getStartingPosition().x
                + oneUnit,
                getStartingPosition().y);
        spritePositions[2] = new Vector2(spritePositions[1].x
                - oneUnit,
                spritePositions[0].y);
        spritePositions[3] = new Vector2(spritePositions[2].x,
                spritePositions[2].y
                - oneUnit);
        spritePositions[4] = new Vector2(spritePositions[3].x
                + oneUnit,
                getStartingPosition().y);

        setPositions(spritePositions);
    }

    @Override
    public void increment(int pos) {
        spritePositions[0].x = getState().getHorizontalIncrements()[pos];
        spritePositions[1].x = spritePositions[0].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        spritePositions[2].x = spritePositions[1].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        spritePositions[3].x = spritePositions[2].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        spritePositions[4].x = spritePositions[3].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
    }

}
