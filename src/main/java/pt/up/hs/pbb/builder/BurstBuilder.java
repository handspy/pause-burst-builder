package pt.up.hs.pbb.builder;

import pt.up.hs.pbb.models.Burst;
import pt.up.hs.pbb.reducer.DoubleAverageReducer;
import pt.up.hs.pbb.reducer.ValueReducer;
import pt.up.hs.pbb.utils.MathUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builder for single bursts.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class BurstBuilder {

    private long pauseDuration;

    private double startX;
    private double startY;
    private long startTimestamp;

    private double previousX;
    private double previousY;
    private long previousTimestamp;

    private DoubleAverageReducer distanceReducer;
    private DoubleAverageReducer speedReducer;

    private Map<String, ValueReducer> extraReducers;

    private BurstBuilder(
            Map<String, ValueReducer> extraReducers,
            long pauseDuration,
            double startX, double startY, long startTimestamp) {
        this.pauseDuration = pauseDuration;

        this.startX = startX;
        this.startY = startY;
        this.startTimestamp = startTimestamp;

        this.previousX = startX;
        this.previousY = startY;
        this.previousTimestamp = startTimestamp;

        this.distanceReducer = new DoubleAverageReducer();
        this.speedReducer = new DoubleAverageReducer();

        this.extraReducers = extraReducers;
    }

    /**
     * Create a new {@link BurstBuilder} after the pause period.
     *
     * @param extraReducers {@link Map} extra features' reducers.
     * @param pauseDuration Duration of the pause that preceded this burst.
     * @param x {@code double} X coordinate of the capture.
     * @param y {@code double} Y coordinate of the capture.
     * @param timestamp {@code long} timestamp of the capture.
     * @param extra {@link Map} extra features captured.
     * @return {@link BurstBuilder} this.
     */
    public static BurstBuilder create(
            Map<String, ValueReducer> extraReducers, long pauseDuration,
            double x, double y, long timestamp, Map<String, ?> extra
    ) {
        BurstBuilder builder = new BurstBuilder(extraReducers, pauseDuration, x, y, timestamp);
        builder.accumulateExtraFeatures(extra);
        return builder;
    }

    /**
     * Add a captured dot to the burst.
     *
     * @param x {@code double} X coordinate of the capture.
     * @param y {@code double} Y coordinate of the capture.
     * @param timestamp {@code long} timestamp of the capture.
     * @param extra {@link Map} extra features captured.
     * @return {@link BurstBuilder} this.
     */
    public BurstBuilder addCapture(double x, double y, long timestamp, Map<String, ?> extra) {

        double distance = MathUtils.calculateCartesianDistance(previousX, previousY, x, y);
        distanceReducer.accumulate(distance);
        speedReducer.accumulate(distance / (timestamp - previousTimestamp));

        accumulateExtraFeatures(extra);

        this.previousX = x;
        this.previousY = y;
        this.previousTimestamp = timestamp;

        return this;
    }

    public Burst conclude() {

        Map<String, Object> extraFeatures = extraReducers.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().reduce()
                ));

        return new Burst(
                pauseDuration,
                startTimestamp, previousTimestamp,
                startX, startY,
                previousX, previousY,
                distanceReducer.reduce(),
                speedReducer.reduce(),
                extraFeatures
        );
    }

    private void accumulateExtraFeatures(Map<String, ?> extra) {

        for (String key: extraReducers.keySet()) {
            ValueReducer reducer = extraReducers.get(key);
            if (reducer != null) {
                reducer.accumulate(extra.get(key));
            }
        }
    }
}
