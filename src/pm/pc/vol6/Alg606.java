package pm.pc.vol6;

import java.math.BigInteger;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110606	The Priest Mathematician
 *
 * BigInteger大数+递推
 *
 */
public class Alg606 {

    public static void main(String[] args) {
        Alg606 alg = new Alg606();

        System.out.println(alg.hanoi(1));
        System.out.println(alg.hanoi(2));
        System.out.println(alg.hanoi(28));
    }

    public static final int MAX_DISK = 10001;
    static BigInteger[] steps = new BigInteger[MAX_DISK];

    public static void hanoiHelper() {
        steps[0] = BigInteger.valueOf(0);
        BigInteger exponent = BigInteger.valueOf(1);

        for(int index = 1, sequence = 1; index < MAX_DISK; sequence++) {

            for(int start = 1; start <= sequence && index < MAX_DISK; start++, index++) {
                steps[index] = steps[index - 1].add(exponent);
            }

            exponent = exponent.multiply(BigInteger.valueOf(2));
        }

    }

    public BigInteger hanoi(int diskNumber) {
        hanoiHelper();
        return steps[diskNumber];
    }




}

/*

 T(N) = 2 * T（M） + H（N - M）（0 <= M <= N）
 设 N 个盘子在 4 柱情况下的移动最小步数为 T（N），在 3 柱子情况下的最小移动步数为 H（N），则
 通过下列方法可以得到最小移动步数 T（N）。假设第一次先将 1 个盘子通过 4 根柱子移动到非目标柱子，
 然后将 N - 1 个盘子通过 3 根柱子移动到目标柱子，最后把最先移动的盘子移动到目标柱子上，则总步
 数为 H（N - 1） + 2 * T（1），若第一次移动两个盘子，则步数为 H（N - 2） + 2 * T（2），依
 此类推，所求的就是当 N 一定时，2 * T（M） + H（N - M）（0 <= M <= N） 的最小值。由于最多
 可有 10000 个盘子，移动方法数将是一个大整数，超出 long long 型的表示范围。实际上，也可以不
 需要计算比较最小移动步数，可以观察到 T（N） 的后一项与前一项的差形成以下数列 （N >= 1）：

 1 2 2 4 4 4 8 8 8 8 16 16 16 16 16 32 32 32 32 32 32 ……

 2 的 m 次幂出现 （m + 1） 次，m >= 0。
 */
