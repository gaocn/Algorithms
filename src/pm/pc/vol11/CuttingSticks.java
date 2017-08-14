package pm.pc.vol11;

public class CuttingSticks {

    /*
    You have to cut a wood stick into pieces. The most affordable company, The Analog Cutting Machinery,
Inc. (ACM), charges money according to the length of the stick being cut. Their procedure of work
requires that they only make one cut at a time.
It is easy to notice that different selections in the order of cutting can led to different prices. For
example, consider a stick of length 10 meters that has to be cut at 2, 4 and 7 meters from one end.
There are several choices. One can be cutting first at 2, then at 4, then at 7. This leads to a price of 10
+ 8 + 6 = 24 because the first stick was of 10 meters, the resulting of 8 and the last one of 6. Another
choice could be cutting at 4, then at 2, then at 7. This would lead to a price of 10 + 4 + 6 = 20, which
is a better price.
Your boss trusts your computer abilities to find out the minimum cost for cutting a given stick.
Input
The input will consist of several input cases. The first line of each test case will contain a positive
number l that represents the length of the stick to be cut. You can assume l < 1000. The next line will
contain the number n (n < 50) of cuts to be made.
The next line consists of n positive numbers ci (0 < ci < l) representing the places where the cuts
have to be done, given in strictly increasing order.
An input case with l = 0 will represent the end of the input.
Output
You have to print the cost of the optimal solution of the cutting problem, that is the minimum cost of
cutting the given stick. Format the output as shown below.
Sample Input
100
3
25 50 75
10
4
4 5 7 8
0
Sample Output
The minimum cutting is 200.
The minimum cutting is 22.
// 是否可以使用贪心法来解决？
//
// 题目是具有最优子结构性质的。最优解是在某一个切割点进行切割，得到的两段棍子的切割费用之和最小值，
// 设切割后棍子分为 A，B 两段，而棍子 A，B 各自的最小费用，同样是在各自某一切割点进行切割后，切割
// 费用和的最小值。
//
// 但是本题的最优子结构能不能得出一个有效的贪心策略呢？
//
// 第一种策略：如样例输入给出的第一组数据，在接近中心的切割点进行切割总费用是最小的，但是对于第二
// 组数据就不是这样。
//
// 第二种策略，在这样的切割点进行切割：在此切割点切割后，左右两段棍子的长度尽量相等，这种策略对第
// 一组数据是适用的，但是对于第二组数据失效了。
//
// 第三种策略，观察第二组测试数据，第一次切割点选在 4，然后形成一个长度为 6，需要切割点为 1，3，4
// 的棍子，刚好在 3 处切割，形成两根长度为 3 的棍子，此方案切割费用最小，为 22，那么是否可以将此
// 策略一般化？答案，否定，如以下测试数据：
//
// 10
// 1 2 3 7
//
// 无法在第一次选择时做出决定，选 1 还是选 7 好。
//
// 综上述，对于贪心法来说，似乎本题无法使用有效的策略来为每一步决定一个最优选择。
//
// 使用动态规划如何？
//
// 对于第一次切割，在任何一个切割点进行，都会将棍子切割成两段 A 和 B，很明显，最小切割费用应该是
// 对棍子 A 和 B 继续进行切割的费用和的最小值，当然对棍子 A 和 B 也应该是一样的，这样似乎有递归
// 的味道。那么不妨设 minCost[i][j] 为对第 i 个切割点起始至第 j 个切割点结束这段棍子的最小切割
// 费用，为了解题方便，假设有 k 个切割点，并设初始时棍子左端为第 0 个切割点，棍子右端为第 k + 1
// 个切割点，切割点 i 距离棍子左端的距离为 places[i]，则有以下递推关系：
//
// minCost[i][j] = min{minCost[i][j], minCost[i][c] + minCost[c][j] + (places[j]
// - places[i])|i < c < j}
//
// 初始时，切割点 i 到 切割点 i + 1 的棍子切割费用为 0，因为两端都为切割点，无须再切割。在递归
// 求解时，可以使用表格式 DP 加快求解速度，因为本题的测试数据似乎很多。
     */


    public static void main(String[] args) {

    }

    public void doCuttingStick(int stickLen, int[] cuts) {

    }


}
