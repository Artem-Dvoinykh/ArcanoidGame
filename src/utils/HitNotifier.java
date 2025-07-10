package utils;

/**
 * Interface for a notifier - a guy who will tell the listeners that an action occured.
 */
public interface HitNotifier {
    /**
     * Func to add a listener.
     * @param hl the listener
     */
    void addHitListener(HitListener hl);

    /**
     * Func to delete a listener.
     * @param hl the listener
     */
    void removeHitListener(HitListener hl);
}