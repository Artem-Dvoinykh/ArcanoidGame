package geometry;

import utils.GeometryTester;

/**
 * Class representing a line (a segment, really) (also, a vector).
 */
public class Line {
    private Point start;
    private Point end;
    private double vectorX; // X coordinate of the vector start -> end
    private double vectorY; // Y coordinate
    private boolean isDot;

    /**
     * Constructor.
     *
     * @param startIn the beginning of a segment
     * @param endIn the end of a segment
     */
    public Line(Point startIn, Point endIn) {
        start = new Point(startIn.getX(), startIn.getY());
        end = new Point(endIn.getX(), endIn.getY());

        // setting the vector coordinates
        vectorX = end.getX() - start.getX();
        vectorY = end.getY() - start.getY();

        if (GeometryTester.doubleEquals(vectorX, 0) && GeometryTester.doubleEquals(vectorY, 0)) {
            isDot = true;
        } else {
            isDot = false;
        }
    }

    /**
     * Constructor.
     *
     * @param x1 x coord of the beginning
     * @param y1 y coord of the beginning
     * @param x2 x coord of the end
     * @param y2 y coord of the end
     */
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);

        // setting the vector coordinates
        vectorX = end.getX() - start.getX();
        vectorY = end.getY() - start.getY();

        if (GeometryTester.doubleEquals(vectorX, 0) && GeometryTester.doubleEquals(vectorY, 0)) {
            isDot = true;
        } else {
            isDot = false;
        }
    }

    /**
     * Getter for the beginning.
     *
     * @return the point of the beginning
     */
    public Point start() {
        return start;
    }

    /**
     * Getter for the end.
     *
     * @return the point of the end
     */
    public Point end() {
        return end;
    }

    /**
     * Getter for the x coord of the vector.
     *
     * @return x coord of the vector
     */
    public double getVectorX() {
        return vectorX;
    }

    /**
     * Getter for the y coord of the vector.
     *
     * @return y coord of the vector
     */
    public double getVectorY() {
        return vectorY;
    }

    /**
     * Checks if the beginning and the end are the same.
     *
     * @return true/false if the segment is in fact a dot
     */
    public boolean getIsDot() {
        return isDot;
    }


    /**
     * Getter for the length of the segment.
     *
     * @return the length
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Finds the middle point of the segment.
     *
     * @return the middle point of the segment
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Method to calculate the cross product between our vector and a vector made out start->p.
     *
     * @param p the p Point
     * @return x, x > 0 if p to the left of the segment, x < 0 if to the right. x = 0 if they are on the same line
     */
    public double crossProduct(Point p) {
        return vectorX * (p.getY() - start.getY()) - (p.getX() - start.getX()) * vectorY;
    }

    /**
     * Method to check if a dot a belongs to our segment.
     *
     * @param a the 'a' Point
     * @return true/false depending on weather the point a belongs to our segment
     */
    public boolean dotBelongs(Point a) {
        if (isDot) {
            return start.equals(a);
        }

        Line lSeg = new Line(start, a), rSeg = new Line(a, end);
        return GeometryTester.doubleEquals(lSeg.length() + rSeg.length(), this.length());
    }

    /**
     * Method to check if two lines are parallel.
     *
     * @param a first line
     * @param b second line
     * @return true/false depending on weather the lines are parallel
     */
    public static boolean parallel(Line a, Line b) {
        if (a.getIsDot() || b.getIsDot()) {
            return true;
        }

        // checking if we have vertical lines
        if (GeometryTester.doubleEquals(a.getVectorX(), 0) || GeometryTester.doubleEquals(b.getVectorX(), 0)) {
            return GeometryTester.doubleEquals(a.getVectorX(), 0)
                    && GeometryTester.doubleEquals(b.getVectorX(), 0);
        }

        return GeometryTester.doubleEquals(a.getVectorY() / a.getVectorX(), b.getVectorY() / b.getVectorX());
    }

    /**
     * Method to check if our line intersects with line 'other'.
     *
     * @param other the other line
     * @return true/false depending on weather the lines intersect
     */
    public boolean isIntersecting(Line other) {
        if (isDot) {
            return other.dotBelongs(start);
        }
        if (other.getIsDot()) {
            return this.dotBelongs(other.start());
        }

        // I use the cross product here cause it works with vertical lines as well
        if (this.crossProduct(other.end()) * this.crossProduct(other.start()) > 0) {
            return false;
        }
        if (other.crossProduct(end) * other.crossProduct(start) > 0) {
            return false;
        }

        // at this point if they are not parallel then they intersect
        if (!Line.parallel(this, other)) {
            return true;
        }

        // if they are parallel, then the segments are on the same line. Let's see if they intersect
        if (start.larger(other.end()) || other.start().larger(end)) {
            return false;
        }
        return true;
    }

    /**
     * Method to check if 2 lines intersect with our line.
     *
     * @param other2 the other line 2
     * @param other1 the other line 1
     * @return true/false depending on weather both lines intersect our line
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Method to returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other the other line
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        // check that they INTERSECT
        if (!this.isIntersecting(other)) {
            return null;
        }

        // check if any of the lines are dots
        if (isDot) {
            return start;
        }
        if (other.getIsDot()) {
            return other.start();
        }

        // check if we have INFINITE intersections
        if (Line.parallel(this, other)) {
            if (start.equals(other.end())) {
                return start;
            }
            if (other.start().equals(end)) {
                return end;
            }
            return null;
        }

        // at this moment the lines INTERSECT and NOT PARALLEL

        // let's check if our segment is VERTICAL
        if (GeometryTester.doubleEquals(vectorX, 0)) {
            // get the equation of the second line: y = k2 * x + b2
            double k2 = (other.start().getY() - other.end().getY()) / (other.start().getX() - other.end().getX()),
                    b2 = other.end().getY() - k2 * other.end().getX();

            // get the intersection Point
            double theX = start.getX(), theY = k2 * theX + b2;
            return new Point(theX, theY);
        }

        // let's check if the other segment is VERTICAL
        if (GeometryTester.doubleEquals(other.getVectorX(), 0)) {
            // get the equation of the first line: y = k1 * x + b1
            double k1 = (start.getY() - end.getY()) / (start.getX() - end.getX()), b1 = end.getY() - k1 * end.getX();

            // get the intersection Point
            double theX = other.start().getX(), theY = k1 * theX + b1;
            return new Point(theX, theY);
        }

        // both lines are NOT VERTICAL
        double k1 = (start.getY() - end.getY()) / (start.getX() - end.getX()), b1 = end.getY() - k1 * end.getX();
        double k2 = (other.start().getY() - other.end().getY()) / (other.start().getX() - other.end().getX()),
                b2 = other.end().getY() - k2 * other.end().getX();

        // get the coordinates of the intersection
        double theX = (b2 - b1) / (k1 - k2), theY = k2 * theX + b2;
        return new Point(theX, theY);
    }

    /**
     * get the closes intersectoin of this line with a given rectangle.
     * @param rect the rectangle
     * @return the closes point of intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> inPoints = rect.intersectionPoints(this);
        Point ans = null;
        double dist = start.distance(end) + 1;
        for (int i = 0; i < inPoints.size(); i++) {
            Point cur = inPoints.get(i);
            double curDist = start.distance(cur);
            if (curDist < dist) {
                dist = curDist;
                ans = cur;
            }
        }
        return ans;
    }

    /**
     * Method to check if the lines are the same.
     *
     * @param other the other line
     * @return true/false depending on weather the lines are the same
     */
    public boolean equals(Line other) {
        return start.equals(other.start) && end.equals(other.end());
    }

    /**
     * Method to check if the line is vertical.
     *
     * @return true/false depending on weather the line is vertical
     */
    public boolean isVertical() {
        return GeometryTester.doubleEquals(vectorX, 0);
    }

    /**
     * Method to check if the line is horizontal.
     *
     * @return true/false depending on weather the line is horizontal
     */
    public boolean isHorizontal() {
        return GeometryTester.doubleEquals(vectorY, 0);
    }
}