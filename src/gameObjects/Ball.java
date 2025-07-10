package gameObjects;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import utils.CollisionInfo;
import utils.Game;
import utils.GameEnvironment;

/**
 * ball class (ball is a bouncing circle on the screen).
 */

public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity vel;
    private int windowW;
    private int windowH;
    private GameEnvironment gm;

    /**
     * Constructor.
     *
     * @param center Point object
     * @param r radius
     * @param color java.awt.Color object
     * @param gm GameEnvironment
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gm) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        vel = new Velocity(0, 0);
        this.gm = gm;
    }

    /**
     * Constructor.
     *
     * @param center Point object
     * @param r radius
     * @param color java.awt.Color object
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        vel = new Velocity(0, 0);
    }

    /**
     * Constructor.
     *
     * @param x x coord, double
     * @param y y coord, double
     * @param r radius
     * @param color java.awt.Color object
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        vel = new Velocity(0, 0);
    }

    // accessors

    /**
     * Getter for x coord of the center.
     *
     * @return x coord of the center
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Getter for y coord of the center.
     *
     * @return y coord of the center
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Getter for radius.
     *
     * @return radius
     */
    public int getSize() {
        return r;
    }

    /**
     * Getter for color.
     *
     * @return color
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * Setter for velocity.
     *
     * @param v Velociyt object
     */
    public void setVelocity(Velocity v) {
        vel = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * Setter for x coord of the center.
     *
     * @param dx x-axis velocity
     * @param dy y-axsi velocity
     */
    public void setVelocity(double dx, double dy) {
        vel = new Velocity(dx, dy);
    }

    /**
     * Getter for veclocity.
     *
     * @return velocity
     */
    public Velocity getVelocity() {
        return vel;
    }

    /**
     * Setter for the size of the screen in which the ball bounces.
     *
     * @param w width
     * @param h height
     */
    public void setWAndH(int w, int h) {
        windowH = h;
        windowW = w;
    }

    /**
     * Moves the ball one step further by changing the center coords and velocity (if needed).
     */
    public void moveOneStep() {
        int k = 400;
        // get k random points on the circle (start[0] is the center)
        Point[] start = new Point[k];
        start[0] = new Point(center.getX(), center.getY());
        for (int i = 1; i < k; i++) {
            start[i] = Point.getRandomPointOnCircle(center, r);
        }
        // create k projectiles from those points
        Line[] projectile = new Line[k];
        for (int i = 0; i < k; i++) {
            projectile[i] = new Line(start[i],
                    new Point(start[i].getX() + vel.getDx(), start[i].getY() + vel.getDy()));
        }

        CollisionInfo colInf = null;
        for (int i = 0; i < k; i++) {
            CollisionInfo cur = gm.getClosestCollision(projectile[i]);
            if (cur == null) {
                continue;
            }
            if (colInf == null || cur.getDist() < colInf.getDist()) {
                colInf = cur;
            }
        }

        if (colInf == null) {
            center = vel.applyToPoint(center);
        } else {
            center = vel.applyToPointDistanceIsSet(center, colInf.getDist() * 0.5);
            vel = colInf.collisionObject().hit(this, colInf.collisionPoint(), vel);
        }
    }

    /**
     * Draws the ball.
     *
     * @param surface the DrawSurface object to draw the ball on
     */
    // draw the ball on the given DrawSurface
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) center.getX(), (int) center.getY(), r);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Function to change the color of the ball.
     * @param newColor the new color
     */
    public void changeColor(java.awt.Color newColor) {
        this.color = newColor;
    }

    /**
     * Function to remove the ball from the game.
     * @param game the game, duh
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
}

