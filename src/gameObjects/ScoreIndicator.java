package gameObjects;

import biuoop.DrawSurface;
import utils.Counter;
import java.awt.Color;

/**
 * Class for showing the score.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructor.
     * @param score the initial score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(350, 35, "Score: " + score.getValue(), 25);
    }

    @Override
    public void timePassed() {
        return;
    }
}
