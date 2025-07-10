package gameObjects;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import utils.Game;
import utils.HitListener;
import utils.HitNotifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a block on the screen.
 */
public class Block implements Sprite, Collidable, HitNotifier {
    private Rectangle rec;
    private java.awt.Color color = java.awt.Color.PINK;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Constructor.
     * @param rec the rectangle that the block represents
     */
    public Block(Rectangle rec) {
        this.rec = rec;
    }

    /**
     * Constructor.
     * @param rec the rectangle that the block represents
     * @param color the color of the block
     */
    public Block(Rectangle rec, java.awt.Color color) {
        this.rec = rec;
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rec;
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return rec.hit(collisionPoint, currentVelocity);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        int x = (int) rec.getUpperLeft().getX(), y = (int) rec.getUpperLeft().getY();
        d.fillRectangle(x, y, (int) rec.getWidth(), (int) rec.getHeight());
    }

    @Override
    public void timePassed() {
        // nothing
    }

    /**
     * Func to check if the color of the block matches the color of the ball.
     * @param ball the ball
     * @return true/false
     */
    public boolean ballColorMatch(Ball ball) {
        return color.equals(ball.getColor());
    }

    /**
     * Func to remove our block from the game.
     * @param game the game
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Func to get the color of our block.
     * @return the color
     */
    public java.awt.Color getColor() {
        return color;
    }
}
