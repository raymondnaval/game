package com.raymondn.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 * This class will have all the attributes of a square sprite inside the well.
 * That includes the coordinates of the sprite, the color, the HP, and others
 * TBD.
 */
public class TitrisSquare {

    private int hp = 10;
    private Vector2 location;
    private Sprite sprite;
    private boolean isActiveBody;
    private PlayState state;
    
    // Physics variables.
    private Body body;
    private BodyDef bdef;
    private Fixture fixture;
    private FixtureDef fixtureDef;

    public TitrisSquare(PlayState ps, Body body, float x, float y) {
        state = ps;
        this.body = body;
        location = new Vector2(x, y);
        isActiveBody = false;

        setPhysicsBox();
    }

    public TitrisSquare(PlayState ps) {
        state = ps;
        isActiveBody = false;
    }

    private void setPhysicsBox() {
        bdef = new BodyDef();
        fixtureDef = new FixtureDef();
        
        Rectangle rect = new Rectangle(
                location.x,
                location.y,
                MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER,
                MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        
        bdef.type = BodyDef.BodyType.StaticBody;
        
        // Set position of square.
        bdef.position.set(location.x 
                + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER)/2),
                location.y
        + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER)/2));
        
        body = state.getWorld().createBody(bdef);
        PolygonShape shape = new PolygonShape();
        
        // Draw the box shape, but not the position of it.
        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        body.setActive(isActiveBody);
    }
    
    public void activatePhysicsSquare(boolean isActiveBody){
        this.isActiveBody = isActiveBody;
        body.setActive(isActiveBody);
    }
    
    public boolean isActivatedPhysicsSquare() {
        return isActiveBody;
    }
    
    public Vector2 getXY() {
        return location;
    }

}
