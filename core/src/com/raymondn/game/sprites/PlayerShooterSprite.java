package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerShooterSprite extends Sprite {

    public World world;
    public Body box2dBody;
    private TextureRegion playerStand, playerShoot;
    private int playerSizeX = 16;
    private int playerSizeY = 32;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private float stateTimer;
    private boolean movingRight, movingLeft, facingRight = true;
    private ShooterProjectile projectile;

    public enum State {
        STANDING, RUNNING, JUMPING, SHOOTING
    };
    public State currentState, previousState;
    private final String TAG = "Class: PlayerShooterSprite";

    public PlayerShooterSprite(World world, PlayState state, ShooterProjectile sp) {
        super(state.getSpritesheet());
        this.world = world;
        projectile = sp;
        definePlayerSprite();

        // Standing sprite.
        playerStand = new TextureRegion(getTexture(),
                //                SpritesheetCoordinates.SH_STAND_X,
                //                SpritesheetCoordinates.SH_STAND_Y,
                //                SpritesheetCoordinates.SH_STAND_W,
                //                SpritesheetCoordinates.SH_STAND_H);
                SpritesheetCoordinates.SH_WLK_X_0,
                SpritesheetCoordinates.SH_WLK_Y_0,
                SpritesheetCoordinates.SH_WLK_W_0,
                SpritesheetCoordinates.SH_WLK_H_0);
        
        // Shooting sprite.
        playerShoot = new TextureRegion(getTexture(),
                SpritesheetCoordinates.SHTR_SHT_X,
                SpritesheetCoordinates.SHTR_SHT_Y,
                SpritesheetCoordinates.SHOOT_WDTH,
                SpritesheetCoordinates.SHOOT_HGHT);
       
        // Animation.
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        // Add moving animation frames to playerRun object.
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_0,
                SpritesheetCoordinates.SH_WLK_Y_0,
                SpritesheetCoordinates.SH_WLK_W_0,
                SpritesheetCoordinates.SH_WLK_H_0));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_1,
                SpritesheetCoordinates.SH_WLK_Y_1,
                SpritesheetCoordinates.SH_WLK_W_1,
                SpritesheetCoordinates.SH_WLK_H_1));
//                SpritesheetCoordinates.JUMP_X_0, 
//                SpritesheetCoordinates.JUMP_Y_0, 
//                SpritesheetCoordinates.JUMP_WDTH_0, 
//                SpritesheetCoordinates.JMP_HEIGHT));

        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_2,
                SpritesheetCoordinates.SH_WLK_Y_2,
                SpritesheetCoordinates.SH_WLK_W_2,
                SpritesheetCoordinates.SH_WLK_H_2));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_3,
                SpritesheetCoordinates.SH_WLK_Y_3,
                SpritesheetCoordinates.SH_WLK_W_3,
                SpritesheetCoordinates.SH_WLK_H_3));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_4,
                SpritesheetCoordinates.SH_WLK_Y_4,
                SpritesheetCoordinates.SH_WLK_W_4,
                SpritesheetCoordinates.SH_WLK_H_4));
//                SpritesheetCoordinates.JUMP_X_0, 
//                SpritesheetCoordinates.JUMP_Y_0, 
//                SpritesheetCoordinates.JUMP_WDTH_0, 
//                SpritesheetCoordinates.JMP_HEIGHT));

        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_5,
                SpritesheetCoordinates.SH_WLK_Y_5,
                SpritesheetCoordinates.SH_WLK_W_5,
                SpritesheetCoordinates.SH_WLK_H_5));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.SH_WLK_X_6,
                SpritesheetCoordinates.SH_WLK_Y_6,
                SpritesheetCoordinates.SH_WLK_W_6,
                SpritesheetCoordinates.SH_WLK_H_6));
        playerRun = new Animation(1 / 7f, frames);

        frames.clear();
        

//        setBounds(SpritesheetCoordinates.SH_STAND_X,
//                SpritesheetCoordinates.SH_STAND_Y,
//                SpritesheetCoordinates.SH_STAND_W / MainGame.PIXELS_PER_METER,
//                SpritesheetCoordinates.SH_STAND_H / MainGame.PIXELS_PER_METER);
        setBounds(
                SpritesheetCoordinates.SH_WLK_X_0,
                SpritesheetCoordinates.SH_WLK_Y_0,
                SpritesheetCoordinates.SH_WLK_W_0 / MainGame.PIXELS_PER_METER,
                SpritesheetCoordinates.SH_WLK_H_0 / MainGame.PIXELS_PER_METER);
        
        setRegion(playerStand);
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = playerShoot;
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((box2dBody.getLinearVelocity().x < 0 || !facingRight) && !region.isFlipX()) {
            region.flip(true, false);
            facingRight = false;
        } else if ((box2dBody.getLinearVelocity().x > 0 || facingRight) && region.isFlipX()) {
            region.flip(true, false);
            facingRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        if (box2dBody.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void definePlayerSprite() {
        BodyDef bdef = new BodyDef();

        // Set starting position of sprite boundary.
        bdef.position.set((MainGame.LEFT_WALL + ((MainGame.RIGHT_WALL 
                - MainGame.LEFT_WALL) / 2)) / MainGame.PIXELS_PER_METER, 
                MainGame.WELL_DEPTH);

        bdef.type = BodyDef.BodyType.DynamicBody;

        box2dBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
//        CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(playerSizeX / 2 / MainGame.PIXELS_PER_METER, 
                playerSizeY / 2 / MainGame.PIXELS_PER_METER);
        fdef.shape = shape;
        box2dBody.createFixture(fdef);
    }

    public void update(float deltaTime) {
        setPosition(box2dBody.getPosition().x - getWidth() / 2,
                box2dBody.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
        setBounds(getX(), getY(), getWidth(), getHeight());

        if (movingLeft) {
            if (box2dBody.getLinearVelocity().x >= -2) {
                box2dBody.applyLinearImpulse(new Vector2(-.1f, 0),
                        box2dBody.getWorldCenter(), true);
            }
        }

        if (movingRight) {
            if (box2dBody.getLinearVelocity().x <= 2) {
                box2dBody.applyLinearImpulse(new Vector2(.1f, 0),
                        box2dBody.getWorldCenter(), true);
            }
        }
        
    }

    public void moveRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void moveLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void shoot() {
        float x = box2dBody.getPosition().x;
        projectile.fire(x, box2dBody.getPosition().y);
    }

    public void stop() {
        box2dBody.setLinearVelocity(0, 0);
    }

    public void jump() {
        box2dBody.applyLinearImpulse(new Vector2(0, 4f), box2dBody.getWorldCenter(), true);
    }
}
