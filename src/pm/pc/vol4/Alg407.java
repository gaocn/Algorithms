package pm.pc.vol4;

/**
 * Created by 高文文 on 2017/1/6.
 *
 * Problem ID: 110407	ShellSort
 */
public class Alg407 {


    public static void main(String[] args) {
        Alg407 alg = new Alg407();

    }
}

/*解题思路：对比当前状态和目标状态，设置两个指针同时从底部向上扫，相同时同时向上 +1，否则当前状态 +1，当前状态扫完后将目标状态未扫到的倒序输出

9 Yertle                      Sir Lancelot               Richard M. Nixon         9 Yertle
6 Duke of Earl                Yertle                     Sir Lancelot             8 Richard M. Nixon
7 Sir Lancelot                Duke of Earl               Yertle                   7 Sir Lancelot
5 Elizabeth Windsor           Elizabeth Windsor          Duke of Earl             6 Duke of Earl
4 Michael Eisner              Michael Eisner             Elizabeth Windsor        5 Elizabeth Windsor
8 Richard M. Nixon            Richard M. Nixon           Michael Eisner           4 Michael Eisner
3 Mr. Rogers                  Mr. Rogers                 Mr. Rogers               3 Mr. Rogers
2 Ford Perfect                Ford Perfect               Ford Perfect             2 Ford Perfect
1 Mack                        Mack                       Mack                     1 Mack

 */
