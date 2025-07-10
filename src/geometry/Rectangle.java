package geometry;

import biuoop.DrawSurface;
import gameObjects.Velocity;

/**
 * Class to represent the rectangle.
 */
public class Rectangle {
    private Point upperLeft;
    private double width, height;

    /**
     * Constructor.
     * @param upperLeft upper-left cornter of the rectangle
     * @param width width
     * @param height height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
    }

    // get a side. Upper side is number 1, numbering is clockwise for other sides.

    /**
     * Funt go get a side. Upper side is number 1, numbering is clockwise for other sides.
     * @param numb the number of the requested side
     * @return the side
     */
    public Line getSide(int numb) {
        if (numb == 1) {
            return new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY()));
        }
        if (numb == 2) {
            return new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                    new Point(upperLeft.getX() + width, upperLeft.getY() + height));
        }
        if (numb == 3) {
            return new Line(new Point(upperLeft.getX() + width, upperLeft.getY() + height),
                    new Point(upperLeft.getX(), upperLeft.getY() + height));
        }
        if (numb == 4) {
            return new Line(new Point(upperLeft.getX(), upperLeft.getY() + height), upperLeft);
        }
        System.out.println("Error in Line.getSide(numb) - numb should be 1 to 4!");
        return null;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.

    /**
     * Func to get all the intersection points with a certain line.
     * @param line the line
     * @return Return a (possibly empty) List of intersection points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> inPoints = new java.util.ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Point inter = this.getSide(i).intersectionWith(line);
            if (inter != null) {
                inPoints.add(inter);
            }
        }
        return inPoints;
    }

    /**
     * Width getter.
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Height getter.
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Upper-left corner getter.
     * @return upper-left corner
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    // draw rectangle on the drawSurface (if it crosses the screen it goes out on the other side)
    // we assume that in can only cross a side border (not upper of lower border)

    /**
     * Func to draw the rectangle.
     * @param d draw surface
     * @param color color
     */
    public void drawOn(DrawSurface d, java.awt.Color color) {
        d.setColor(color);
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }

    /**
     * Func to move the rectangle sidewise.
     * @param upperLeftX the new upper-left after the move
     */
    public void moveSidewise(int upperLeftX) {
        upperLeft = new Point(upperLeftX, upperLeft.getY());
    }

    /**
     * Func to get a new velocity after hitting the rectangle.
     * @param collisionPoint the collision point of a hit
     * @param currentVelocity the velocity before the hit
     * @return the new velocity
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Velocity ans = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        for (int i = 1; i <= 4; i++) {
            Line side = getSide(i);
            if (!side.dotBelongs(collisionPoint)) {
                continue;
            }
            if (i == 1 || i == 3) {
                ans.invertDy();
            }
            if (i == 2 || i == 4) {
                ans.invertDx();
            }
        }
        return ans;
    }

    /**
     * Func to check if the point is on our rectangle.
     * @param p the point
     * @return true/false
     */
    public boolean isPointOnRec(Point p) {
        for (int i = 1; i <= 4; i++) {
            Line side = getSide(i);
            if (side.dotBelongs(p)) {
                return true;
            }
        }
        return false;
    }
}