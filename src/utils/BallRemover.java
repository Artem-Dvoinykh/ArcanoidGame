package utils;

import gameObjects.Ball;
import gameObjects.Block;

/**
 * Listener calss to remove a ball from the game.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor.
     * @param game the game balls belong to
     * @param remainingBalls counter object keeping the count of all balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        remainingBalls.decrease(1);
        hitter.removeFromGame(game);
    }
}
