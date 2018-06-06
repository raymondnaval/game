package com.raymondn.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.raymondn.game.sprites.UserSprite;
import com.raymondn.game.states.GameStateManager;
import com.raymondn.game.states.PlayState;

public class MainGame extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 720;
    public static final float PIXELS_PER_METER = 100;
    public static final float PIXEL_SIZE = 16;
    public static final float LEFT_WALL = 320;
    public static final float RIGHT_WALL = 480;
    public static final float WELL_DEPTH = (HEIGHT - 528) / PIXELS_PER_METER; // 33 tiles * 16 px
    public static final String TITLE = "Think and Grow Rich";
    public static final float DEFAULT_DESCENT_SPEED = .88f / PIXELS_PER_METER;
    public static final Vector2 TITRIS_STARTING_POSITION = new Vector2
            (MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, 
            (MainGame.HEIGHT - MainGame.PIXEL_SIZE) / MainGame.PIXELS_PER_METER);
    
    // Box2D collision bits.
    public static final short NOTHING_BIT = 0;
    public static final short SIDE_WELL_BIT = 1;
    public static final short BOTTOM_WELL_BIT = 2;
    public static final short DESCENDING_BIT = 4;
    public static final short STOPPED_BIT = 8;
    public static final short PROJECTILE_BIT = 16;
    public static final short RIGHT_WELL_BIT = 32;
           
    private SpriteBatch batch;
    private Texture img;
    private UserSprite user;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayState(this));
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
