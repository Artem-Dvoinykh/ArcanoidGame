package geometry;

import utils.GeometryTester;

/**
 * Class for a point.
 */
public class Point {
    private double x;
    private double y;
    // constructor

    /**
     * Constructor.
     *
     * @param x x coord
     * @param y y coord
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Function to compare our point to 'other' point.
     *
     * @param other another point
     * @return true if our point is strictly larger (lexicographically) than other point
     */
    public boolean larger(Point other) {
        return x > other.getX()
                || (GeometryTester.doubleEquals(x, other.getX()) && y > other.getY());
    }

    /**
     * Function to calculate distance to another point.
     *
     * @param other another point
     * @return the distance from this point to the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2));
    }

    /**
     * To check if the points are the same.
     *
     * @param other another point
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return GeometryTester.doubleEquals(other.getY(), this.y) && GeometryTester.doubleEquals(other.getX(), this.x);
    }

    /**
     * Method to get a random point on a circle.
     *
     * @param r radius
     * @param center Point object of the center of the circle
     * @return a point on the circle
     */
    public static Point getRandomPointOnCircle(Point center, int r) {
        java.util.Random rand = new java.util.Random();
        double x = center.getX() - r + rand.nextInt(2 * r + 1);
        int side = -1 + 2 * rand.nextInt(2);
        double y = center.getY() + side * Math.sqrt(r * r - (center.getX() - x) * (center.getX() - x));
        return new Point(x, y);
    }

    /**
     * Getter for x.
     *
     * @return x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Getter for y.
     *
     * @return y
     */
    public double getY() {
        return this.y;
    }
}