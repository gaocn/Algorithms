package gww.geeks.dp;

/**
 * Created by 高文文 on 2017/7/10.
 */
public class MaxValueContigunousSubsequence {

    /*
        Given a sequence of n real number A(1) ... A(n),
        determine a contiguous sub-sequence A(i) ... A(j) for which the sum of element in th sub-sequence is maximized!


     */

    public static void main(String[] args) {
        MaxValueContigunousSubsequence alg = new MaxValueContigunousSubsequence();

        int[] seq = new int[] {-2, -3, 4, -1, -2, 1, 5, -3};

        System.out.println("Max Sum:" + alg.maxSumSequence(seq, seq.length - 1));

    }

    public int maxSumSequence(int[] seq, int to) {
        if(to < 0)  return 0;
        if(to == 0) return seq[to];

        int maxSum = Integer.MIN_VALUE, tmp;
        for(int i = 0; i < to; i++) {
            tmp = maxSumSequence(seq, i);
            if(tmp + seq[to] >= 0) {
                maxSum = Math.max(maxSum, Math.max(tmp + seq[to], seq[to]));
            } else {
                maxSum = Math.max(maxSum, Math.max(tmp, seq[to]));
            }
        }
        return maxSum;
    }

}
