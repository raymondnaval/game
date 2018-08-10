package com.raymondn.game.sprites.shapes;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public interface TShapeInterface {

    /**
     * Stop all sprites from descending. Will change for sprite rotation.
     *
     * @param bottomSprite The y position of the bottom-most sprite.
     */
    public void stop(float bottomSprite);
}
