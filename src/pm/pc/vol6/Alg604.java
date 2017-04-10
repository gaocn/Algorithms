package pm.pc.vol6;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110604	Expressions
 *
 * 设F[m, d]表示括号数为m(n = 2 * m), 深度不超过d的合法括号表达式总数；
 * F[m, d] = sum{ F[k, D - 1] * F[m - k - 1, D] }, k 属于[0, m -1]
 * F[0, d] = 1;
 *
 */
public class Alg604 {

    public static void main(String[] args) {
        Alg604 alg = new Alg604();


    }

    static BigInteger[][] F = new BigInteger[151][151];
    public long doExpressions(int L, int D) {



        return 0;
    }
}

/*
for (int m = 1; m < MAXM; m++)
        for (int d = 1; d < MAXD; d++)
            for (int k = 0; k <= m - 1; k++)
                result[m][d] =
                    result[m][d] + result[k][d - 1] * result[m - k - 1][d];
				result[n / 2][d] - result[n / 2][d - 1]
// 假设 E 是一个深度为 d ，括号对数为 m 的合法表达式，则表达式 E 的最左边的括号
// l 一定和某个右括号 r 配对，他们合在一起把表达式划分为两个合法的括号表达式，在 l 和 r 之间的
// 部分以及 r 右边的部分，即：
//
// E = （X）Y
//
// 假设左边部分有 k 对括号，则右边部分有 n - k - 1 对括号，因为 l 和 r 已经用了一对括号，则括号
// 表达式 X 的深度最大为 d - 1 ，括号表达式 Y 的深度最大为 d 。则括号对数为 m，深度为 d 的合法
// 表达式数量为 T（m，d） - T（m，d - 1）。
 */