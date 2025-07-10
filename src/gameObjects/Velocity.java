package gameObjects;

import geometry.Point;

/**
 * Class for velocity.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param dx x coord
     * @param dy y coord
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Method to get a velocity from angle and speed.
     *
     * @param angle angle in degrees (between the vector and x-axis)
     * @param speed speed
     * @return velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        double dx = -1 * speed * cos;
        double dy = -1 * speed * sin;
        return new Velocity(dx, dy);
    }

    /**
     * Getter for x coord.
     *
     * @return x coord
     */
    public double getDx() {
        return dx;
    }

    /**
     * Getter for y coord.
     *
     * @return y coord
     */
    public double getDy() {
        return dy;
    }

    /**
     * Method to invert x coord.
     */
    public void invertDx() {
        dx *= -1;
    }

    /**
     * Method to invert y coord.
     */
    public void invertDy() {
        dy *= -1;
    }

    /**
     * Method ot apply the velocity to a point.
     *
     * @param p the point
     * @return the point after applying the velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Func to apply the direction of our velocity to a point with the total traveling distance being set.
     * @param p the point
     * @param dist the distance
     * @return the new point
     */
    public Point applyToPointDistanceIsSet(Point p, double dist) {
        double initDist = Math.sqrt(dx * dx + dy * dy);
        double coefficient = dist / initDist;
        double dx1 = dx * coefficient, dy1 = dy * coefficient;
        return new Point(p.getX() + dx1, p.getY() + dy1);
    }
}