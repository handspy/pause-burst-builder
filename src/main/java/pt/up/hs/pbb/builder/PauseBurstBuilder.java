package pt.up.hs.pbb.builder;

import pt.up.hs.pbb.models.Burst;
import pt.up.hs.pbb.reducer.ValueReducer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Pause-burst builder.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class PauseBurstBuilder {

    private final long threshold;

    // extra reducers' class
    private Map<String, Class<? extends ValueReducer>> extraReducers;

    // built objects
    private List<Burst> bursts;

    // current state
    private BurstBuilder burstBuilder = null;
    private Long previousTimestamp = null;

    public PauseBurstBuilder(long threshold) {
        this.threshold = threshold;
        this.extraReducers = new HashMap<>();
        this.bursts = new ArrayList<>();
    }

    /**
     * Add a reducer to reduce values for an extra captured feature.
     *
     * @param extra        {@link String} extra feature name.
     * @param reducerClazz {@link Class} reducer class.
     * @return {@link PauseBurstBuilder} this.
     */
    public PauseBurstBuilder extraReducer(String extra, Class<? extends ValueReducer> reducerClazz) {
        this.extraReducers.put(extra, reducerClazz);
        return this;
    }

    /**
     * Set reducers to reduce values for extra captured features.
     *
     * @param extraReducers {@link Map} map of extra feature names to reducer
     *                      classes.
     * @return {@link PauseBurstBuilder} this.
     */
    public PauseBurstBuilder extraReducers(Map<String, Class<? extends ValueReducer>> extraReducers) {
        this.extraReducers = extraReducers;
        return this;
    }

    /**
     * Add a point to pause-burst builder.
     *
     * @param x         X coordinate of the point.
     * @param y         Y coordinate of the point.
     * @param timestamp Timestamp of the point.
     * @return {@link PauseBurstBuilder} this object.
     */
    public PauseBurstBuilder addCapture(double x, double y, long timestamp) {
        return addCapture(x, y, timestamp, new HashMap<>());
    }

    /**
     * Add a point to pause-burst builder.
     *
     * @param x         X coordinate of the point.
     * @param y         Y coordinate of the point.
     * @param timestamp Timestamp of the point.
     * @param extra     {@link Map} extra features captured.
     * @return {@link PauseBurstBuilder} this object.
     */
    public PauseBurstBuilder addCapture(double x, double y, long timestamp, Map<String, ?> extra) {

        if (previousTimestamp == null) {
            burstBuilder = BurstBuilder.create(
                    instantiateReducers(),
                    0,
                    x, y, timestamp, extra
            );
        } else {
            long pauseDuration = timestamp - previousTimestamp;
            if (pauseDuration >= threshold) {

                bursts.add(burstBuilder.conclude());

                burstBuilder = BurstBuilder.create(
                        instantiateReducers(),
                        pauseDuration,
                        x, y, timestamp, extra
                );
            } else {
                burstBuilder.addCapture(x, y, timestamp, extra);
            }
        }

        this.previousTimestamp = timestamp;

        return this;
    }

    public List<Burst> conclude() {
        if (burstBuilder != null) {
            bursts.add(burstBuilder.conclude());
        }
        return bursts;
    }

    private Map<String, ValueReducer> instantiateReducers() {
        return extraReducers.entrySet()
                .parallelStream()
                .collect(Collectors.toConcurrentMap(
                        Map.Entry::getKey,
                        e -> {
                            try {
                                return e.getValue().getConstructor().newInstance();
                            } catch (
                                    InstantiationException |
                                            IllegalAccessException |
                                            InvocationTargetException |
                                            NoSuchMethodException ex) {
                                ex.printStackTrace();
                                return null;
                            }
                        }
                ));
    }
}
