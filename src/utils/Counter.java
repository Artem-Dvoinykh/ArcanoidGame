package utils;

/**
 * Class for the counter.
 */
public class Counter {
    private int cnt = 0;

    /**
     * Func to increase the counter.
     * @param number the number to be increased by
     */
    public void increase(int number) {
        cnt += number;
    }

    /**
     * Func to decrease the counter.
     * @param number the number to be decreased by
     */
    public void decrease(int number) {
        cnt -= number;
    }

    /**
     * Func to get the current count.
     * @return the current count
     */
    public int getValue() {
        return cnt;
    }
}