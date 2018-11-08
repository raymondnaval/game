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
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.util.ArrayList;

/**
 *
 * @author Raymond Naval
 */
public class ShooterProjectile extends Sprite {

    public World world;
    public Body boxBody;
    private PlayState play;
    private int projectileSize = 10;
    private Animation<TextureRegion> projectileAnim;
    private TextureRegion tr;
    private final String TAG = "Class: ShooterProjectile";
    private float moveUp = 0f;
    private boolean isFired = false;
    private Vector2 startingPoint;

    public ShooterProjectile(World world, PlayState play) {
        super(new Texture("sprite_sheet.png"));
        this.world = world;
        this.play = play;
        startingPoint = new Vector2(); // Start of projectile trajectory.
//        defineProjectile();
        tr = new TextureRegion(getTexture(),
                SpritesheetCoordinates.PROJ_BL_0_X,
                SpritesheetCoordinates.PROJ_BL_0_Y,
                SpritesheetCoordinates.PROJ_WIDTH,
                SpritesheetCoordinates.PROJ_HGT_0);

        ArrayList<TextureRegion> frames = new ArrayList<TextureRegion>();
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.PROJ_BL_0_X,
                SpritesheetCoordinates.PROJ_BL_0_Y,
                SpritesheetCoordinates.PROJ_WIDTH,
                SpritesheetCoordinates.PROJ_HGT_0));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.PROJ_BL_1_X,
                SpritesheetCoordinates.PROJ_BL_1_Y,
                SpritesheetCoordinates.PROJ_WIDTH,
                SpritesheetCoordinates.PROJ_HGT_0));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.PROJ_BL_2_X,
                SpritesheetCoordinates.PROJ_BL_2_Y,
                SpritesheetCoordinates.PROJ_WIDTH,
                SpritesheetCoordinates.PROJ_HGT_0));
        frames.add(new TextureRegion(getTexture(),
                SpritesheetCoordinates.PROJ_BL_3_X,
                SpritesheetCoordinates.PROJ_BL_3_Y,
                SpritesheetCoordinates.PROJ_WIDTH,
                SpritesheetCoordinates.PROJ_HGT_0));
        projectileAnim = new Animation(1 / 7f, frames);
        frames.clear();
        setBounds(1,
                2,
                SpritesheetCoordinates.PROJ_WIDTH / MainGame.PIXELS_PER_METER,
                SpritesheetCoordinates.PROJ_HGT_0 / MainGame.PIXELS_PER_METER);

    }
    
    // *****isProjectileDormant()*****
    // if projectile hasn't been fired and isn't on trajectory, return true. 
    // This will keep the sprite from being drawn in PlayState class.
    public boolean isProjectileDormant() {
        boolean isDormant = false;
        if(moveUp == 0 && isFired == false) {
            isDormant = true;
        }
        return isDormant;
    }

    public void updateProjectile(float deltaTime) {
        setPosition(startingPoint.x,
                startingPoint.y);
        setRegion(tr);

        // If fired buton is pressed, start projectile trajectory. Else set to 
        // 0.
        if (isFired) {
            moveUp += .04f;
        } else {
            moveUp = 0;
        }
        setBounds(getX(), getY() + moveUp, getWidth(), getHeight());

        // If projectile leaves screen, end projectile trajectory.
        if (getY() >= play.getVerticalIncrements()[32]) {
            isFired = false;
        }
//        Gdx.app.log(TAG, "updateProjectile -- getY: " + getY());
    }

    public void fire(float x, float y) {
        
        // Don't fire projectile if it is still on its trajectory.
        if (!isFired) {
            this.startingPoint = new Vector2(x, y);
            isFired = true;
        }
        Gdx.app.log(TAG, "fired: " + isFired);
    }

    private void defineProjectile() {
        BodyDef bdef = new BodyDef();

        bdef.position.set((MainGame.LEFT_WALL + ((MainGame.RIGHT_WALL
                - MainGame.LEFT_WALL) / 2)) / MainGame.PIXELS_PER_METER,
                MainGame.WELL_DEPTH + (64 / MainGame.PIXELS_PER_METER));

        bdef.type = BodyDef.BodyType.KinematicBody;
        boxBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(projectileSize / 2 / MainGame.PIXELS_PER_METER,
                projectileSize / 2 / MainGame.PIXELS_PER_METER);
        fdef.shape = shape;
        boxBody.createFixture(fdef);
        Gdx.app.log(TAG, boxBody.getPosition().toString());
    }

}
