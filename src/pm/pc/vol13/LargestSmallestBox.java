package pm.pc.vol13;

/**
 * Created by 高文文 on 2017/6/9.
 */
public class LargestSmallestBox {

    /**
     *
     * V = (L-2x)(W-2x)x   x = [0, min(L,W)/2]
     * 求V取最大值、最小值时的x值
     * 1. V的最小值是V=0 => x=0 或 x = min(L,W)/2
     * 2. V有最小值，当且仅当V'=12x^2-4x(L+W)+LW中的delta =16(L^2+W^2-LW) > 0
     *    则有x1 = [(L+W)-sqrt(L^2+W^2-LW)]/6
     *       x2 = [(L+W)+sqrt(L^2+W^2-LW)]/6
     * 根据单调性可以知道：x1是极大值，x2是极小值(x2 > min(L,W)/2 不在定义域内)
     *
     * 1 1      0.167 0.000 0.500
     * 2 2      0.333 0.000 1.000
     * 3 3      0.500 0.000 1.500
     *
     */
}
