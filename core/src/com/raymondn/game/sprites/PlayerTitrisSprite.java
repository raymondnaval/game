package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.shapes.TShape;
import com.raymondn.game.sprites.shapes.TShapeStraightThree;
import com.raymondn.game.states.PlayState;
import java.util.Random;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerTitrisSprite {

//    private Sprite activeTitris;
    private TShape activeTitris;
    private float scale = MainGame.DEFAULT_DESCENT_SPEED / 4;
//    private Sprite[] titrisPieces;
    private boolean doneDescending = false;
    private Rectangle bounds;
    private Vector2[] positions;
    private Vector2 position;
    private Vector2 physicsBodyCoords; // Position the physics box.
    private int currentIncrement = 0;
    private final String TAG = "Class -- PlayerTetrisSprite";
    private BodyDef bdef = new BodyDef();
    private Body body, bodyEdge;
    private Rectangle rect;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
//    private Fixture fixture;
    private PlayState ps;
    private boolean paused = false, touchingRightWall = false, touchingLeftWall = false;

    private TShape[] tPieces;

    public PlayerTitrisSprite(PlayState state) {
        ps = state;
        shape = new PolygonShape();

        position = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER,
                (MainGame.HEIGHT - MainGame.PIXEL_SIZE) / MainGame.PIXELS_PER_METER);

        tPieces = new TShape[6];
        tPieces[0] = new TShapeStraightThree(ps);
//        tPieces[1] = new TShapeC(body, ps);
//        tPieces[2] = new TShapeBigE(body, ps);
//        tPieces[3] = new TShapePlus(body, ps);
//        tPieces[4] = new TShapeSingle(body, ps);
//        tPieces[5] = new TShapeT(body, ps);

        activeTitris = generateRandomTitris();
        activeTitris.activateTShapeBoundaries();

        // Starting position of titris sprite piece.
        positions = activeTitris.getPositions();

    }

    public TShape generateRandomTitris() {
        int rand = new Random().nextInt(6);
        Gdx.app.log(TAG, "titrisPiece: " + rand);
        return tPieces[0];
    }

    public void isTouchingRightWall(boolean tisIs) {
        touchingRightWall = tisIs;
    }

    public void isTouchingLeftWall(boolean tisIs) {
        touchingLeftWall = tisIs;
    }

    public void changeToStopped() {

        Filter stoppedFilter = new Filter();
        stoppedFilter.categoryBits = MainGame.STOPPED_BIT;
        stoppedFilter.maskBits = MainGame.SIDE_WELL_BIT | MainGame.RIGHT_WELL_BIT
                | MainGame.STOPPED_BIT | MainGame.DESCENDING_BIT | MainGame.BOTTOM_WELL_BIT;
        activeTitris.getFixture().setFilterData(stoppedFilter);

//        Gdx.app.log(TAG, "maskbits: " + fixture.getFilterData().categoryBits);
    }

    public String getBit() {
        String bit = "";
        if (activeTitris.getFixture().getFilterData().categoryBits == MainGame.DESCENDING_BIT) {
            bit = "descending";
        }
        if (activeTitris.getFixture().getFilterData().categoryBits == MainGame.STOPPED_BIT) {
            bit = "stopped";
        }
        return bit;
    }

    public void update(float dt) {

//        double degToRads = Math.toRadians(activeTitris.getRotation());
        physicsBodyCoords = new Vector2(
                position.x + (getTitrisWidth() / 2) + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER),
                position.y + (getTitrisHeight() / 2));
