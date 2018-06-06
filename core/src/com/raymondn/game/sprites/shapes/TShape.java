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
    private Rectangle rect;
    private PolygonShape shape;
    private float width, height;
    public Sprite[] fullShape;
    private Texture squareGraphics;
    private Body body;
    private PlayState state;
    private Fixture fixture;
    private Vector2[] positions;
    private final String TAG = "Class: TShape";
    
    public TShape(Body body, PlayState ps) {
        state = ps;
        this.body = body;
        
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
    
    /**
     * 
     * @return positions The starting position of each square sprite.
     */
    public Vector2[] getPositions() {
        return positions;
    }
    
    public void setPositions(Vector2[] positions) {
        this.positions = positions;
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
        
        for(int i=0; i<numSquares; i++) {
            int rand = new Random().nextInt(squares.size());
            Object[] values = squares.values().toArray();
            temp[i] = (Sprite) values[rand];
        }
        
        return temp;
    }
    
    protected void defineTitris(Sprite spr, Vector2 position, Vector2 startPosition, float width, float height, float originX, float originY, PolygonShape shape) {

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
        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("titris");

        spr.setOrigin(originX, originY);
    }
    
    public Body getBody() {
        return body;
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
}
