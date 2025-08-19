package aitkenably.aurelius;

public class Util {

    /**
     * Returns a random integer between min and max, inclusive.
     * @param min
     * @param max
     * @return random integer
     */
    public static int randInt(int min, int max) {
        if( min >= max) throw new IllegalArgumentException("max must be greater than min");
        return (int) (Math.random() * ((max - min)) + min);
    }

    /**
     * Returns a random integer between negative percent and positive percent of the value, inclusive.
     * @param value
     * @param percent
     * @return random integer
     */
    public static int jitter(int value, double percent) {
        return (int) (value * (1 + (percent * 2 * (Math.random() - 0.5))));
    }





}
