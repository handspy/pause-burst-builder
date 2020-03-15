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
        this.distance = distance;
        this.speed = speed;
        this.extraFeatures = extraFeatures;
    }

    public long getPauseDuration() {
        return pauseDuration;
    }

    public void setPauseDuration(long pauseDuration) {
        this.pauseDuration = pauseDuration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Map<String, Object> getExtraFeatures() {
        return extraFeatures;
    }

    public void setExtraFeatures(Map<String, Object> extraFeatures) {
        this.extraFeatures = extraFeatures;
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
                ", distance=" + distance +
                ", speed=" + speed +
                ", extraFeatures=" + extraFeatures +
                '}';
    }
}
