package pm.pc.vol14;

/**
 * Created by 高文文 on 2017/6/28.
 */
public class TreesOnIsland {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        TreesOnIsland alg = new TreesOnIsland();

        Point[] points = new Point[] {
                new Point(3, 1),
                new Point(6, 3),
                new Point(9, 2),
                new Point(8, 4),
                new Point(9, 6),
                new Point(9, 9),
                new Point(8, 9),
                new Point(6, 5),
                new Point(5, 8),
                new Point(4, 4),
                new Point(3, 5),
                new Point(1, 3),
        };

        System.out.println(alg.doTreesOnIsland(points));

    }


    /**
     * Pick定理：一个计算点阵中顶点在格点上的多边形面积公式
     *          2S = 2a + b - 2
     *          S为多边形面积
     *          a为多边形内部点数
     *          b为多边形边界上的点数
     */
    public long doTreesOnIsland(Point[] points) {
        // 由多边形面积计算公式计算多边形面积，注意计算结果是面积实际值乘以 2，这样避免了小数。
        long S = calculateArea(points);
        long b = boundaryPoints(points);
        return (2 * S + 4 - 2 * b) / 4;
    }

    /**
     * 计算多边形面积 返回值为多边形面积的二倍
     */
    private long calculateArea(Point[] points) {
        long area = 0;
        for(int i =0, j; i < points.length; i++) {
            j = (i + 1) % points.length;
            area += points[j].y*points[i].x - points[j].x * points[i].y;
        }
        return Math.abs(area);
    }

    /**
     * 计算多边形边界上的顶点个数，实际上计算相邻两个顶点之间连线有多少个格点
     */
    private long boundaryPoints(Point[] points) {
        long pointNumber = 0;
        for(int i = 0, j; i < points.length; i++) {
            j = (i + 1) % points.length;

            if(points[i].x == points[j].x) {        //1. 线段平行Y轴
                pointNumber += Math.abs(points[i].y - points[j].y) - 1;
            } else if(points[i].y == points[j].y) { //2. 线段平行X轴
                pointNumber += Math.abs(points[i].x - points[j].x) - 1;
            } else {
                //横坐标与纵坐标的差的绝对值
                long xDiff = Math.abs(points[i].x - points[j].x);
                long yDiff = Math.abs(points[i].y - points[j].y);

                /** 可以通过求xDiff与yDiff的最大公约数算出此线段上点的个数
                 *
                 */
                pointNumber += gcd(xDiff, yDiff) - 1;
            }
        }
        return points.length + pointNumber;
    }

    /**
     * 最大公约数
     * @param a
     * @param b
     * @return
     */
    private long gcd(long a, long b) {
        if(b > a) return gcd(b, a);
        if(b == 0) return a;
        return gcd(b, a % b);
    }



    /*
    12
3 1
6 3
9 2
8 4
9 6
9 9
8 9
6 5
5 8
4 4
3 5
1 3
12
1000 1000
2000 1000
4000 2000
6000 1000
8000 3000
8000 8000
7000 8000
5000 4000
4000 5000
3000 4000
3000 5000
1000 3000
0
Sample Output

21
25990001
     */
}
