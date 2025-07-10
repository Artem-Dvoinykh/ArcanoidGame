package utils;

import gameObjects.Collidable;
import geometry.Point;

/**
 * Class to store information about the collision.
 */
public class CollisionInfo {
    private Point colPoint;
    private Collidable obj;
    private double dist;

    /**
     * Constructor.
     * @param obj collidable with which we collided
     * @param colPoint collision point
     */
    public CollisionInfo(Point colPoint, Collidable obj) {
        this.colPoint = colPoint;
        this.obj = obj;
    }

    /**
     * Constructor.
     * @param dist distance to the collision point
     * @param obj collidable with which we collided
     * @param colPoint collision point
     */
    public CollisionInfo(Point colPoint, Collidable obj, double dist) {
        this.colPoint = colPoint;
        this.obj = obj;
        this.dist = dist;
    }

    // the point at which the collision occurs.
    /**
     * Getter.
     * @return collisoin point
     */
    public Point collisionPoint() {
        return colPoint;
    }

    /**
     * Getter.
     * @return collidable object
     */
    public Collidable collisionObject() {
        return obj;
    }

    /**
     * Getter.
     * @return distance
     */
    public double getDist() {
        return dist;
    }
}