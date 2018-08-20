package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private TextureRegion playerStand;
    private int playerSizeX = 16;
    private int playerSizeY = 32;
//    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private float stateTimer;
    private boolean movingRight = true;

    public enum State {
        STANDING, RUNNING, JUMPING
    };
    public State currentState, previousState;
    private final String TAG = "Class: PlayerShooterSprite";

    public PlayerShooterSprite(World world, PlayState state) {
        super(new Texture("sprite_sheet.png"));
        this.world = world;
        definePlayerSprite();
        playerStand = new TextureRegion(getTexture(), 0, 0, playerSizeX, playerSizeY);
        setBounds(0, 0, playerSizeX / MainGame.PIXELS_PER_METER, playerSizeY / MainGame.PIXELS_PER_METER);
        setRegion(playerStand);

        // Animation.
//        currentState = State.STANDING;
//        previousState = State.STANDING;
//        stateTimer = 0;
//        movingRight = true;
//        Array<TextureRegion> frames = new Array<TextureRegion>();
        // Add moving animation frames to playerRun object.
//        for (int i = 1; i < 3; i++) {
//            frames.add(new TextureRegion(getTexture(), i * playerSizeX, 0, playerSizeX, playerSizeY));
//            playerRun = new Animation(0.1f, frames);
//            System.out.println(i * playerSizeX);
//        }
//        frames.clear();
//        playerStand = new TextureRegion(new Texture("protaganist_small.png"), 0, 0, playerSizeX, playerSizeY);
        Gdx.app.log(TAG, "width coord: height coord: " + MainGame.WIDTH / 2 / MainGame.PIXELS_PER_METER + "," + ((MainGame.WIDTH / 2) + playerSizeX) / MainGame.PIXELS_PER_METER + " : ");
    }

    public void update(float deltaTime) {
        setPosition(box2dBody.getPosition().x - getWidth() / 2,
                box2dBody.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case RUNNING:
//                region = playerRun.getKeyFrame(stateTimer, true);
//                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((box2dBody.getLinearVelocity().x < 0 || !movingRight) && !region.isFlipX()) {
            region.flip(true, false);
            movingRight = false;
        } else if ((box2dBody.getLinearVelocity().x > 0 || movingRight) && region.isFlipX()) {
            region.flip(true, false);
            movingRight = true;
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

        // Set starting position of sprite.
        bdef.position.set(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, MainGame.WELL_DEPTH);

        bdef.type = BodyDef.BodyType.DynamicBody;

        box2dBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
//        CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(playerSizeX / 2 / MainGame.PIXELS_PER_METER, playerSizeY / 2 / MainGame.PIXELS_PER_METER);
        fdef.shape = shape;
        box2dBody.createFixture(fdef);
    }

    public void moveRight() {

//        box2dBody.applyLinearImpulse(new Vector2(1, 0), box2dBody.getWorldCenter(), true);
        box2dBody.setLinearVelocity(1,0);
    }
    
    public void moveLeft() {
        box2dBody.setLinearVelocity(-1,0);
    }

    public void stop() {
        box2dBody.setLinearVelocity(0,0);
    }
}
