/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.raymondn.game.MainGame;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class WorldCreator {
    
    public WorldCreator(TiledMap map, World world) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2 / MainGame.PIXELS_PER_METER, rect.getY() + rect.getHeight() / 2 / MainGame.PIXELS_PER_METER);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MainGame.PIXELS_PER_METER, rect.getHeight() / 2 / MainGame.PIXELS_PER_METER);
            fixture.shape = shape;
            body.createFixture(fixture);
        }
    }
    
}
