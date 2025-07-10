package gameObjects;

import geometry.Point;
import geometry.Rectangle;

/**
 * Interface for all the collidable objects.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return a Rectangle object
     */
    Rectangle getCollisionRectangle();

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    /**
     * Return the new velocity after hitting the object.
     * @param hitter the ball that hits our collidable
     * @param collisionPoint point of the collision with the collidable
     * @param currentVelocity velocity before hitting the collidable
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}