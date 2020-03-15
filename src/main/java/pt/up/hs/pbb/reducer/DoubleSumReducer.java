package pt.up.hs.pbb.reducer;

/**
 * Reducer that calculates the sum of {@link Double} values.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class DoubleSumReducer implements ValueReducer {

    private double sum = 0D;

    @Override
    public void accumulate(Object value) {
        if (value != null) {
            sum += (double) value;
        }
    }

    @Override
    public Double reduce() {
        return sum;
    }
}
