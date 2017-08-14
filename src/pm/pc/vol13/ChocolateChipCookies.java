package pm.pc.vol13;

/**
 * Created by 高文文 on 2017/6/8.
 */
public class ChocolateChipCookies {
    public static void main(String[] args) {

    }

    /**
     *
     * 对于平面上的若干个点，给定一个直径为5的圆，求该圆中能够包含的最多点数，包括在圆周上的点。
     * 遍历所有的点，对于任意两个点P0, P1, 两点的距离为D, 最多点数为maxCount
     *   1. 若D > 5， 则P0, P1在圆外，重新选择另一组两点进行计算；
     *   2. 若D == 5，则P0, P1在圆上，计算圆心坐标后，遍历所有点计算在此圆的点数为count, maxCount = Math.max(maxCount, count)
     *   3. 若D < 5, 则P0, P1在圆内，根据公式计算圆心坐标后，遍历所有点计算在此圆的点数为count, maxCount = Math.max(maxCount, count)
     *  返回maxCount
     *
     */
    public int doChocolateChipCookies() {
        return 0;
    }
}
