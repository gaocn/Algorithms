package gww.geeks.dp;

public class IntegerKnapsack {
    /*
      1. 0-1背包
         有N件物品和一个容量为W的背包。放入第i件物品耗费的空间是Ci,得到的价值为Vi。
         求解：将哪些物品装入背包中，可使得价值总和最大？
      【解】
         F(i, j)表示将前i件物品放入容量为j的包中可得到的最大价值，则F(N, W)为所求
         对于第i件物品有两种情况：将其放入背包中、不将其放入背包中。状态转移方程为：
         F(i, j) = max{F(i-1, j), F(i-1, j-C[i-1]) + V[i-1]}
      【代码】
        01_kanpsack(int N, int W, int[] C, int[] v) {
            int[][] F = new int[N+1][W+1];

            //bottom up matter
            for(int i = 0; i <= N; i++) {
                for(int j = 0; j <= W; j++) {
                    if(i == 0 || j == 0)
                        F[i][j] = 0;
                    else if(j >= C[i-1])
                        F[i][j] = Math.max(F[i-1][j], F[i-1][j-C[i-1]] + v[i-1]);
                    else
                        F[i][j] = F[i-1][j];
                }
            }
            return F[N][W];
        }
        时间复杂度O(N*V)已无法优化，空间复杂度可以优化为O(V)

       【恰好装满背包 与 只希望价值尽量大的区别】
        恰好装满背包：初始化时F[0] = 0 其他的均为无穷大， 可以保证F[V]就是恰好装满时的价值
        价值尽量大时：初始化时F[0..V] = 0 得到的是价值尽可能大


      2. 完全背包(Unbounded Knapsack)
        有N种物品和一个容量为W的背包，每种物品都有无限件可用。放入第i中物品耗费的空间为Ci，得到的价值为Vi。
        求解：将哪些物品装入背包，可使这些物品的耗费的空间空间总和不超过背包总量，且价值总和最大？
     【优化1】 对于Vi < Vj && Ci > Cj
     【优化2】 转换为0-1背包问题
     【解】
        F(i, j)表示将前i种放入容量为j的包中，所能得到的最大价值。
        对于任意一种物品i能够放入物品的数目范围为k=[0, W/Ci]
        因此状态转移方程为
        F(i, j) = max{F(i-1, j - kCi) + kVi | 0 <= kCi <= W} [根据0-1背包类推得到的公式]
                = max{F(i-1, j) , F(i-1, j - kCi) + kVi | 1 <= kCi <= W}
        有O(NV)个状态需要求解，而每一个状态解的时间不在是0-1背包的常熟，求解状态F(i, v)的时间是O(v/ci)，总的时间复杂度为O(N*V*sum(v/ci))
        将等式 F(i, j-Ci) = max{F(i-1, j - (k+1)Ci) + (k+1)Vi}  代入原方程可以得到：


    ==> F(i, j) = max{F(i-1, j), F(i, j-Ci) + vi}
    ==> F(j) = max{F(j), f(j-ci)+wi | 0 =< i <= n where ci <= v}

     【代码】 《===有第二个状态转移方程得到
       unbounded_knapsack(int N, int W, int[] c, int [] v) {
           int[][]  F = new int[N+1][W+1];
           Array.fill(F, 0);

           for(int w = 0; w <= W; w++) {
               for(int i = 0; i <= N; i++) {
                   F[i][w] = Math.max(F[i-1][w], F[i][w-c[i]] + v[i]);
               }
           }
           return F[N][W];
       }
     【为什么0-1背包和完全背包外循环和内循环中N,W次序是相反的？】

      3. 多重背包
        有N中物品和一个容量为W的背包，第i中物品最多有Mi件可用，每件耗费的空间是Ci，价值为Vi。
        求解：将哪些物品转入背包可使这些物品的耗费空间总和不超过背包容量，且价值总和最大。
     【解】
        F(i, j)表示将前i种放入容量为j的包中，所能得到的最大价值。
        F(i, j) = max{ F(i-1, j), F(i, j - k*ci) + k*vi | 1 <= k <= min(W/ci, Mi)}
     【】

     */
}
