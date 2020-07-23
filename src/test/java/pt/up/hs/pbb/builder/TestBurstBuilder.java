package pt.up.hs.pbb.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.pbb.TestUtils;
import pt.up.hs.pbb.models.Burst;

import java.util.HashMap;

/**
 * Test {@link BurstBuilder}.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class TestBurstBuilder {

    @Test
    public void testSingleDotBurst() {
        Burst burst = BurstBuilder
                .create(new HashMap<>(), 1500, 1D, 2D, 1600, new HashMap<>())
                .conclude();

        Assertions.assertEquals(1500, burst.getPauseDuration());
        Assertions.assertEquals(1, burst.getStartX(), TestUtils.EPSILON);
        Assertions.assertEquals(1, burst.getEndX(), TestUtils.EPSILON);
        Assertions.assertEquals(2, burst.getStartY(), TestUtils.EPSILON);
        Assertions.assertEquals(2, burst.getEndY(), TestUtils.EPSILON);
        Assertions.assertEquals(1600, burst.getStartTime());
        Assertions.assertEquals(1600, burst.getEndTime());
        Assertions.assertEquals(0, burst.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0, burst.getSpeed(), TestUtils.EPSILON);
    }

    @Test
    public void testStandardBurst() {
        Burst burst = BurstBuilder
                .create(new HashMap<>(), 1500, 1D, 2D, 1600, new HashMap<>())
                .addCapture(1D, 3D, 1800, new HashMap<>())
                .addCapture(2D, 3D, 1900, new HashMap<>())
                .addCapture(6D, 3D, 2300, new HashMap<>())
                .conclude();

        Assertions.assertEquals(1500, burst.getPauseDuration());
        Assertions.assertEquals(1, burst.getStartX(), TestUtils.EPSILON);
        Assertions.assertEquals(6, burst.getEndX(), TestUtils.EPSILON);
        Assertions.assertEquals(2, burst.getStartY(), TestUtils.EPSILON);
        Assertions.assertEquals(3, burst.getEndY(), TestUtils.EPSILON);
        Assertions.assertEquals(1600, burst.getStartTime());
        Assertions.assertEquals(2300, burst.getEndTime());
        Assertions.assertEquals(6, burst.getDistance(), TestUtils.EPSILON);
        Assertions.assertEquals(0.008333, burst.getSpeed(), TestUtils.EPSILON);
    }
}
