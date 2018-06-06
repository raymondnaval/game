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
import com.raymondn.game.sprites.PlayerTitrisSprite;
import com.raymondn.game.states.PlayState;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class TShapeStraightThree extends TShape {

    private Sprite[] squares;
    private Vector2 physicsBoxPosition;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 3; // Number of squres in graphic.
    private final String TAG = "Class: TShapeStraightThree";

    /**
     *
     * @param startingPosition The starting position of the top-left square
     * sprite.
     */
    public TShapeStraightThree(Body body, PlayState ps) {
        super(body, ps);
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        setFullShape(squares);
        positionStraightThree();
        height = squares[0].getHeight();
        width = height * 3;

//        position = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - height) / MainGame.PIXELS_PER_METER);
        // Create a rectangle object because the the objects in the loop are all rectangles.
        Rectangle rect = new Rectangle(
                MainGame.TITRIS_STARTING_POSITION.x, MainGame.TITRIS_STARTING_POSITION.y,
                width / MainGame.PIXELS_PER_METER,
                height / MainGame.PIXELS_PER_METER);
        Gdx.app.log(TAG, "defineTitris -- width: " + width + " height: " + height);

        PolygonShape shape = new PolygonShape();
        physicsBoxPosition = new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));
        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        defineTitris(squares[0], MainGame.TITRIS_STARTING_POSITION, physicsBoxPosition, width, height, (MainGame.PIXEL_SIZE / 2) / MainGame.PIXELS_PER_METER,
                (height / 2) / MainGame.PIXELS_PER_METER, shape);

        shape.dispose();
    }

    /**
     * Create Vector2 coordinates for all three sprites.
     *
     */
    private void positionStraightThree() {
        spritePositions[0] = MainGame.TITRIS_STARTING_POSITION;
        spritePositions[1] = new Vector2(MainGame.TITRIS_STARTING_POSITION.x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER),
                MainGame.TITRIS_STARTING_POSITION.y);
        spritePositions[2] = new Vector2(spritePositions[1].x 
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER), 
                MainGame.TITRIS_STARTING_POSITION.y);
        setPositions(spritePositions);
    }

    @Override
    public float getWidth() {
        return width / 3 / MainGame.PIXELS_PER_METER;
    }

}
