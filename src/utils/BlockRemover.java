package utils;

import gameObjects.Ball;
import gameObjects.Block;

/**
 * Listener-class to remove a block when a ball and a block of different colors collide.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game the game blocks belong to
     * @param remainingBlocks counter of the remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        remainingBlocks.decrease(1);
        beingHit.removeFromGame(game);
        hitter.changeColor(beingHit.getColor());
    }
}
