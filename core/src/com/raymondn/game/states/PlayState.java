package com.raymondn.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.UserSprite;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayState extends State {

    private UserSprite user;
    private Texture bg;
    private InputProcessor inputP;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, MainGame.WIDTH / 2, MainGame.HEIGHT - 1);
        user = new UserSprite("protaganist.jpg", 50, 50);
        bg = new Texture("TheWorld.png");
    }

    @Override
    protected void handleInput(final float deltaTime) {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            user.updateX(deltaTime, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            user.updateX(deltaTime, false);
        }

    }

    @Override
    public void update(float dt) {
        handleInput(dt);
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(user.getTexture(), user.getX(), user.getY());
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        user.dispose();
    }

}
