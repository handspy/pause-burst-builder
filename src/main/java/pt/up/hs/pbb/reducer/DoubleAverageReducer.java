package pt.up.hs.pbb.reducer;

/**
 * Reducer that calculates the average of {@link Double} values.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class DoubleAverageReducer implements ValueReducer {

    private double sum = 0D;
    private int count = 0;

    @Override
    public void accumulate(Object value) {
        if (value != null) {
            sum += (double) value;
        }
        count++;
    }

    @Override
    public Double reduce() {
        if (count == 0) {
            return 0D;
        }
        return sum / count;
    }
}
