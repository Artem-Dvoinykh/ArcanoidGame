package gameObjects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import utils.Game;

import java.awt.Color;

/**
 * Class to represent the paddle.
 */
public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle leftRec; // the most left part of the paddle (region 1 out of 5)
    private final int movePixels = 6;
    private int segLen = 30, segH = 20;
    private int screenW = 800, screenH = 600;

    /**
     * Constructor.
     * @param leftRec the most-left rectangle of the paddle (out of 5)
     * @param keyboard the keyboard sensor
     * @param screenW screen width
     * @param screenH screen hight
     */
    public Paddle(Rectangle leftRec, KeyboardSensor keyboard, int screenW, int screenH) {
        this.keyboard = keyboard;
        this.leftRec = leftRec;
        segLen = (int) leftRec.getWidth();
        segH = (int) leftRec.getHeight();
        this.screenW = screenW;
        this.screenH = screenH;
    }

    /**
     * Func to move the paddle to the left.
     */
    public void moveLeft() {
        double curLeft = leftRec.getUpperLeft().getX();
        double newLeft = curLeft - movePixels;
        if (newLeft < 0) {
            leftRec.moveSidewise(screenW - segLen * 5);
        } else {
            leftRec.moveSidewise((int) newLeft);
        }
    }

    /**
     * Func to move the paddle to the right.
     */
    public void moveRight() {
        double curRight = leftRec.getUpperLeft().getX() + segLen * 5;
        double newRight = curRight + movePixels;
        if (newRight > screenW) {
            leftRec.moveSidewise(0);
        } else {
            leftRec.moveSidewise((int) (leftRec.getUpperLeft().getX() + movePixels));
        }
    }

    // Sprite
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        Color grey1 = new Color(96, 96, 96);
        Color grey2 = new Color(128, 128, 128);
        Color grey3 = new Color(160, 160, 160);
        Color[] colors = {grey3, grey2, grey1, grey2, grey3};
        Point upperLeft = leftRec.getUpperLeft();

        for (int i = 0; i < 5; i++) {
            Rectangle cur = new Rectangle(new Point(((upperLeft.getX() + i * segLen)
                    % screenW + screenW) % screenW, upperLeft.getY()),
                    segLen, segH);
            cur.drawOn(d, colors[i]);
        }
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(leftRec.getUpperLeft(), segLen * 5, segH);
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity curVel) {
        Velocity ans = null;
        Point upperLeft = leftRec.getUpperLeft();
        double speed = Math.sqrt(curVel.getDx() * curVel.getDx() + curVel.getDy() * curVel.getDy());
        for (int i = 0; i < 5; i++) {
            Rectangle cur = new Rectangle(new Point(upperLeft.getX() + i * segLen, upperLeft.getY()),
                    segLen, segH);
            if (!cur.getSide(1).dotBelongs(collisionPoint)) {
                continue;
            }
            if (i == 0) {
                ans = Velocity.fromAngleAndSpeed(150, speed);
            }
            if (i == 1) {
                ans = Velocity.fromAngleAndSpeed(120, speed);
            }
            if (i == 2) {
                ans = new Velocity(curVel.getDx(), -1 * curVel.getDy());
            }
            if (i == 3) {
                ans = Velocity.fromAngleAndSpeed(60, speed);
            }
            if (i == 4) {
                ans = Velocity.fromAngleAndSpeed(30, speed);
            }
            return ans;
        }
        for (int i = 0; i < 5; i++) {
            Rectangle cur = new Rectangle(new Point((upperLeft.getX() + i * segLen) % screenW, upperLeft.getY()),
                    segLen, segH);
            if (!cur.isPointOnRec(collisionPoint)) {
                continue;
            }
            ans = cur.hit(collisionPoint, curVel);
            break;
        }
        return ans;
    }

    /**
     * Func to add this paddle to the game.
     * @param g the game
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}