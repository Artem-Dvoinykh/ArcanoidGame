package utils;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import gameObjects.Collidable;
import gameObjects.Sprite;
import gameObjects.Velocity;
import geometry.Point;
import geometry.Rectangle;
import gameObjects.Paddle;
import gameObjects.ScoreIndicator;
import gameObjects.Block;
import gameObjects.Ball;

import java.awt.Color;

/**
 * Class representing the game.
 */
public class Game {
    private SpriteCollection sprites = null;
    private GameEnvironment environment = null;
    private GUI gui;
    private Paddle paddle;
    private Counter blocksCnt, ballsCnt, score;

    /**
     * Constructor.
     */
    public Game() {
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        gui = new GUI("ARKANOID", 800, 600);

        paddle = new Paddle(new Rectangle(new Point(50, 550), 40, 20),
                gui.getKeyboardSensor(), 800, 600);
        paddle.addToGame(this);

        blocksCnt = new Counter();
        ballsCnt = new Counter();
        score = new Counter();
    }

    /**
     * Func to remove a collidable from our game.
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Func to remove a sprite.
     * @param s the sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Func to add a collidable.
     * @param c the collidable
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Func to add a sprite.
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Balls (and Paddle) and add them to the game.
     */
    public void initialize() {
        BlockRemover blockR = new BlockRemover(this, blocksCnt);
        BallRemover ballR = new BallRemover(this, ballsCnt);
        ScoreTrackingListener scoreT = new ScoreTrackingListener(score);

        // the boarders
        initBlock(new Point(0, 50), 800, 10, java.awt.Color.PINK, null, null);
        initBlock(new Point(790, 50), 10, 800, java.awt.Color.PINK, null, null);
        initBlock(new Point(0, 600), 800, 10, java.awt.Color.PINK, ballR, null);
        initBlock(new Point(0, 50), 10, 800, java.awt.Color.PINK, null, null);

        // we don't count boarders as blocks
        blocksCnt.decrease(4);

        // the blocks
        for (int i = 1; i <= 12; i++) {
            initBlock(new Point(790 - i * 50 - i, 125), 50, 25, new Color(100, 180, 255),
                    blockR, scoreT);
        }
        for (int i = 1; i <= 11; i++) {
            initBlock(new Point(790 - i * 50 - i, 151), 50, 25, new Color(255, 150, 255),
                    blockR, scoreT);
        }
        for (int i = 1; i <= 10; i++) {
            initBlock(new Point(790 - i * 50 - i, 177), 50, 25, new Color(153, 153, 255),
                    blockR, scoreT);
        }
        for (int i = 1; i <= 9; i++) {
            initBlock(new Point(790 - i * 50 - i, 203), 50, 25, new Color(255, 180, 100),
                    blockR, scoreT);
        }
        for (int i = 1; i <= 8; i++) {
            initBlock(new Point(790 - i * 50 - i, 229), 50, 25, new Color(255, 200, 230),
                    blockR, scoreT);
        }

        // the balls
        initBall(new Point(30, 80), 10, new Velocity(1, 3));
        initBall(new Point(80, 150), 10, new Velocity(3, 1));
        initBall(new Point(80, 130), 10, new Velocity(3, 3));

        // Init the score indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(score);
        addSprite(scoreIndicator);
    }

    /**
     * Func to initialize the block.
     * @param remover a listener that removes blocks
     * @param scorer a listener that keep track of the score
     * @param color the color
     * @param h height
     * @param w width
     * @param upperLeft the upper-left corner of the block
     */
    private void initBlock(Point upperLeft, double w, double h, Color color, HitListener remover, HitListener scorer) {
        Block cur = new Block(new Rectangle(upperLeft, w, h), color);
        if (remover != null) {
            cur.addHitListener(remover);
        }
        if (scorer != null) {
            cur.addHitListener(scorer);
        }
        blocksCnt.increase(1);
        sprites.addSprite(cur);
        environment.addCollidable(cur);
    }

    /**
     * Func to initialize the ball.
     * @param center the center of the circle
     * @param r radius
     * @param vel velocity of the ball
     */
    private void initBall(Point center, int r, Velocity vel) {
        Ball ball = new Ball(center, r, Color.MAGENTA, environment);
        ball.setVelocity(vel);
        sprites.addSprite(ball);
        ballsCnt.increase(1);
    }

    /**
     * Func to show a message to the user.
     */
    private void showYouWinMessage() {
        Sleeper sleeper = new Sleeper();
        DrawSurface d = gui.getDrawSurface();
        d.drawText(220, 300, "You won!!!", 50);
        d.drawText(240, 300, "Score: " + score.getValue(), 50);
        gui.show(d);
        sleeper.sleepFor(10000);
    }

    /**
     * Func to show a message to the user.
     */
    private void showGameOverMessage() {
        Sleeper sleeper = new Sleeper();
        DrawSurface d = gui.getDrawSurface();
        d.drawText(220, 260, "Game Over :(", 50);
        d.drawText(240, 340, "Score: " + score.getValue(), 50);
        gui.show(d);
        sleeper.sleepFor(10000);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 100;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }

            if (blocksCnt.getValue() == 0) {
                showYouWinMessage();
                gui.close();
                return;
            }
            if (ballsCnt.getValue() == 0) {
                showGameOverMessage();
                gui.close();
                return;
            }
        }
    }
}