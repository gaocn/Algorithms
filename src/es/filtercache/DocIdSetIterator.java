package es.filtercache;

import java.io.IOException;

/**
 * Created by 高文文 on 2017/6/23.
 * This abstract class defines methods to iterate over a set of non-decreasing
 * doc ids. Note that this class assume it iterates on doc ids, and therefore
 * {@link #NO_MORE_DOCS} is set to {@value #NO_MORE_DOCS} in order to be used
 * as a sentinel object. Implementations of this class are expected to consider
 * {@link Integer#MAX_VALUE} as an invalid value.
 */
public abstract class DocIdSetIterator {

    /**
     * When return by{@link #nextDoc()}, {@link #advance(int)} and
     * {@link #docID()} it means there are  no more docs in the iterator.
     */
    public static final int NO_MORE_DOCS = Integer.MAX_VALUE;

    /**
     *  Returns the following:
     *  <ul>
     *      <li>
     *          -1 or {@link #NO_MORE_DOCS} if {@link #nextDoc()} or
     *          {@link #advance(int)} were not called yet.
     *      </li>
     *      <li>
     *          {@link #NO_MORE_DOCS} if the iterator has exhausted.
     *      </li>
     *      <li>
     *          Otherwise it should return the doc ID it is currently on.
     *      </li>
     *  </ul>
     */
    public abstract int docID();

    /**
     *  Advances to the next document in the set and returns the doc it is
     *  currently on, or {@link #NO_MORE_DOCS} if there are no docs in the
     *  set.<br/>
     *
     *  <b>Notes:</b> after the iterator has exhausted you should not call this method,
     *  as it may result in unpredicted behavior.
     */
    public abstract int nextDoc() throws IOException;

    /**
     * Advances the first beyond(see NOTE below) the current whose document
     * number is greater than or equal to <i>target</i>. Returns the current
     * document number or {@link #NO_MORE_DOCS} if there are no more docs in
     * the set.
     * <p>
     *  Behave as if written:
     *  <pre>
     *  int advance(int target) {
     *      int doc;
     *      while((doc = nextDoc()) &lt; target) {
     *      }
     *      return doc;
     *  }
     *  </pre>
     *
     *  Some implementations are considerably more efficient than that.
     *  <p>
     *  <b>NOTES:</b>when <code>target &lt; current</code> implementations
     *   may opt not to advance beyond their current {@link #docID()}
     *  </p>
     *  <p>
     *  <b>NOTES:</b> this method may be called with {@link #NO_MORE_DOCS} for
     *  efficiency by some Scores. If your implementation cannot efficiently determine
     *  that it should exhaust, it is recommended that you check for that value
     *  in each call to this method.
     *  </p>
     *  <p>
     *  <b>NOTE:</b> after the iterator has exhausted you should not call this method,
     *  as it may result in unpredicted behavior.
     *  </p>
     * </p>
     */
    public abstract int advance(int target) throws IOException;
}
