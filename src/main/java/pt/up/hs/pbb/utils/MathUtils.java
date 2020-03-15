package pt.up.hs.pbb.utils;

/**
 * Utilities to deal with maths.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class MathUtils {

    /**
     * Calculate the cartesian distance between two points (x1, y1) and (x2, y2).
     *
     * @param x1 X coordinate of first point.
     * @param y1 Y coordinate of first point.
     * @param x2 X coordinate of second point.
     * @param y2 Y coordinate of second point.
     * @return the cartesian distance between the two points (x1, y1) and (x2, y2).
     */
    public static double calculateCartesianDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
