package utils;

import gameObjects.Ball;
import gameObjects.Block;

/**
 * Interface for a listener that listens for all collisions between blocks and balls.
 */
public interface HitListener {
    /**
     * Function called when a block is being hit by a ball.
     * @param beingHit the block
     * @param hitter the ball
     */
    void hitEvent(Block beingHit, Ball hitter);
}
