package es.filtercache;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by 高文文 on 2017/6/23.
 *
 */
public class IntArrayDocIdSet extends DocIdSet{

    public static class Builder {
        private int[] docIDs = new int[0];
        int off;

        public void add(int docID) {
            if(off > 0 && docID <= docIDs[off -1]) {
                throw new IllegalArgumentException();
            }
            if(off == docIDs.length) {
                docIDs = grow(docIDs);
            }
            docIDs[off++] = docID;
        }
        public static int[] grow(int[] docIDs) {
            int size = docIDs.length * 2 + 1;
            int[] newArray = new int[size];
            System.arraycopy(docIDs, 0, newArray, 0, docIDs.length);
            return newArray;
        }
        public IntArrayDocIdSet build() {
            add(DocIdSetIterator.NO_MORE_DOCS);
            final IntArrayDocIdSet set = new IntArrayDocIdSet(Arrays.copyOf(docIDs, off));
            docIDs = null;
            return set;
        }
    }


    private final int[] docIDs;

    public IntArrayDocIdSet(int[] docIDs) {
        this.docIDs = docIDs;
    }

    @Override
    public DocIdSetIterator iterator() throws IOException {
        return new Iterator(docIDs);
    }

    private static class Iterator extends DocIdSetIterator {
        private final int[] docIDs;
        int index;

        public Iterator(int[] docIDs) {
            this.docIDs = docIDs;
            index = -1;
        }
        @Override
        public int docID() {
            if(index == -1) return -1;
            return docIDs[index];
        }
        @Override
        public int nextDoc() throws IOException {
            return docIDs[++index];
        }
        @Override
        public int advance(int target) throws IOException {
            int low = index + 1;
            int high = low + 1;

            while (high < docIDs.length && docIDs[high] < target) {
                final int diff = high - low;
                low = high;
                high += 2 * diff;
            }
            high = Math.min(high, docIDs.length);
            index = Arrays.binarySearch(docIDs, low, high, target);
            if (index < 0) {
                index = -1 - index;
            }
            return docIDs[index];
        }

        public long cost() {
            return docIDs.length - 1;
        }
    }

}
