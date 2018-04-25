package com.raymondn.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;
import java.util.Random;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayerTitrisSprite {

    private Sprite activeTitris;
    private float scale = .88f / MainGame.PIXELS_PER_METER;
//    private float titrisWidth = 16 / MainGame.PIXELS_PER_METER, titrisHeight = 16 / MainGame.PIXELS_PER_METER;
    private Sprite[] titrisPieces;
    private boolean doneDescending = false;
    private Rectangle bounds;
    private Texture titris;
    private Vector2 position;
    private int currentIncrement = 0;
    private float[] increments = new float[10];
    private final String TAG = "Class -- PlayerTetrisSprite";

    public PlayerTitrisSprite(PlayState state) {

        // Titris pieces.
        titris = new Texture(Gdx.files.internal("titris_test.png"));
        titrisPieces = new Sprite[]{
            new Sprite(titris, 0, 0, 16, 16),
            new Sprite(titris, 16, 0, 16, 16),
            new Sprite(titris, 32, 0, 16, 16),
            new Sprite(titris, 16, 0, 32, 16),
            new Sprite(titris, 0, 0, 32, 16)
        };

        activeTitris = generateTitrisPiece();
        activeTitris.setOrigin((MainGame.PIXEL_SIZE / 2) / MainGame.PIXELS_PER_METER, (activeTitris.getHeight() / 2) / MainGame.PIXELS_PER_METER);
        activeTitris.setBounds(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER, activeTitris.getRegionWidth(), activeTitris.getRegionHeight());

        position = new Vector2(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER);

        bounds = new Rectangle(MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER, (MainGame.HEIGHT - titris.getHeight()) / MainGame.PIXELS_PER_METER, activeTitris.getRegionWidth(), activeTitris.getRegionHeight());

        Gdx.app.log(TAG, "titris width: " + titris.getWidth());

        // Horizontal increments in well.
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

        // If it's the elongated piece, change the width dimensions.
        if (rand == 3) {
//            titrisWidth = 32 / MainGame.PIXELS_PER_METER;
        }

        return titrisPieces[rand];
    }

    public void update(float dt) {

        // If not descending, generate new piece.
        if (position.y > MainGame.WELL_DEPTH - (activeTitris.getRegionHeight() / MainGame.PIXELS_PER_METER)) {
            position.y -= scale;
            bounds.setPosition(position.x, position.y);
        } else {

            // Stop the image at the base of the well.
            position.y = MainGame.WELL_DEPTH - (activeTitris.getRegionHeight() / MainGame.PIXELS_PER_METER);

            doneDescending = true;
        }
//        Gdx.app.log(TAG, "bounds.y: " + bounds.y);
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
                + activeTitris.getOriginY() + " width: " + activeTitris.getWidth()
                + " height: " + activeTitris.getHeight());
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
                scale = 4.8f / MainGame.PIXELS_PER_METER;
            } else {
                scale = 0.88f / MainGame.PIXELS_PER_METER;
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
