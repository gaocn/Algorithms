package es.filtercache;

/**
 * Created by 高文文 on 2017/6/23.
 * Interface for Bitset-like structures.
 */
public interface Bits {
    public boolean get(int index);
    public int length();

    public static final Bits[] EMPTY_ARRAY = new Bits[0];


    /**
     *  Bits impl of the specified length with all bits set.
     */
    public static class MatchAllBits implements Bits {
        final int len;

        public MatchAllBits(int len) {
            this.len = len;
        }

        @Override
        public boolean get(int index) {
            return true;
        }

        @Override
        public int length() {
            return len;
        }
    }

    /**
     * Bits impl of the specified length with all bits unset.
     */
    public static class MatchNoBits implements Bits {
        final int len;

        public MatchNoBits(int len) {
            this.len = len;
        }

        @Override
        public boolean get(int index) {
            return false;
        }

        @Override
        public int length() {
            return len;
        }
    }
}
