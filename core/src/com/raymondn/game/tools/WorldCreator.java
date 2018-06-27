/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class WorldCreator {

    private TiledMap map;
    private World world;
    private Vector2[] wallBounds, groundBounds;
    Body rightBody;

    public WorldCreator(PlayState play) {
        map = play.getMap();
        world = play.getWorld();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        Body body;

        // Ground object.
        // Loop through each object in the ground layer.
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {

            // Create a rectangle object because the the objects in the loop are all rectangles.
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody; // Unmovable object.

            // Position the rectangle exactly where its drawn on the Tile map.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / MainGame.PIXELS_PER_METER);

            // @TODO Add comments to these below...
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MainGame.PIXELS_PER_METER, rect.getHeight() / 2 / MainGame.PIXELS_PER_METER);
            fixture.shape = shape;
            fixture.filter.categoryBits = MainGame.BOTTOM_WELL_BIT;
            body.createFixture(fixture).setUserData("bottom_well");

        }

        // Left wall.
//        wallBounds = new Vector2[map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class).size];
        int i = 0;
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / MainGame.PIXELS_PER_METER);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MainGame.PIXELS_PER_METER, rect.getHeight() / 2 / MainGame.PIXELS_PER_METER);
            fixture.shape = shape;
            fixture.filter.categoryBits = MainGame.SIDE_WELL_BIT;
            body.createFixture(fixture).setUserData("side_well");

//            wallBounds[i] = new Vector2(bdef.position.x, bdef.position.y);
//            i++;
        }
        
        // Right wall.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / MainGame.PIXELS_PER_METER);

            rightBody = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MainGame.PIXELS_PER_METER, rect.getHeight() / 2 / MainGame.PIXELS_PER_METER);
            fixture.shape = shape;
            fixture.filter.categoryBits = MainGame.SIDE_WELL_BIT;
            rightBody.createFixture(fixture).setUserData("right_well");
            rightBody.setActive(false);
        }
       
    }
    
    public Body getRightBody() {
        return rightBody;
    }

}
