package utils;

import biuoop.DrawSurface;
import gameObjects.Block;
import gameObjects.Collidable;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;

/**
 * Class to store all the collidables of the game.
 */
public class GameEnvironment {
    private ArrayList<Collidable> collidables;

    /**
     * Constructor.
     */
    public GameEnvironment() {
        collidables = new ArrayList<>();
    }

    /**
     * Func to add a collidable.
     * @param c the collidable
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Func to remove a collidable.
     * @param c the collidable.
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * Func to add a collidable (in this case a rectangle).
     * @param upperLeft upper-left corner of the rectangle
     * @param w width
     * @param h height
     */
    public void addCollidable(Point upperLeft, double w, double h) {
        Collidable c = new Block(new Rectangle(upperLeft, w, h));
        collidables.add(c);
    }

    /**
     * Get the closest collision with the trajectory out of all available collidables.
     * @param trajectory the trajectory
     * @return Assume an object moving from line.start() to line.end().
     *      If this object will not collide with any of the collidables
     *      in this collection, return null. Else, return the information
     *      about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo ans = null;
        double dist = 0;
        for (int i = 0; i < collidables.size(); i++) {
            Collidable cur = collidables.get(i);
            Point curColPoint = trajectory.closestIntersectionToStartOfLine(cur.getCollisionRectangle());
            if (curColPoint == null) {
                continue;
            }
            if (ans == null || dist > trajectory.start().distance(curColPoint)) {
                dist = trajectory.start().distance(curColPoint);
                ans = new CollisionInfo(curColPoint, cur, dist);
            }
        }
        return ans;
    }

    /**
     * Func to draw all the collidables.
     * @param d the drew surface
     */
    public void drawOn(DrawSurface d) {
        for (int i = 0; i < collidables.size(); i++) {
            ((Block) collidables.get(i)).drawOn(d);
        }
    }
}