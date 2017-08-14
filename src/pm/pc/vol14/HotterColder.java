package pm.pc.vol14;

/**
 * Created by 高文文 on 2017/6/21.
 */
public class HotterColder {
    public static void main(String[] args) {

    }


    private static final double SMALL_NUM = 0.000001;
    static class Point {
        double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "P[" + x +
                    ", " + y +
                    ']';
        }
    }
    static class Vector {
        double x, y;
        public Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    static class Line {
        Point P0, P1;

        public Line(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
    static class Segment {
        Point P0, P1;
        public Segment(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }

    private double dotProduct(Vector u, Vector v) {
        return u.x * v.x + u.y *v.y;
    }
    private double perpDotProduct(Vector u, Vector v) {
        return u.x*v.y - u.y*v.x;
    }

    /**
     *  两直线平行(包括相等) <=> 垂直点乘 PerpDotProduct(u, v) = 0
     *  向量U(ux, uy)的垂直算子为U^_|_(-uy, ux)
     * @param L1
     * @param L2
     * @return
     */
    public boolean parallelLine(Line L1, Line L2) {
        Vector u = new Vector(L1.P1.x - L1.P0.x, L1.P1.y - L1.P0.y);
        Vector v = new Vector(L2.P1.x - L2.P0.x, L2.P1.y - L2.P0.y);
        return perpDotProduct(u, v) < SMALL_NUM;
    }

    /**
     * 首先满足两直线平行，然后若两直线上的任意两点的向量与任意直线的向量的垂直点乘为0，则是同一条直线
     * @param L1
     * @param L2
     * @return
     */
    public boolean sameLine(Line L1, Line L2) {
        Vector u = new Vector(L1.P1.x - L1.P0.x, L1.P1.y - L1.P0.y);
        Vector v = new Vector(L2.P1.x - L2.P0.x, L2.P1.y - L2.P0.y);
        Vector w = new Vector(L2.P0.x - L1.P0.x, L2.P0.y - L1.P0.y);
        return perpDotProduct(u, v) < SMALL_NUM && perpDotProduct(u, w) < SMALL_NUM;
    }

    /**
     *  求两直线间的交点
     * @param L1
     * @param L2
     * @return
     * @throws SameLineException
     * @throws ParallelLineException
     */
    public Point lineIntersection(Line L1, Line L2) throws SameLineException, ParallelLineException{
        if(sameLine(L1, L2)) throw new SameLineException();
        if(parallelLine(L1, L2)) throw new ParallelLineException();

        Vector v = new Vector(L1.P1.x - L1.P0.x, L1.P1.y - L1.P0.y);
        Vector u = new Vector(L2.P1.x - L2.P0.x, L2.P1.y - L2.P0.y);
        Vector w = new Vector(L2.P0.x - L1.P0.x, L2.P0.y - L1.P0.y);
        double t = perpDotProduct(u, w) / perpDotProduct(u, v);
        Point I = new Point(L1.P0.x + t * v.x,L1.P0.y + t * v.y);
        return I;
    }

    /**
     * 判断点是否在线段对应的平面空间
     * @param P
     * @param S
     * @return
     */
    public boolean pointInSegmeng(Point P, Segment S) {
        Vector v = new Vector(S.P1.x - S.P0.x, S.P1.y - S.P0.y);
        Vector w = new Vector(P.x - S.P0.x, P.y - S.P0.y);
        double t = dotProduct(w, v) / dotProduct(v, v);
        return t >= 0 && t <= 1;
    }

    /**
     * 计算顶点为points构成的多边形的面积，
     *  1. 顶点顺序若是按照逆时针方向，结果为正；
     *  2. 若是按照顺时针方向，结果为负；
     * @param points
     * @return
     */
    public double polygonArea(Point[] points) {
        double area = 0.0;
        for(int i = 0, j = 0; i < points.length; i++) {
            j = (i + 1) % points.length;
            area += points[j].y * points[i].x - points[j].x * points[i].y;
        }
        return Math.abs(area)/2;
    }

    /**
     * 判断P3与直线P1P2的关系
     * @return > 0 P3在直线P1P2的左边
     *         = 0 P3在直线P1P2上
     *         < 0 P3在直线P1P2的右边
     */
    public double isLeft(Point P1, Point P2, Point P3) {
        return (P3.y - P1.y)*(P2.x - P1.x) - (P3.x - P1.x)*(P2.y - P1.y);
    }



//    public double doHotterColder(..)



}


class SameLineException extends Exception {
    public SameLineException() {
        super("同一条直线");
    }

    public SameLineException(String message) {
        super(message);
    }
}

class ParallelLineException extends Exception {
    public ParallelLineException() {
        super("两条直线平行");
    }

    public ParallelLineException(String message) {
        super(message);
    }
}