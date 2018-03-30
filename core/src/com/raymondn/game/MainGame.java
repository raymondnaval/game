package com.raymondn.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.raymondn.game.sprites.UserSprite;
import com.raymondn.game.states.GameStateManager;
import com.raymondn.game.states.PlayState;

public class MainGame extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 720;
    public static final float PIXELS_PER_METER = 100;
    public static final String TITLE = "Legend of the Industrial Hygienist";
           
    private SpriteBatch batch;
    Texture img;
    UserSprite user;

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
