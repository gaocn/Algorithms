package gww.geeks.dp;

public class BalancedPartition {

    /*
        Balanced Partition
        You have a set of n integers each in the range 0 ... K. Partition these integers into two subsets such that you
         minimize |S1 - S2|, where S1, S2 denote the sums of the elements in each of the two subsets.

        Input: n integers(range 0..K) A1 .. An
        Goal: partition into subsets S1 & S2, minimize|sum(S1) - sum(S2)|

         定义：P(i, j)：若A1..Ai的子集之和为j则P(i, j) = 1,反之P(i, j) = 0
              i范围[1 .. n], j的范围为[0 .. sum(Ai)] 或设置为[0 .. nk]

                    [ 1  if sum of some subset of A1 .. Ai is j
         P(i , j) = [
                    [ 0  ,otherwise
         状态转移方程为：
                   [ P(i - 1, j) if P(i-1, j)=1
         P(i, j) = [
                   [ P(i - 1, j - Ai) if P(i-1, j-Ai) = 1
         P(i, j) = max{P(i-1, j), P(i-1, j-A1)}
         时间复杂度为：O(N^2)

         令S = sum{Ai} / 2
         求：
          min{S - i | P(n, i)=1} i<=S 即所有子集之和与S之差的最小值即为要求的解；
            其中S1 = i, S2 = 2*S - i
          因此 min|sum(S1) - sum(S2)| = 2*(S - i)

     */



}
