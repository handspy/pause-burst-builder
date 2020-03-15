package pt.up.hs.pbb.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.pbb.TestUtils;
import pt.up.hs.pbb.models.Burst;
import pt.up.hs.pbb.reducer.DoubleAverageReducer;
import pt.up.hs.pbb.reducer.ValueReducer;

import java.util.List;
import java.util.Map;

/**
 * Test {@link PauseBurstBuilder}.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class TestPauseBurstBuilder {

    @Test
    public void testNoBurst() {
        List<Burst> bursts = new PauseBurstBuilder(1).conclude();

        Assertions.assertEquals(0, bursts.size());
    }

    @Test
    public void testSingleBurst() {
        List<Burst> bursts = new PauseBurstBuilder(1000)
                .addCapture(1, 1, 0)
                .addCapture(2, 2, 500)
                .addCapture(3, 3, 1000)
                .addCapture(4, 4, 1300)
                .addCapture(5, 5, 1900)
                .addCapture(5, 5, 2000)
                .conclude();

        Assertions.assertEquals(1, bursts.size());

        Burst burst = bursts.get(0);
        Assertions.assertEquals(0, burst.getPauseDuration());
        Assertions.assertEquals(1, burst.getStartX());
        Assertions.assertEquals(5, burst.getEndX());
        Assertions.assertEquals(1, burst.getStartY());
        Assertions.assertEquals(5, burst.getEndY());
        Assertions.assertEquals(0, burst.getStartTime());
        Assertions.assertEquals(2000, burst.getEndTime());
        Assertions.assertEquals(1.131371, burst.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0.002546, burst.getSpeed(), TestUtils.EPSILON);
    }

    @Test
    public void testSingleBurstPlusOutlier() {
        List<Burst> bursts = new PauseBurstBuilder(1000)
                .addCapture(1, 1, 0)
                .addCapture(2, 2, 500)
                .addCapture(3, 3, 1000)
                .addCapture(4, 4, 1300)
                .addCapture(5, 5, 1900)
                .addCapture(5, 5, 2000)
                .addCapture(10, 10, 5000)
                .conclude();

        Assertions.assertEquals(2, bursts.size());

        Burst burst = bursts.get(0);
        Assertions.assertEquals(0, burst.getPauseDuration());
        Assertions.assertEquals(1, burst.getStartX());
        Assertions.assertEquals(5, burst.getEndX());
        Assertions.assertEquals(1, burst.getStartY());
        Assertions.assertEquals(5, burst.getEndY());
        Assertions.assertEquals(0, burst.getStartTime());
        Assertions.assertEquals(2000, burst.getEndTime());
        Assertions.assertEquals(1.131371, burst.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0.002546, burst.getSpeed(), TestUtils.EPSILON);

        Burst outlier = bursts.get(1);
        Assertions.assertEquals(3000, outlier.getPauseDuration());
        Assertions.assertEquals(10, outlier.getStartX());
        Assertions.assertEquals(10, outlier.getEndX());
        Assertions.assertEquals(10, outlier.getStartY());
        Assertions.assertEquals(10, outlier.getEndY());
        Assertions.assertEquals(5000, outlier.getStartTime());
        Assertions.assertEquals(5000, outlier.getEndTime());
        Assertions.assertEquals(0, outlier.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0, outlier.getSpeed(), TestUtils.EPSILON);
    }

    @Test
    public void testMultipleBurst() {
        List<Burst> bursts = new PauseBurstBuilder(500)
                .addCapture(1, 1, 0)
                .addCapture(2, 2, 500)
                .addCapture(3, 3, 1000)
                .addCapture(4, 4, 1300)
                .addCapture(5, 5, 1900)
                .addCapture(5, 5, 2000)
                .conclude();

        Assertions.assertEquals(4, bursts.size());

        Burst burst1 = bursts.get(0);
        Assertions.assertEquals(0, burst1.getPauseDuration());
        Assertions.assertEquals(1, burst1.getStartX());
        Assertions.assertEquals(1, burst1.getEndX());
        Assertions.assertEquals(1, burst1.getStartY());
        Assertions.assertEquals(1, burst1.getEndY());
        Assertions.assertEquals(0, burst1.getStartTime());
        Assertions.assertEquals(0, burst1.getEndTime());
        Assertions.assertEquals(0, burst1.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0, burst1.getSpeed(), TestUtils.EPSILON);

        Burst burst2 = bursts.get(1);
        Assertions.assertEquals(500, burst2.getPauseDuration());
        Assertions.assertEquals(2, burst2.getStartX());
        Assertions.assertEquals(2, burst2.getEndX());
        Assertions.assertEquals(2, burst2.getStartY());
        Assertions.assertEquals(2, burst2.getEndY());
        Assertions.assertEquals(500, burst2.getStartTime());
        Assertions.assertEquals(500, burst2.getEndTime());
        Assertions.assertEquals(0, burst2.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0, burst2.getSpeed(), TestUtils.EPSILON);

        Burst burst3 = bursts.get(2);
        Assertions.assertEquals(500, burst3.getPauseDuration());
        Assertions.assertEquals(3, burst3.getStartX());
        Assertions.assertEquals(4, burst3.getEndX());
        Assertions.assertEquals(3, burst3.getStartY());
        Assertions.assertEquals(4, burst3.getEndY());
        Assertions.assertEquals(1000, burst3.getStartTime());
        Assertions.assertEquals(1300, burst3.getEndTime());
        Assertions.assertEquals(1.414214, burst3.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0.004714, burst3.getSpeed(), TestUtils.EPSILON);

        Burst burst4 = bursts.get(3);
        Assertions.assertEquals(600, burst4.getPauseDuration());
        Assertions.assertEquals(5, burst4.getStartX());
        Assertions.assertEquals(5, burst4.getEndX());
        Assertions.assertEquals(5, burst4.getStartY());
        Assertions.assertEquals(5, burst4.getEndY());
        Assertions.assertEquals(1900, burst4.getStartTime());
        Assertions.assertEquals(2000, burst4.getEndTime());
        Assertions.assertEquals(0, burst4.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0, burst4.getSpeed(), TestUtils.EPSILON);
    }

    @Test
    public void testMultipleBurstExtraFeatureReducers() {
        List<Burst> bursts = new PauseBurstBuilder(500)
                .extraReducer("pressure", DoubleAverageReducer.class)
                .extraReducer("dots", CounterReducer.class)
                .addCapture(1, 1, 0, Map.of("pressure", 0.3065996766090393))
                .addCapture(2, 2, 500, Map.of("pressure", 0.3333376944065094))
                .addCapture(3, 3, 1000, Map.of("pressure", 0.40512996912002563))
                .addCapture(4, 4, 1300, Map.of("pressure", 0.4322332441806793))
                .addCapture(5, 5, 1900, Map.of("pressure", 0.4849742650985718))
                .addCapture(5, 5, 2000, Map.of("pressure", 0.21974508464336395))
                .conclude();

        Assertions.assertEquals(4, bursts.size());

        Burst burst1 = bursts.get(0);
        Assertions.assertEquals(0.3065997, (Double) burst1.getExtraFeatures().get("pressure"), TestUtils.EPSILON);
        Assertions.assertEquals(1, (int) burst1.getExtraFeatures().get("dots"));

        Burst burst2 = bursts.get(1);
        Assertions.assertEquals(0.3333377, (Double) burst2.getExtraFeatures().get("pressure"), TestUtils.EPSILON);
        Assertions.assertEquals(1, (int) burst2.getExtraFeatures().get("dots"));

        Burst burst3 = bursts.get(2);
        Assertions.assertEquals(0.4186816, (Double) burst3.getExtraFeatures().get("pressure"), TestUtils.EPSILON);
        Assertions.assertEquals(2, (int) burst3.getExtraFeatures().get("dots"));

        Burst burst4 = bursts.get(3);
        Assertions.assertEquals(0.35235967, (Double) burst4.getExtraFeatures().get("pressure"), TestUtils.EPSILON);
        Assertions.assertEquals(2, (int) burst4.getExtraFeatures().get("dots"));
    }

    public static class CounterReducer implements ValueReducer {

        private int count = 0;

        public CounterReducer() {}

        @Override
        public void accumulate(Object value) {
            count++;
        }

        @Override
        public Integer reduce() {
            return count;
        }
    }
}
