package com.raymondn.game.states;

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
    private InputProcessor input;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, MainGame.WIDTH / 2, MainGame.HEIGHT - 1);
        user = new UserSprite("protaganist.jpg", 50, 50);
        bg = new Texture("TheWorld.png");
    }

    @Override
    protected void handleInput(final float deltaTime) {
        input = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT) {
                    user.update(deltaTime);
                    System.out.println("keydown");
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
    }

    @Override
    public void update(float dt) {
        handleInput(dt);
        cam.position.x = user.getX() + UserSprite.MOVE;
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth /2), 0);
        sb.draw(user.getTexture(), user.getX(), user.getY());
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        user.dispose();
    }

}
