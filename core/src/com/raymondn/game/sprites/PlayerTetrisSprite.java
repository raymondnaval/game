/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.util.Random;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerTetrisSprite {

    public World world;
    private Body box2dBody;
    private TextureRegion activeTitris;
    private float scale = .16f/MainGame.PIXELS_PER_METER;
    private int playerSizeY = 16;
    private TextureRegion[] titrisPieces;
    private float stateTimer;
    private boolean movingRight;
    private Rectangle bounds;
    private Texture titris;
    private Vector2 position;

    public PlayerTetrisSprite(World world, PlayState state) {

        // Titris pieces.
        titris = new Texture(Gdx.files.internal("titris_test.png"));
        titrisPieces = new TextureRegion[]{
            new TextureRegion(titris, 0, 0, 16, 16),
            new TextureRegion(titris, 16, 0, 16, 16),
            new TextureRegion(titris, 32, 0, 16, 16)
        };

        activeTitris = generateTitrisPiece();

//        position = new Vector2((MainGame.LEFT_WALL + (scale * 4)) / MainGame.PIXELS_PER_METER, 0);
        position = new Vector2(((MainGame.WIDTH/2)-(titrisPieces[0].getRegionWidth()/2))/MainGame.PIXELS_PER_METER, (MainGame.HEIGHT-titris.getHeight())/MainGame.PIXELS_PER_METER);
        bounds = new Rectangle(MainGame.LEFT_WALL + (scale * 4), 0, MainGame.LEFT_WALL + (scale * 4) + (titrisPieces.length / 3), playerSizeY);
//        bounds = new Rectangle(1/MainGame.PIXELS_PER_METER, 1/MainGame.PIXELS_PER_METER, activeTitris.getWidth()/MainGame.PIXELS_PER_METER, activeTitris.getHeight()/MainGame.PIXELS_PER_METER);
        Gdx.app.log("playertetrissprite", "titris width: " + titris.getWidth());
    }

    private TextureRegion generateTitrisPiece() {
        Gdx.app.log("playertetrissprite", "titrispieces.length: " + titrisPieces.length);
        
        return titrisPieces[new Random().nextInt(titrisPieces.length)];
    }

    public void update(float dt) {

        // If not descending, generate new piece.
        position.y -= scale;
        bounds.setPosition(position.x, position.y);

    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public TextureRegion getTitrisPiece() {
        return activeTitris;
    }
}
