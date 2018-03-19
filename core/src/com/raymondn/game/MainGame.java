package com.raymondn.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raymondn.game.sprites.UserSprite;
import com.raymondn.game.states.GameStateManager;
import com.raymondn.game.states.PlayState;

public class MainGame extends ApplicationAdapter {

    public static final int WIDTH = 512;
    public static final int HEIGHT = 448;
    public static final String TITLE = "Legend of the Industrial Hygienist";
           
    SpriteBatch batch;
    Texture img;
    UserSprite user;
    private GameStateManager gsm;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        Gdx.gl.glClearColor(0, 0, 1, 1);
        gsm = new GameStateManager();
        gsm.push(new PlayState(gsm));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
