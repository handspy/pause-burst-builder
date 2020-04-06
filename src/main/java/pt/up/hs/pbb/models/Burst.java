package pt.up.hs.pbb.models;

import java.io.Serializable;
import java.util.Map;

/**
 * Information about a burst.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Burst implements Serializable {

    private long pauseDuration;

    private long startTime;
    private long endTime;

    private double startX;
    private double startY;

    private double endX;
    private double endY;

    private int captureCount;

    private double distance;
    private double speed;

    private Map<String, Object> extraFeatures;

    public Burst() {
    }

    public Burst(
            long pauseDuration,
            long startTime, long endTime,
            double startX, double startY,
            double endX, double endY,
            int captureCount,
            double distance, double speed,
            Map<String, Object> extraFeatures
    ) {
        this.pauseDuration = pauseDuration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.captureCount = captureCount;
        this.distance = distance;
        this.speed = speed;
        this.extraFeatures = extraFeatures;
    }

    public long getPauseDuration() {
        return pauseDuration;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public int getCaptureCount() {
        return captureCount;
    }

    public double getDistance() {
        return distance;
    }

    public double getSpeed() {
        return speed;
    }

    public Map<String, Object> getExtraFeatures() {
        return extraFeatures;
    }

    @Override
    public String toString() {
        return "Burst{" +
                "pauseDuration=" + pauseDuration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", captureCount=" + captureCount +
                ", distance=" + distance +
                ", speed=" + speed +
                ", extraFeatures=" + extraFeatures +
                '}';
    }
}
