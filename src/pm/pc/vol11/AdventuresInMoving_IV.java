package pm.pc.vol11;

import java.util.Arrays;

public class AdventuresInMoving_IV {
    /*
To help you move from Waterloo to the big city, you are considering
renting a moving truck. Gas prices being so high these days, you
want to know how much the gas for such a beast will set you back.
The truck consumes a full litre of gas for each kilometre it travels.
It has a 200 litre gas tank. When you rent the truck in Waterloo,
the tank is half full. When you return it in the big city, the tank
must be at least half full, or you’ll get gouged even more for gas by
the rental company. You would like to spend as little as possible on gas, but you don’t want to run out
along the way.
Input
The input begins with a single positive integer on a line by itself indicating the number of the cases
following, each of them as described below. This line is followed by a blank line, and there is also a
blank line between two consecutive inputs.
Input is all integers. The first integer is the distance in kilometres from Waterloo to the big city,
at most 10000. Next comes a set of up to 100 gas station specifications, describing all the gas stations
along your route, in non-decreasing order by distance. Each specification consists of the distance in
kilometres of the gas station from Waterloo, and the price of a litre of gas at the gas station, in tenths
of a cent, at most 2000.
Output
For each test case, the output must follow the description below. The outputs of two consecutive cases
will be separated by a blank line.
Output is the minimum amount of money that you can spend on gas to get you from Waterloo to
the big city. If it is not possible to get from Waterloo to the big city subject to the constraints above,
print ‘Impossible’.
Sample Input
1
500
100 999
150 888
200 777
300 999
400 1009
450 1019
500 1399
Sample Output
450550

 设到达第 i 个加油站时，剩余油量为 j 升时的最小花费为 cost[i][j]，d[m][i] 为第 m 个加油站
 和第 i 个加油站之间的距离，p[i] 为第 i 个加油站加 1 升油的费用，则有以下状态转移方程：

 cost[i][j] = min{cost[m][n] + ((n - d[m][i] >= j) ? 0 : (d[m][i] + j - n) * p[i] | d[m][i] <= n, 1<= m < i}
 cost[0][j] = 0
 cost[i][0] = MAX
 求解：cost[0][N]，从第1站到最后一站的最小开销，若不可能返回Impossible
     */

    public static void main(String[] args) {

        //** 设置起点为第一个虚拟加油站，加油价格为0，因为不会再此虚拟加油站加油
        int dist = 500;
        int[] gasDist = new int[]{0, 100, 150, 200, 300, 400,  450,  500,  dist};
        int[] gasFee  = new int[]{0, 999, 888, 777, 999, 1009, 1019, 1399, 2000};
        //** 设置终点为一个虚拟加油站，加油价格为最大值，以免在最后一个虚拟加油站加油

        AdventuresInMoving_IV alg = new AdventuresInMoving_IV();
        alg.doAdventuresInMoving(dist, gasDist, gasFee);
    }

    public void doAdventuresInMoving(int dist, int[] gasDist, int[] gasFee) {
        if(!isReachable(dist, gasDist)) {
            System.out.println("Impossible");
            return;
        }
        int nStation  = gasDist.length;
        int MAX_LITER = 200;
        int[][] cost = new int[nStation][MAX_LITER];

//        System.out.println("NStations: " + gasDist.length);

        for(int i = 0; i < nStation; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
        }

        cost[0][100] = 0;
        for(int i = 0; i < nStation - 1; i++) {
            for(int j = 0; j < MAX_LITER; j++) {
                if(cost[i][j] < Integer.MAX_VALUE) {
                    //计算从加油站i，剩余油量j到达下一站next剩余流量为k的最小费用
                    int next = i + 1;
                    while((next < nStation)&& (gasDist[next] - gasDist[i] <= j)) {
                        //计算剩余油量，若剩余油量大于等于k升，则不需要加油；否则需要加油；
                        int remain = j - (gasDist[next] - gasDist[i]);
                        for(int k = 0; k <= remain; k++) {
                            cost[next][k] = Math.min(cost[i][j], cost[next][k]);
                        }

                        for(int k = remain + 1; k < MAX_LITER; k++) {
                            cost[next][k] = Math.min(cost[i][j] + (k - remain)*gasFee[next], cost[next][k]);
                        }
                        next++;
                    }
                }
            }
        }
        for(int i = 0; i < nStation; i++)
            System.out.println(Arrays.toString(cost[i]));
        System.out.print("minCost: " + cost[nStation - 1][100]);
    }

    public boolean isReachable(int dist, int[] gasDist) {

        // 距离终点距离为0
        if(dist == 0) return true;

        //和城市距离不为零，无加油站
        if(gasDist.length == 2) return false;

        //剩余油能否支持到达第一站
        if(100 < gasDist[1]) return false;

        //最后一站后，剩余油量没有达到至少100
        if(dist - gasDist[gasDist.length - 2] > 100) return false;

        //任意两站距离大于200
        for(int i = 2; i < gasDist.length; i++) {
            if(gasDist[i] - gasDist[i - 1] > 200)
                return false;
        }

        return true;
    }

/*
// 动态规划求花费的最小费用。
int dynamic_programming()
{
    // 初始时，置全部费用为最大值。
    for (int i = 0; i <= nstations; i++)
        for (int j = 0; j < MAXL; j++)
            cost[i][j] = MAXINT;

    // 第 0 个加油站剩余油量为 100 升时，总费用为 0。
    cost[0][100] = 0;
    for (int i = 0; i < nstations - 1; i++)
        for (int j = 0; j < MAXL; j++)
            if (cost[i][j] < MAXINT)
            {
                // 计算从加油站 i，剩余油量为 j 升时到达下一个加油站 next，
                // 剩余油量为 k 升时的最小费用。
                int next = i + 1;
                while ((kilometers[next] - kilometers[i]) <= j && next < nstations)
                {
                    // 计算剩余油量。若剩余油量大于等于 k 升，则不需加油，否则需要加油。
                    int remain = j - (kilometers[next] - kilometers[i]);
                    for (int k = 0; k <= remain; k++)
                        cost[next][k] =
                        min(cost[i][j], cost[next][k]);

                    for (int k = remain + 1; k < MAXL; k++)
                        cost[next][k] = min(cost[i][j] +
                            (k - remain) * price[next], cost[next][k]);

                    next++;
                }
            }

    // 取到达最后一个加油站时，剩余油量为 100 升时的费用。
    return cost[nstations - 1][100];
}  
 */



}
