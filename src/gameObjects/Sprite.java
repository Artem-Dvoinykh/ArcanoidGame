package gameObjects;

import biuoop.DrawSurface;

/**
 * Interface for the sprites (all singing and dancing crap of the world).
 */
public interface Sprite {
    /**
     * Func to draw the sprite.
     * @param d draw surface
     */
    void drawOn(DrawSurface d);

    /**
     * Func to notify the sprite that the time has passed.
     */
    void timePassed();
}