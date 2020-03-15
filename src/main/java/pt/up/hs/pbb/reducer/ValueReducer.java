package pt.up.hs.pbb.reducer;

/**
 * Interface implemented by reducers of additional values connected with a
 * burst.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public interface ValueReducer {

    void accumulate(Object value);

    Object reduce();
}