//
//        // Change titris physics boundaries when rotated.
//        float rotatedHeight = getTitrisHeight() / 2;
//        if (activeTitris.getRotation() == 90.0f) {
//            newCoords.x = position.x + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
//            newCoords.y = position.y + getTitrisWidth() / 2;
//            rotatedHeight = getTitrisWidth() / 2;
//        }
//        if (activeTitris.getRotation() == 180.0f) {
//            newCoords.x = position.x - getTitrisWidth() / 2 + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
////                newCoords.y = activePiece.getY() + activePiece.getTitrisWidth() / 2;
//            rotatedHeight = getTitrisHeight() / 2;
//        }
//        if (activeTitris.getRotation() == 270.0f) {
//            newCoords.x = position.x + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
//            newCoords.y = position.y - getTitrisWidth() / 2 + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
//            rotatedHeight = -((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
//        }
        // If not descending bit, stop piece.
        if (activeTitris.getFixture().getFilterData().categoryBits == MainGame.DESCENDING_BIT) {
            for (int i = 0; i < positions.length; i++) {
                positions[i].y -= scale;
            }
            physicsBodyCoords.y = positions[0].y + ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
            activeTitris.getBody().setTransform(physicsBodyCoords, 0);
//            body.setTransform(newCoords, (float) degToRads);
        } else {
            // TODO: Stop the image once the bottom part of the piece touches the top of another piece. Create an edge shape.
//            Gdx.app.log(TAG, "getPosition: " + body.getPosition().toString() + " bdef: " + bdef.position.toString() + " base of well: " + MainGame.WELL_DEPTH + " rotated height: " + rotatedHeight);
            
            doneDescending = true;
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                Gdx.app.log(TAG, ex.toString());
            }
//            positions[0].y = activeTitris.getBody().getPosition().y - ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2);
            activeTitris.stop(activeTitris.getBody().getPosition().y - ((MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER) / 2));
        }

    }

    public void debugGetPosition(int pos) {
//        Gdx.app.log(TAG, "getPosition " + pos + ": " + body.getPosition().toString());

    }

    //*****rotate()*****
    public void rotate() {
        boolean canRotate = false;

//        if (activeTitris.getWidth() / MainGame.PIXEL_SIZE > currentIncrement + 1) {
//            if (activeTitris.getRotation() + 90f != 180f) {
//                canRotate = true;
//            }
//        } else {
//            canRotate = true;
//        }
//
//        if (canRotate) {
//            activeTitris.rotate(90f);
//        }
//
//        if (activeTitris.getRotation() == 360.0) {
//            activeTitris.setRotation(0f);
//        }
//
//        Gdx.app.log(TAG, "getrotation: " + activeTitris.getRotation()
//                + " getoriginx: " + activeTitris.getOriginX() + " getoriginY: "
//                + activeTitris.getOriginY() + " regionwidth: " + activeTitris.getRegionWidth()
//                + " regionheight: " + activeTitris.getRegionHeight() + " getwidth: " + activeTitris.getWidth());
    }

    public void setX(boolean movingRight) {

        // If not at the bottom of the well, enable left and right movement.
        if (!doneDescending) {
            if (movingRight) {
                if (!touchingRightWall && currentIncrement < 9) {
                    currentIncrement++;
                    position.x = ps.getHorizontalIncrements()[currentIncrement];
                    activeTitris.increment(currentIncrement);
                }
            } else {
                Gdx.app.log(TAG, "touching left wall: " + touchingLeftWall);
                if (!touchingLeftWall && currentIncrement > 0) {
                    currentIncrement--;
                    position.x = ps.getHorizontalIncrements()[currentIncrement];
                    activeTitris.increment(currentIncrement);
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

    public void pause(boolean pauseGame) {
        paused = pauseGame;
        if (paused) {
            scale = 0;
        } else {
            scale = MainGame.DEFAULT_DESCENT_SPEED / 4;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getTitrisWidth() {
        return activeTitris.getWidth();
    }

    public float getTitrisHeight() {
        return activeTitris.getHeight();
    }

    public boolean isDoneDescending() {
        return doneDescending;
    }

    public Vector2[] getXY() {
        return activeTitris.getPositions();
    }

    public Sprite[] getTitrisPiece() {
        return activeTitris.getFullShape();
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
