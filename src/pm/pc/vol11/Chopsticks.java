package pm.pc.vol11;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.Arrays;

public class Chopsticks {

    /*
In China, people use a pair of chopsticks to get food on the table, but Mr. L is a bit different. He uses
a set of three chopsticks – one pair, plus an EXTRA long chopstick to get some big food by piercing
it through the food. As you may guess, the length of the two shorter chopsticks should be as close as
possible, but the length of the extra one is not important, as long as it’s the longest. To make things
clearer, for the set of chopsticks with lengths A, B, C (A ≤ B ≤ C), (A − B)^2 is called the “badness” of the set.

It’s December 2nd, Mr.L’s birthday! He invited K people to join his birthday party, and would like
to introduce his way of using chopsticks. So, he should prepare K + 8 sets of chopsticks(for himself,
his wife, his little son, little daughter, his mother, father, mother-in-law, father-in-law, and K other
guests). But Mr.L suddenly discovered that his chopsticks are of quite different lengths! He should find
a way of composing the K + 8 sets, so that the total badness of all the sets is minimized.

Input
The first line in the input contains a single integer T, indicating the number of test cases (1 ≤ T ≤ 20).
Each test case begins with two integers K, N (0 ≤ K ≤ 1000, 3K + 24 ≤ N ≤ 5000), the number of
guests and the number of chopsticks.
There are N positive integers Li on the next line in non–decreasing order indicating the lengths of
the chopsticks (1 ≤ Li ≤ 32000).

Output
For each test case in the input, print a line containing the minimal total badness of all the sets.
Note: For the sample input, a possible collection of the 9 sets is:
8,10,16; 19,22,27; 61,63,75; 71,72,88; 81,81,84; 96,98,103; 128,129,148; 134,134,139; 157,157,160

Sample Input
1
1 40
1 8 10 16 19 22 27 33 36 40 47 52 56 61 63 71 72 75 81 81 84 88 96 98
103 110 113 118 124 128 129 134 134 139 148 157 157 160 162 164

Sample Output
23



若按照递增长度排序，f(i, j)中第i根筷子一定是最长的，这个肯定不会取；而若按递减长度排序，则f(i, j)的第i根
肯定是最小，可以选取此时一定是选择i,i-1组成一组(证明如下)而第三根筷子只要不小于第i根筷子且满足 i>=j*3即可

 对于筷子长度形成的序列（按递增排序）：
 ...，C1，C2，C3，...
 可以证明，若 C1 < C2，则最优方案中若 C1 被用作第一支筷子，则 C2 必被用作为第二支筷子。可使用
 反证法证明。假设最优方案中 C1 未与 C2 组成一组筷子，则由于 C1 与 C2 组成的筷子难用度比 C1
 与其他筷子组成的筷子难用度都要小，与最优方案相矛盾。故在选择第一支和第二支筷子时，总是选择相邻
 筷子进行组合，可以获得最小的难用度。

 设 badness[i][j] 为取前i支筷子形成的j组筷子组合所能得到的最小难用度，length[i] 为第
  i根筷子的长度，则有以下状态转移方程：
    1. 若不选第i根筷子，则最小难度为badness[i-1][j]
    2. 若选第i根筷子，则第i，i-1根筷子一定会组合成最小难度的一组，因此为badness[i - 2][j - 1] + (length[i] - length[i - 1)^2

 badness[i][j] = min{badness[i - 1][j], badness[i - 2][j - 1] + (length[i] - length[i - 1])^2 | i >= 3 * j }
 badness[0][j] = 0
 badness[i][0] = 0

*/

    public static void main(String[] args) {
        int[] chopsLen = new int[]{0,164,162,160,157,157,148,139,134,134,129,128,124,118,113,110,103,98,96,88,84,81,81,75,72,71,63,61,56,52,47,40,36,33,27,22,19,16,10,8,1};
        int k = 1;
        int nPeople = 8 + k;
        int nChopsticks = chopsLen.length - 1;

        Chopsticks alg = new Chopsticks();
        alg.doChopsticks(nPeople, nChopsticks,chopsLen);
    }

    public void doChopsticks(int nPeople, int nChopsticks ,int[] chopsLen) {
        int[][] badness = new int[nChopsticks + 1][nPeople + 1];

        for(int i = 1; i <= nChopsticks; i++) {
            badness[i][0] = 0;
            for(int j = 1;j <= nPeople; j++) {
                badness[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = 1; i <= nChopsticks; i++) {
            for(int j = 1; j <= nPeople; j++) {
                if(i >= 3 * j && badness[i-2][j-1] != Integer.MAX_VALUE) {
                    badness[i][j] = Math.min(
                        badness[i - 1][j],
                        badness[i - 2][j - 1] + (chopsLen[i] - chopsLen[i - 1])*(chopsLen[i] - chopsLen[i - 1])
                    );
                }
            }
        }

        for(int i=0; i <= nChopsticks; i++)
            System.out.println(Arrays.toString(badness[i]));
        System.out.println("Badness: " + badness[nChopsticks][nPeople]);

    }

}
