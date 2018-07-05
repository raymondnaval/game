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
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.PlayerTitrisSprite;
import com.raymondn.game.states.PlayState;
import java.text.DecimalFormat;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class TShapeStraightThree extends TShape implements TShapeInterface {

    private Sprite[] squares;
    private Vector2 physicsBoxPosition;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 3; // Number of squares in graphic.
    private final String TAG = "Class: TShapeStraightThree";

    public TShapeStraightThree(PlayState ps) {
        super(ps);
        tileWidth = 3;
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        activeBoundaries = new Rectangle[TOTAL_SQUARES];
        setFullShape(squares);
        positionSprites();
        height = MainGame.PIXEL_SIZE;
        width = height * 3;

    }

    @Override
    public void activateTShapeBoundaries() {

        // Set the width and height of physics box, but not the position of it.
        Rectangle rect = new Rectangle(
                getStartingPosition().x,
                getStartingPosition().y,
                width / MainGame.PIXELS_PER_METER,
                height / MainGame.PIXELS_PER_METER);
        Gdx.app.log(TAG, "rect: " + rect.toString() + " width: " + (width / MainGame.PIXELS_PER_METER)
                + " height: " + (height / MainGame.PIXELS_PER_METER));

        PolygonShape shape = new PolygonShape();
        physicsBoxPosition = new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2) + 2);

        // Draw the box shape, but not the position of it.
        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);

        // Top and bottom edges of shape.
        EdgeShape topEdgeShape = new EdgeShape();
        topEdgeShape.set(-rect.getWidth() / 2 + (5.0f / MainGame.PIXELS_PER_METER),
                rect.getHeight() / 2,
                rect.getWidth() / 2 - (5.0f / MainGame.PIXELS_PER_METER),
                rect.getHeight() / 2);

        EdgeShape bottomEdgeShape = new EdgeShape();
        bottomEdgeShape.set(-rect.getWidth() / 2 + (5.0f / MainGame.PIXELS_PER_METER),
                -rect.getHeight() / 2,
                rect.getWidth() / 2 - (5.0f / MainGame.PIXELS_PER_METER),
                -rect.getHeight() / 2);

//        defineTitris(squares[0], physicsBoxPosition, (MainGame.PIXEL_SIZE / 2) / MainGame.PIXELS_PER_METER,
//                (height / 2) / MainGame.PIXELS_PER_METER, shape, topEdgeShape, bottomEdgeShape);
        topEdgeShape.dispose();
        bottomEdgeShape.dispose();
        shape.dispose();
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
        setActiveBoundaries();
    }

    @Override
    public void setActiveBoundaries() {
        for (int i = 0; i < spritePositions.length; i++) {
            DecimalFormat df = new DecimalFormat("###.##");
            float roundHorzPosTo2DecimalPlaces = Float.valueOf(df.format(spritePositions[i].x + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER)));
            activeBoundaries[i] = new Rectangle(
                    roundHorzPosTo2DecimalPlaces,
                    spritePositions[i].y,
                    MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER,
                    MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        }
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
