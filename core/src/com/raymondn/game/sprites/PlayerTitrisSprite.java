package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.util.Random;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerTitrisSprite {

    private Sprite activeTitris;
    private float scale = MainGame.DEFAULT_DESCENT_SPEED / 4;
    private Sprite[] titrisPieces;
    private boolean doneDescending = false;
    private Rectangle bounds;
    private Texture titris, titris2;
    private Vector2 position;
    private int currentIncrement = 0;
    private float[] increments = new float[10];
    private final String TAG = "Class -- PlayerTetrisSprite";
    private BodyDef bdef = new BodyDef();
    private Body body;
    private Rectangle rect;
    private PolygonShape shape;
    private FixtureDef fixture;
    private PlayState ps;

    public PlayerTitrisSprite(PlayState state) {

        ps = state;
        shape = new PolygonShape();

        // Titris pieces.
        titris2 = new Texture(Gdx.files.internal("sprite_sheet.png"));
        titris = new Texture(Gdx.files.internal("titris_test.png"));
        titrisPieces = new Sprite[]{
            new Sprite(titris, 0, 0, 16, 16),
            new Sprite(titris, 0, 0, 32, 16),
            new Sprite(titris2, 0, 64, 48, 16)
        };

        activeTitris = generateTitrisPiece();

        // Starting position of titris piece.
        position = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER);
        /* DEBUG */
        //position = new Vector2((MainGame.WIDTH/MainGame.PIXELS_PER_METER)/2, (MainGame.HEIGHT/ MainGame.PIXELS_PER_METER)/2);

        bounds = new Rectangle((MainGame.PIXEL_SIZE / 2) / MainGame.PIXELS_PER_METER,
                (activeTitris.getHeight() / 2) / MainGame.PIXELS_PER_METER,
                activeTitris.getRegionWidth(), activeTitris.getRegionHeight());

        Gdx.app.log(TAG, "titris width: " + titris.getWidth());

        defineTitris();

        // Horizontal increments in well. MOVE TO PlayState class.
        increments[0] = MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER;
        increments[1] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10))) / MainGame.PIXELS_PER_METER;
        increments[2] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 2))) / MainGame.PIXELS_PER_METER;
        increments[3] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 3))) / MainGame.PIXELS_PER_METER;
        increments[4] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 4))) / MainGame.PIXELS_PER_METER;
        increments[5] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 5))) / MainGame.PIXELS_PER_METER;
        increments[6] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 6))) / MainGame.PIXELS_PER_METER;
        increments[7] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 7))) / MainGame.PIXELS_PER_METER;
        increments[8] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 8))) / MainGame.PIXELS_PER_METER;
        increments[9] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 9))) / MainGame.PIXELS_PER_METER;
    }

    private Sprite generateTitrisPiece() {
        int rand = new Random().nextInt(titrisPieces.length);

        return titrisPieces[rand];
    }

    private void defineTitris() {
        activeTitris.setOrigin((MainGame.PIXEL_SIZE / 2) / MainGame.PIXELS_PER_METER,
                (activeTitris.getHeight() / 2) / MainGame.PIXELS_PER_METER);
        activeTitris.setBounds(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER, activeTitris.getRegionWidth(), activeTitris.getRegionHeight());
        fixture = new FixtureDef();

        // Define physics bit.
        fixture.filter.categoryBits = MainGame.TITRIS_BIT;

        // Collision with side walls and bottom well and other titris pieces.
        fixture.filter.maskBits = MainGame.SIDE_WELL_BIT
                | MainGame.BOTTOM_WELL_BIT | MainGame.TITRIS_BIT;

        // Create a rectangle object because the the objects in the loop are all rectangles.
        rect = new Rectangle(
                position.x, position.y,
                activeTitris.getWidth() / MainGame.PIXELS_PER_METER,
                activeTitris.getHeight() / MainGame.PIXELS_PER_METER);

        bdef.type = BodyDef.BodyType.DynamicBody;

        // Position the rectangle exactly where its drawn on the Tile map.
        bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

        // @TODO Add comments to these below...
        body = ps.getWorld().createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        fixture.shape = shape;

        body.createFixture(fixture).setUserData("titris");

        EdgeShape side = new EdgeShape();
        side.set(new Vector2(-2 / MainGame.PIXELS_PER_METER, 6 / MainGame.PIXELS_PER_METER),
                new Vector2(2 / MainGame.PIXELS_PER_METER, 6 / MainGame.PIXELS_PER_METER));
        fixture.isSensor = true;
//        body.createFixture(fixture).setUserData(this);
    }

    public void update(float dt) {

        double degToRads = Math.toRadians(activeTitris.getRotation());
        Vector2 newCoords = new Vector2(
                position.x + (getTitrisWidth() / 2),
                position.y + (getTitrisHeight() / 2));

        // If not descending, generate new piece.
//        if (position.y > MainGame.WELL_DEPTH - (activeTitris.getRegionHeight() / MainGame.PIXELS_PER_METER)) {
        if (!ps.madeContact()) {

            position.y -= scale;

            // Change titris physics boundaries when rotated.
            if (activeTitris.getRotation() == 90.0f) {
                newCoords.x = position.x + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
                newCoords.y = position.y + getTitrisWidth() / 2;
            }
            if (activeTitris.getRotation() == 180.0f) {
                newCoords.x = position.x - getTitrisWidth() / 2 + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
//                newCoords.y = activePiece.getY() + activePiece.getTitrisWidth() / 2;
            }
            if (activeTitris.getRotation() == 270.0f) {
                newCoords.x = position.x + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
                newCoords.y = position.y - getTitrisWidth() / 2 + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
            }
            bounds.setPosition(position.x, position.y);
            body.setTransform(newCoords, (float) degToRads);

        } else {

            // Stop the image at the base of the well.
            position.y = MainGame.WELL_DEPTH - (activeTitris.getRegionHeight() / MainGame.PIXELS_PER_METER);

//            Gdx.app.log(TAG, "getTransform: " + body.getTransform().getPosition().toString());
            body.setTransform(newCoords, (float) degToRads);
            doneDescending = true;
        }

    }

    public void rotate() {
        boolean canRotate = false;

        if (currentIncrement == 0 && activeTitris.getWidth() == MainGame.PIXEL_SIZE * 2) {
            if (activeTitris.getRotation() + 90f != 180f) {
                canRotate = true;
            }
        } else {
            canRotate = true;
        }

        if (canRotate) {
            activeTitris.rotate(90f);
        }

        if (activeTitris.getRotation() == 360.0) {
            activeTitris.setRotation(0f);
        }

        Gdx.app.log(TAG, "getrotation: " + activeTitris.getRotation()
                + " getoriginx: " + activeTitris.getOriginX() + " getoriginY: "
                + activeTitris.getOriginY() + " regionwidth: " + activeTitris.getRegionWidth()
                + " regionheight: " + activeTitris.getRegionHeight() + " getscalex: " + activeTitris.getScaleX());
    }

    public void setX(boolean movingRight) {

        // If not at the bottom of the well, enable left and right movement.
        if (!doneDescending) {
            if (movingRight) {
                if (currentIncrement < 9 && activeTitris.getWidth() == MainGame.PIXEL_SIZE) {
                    currentIncrement++;
                    position.x = increments[currentIncrement];
                }
                if (currentIncrement < 8 && activeTitris.getWidth() == MainGame.PIXEL_SIZE * 2) {
                    currentIncrement++;
                    position.x = increments[currentIncrement];
                }
                if (currentIncrement < 7 && activeTitris.getWidth() == MainGame.PIXEL_SIZE * 3) {
                    currentIncrement++;
                    position.x = increments[currentIncrement];
                }
            } else {
                if (currentIncrement > 0 && activeTitris.getRotation() != 180.0) {
                    currentIncrement--;
                    position.x = increments[currentIncrement];
                }
                if (currentIncrement > 1 && activeTitris.getRotation() == 180.0) {
                    currentIncrement--;
                    position.x = increments[currentIncrement];
                }
            }
            Gdx.app.log("increment", "" + currentIncrement);
        }
    }

    public void accelerateDescent(boolean faster) {
        if (!doneDescending) {
            if (faster) {
                scale = MainGame.DEFAULT_DESCENT_SPEED * 5;
            } else {
                scale = MainGame.DEFAULT_DESCENT_SPEED / 4;
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return position.x;
    }

    public float getTitrisWidth() {
        return activeTitris.getWidth() / MainGame.PIXELS_PER_METER;
    }

    public float getTitrisHeight() {
        return activeTitris.getHeight() / MainGame.PIXELS_PER_METER;
    }

    public boolean isDoneDescending() {
        return doneDescending;
    }

    public float getY() {
        return position.y;
    }

    public TextureRegion getTitrisPiece() {
        return activeTitris;
    }

    public Sprite getSprite() {
        return activeTitris;
    }

    public float getOriginX() {
        return activeTitris.getOriginX();
    }

    public float getOriginY() {
        return activeTitris.getOriginY();
    }

    public float getScaleX() {
        return activeTitris.getScaleX();
    }

    public float getScaleY() {
        return activeTitris.getScaleY();
    }

    public float getRotation() {
        return activeTitris.getRotation();
    }
}
