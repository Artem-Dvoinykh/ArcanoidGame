/******************
 Name: Artem Dvoinykh
 ID: 345853543
 Assignment: Ex3
 *******************/

package utils;

import geometry.Line;
import geometry.Point;

/**
 * This class does some simple tessting of the Point and Line classes.
 */
public class GeometryTester {

    static final double COMP_T = 0.00001;

    /**
     *
     * @param a
     * @param b
     * @return true/false if the doubles are true on not
     */
    public static boolean doubleEquals(double a, double b) {
        return  Math.abs(a - b) < GeometryTester.COMP_T;
    }

    /**
     * The method is in charge of testing the Point class.
     *
     * @return true if not mistakes were found, false otherwise.
     */
    public boolean testPoint() {
        boolean mistake = false;
        Point p1 = new Point(12, 2);
        Point p2 = new Point(9, -2);

        if (!doubleEquals(p1.getX(), 12)) {
            System.out.println("Test p1.getX() failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.getY(), 2)) {
            System.out.println("Test p1.getY() failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.distance(p1), 0)) {
            System.out.println("Test distance to self failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.distance(p2), p2.distance(p1))) {
            System.out.println("Test distance symmetry failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.distance(p2), 5)) {
            System.out.println("Test distance failed.");
            mistake = true;
        }
        if (!p1.equals(p1)) {
            System.out.println("Equality to self failed.");
            mistake = true;
        }
        if (!p1.equals(new Point(12, 2))) {
            System.out.println("Equality failed.");
            mistake = true;
        }
        if (p1.equals(p2)) {
            System.out.println("Equality failed -- should not be equal.");
            mistake = true;
        }

        return !mistake;
    }

    /**
     * The method is in charge of testing the Line class.
     *
     * @return true if not mistakes were found, false otherwise.
     */
    public boolean testLine() {
        boolean mistakes = false;
        Line l = new Line(-5, -5, 5, 5);
        Line l2 = new Line(0, -5, 0, 10);

        Point a = l.intersectionWith(l2);
        System.out.println(a.getX() + " " + a.getY());

        a = l2.intersectionWith(l);
        System.out.println(a.getX() + " " + a.getY());

        return !mistakes;
    }

    /**
     * Main method, running tests on both the point and the line classes.
     * @param args ignored.
     */
    public static void main(String[] args) {
        GeometryTester tester = new GeometryTester();
        if (tester.testPoint() && tester.testLine()) {
            System.out.println("Test Completed Successfully!");
        } else {
            System.out.println("Found failing tests.");
        }
    }
}