package pm.pc.vol6;

import java.math.BigInteger;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110605	Complete Tree labeling
 *
 * BigInteger 、DP
 *
 */
public class Alg605 {

    public static void main(String[] args) {
        Alg605 alg = new Alg605();


    }




}

/*
 如何得到递推关系？设 T（K，D）表示分支因子为 K，深度为 D 的 K 叉树标号的方案数。又设N（K，D）表示分支因子为 K，深度为 D 的完全 K
 叉树所包含的节点数。根据完全 K 叉树的定义，深度为 D 的完全 K 叉树包含了 K 个深度为 （D - 1） 的完全 K 叉数，其包含的节点数可以表
 示为 N（K，D - 1），有 K * N（K，D - 1） + 1 = N（K，D）。每个子树的标号方案数为T（K，D - 1），则问题转化为下面的问题：
 N（K，D） - 1 个节点，编号为 1 - （N（K，D） - 1），划分为 K 个子集，每个子集有 N（K，D - 1）个节点，这种划分方法有多少？因为只要
 确定了划分方法数，则由于知道了 T（K，D - 1），只要将每种划分方法得到的子集和每棵子树一一对应，每个子集的元素可以与 T（K，D - 1）中
 的标号形成一一映射，设总的划分方法数为X，则有以下关系：
    T（K，D） = X * (T（K，D - 1) ^ K)    （1）
而集合的划分种数 X 相当与每次从 N（K，D） - 1 个节点中取 N（K，D - 1）个节点，共取 K 次所能得到的不同取法总数。
设 A = （N（K，D） - 1），B = N（K，D - 1），A = K * B，有：
    X = C（A，B） * C（A - B，B） * …… * C（A - T * B，B），0 <= T <= K - 1  （2）
根据组合数的定义可将（2）式化简为：
    X = （A！） / （（B！） ^ K ）   （3）
最终有：
    T（K，D） = （（（N（K，D） - 1）！ / （（N（K，D - 1）！） ^ K）） * T（K，D - 1)^ K （4）
容易知道： T（K，0） = 1，N（K，D） = （K ^ （D + 1） - 1） / （K - 1）。
则可进一步化简得到：
    T（K，D） = （N（K，D） - 1）！ / N（K，D - 1） ^ K / N（K，D - 2） ^ （K ^ 2) /.../ N（K，1） ^ （K ^ （D - 1）） （5）




 */