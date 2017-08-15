package gww.geeks.dp;

public class LongestIncreasingSubsequence {

    /*
        Q: Given a sequence of N real numbers A(1)...A(n)，determine a subsequence(not necessarily contiguous) of maximum
            length in which the values in the subsequence form a strictly increasing sequence.
        Input:  sequence A(1)..A(n)
        Output: find a longest strictly increasing subsequence(not necessarily contiguous)

        A:  首先，贪心法是不满足条件的，因为贪心需要对
            设置LIS(A, j)为前j个数构成的最大严格递子序列增长度，则有：
            LIS(A, j) = max{
                            LIS(A, i) + (1  if A(i) < A(j) else 0) k属于区间[0, j-1]
                        }
            初始条件：LIS(A, 0) = 0
                     LIS(A, 1) = 1
            时间复杂度 max{LIS(A, j)}为 O(N^2)


        解法2：时间复杂度为O(NlogN),采用分治算法
            考虑A[x] A[y]，满足条件x < y A[x] < A[y]且LIS[x] == LIS[y]，此时LIS[N]要选择时，选哪一个构成最优呢？
            显然，选择A[x]时更有潜力，因为可能会存在A[x] < A[z] < A[y]，这样LIS[N]可以获得最优值。
            因此，当LIS[k]相同时，选择更小的A[x]可以得到最优解。
     */

    public static void main(String[] args) {
        LongestIncreasingSubsequence alg = new LongestIncreasingSubsequence();
        int[] A = new int[] {2,3,-1};
    }


}
