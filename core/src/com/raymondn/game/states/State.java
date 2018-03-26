package com.raymondn.game.states;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.raymondn.game.MainGame;

public abstract class State {
    protected OrthographicCamera gameView;
    protected Vector2 mouse;
    protected GameStateManager gsm;
    
    public State(GameStateManager gsm) {
        this.gsm = gsm;
        gameView = new OrthographicCamera();
        mouse = new Vector2();
    }
    
    public State() {
        gameView = new OrthographicCamera();
        mouse = new Vector2();
    }
    
    protected abstract void handleInput(float dt);
    public abstract void update(float dt);
    public abstract void dispose();
}
