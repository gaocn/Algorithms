package es.filtercache;

import java.io.IOException;

/**
 * Created by 高文文 on 2017/6/23.
 * A DocIdSet contains a set of doc ids. Implementing classes must
 * only implement {@link #iterator} to provide access to the set
 */
public abstract class DocIdSet {
    /** An empty {@code DocIdSet} instance for easy, e.g. in Filters that hit no document */
    public static  final DocIdSet EMPTY_DOCIDSET = new DocIdSet() {
        private final DocIdSetIterator iterator = new DocIdSetIterator() {
            @Override
            public int advance(int target) throws IOException { return NO_MORE_DOCS; }
            @Override
            public int docID() { return NO_MORE_DOCS; }
            @Override
            public int nextDoc() throws IOException { return NO_MORE_DOCS; }
        };

        @Override
        public DocIdSetIterator iterator() throws IOException {
            return iterator;
        }

        /** we explicitly provide no random access, as this filter is 100% sparse and iterator exists faster*/
        @Override
        public Bits bits() throws IOException {
            return null;
        }

        @Override
        public boolean isCacheable() {
            return true;
        }
    };


    public abstract DocIdSetIterator iterator() throws IOException;

    /**
     * Optionally provides a {@link Bits} interface for random access to matching
     * documents.
     *
     * @return {@code null}, if this {@code DocIdSet} dose not support random access.
     * In contrast to {@link #iterator()}, a return value of {@code null} <b>does not</b>
     * imply thar no documents match the filter!
     * The default implementation does not provide random access, so you only need to
     * implement this method if you DocIdSet can guarantee random access to every doc id
     * in O(1) time without external disk access (as {@link Bits} interface cannot throw
     * {@link IOException}). This is generally true for bit sets like
     * {@code org.apache.lucene.util.FixedBitSet}, which return itself if they are used
     * as {@code DocIdSet}
     */
    public Bits bits() throws IOException {
        return null;
    }

    /**
     * This method is a hint for {@code CachingWrapperFilter}, if this <code>DocIdSet</code>
     * should be cached without copying it into a BitSet. The default is to return <code>false</code>.
     * If you have an own <code>DocIdSet</code> implementation that does its iteration very
     * effective and fast without doing disk I/O, override this method and return <code>true</code>.
     */
    public boolean isCacheable() {
        return false;
    }
}
