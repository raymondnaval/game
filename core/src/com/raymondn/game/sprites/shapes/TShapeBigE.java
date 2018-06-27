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
public class TShapeBigE extends TShape {
    
    private Sprite[] squares;
    private Vector2 physicsShapePosition;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 8; // Number of squres in graphic.
    private final String TAG = "Class: TShapeBigE";

    public TShapeBigE(Body body, PlayState ps) {
        super(body, ps);
        
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        setFullShape(squares);
        positionSprites();

        height = MainGame.PIXEL_SIZE * 2;
        width = MainGame.PIXEL_SIZE * 5;
    }
    
    @Override
    public void activateTShapeBoundaries() {
        // Create a polygon object because it is a unique shape.
        // TODO: Create muliple convex polygons for concave polygons.
        Rectangle rect = new Rectangle(
                getStartingPosition().x, getStartingPosition().y,
                width, height);

        Vector2[] polygonVertices = new Vector2[6];
        polygonVertices[0] = new Vector2(-width / MainGame.PIXELS_PER_METER / 2,
                (height - MainGame.PIXEL_SIZE) / 2 / MainGame.PIXELS_PER_METER);
        polygonVertices[1] = new Vector2(polygonVertices[0].x,
                polygonVertices[0].y
                - (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) * 2);
        polygonVertices[2] = new Vector2(polygonVertices[0].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) * 3,
                polygonVertices[1].y);
        polygonVertices[3] = new Vector2(polygonVertices[2].x,
                polygonVertices[0].y);
        polygonVertices[4] = new Vector2(polygonVertices[0].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) * 2,
                polygonVertices[0].y);
        polygonVertices[5] = new Vector2(polygonVertices[4].x,
                -(MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER));
//        polygonVertices[6] = new Vector2(0, 0);
//        polygonVertices[7] = new Vector2(0, 0);
        PolygonShape shape = new PolygonShape();
        shape.set(polygonVertices);
        physicsShapePosition = new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));
        defineTitris(squares[0], physicsShapePosition,
                (MainGame.PIXEL_SIZE / 2) / MainGame.PIXELS_PER_METER,
                (height / 2) / MainGame.PIXELS_PER_METER, shape);

        shape.dispose();
    }

    @Override
    protected void positionSprites() {
        // oneUnit is the length of one square.
        float oneUnit = MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER;
        
        spritePositions[0] = getStartingPosition();
        spritePositions[1] = new Vector2(getStartingPosition().x,
                getStartingPosition().y
                - oneUnit);
        spritePositions[2] = new Vector2(spritePositions[1].x
                + oneUnit,
                spritePositions[1].y);
        spritePositions[3] = new Vector2(spritePositions[2].x
                + oneUnit,
                spritePositions[1].y);
        spritePositions[4] = new Vector2(spritePositions[3].x,
                getStartingPosition().y);
        spritePositions[5] = new Vector2(spritePositions[3].x
                + oneUnit,
                getStartingPosition().y
                - oneUnit);
        spritePositions[6] = new Vector2(spritePositions[5].x
                + oneUnit,
                spritePositions[5].y);
        spritePositions[7] = new Vector2(spritePositions[6].x,
                getStartingPosition().y);

        setPositions(spritePositions);
    }
}
