package gww.geeks.dp;

public class OptimalStrategyForAAGame {

    /*
        Optimal Strategy For a Game
        Consider a row of n coins of values v(1) .. V(n), where n is even. We play a game against an opponent by altering
        turns. In each turn, a player selects either the first or last coin from the row, removes it from the row permanently,
        and receives the value of coin. Determine the maximum possible amount of money we can definitely win if we move first.

        Input: row of coins of values v(1)..v(n), in which n is even
        Goal:  maximize value of coin selected

        F(i, j): represents the maximum value the user can collect from i'th coin to j'th coin.

        first compute:  F(i, i)
        second compute: F(i, i+1)
        third compute:  F(i, i+2)
              ..
              ..
              ..
        last compute:   F(i, i+n)
         __ __ ___ __ __ ___ __ __
        |__|__|_i_|__|__|_j_|__|__|

        若我要赢，对于[i, j]之间的数，有两两种选择：
            1. 选择i，对手会在[i+1, j]之间选择数，对手会选择剩余值的最大值因此留给自己最小的：
                        G  = min{F(i+2,j), F(i+1, j-1)} + vi
            2. 选择j，对手会在[i, j-1]之间选择数，对手会选择剩余值的最大值因此留给自己最小的：
                        K = min{F(i+1, j-1), F(i, j-2)} + vj
        F(i, j) = max{G , K}

        F(i, j) = vi          if i == j
        F(i, j) = max(vi, vj) if i == j+1

     */
}
