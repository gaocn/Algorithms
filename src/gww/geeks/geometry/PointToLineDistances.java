package gww.geeks.geometry;

/**
 * Created by 高文文 on 2017/5/11.
 */
public class PointToLineDistances {

    public static void main(String[] args) {

    }

    /**
     * 欧几里德距离(欧拉距离,Euclidean metric)
     *     是指多维空间两点间的直线距离,  two points P=(p1,p2,...pn) and Q=(q1,...,qn),它们的欧几里得距离为：
     *           d(P, Q) = |P - Q| = sqrt[sum{(pi - qi)^2}]   i = [1, n]
     * 曼哈顿(Manhattan)距离
     *     是该点与相邻的上下左右四个方向的任一邻点的距离, 两个点在标准坐标系上的绝对轴距总和
     *          d(P, Q) = sum{abs(pi - q1)} i = [1, n]
     *
     * 切比雪夫距离(Chebyshev)
     *     各坐标数值差的最大值，在2维空间中的计算公式为:
     *          d(P, Q) = max{abs(pi - q1)} i = [1, n]
     *
     *
     * 线段的表示方式：
     *  1. Explicit 2D:     y = f(x) = mx + b                 a non-vertical 2D line
     *  2. implicit 2D:     f(x, y) = ax + by + c             any 2D line
     *  3. Parametric:      P(t) = P0 + t*Vn                  any line any dimension
     *      Vn = P1 - P0  t = [0, 1]  P(0) = P0, P(1) = P1  P(1/2) = (P0 + P1) / 2 表示线段的中点
     *      if t < 0 then P(t) is outside the segment on the P0 side, and if t > 1 then P(t) is outside on the P1 side.
     *
     *
     * 点到线的距离：
     * 1. In 2D and 3D, when L is given by two points P0 and P1, one can use the cross-product to directly compute the
     *    distance from any point P to L. The 2D case is handled by embedding it in 3D with a third z-coordinate = 0.
     *                      D(P, L) = (ax + by + c)c /sqrt(a^2 + b^2)
     * 2.
     *
     * 点到线段或射线的距离
     *  通过求向量P0P1与向量P0P或向量P1P的点积，判断P是距离P0近还是距离P1近：
     *      若 dotProduct(P0P1, P0P) <= 0 则P点距离P0近，在P0的前面
     *      若 dotProduct(P0P1, P1P) >= 0 则P点距离P1近，在P1的后面
     */

    class Line {
        Point P0, P1;
        public Line(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
    class Segement {
        Point P0, P1;
        public Segement(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }

    private double dotProduct(Point u, Point v) {
        return u.x * v.x + u.y * v.y;
    }
    /** norm = length of  vector */
    private double norm(Point v) {
        return Math.sqrt(dotProduct(v , v));
    }
    /**  distance = norm of difference */
    private double d(Point u, Point v) {
        return norm(new Point(u.x - v.x, u.y - v.y));
    }


    public int closest2DPointToLine(Point[] points, Line L) {
        /**
         * for line P0P1, 则直线的方向向量为V = (x1 - x0, y1 - y0),
         *  而直线的垂直向量为N=(-(y1 - y0), x1 - x0)=(y0 - y1, x1 - x0),这有 L dot N = 0，可以得到直线的一般式为：
         *  T: 如果已知直线的法向量（与直线垂直的向量）n=(A,B) ,又已知直线过定点M(x0,y0),那么直线的方程为 A(x-x0)+B(y-y0)=0
         *  T: 如果已知直线的方向向量（与直线平行的向量）v=(v1,v2) ,又已知直线过定点M(x0,y0) ,那么直线的方程为 (x-x0)/v1=(y-y0)/v2
         *   直线的一般式为 (y0 - y1)x + (x1 - x0)y + (x0y1 - x1y0) = 0
         */
        double a = L.P0.y - L.P1.y;
        double b = L.P1.x - L.P0.x;
        double c = L.P0.x * L.P1.y - L.P1.x * L.P0.y;

        //initialize min index and distance to P0
        int minIdx = 0;
        double minDistance = points[0].x * a + points[0].y * b + c;
        minDistance = Math.abs(minDistance);

        //loop to find min distance to L
        for(int i = 1; i < points.length; i++) {
            double dist = Math.abs(points[i].x * a + points[i].y * b + c);
            if(dist < minDistance) {
                minIdx = i;
                minDistance = dist;
            }
        }
        // index of the closest point[midIdx]
        return minIdx;
    }

    /**
     * @return shortest distance from point P to Line L in any N-dimensional space
     *
     */
    public double pointToLineDist(Point P, Line L) {
        Point vectorV  = new Point(L.P1.x - L.P0.x, L.P1.y - L.P0.y);
        Point vectorW  = new Point(P.x - L.P0.x, P.y - L.P0.y);

        double c1 = dotProduct(vectorW, vectorV);
        double c2 = dotProduct(vectorV, vectorV);
        double b = c1/ c2;

        Point Pb = new Point(L.P0.x + b * vectorV.x, L.P0.y + b * vectorV.y);
        return d(P, Pb);
    }

    /**
     *
     * @return shortest distance from point P to segment L in any N-dimensional space
     *  若P点的垂线是在线段P0P1外的，那么此时P点到线段的距离即为P点到线段的两个端点的距离；
     *  若垂线在P0P1之间那么，计算方式如上
     */
    public double pointToSegmentDist(Point P, Segement segement) {
        Point vectorV = new Point(segement.P1.x - segement.P0.x, segement.P1.y - segement.P0.y);
        Point vectorW = new Point(P.x - segement.P0.x, P.y - segement.P0.y);

        double c1 = dotProduct(vectorW, vectorV);
        if(c1 <= 0)
            return d(P, segement.P0);

        double c2 = dotProduct(vectorV, vectorV);
        if(c2 <= c1)
            return d(P, segement.P1);

        double b = c1 / c2;
        Point Pb = new Point(segement.P0.x + b * vectorV.x, segement.P0.y + b * vectorV.y);
        return d(P, Pb);
    }


}
