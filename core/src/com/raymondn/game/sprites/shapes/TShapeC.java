package com.raymondn.game.sprites.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.raymondn.game.MainGame;
import com.raymondn.game.states.PlayState;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class TShapeC extends TShape implements TShapeInterface {

    private Sprite[] squares;
    private Vector2[] spritePositions;
    private float height, width;
    private final int TOTAL_SQUARES = 5; // Number of squres in graphic.
    private final String TAG = "Class: TShapeC";

    public TShapeC(PlayState ps) {
        super(ps);
        tileWidth = 3;
        tileHeight = 2;
        squares = getRandomSquares(TOTAL_SQUARES);
        spritePositions = new Vector2[TOTAL_SQUARES];
        activeBoundaries = new Rectangle[TOTAL_SQUARES];
        setFullShape(squares);
        positionSprites();
        height = MainGame.PIXEL_SIZE * 2;
        width = MainGame.PIXEL_SIZE * 3;
    }

    @Override
    public void increment(int pos) {
        spritePositions[0].x = getState().getHorizontalIncrements()[pos];
        spritePositions[1].x = spritePositions[0].x;
        spritePositions[2].x = spritePositions[1].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        spritePositions[3].x = spritePositions[2].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER);
        spritePositions[4].x = spritePositions[3].x;
    }

    @Override
    protected void positionSprites() {
        spritePositions[0] = getStartingPosition();
        spritePositions[1] = new Vector2(getStartingPosition().x,
                getStartingPosition().y
                - (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER));
        spritePositions[2] = new Vector2(spritePositions[1].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER),
                spritePositions[1].y);
        spritePositions[3] = new Vector2(spritePositions[2].x
                + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER),
                spritePositions[1].y);
        spritePositions[4] = new Vector2(spritePositions[3].x,
                getStartingPosition().y);
        setPositions(spritePositions);
    }

    @Override
    public int getTileWidth() {
        return tileWidth;
    }
    
    @Override
    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }
    
    @Override
    public void stop(float bottomSprite) {

    }
}
