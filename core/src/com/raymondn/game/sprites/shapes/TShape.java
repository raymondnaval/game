/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class TShape {

    public static final String YELLOW = "yellow";
    private static final String GREEN = "green";
    private static final String RED = "red";
    private static final String BLUE = "blue";
    private static final String LIGHT_PURPLE = "light_purple";
    private static final String PURPLE = "purple";
    private static final String BLACK = "black";
    private HashMap<String, Sprite> squares;
    private FixtureDef fixtureDef;
    private BodyDef bdef;
    private PolygonShape shape;
    private float width, height;
    public Sprite[] fullShape;
    private Texture squareGraphics;
    private Body body, bodyTEdge, bodyBEdge;
    private PlayState state;
    private Fixture fixture, fixtureTopEdge, fixtureBottomEdge;
    private Vector2[] positions;
    private final String TAG = "Class: TShape";
    private Vector2 startingPosition;
    
    // Sprite boundaries for collision detection.
    protected Rectangle[] activeBoundaries;

    public TShape(Body body, PlayState ps) {

    }

    public TShape(PlayState ps) {
        state = ps;

        startingPosition = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER,
                (MainGame.HEIGHT - MainGame.PIXEL_SIZE) / MainGame.PIXELS_PER_METER);

        squareGraphics = new Texture(Gdx.files.internal("sprite_sheet.png"));

        squares = new HashMap(7);
        squares.put(YELLOW, new Sprite(squareGraphics, 0, 64, 16, 16));
        squares.put(GREEN, new Sprite(squareGraphics, 16, 64, 16, 16));
        squares.put(RED, new Sprite(squareGraphics, 32, 64, 16, 16));
        squares.put(BLUE, new Sprite(squareGraphics, 48, 64, 16, 16));
        squares.put(LIGHT_PURPLE, new Sprite(squareGraphics, 64, 64, 16, 16));
        squares.put(PURPLE, new Sprite(squareGraphics, 80, 64, 16, 16));
        squares.put(BLACK, new Sprite(squareGraphics, 96, 64, 16, 16));
    }

    public TShape(Body body, Body bodyTEdge, PlayState ps) {
        state = ps;
        this.body = body;
        this.bodyTEdge = bodyTEdge;

        startingPosition = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER,
                (MainGame.HEIGHT - MainGame.PIXEL_SIZE) / MainGame.PIXELS_PER_METER);

        squareGraphics = new Texture(Gdx.files.internal("sprite_sheet.png"));

        squares = new HashMap(7);
        squares.put(YELLOW, new Sprite(squareGraphics, 0, 64, 16, 16));
        squares.put(GREEN, new Sprite(squareGraphics, 16, 64, 16, 16));
        squares.put(RED, new Sprite(squareGraphics, 32, 64, 16, 16));
        squares.put(BLUE, new Sprite(squareGraphics, 48, 64, 16, 16));
        squares.put(LIGHT_PURPLE, new Sprite(squareGraphics, 64, 64, 16, 16));
        squares.put(PURPLE, new Sprite(squareGraphics, 80, 64, 16, 16));
        squares.put(BLACK, new Sprite(squareGraphics, 96, 64, 16, 16));
    }

    protected Vector2 getStartingPosition() {
        return startingPosition;
    }

    protected PlayState getState() {
        return state;
    }
    
    /**
     *
     * @return lowest - The lowest point of the descending shape.
     */
    public float lowestPoint() {
        float lowest = positions[0].y;
        for (int i = 1; i < positions.length; i++) {
            if(lowest > positions[i].y) {
                lowest = positions[i].y;
            }
//            Gdx.app.log(TAG, "getPositions() -- position: " + i + ") " + positions[i].y);
        }
        return lowest;
    }

    /**
     *
     * @return positions The starting position of each square sprite.
     */
    public Vector2[] getPositions() {
        return positions;
    }
    
    public Rectangle[] getActiveBoundaries() {
        return activeBoundaries;
    }

    public void setPositions(Vector2[] positions) {
        this.positions = positions;
//        setActiveBoundaries(positions);
    }

    public void setFullShape(Sprite[] fullShape) {
        this.fullShape = fullShape;
    }

    public Sprite[] getFullShape() {
        return fullShape;
    }

    public Sprite getSprite(String key) {
        return squares.get(key);
    }

    protected Sprite[] getRandomSquares(int numSquares) {
        Sprite[] temp = new Sprite[numSquares];

        for (int i = 0; i < numSquares; i++) {
            int rand = new Random().nextInt(squares.size());
            Object[] values = squares.values().toArray();
            temp[i] = (Sprite) values[rand];
        }

        return temp;
    }
    
    protected void setActiveBoundaries(Vector2[] bounds) {
        
    }

    public void activateTShapeBoundaries() {
    }

    protected void defineTitris(Sprite spr, Vector2 startPosition, float originX,
            float originY, PolygonShape shape) {

    }

    protected void defineTitris(Sprite spr, Vector2 startPosition, float originX,
            float originY, PolygonShape shape, EdgeShape topEdge, EdgeShape bottomEdge) {

        fixtureDef = new FixtureDef();
        bdef = new BodyDef();

        // Define physics bit.
        fixtureDef.filter.categoryBits = MainGame.DESCENDING_BIT;

        // Collision with side walls and bottom well and other titris pieces.
        fixtureDef.filter.maskBits = MainGame.SIDE_WELL_BIT | MainGame.STOPPED_BIT
                | MainGame.BOTTOM_WELL_BIT | MainGame.RIGHT_WELL_BIT;

        bdef.type = BodyDef.BodyType.DynamicBody;

        // Position the rectangle exactly where its drawn on the Tile map.
        bdef.position.set(startPosition);

        // @TODO Add comments to these below...
        body = state.getWorld().createBody(bdef);
        body.setGravityScale(0); // No gravity.
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0; // Remove bounciness.

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("titris");

        // Top and bottom edges of shape used for collision detection.
        BodyDef bdefTEdge = new BodyDef();
        bdefTEdge.type = BodyDef.BodyType.DynamicBody;
        bdefTEdge.position.set(startPosition);
        bodyTEdge = state.getWorld().createBody(bdefTEdge);
        FixtureDef fixtureDefTEdge = new FixtureDef();
        fixtureDefTEdge.shape = topEdge;
        fixtureDefTEdge.isSensor = true;
        fixtureTopEdge = body.createFixture(fixtureDefTEdge);
        fixtureTopEdge.setUserData("top_edge");

        BodyDef bdefBEdge = new BodyDef();
        bdefBEdge.type = BodyDef.BodyType.DynamicBody;
        bdefBEdge.position.set(startPosition);
        bodyBEdge = state.getWorld().createBody(bdefBEdge);
        FixtureDef fixtureDefBEdge = new FixtureDef();
        fixtureDefBEdge.shape = bottomEdge;
        fixtureDefBEdge.isSensor = true;
        fixtureBottomEdge = body.createFixture(fixtureDefBEdge);
        fixtureBottomEdge.setUserData("bottom_edge");

        spr.setOrigin(originX, originY);
    }

    // Increment left or right.
    public void increment(int pos) {
    }

    /**
     * Stop all sprites from descending. Will change for sprite rotation.
     *
     * @param bottomSprite The y position of the bottom-most sprite.
     */
    public void stop(float bottomSprite) {

    }
    
    /**
     * Set rectangle boundaries for each sprite for collision detection.
     */
    protected void setActiveBoundaries() {
        
    }

    public Body getBody() {
        return body;
    }

    public Body getBodyEdge() {
        return bodyTEdge;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public float getWidth() {
        return squares.get(YELLOW).getWidth() / MainGame.PIXELS_PER_METER;
    }

    public float getHeight() {
        return squares.get(YELLOW).getHeight() / MainGame.PIXELS_PER_METER;
    }

    public Sprite getShape() {
        return squares.get(YELLOW);
    }

    public float getOriginX() {
        return squares.get(YELLOW).getOriginX();
    }

    public float getOriginY() {
        return squares.get(YELLOW).getOriginY();
    }

    public float getScaleX() {
        return squares.get(YELLOW).getScaleX();
    }

    public float getScaleY() {
        return squares.get(YELLOW).getScaleY();
    }

    public float getRotation() {
        return squares.get(YELLOW).getRotation();
    }

    /**
     * Positions the individual sprite squares to make up the Titris shape.
     * Method must be overidden in child classes.
     */
    protected void positionSprites() {
    }
}
