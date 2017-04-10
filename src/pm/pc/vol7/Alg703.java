package pm.pc.vol7;

/**
 * Created by 高文文 on 2017/2/16.
 *
 * Problem ID: 110703	Euclid problem
 *求同余方程的最小解
 * 数论。扩展的欧几里得算法。
 *
 * http://anh.cs.luc.edu/331/notes/xgcd.pdf
 * http://math.cmu.edu/~bkell/21110-2010s/extended-euclidean.html
 */
public class Alg703 {
    public static void main(String[] args) {
        Alg703 alg = new Alg703();

        System.out.println(alg.gcd(5,15));
        System.out.println(alg.gcd(15,5));
    }

    // (a * b) / gcd(a,b)
    public int lcm(int a, int b) {
        int gcd = gcd(a, b);
        return (a / gcd) * (b / gcd);
    }

    // a > b
    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}


/*
// 根据 Euclid 求 GCD 的过程，可以找到两个数 x 和 y，使得：
//
// a * x + b * y = GCD(a, b)    (1)
//
// 由于 GCD(a, b) = GCD(b, a')，其中 a' = a - b * [a / b]，根据数学归纳法，假设
// 已经找到了整数 x' 和 y'，使得：
//
// b * x' + a' * y' = GCD(a, b) (2)
//
// 把 a' 的表达式代入(2)式，得到：
//
// b * x' + (a - b * [a / b]) * y' = GCD(a, b)  (3)
//
// 起始条件是： a * 1 + 0 * 0 = GCD(a, 0)，整理可得到：
//
// x = y'
// y = x' - b * [a / b] * y'
//

证明绝对值相加最小，x<y？
// 由以上可以得到方程 （1） 的一组解，由于方程 （1） 和同余方程 a * x ≡ GCD(a, b) (mod b)
// 是等价的，而通过 Euclid 求 GCD 的过程可以得到同余方程的一个解 y'，则所有解可表示为：
//
// x = y' + k * b / GCD(a, b)
// y = (GCD(a, b) - a * y' - a * k * b / GCD(a, b)) / b
//
// 其中 k 为任意整数。由于已经限定 a 和 b 都为正整数，可以不需考虑 a 或（和） b 为 0 的情况。
// 由于 GCD(a, b) <= min(a, b)，故 x 和 y 必有 min(x, y) <= 0，max(x, y) > 0。
//
// 可以将求 GCD 的过程进行扩展，得到相应的解，输出即可达到要求。算法得到的 x 和 y 即是绝对值和
// 最小的（这一点为什么成立？因为起始条件 a * 1 + 0 * 0 = GCD(a, 0) 决定了它必将是绝对值和
// 最小的）。另外题目的叙述有点让人疑惑，应该是先考虑绝对值最小的，然后再考虑 x <= y 这个条件。
long gcd(long a, long b, long *x, long *y)
{
    long tx, ty;
    long g;

    if (b > a)
        return gcd(b, a, y, x);

    if (b == 0)
    {
        *x = 1;
        *y = 0;

        return a;
    }

    g = gcd(b, a % b, &tx, &ty);

    *x = ty;
    *y = tx - floor(a / b) * ty;

    return g;
}



 */


