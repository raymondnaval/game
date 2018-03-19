package com.raymondn.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.UserSprite;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayState extends State {

    private UserSprite user;
    private Sprite bg;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        user = new UserSprite("protaganist.jpg", 10, 10);
        bg = new Sprite(new Texture("TheWorld.png"));
        bg.setPosition(0, 0);
        cam.position.set(MainGame.WIDTH / 2, MainGame.HEIGHT / 2, 0);
        System.out.println(cam.position.x);
    }

    @Override
    protected void handleInput(final float deltaTime) {

        // Run button.
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            user.setMoveSpeed(2.5f);
        } else {
            user.setMoveSpeed(UserSprite.DEFAULT_SPEED);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            user.updateX(deltaTime, true);

            // If center of user sprite is at midpoint of viewport - SPACE_IN_FRONT 
            // && camera isn't at end of screen, 
            // scroll right. 
            if ((user.getCenterX() >= cam.viewportWidth / 2 - UserSprite.SPACE_IN_FRONT)
                    && cam.position.x < bg.getWidth() - cam.viewportWidth / 2 - 1) {
                cam.position.x = user.getCenterX() + UserSprite.SPACE_IN_FRONT;
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            // if center of user sprite is at midpoint of viewport + SPACE_IN_FRONT
            // && camera isn't at end of screen,
            // && camera isn't at beginning of screen,
            // scroll left.
            if ((user.getCenterX() >= cam.viewportWidth / 2 + UserSprite.SPACE_IN_FRONT)
                    && user.getCenterX() < bg.getWidth() - cam.viewportWidth / 2 + UserSprite.SPACE_IN_FRONT
                    && cam.position.x >= -cam.viewportWidth) {
                cam.position.x = user.getCenterX() - UserSprite.SPACE_IN_FRONT;
            }

            user.updateX(deltaTime, false);
        } else {

        }

    }

    @Override
    public void update(float dt) {
        handleInput(dt);
        System.out.println("cam position: " + cam.position.x);

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0);
        sb.draw(user.getTexture(), user.getX(), user.getY());
        sb.end();
    }

    @Override
    public void dispose() {
        user.dispose();
    }

}
