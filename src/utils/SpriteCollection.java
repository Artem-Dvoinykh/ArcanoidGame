package utils;

import biuoop.DrawSurface;
import gameObjects.Sprite;

import java.util.ArrayList;

/**
 * Class to hold all the sprites.
 */
public class SpriteCollection {
    private ArrayList<Sprite> collection;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        collection = new ArrayList<>();
    }

    /**
     * Func to add a sprite to the collection.
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        collection.add(s);
    }

    void removeSprite(Sprite s) {
        collection.remove(s);
    }

    /**
     * Func to notify all the sprites that the time has passed.
     */
    public void notifyAllTimePassed() {
        ArrayList<Sprite> col = new ArrayList<>(collection);
        for (int i = 0; i < col.size(); i++) {
            col.get(i).timePassed();
        }
    }

    /**
     * Func to draw all the sprites.
     * @param d draw surface
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < collection.size(); i++) {
            collection.get(i).drawOn(d);
        }
    }
}