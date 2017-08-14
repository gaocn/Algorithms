package gww.geeks.plane.intersect;

import jdk.nashorn.internal.runtime.ECMAException;
import sun.nio.cs.ext.MacHebrew;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by 高文文 on 2017/7/3.
 */
public class MostDistanceFromSea {
    /*
Description

The main land of Japan called Honshu is an island surrounded by the sea. In such an island, it is natural to ask a question:
  “Where is the most distant point from the sea?” The answer to this question for Honshu was found in 1996.
  The most distant point is located in former Usuda Town, Nagano Prefecture, whose distance from the sea is 114.86 km.
In this problem, you are asked to write a program which, given a map of an island, finds the most distant point from the sea
  in the island, and reports its distance from the sea. In order to simplify the problem, we only consider maps representable
  by convex polygons.

Input
The input consists of multiple datasets. Each dataset represents a map of an island, which is a convex polygon. The format
  of a dataset is as follows.
        n
        x1		y1
        ⋮
        xn		yn
Every input item in a dataset is a non-negative integer. Two input items in a line are separated by a space.
n in the first line is the number of vertices of the polygon, satisfying 3 ≤ n ≤ 100. Subsequent n lines are the x- and
  y-coordinates of the n vertices. Line segments (xi, yi)–(xi+1, yi+1) (1 ≤ i ≤ n − 1) and the line segment (xn, yn)–(x1, y1)
  form the border of the polygon in counterclockwise order. That is, these line segments see the inside of the polygon in the
  left of their directions. All coordinate values are between 0 and 10000, inclusive.
You can assume that the polygon is simple, that is, its border never crosses or touches itself. As stated above, the given
  polygon is always a convex one.
The last dataset is followed by a line containing a single zero.

Output
For each dataset in the input, one line containing the distance of the most distant point from the sea should be output. An
  output line should not contain extra characters such as spaces. The answer should not have an error greater than 0.00001
  (10−5). You may output any number of digits after the decimal point, provided that the above accuracy condition is satisfied.

Sample Input
4
0 0
10000 0
10000 10000
0 10000
3
0 0
10000 0
7000 1000
6
0 40
100 20
250 40
250 70
100 90
0 70
3
0 0
10000 10000
5000 5001
0
Sample Output

5000.000000
494.233641
34.542948
0.353553
     */

    private static final double EPS = 1.0e-10;
    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x +
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

        @Override
        public String toString() {
            return "[" + x +
                    ", " + y +
                    ']';
        }
    }

    static class Line {
        double a, b ,c;

        public Line(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }
    /**
     * 两点确定一条直线
     * 对于直线上的两点(x0, y0), (x1, y1), 直线的方向向量为V=(x1-x0, y1-y0)
     * 而此方向向量的垂直算子为N=(y0-y1, x1-x0)
     * 由dot(V, N) = 0可以得到直线P0P1的隐式表达式：
     *      (y0-y1)x + (x1-x0)y +(x0y1-x1y0) = 0
     *
     * */
    public Line getLine(Point P0, Point P1) {
        double a = P0.y - P1.y;
        double b = P1.x - P0.x;
        double c = P0.x*P1.y - P1.x * P0.y;
        return new Line(a, b, c);
    }

    /**
     * Q0Q1构成的线段与直线L之间的距离
     * */
    public Point lineIntersect(Point Q0, Point Q1, Line L) throws Exception{
        Line Q = getLine(Q0, Q1);

        double X = (L.b*Q.c - Q.b*L.c);
        double Y = (L.a*Q.c - Q.a*L.c);
        double D = L.a * Q.b - Q.a * L.b;
//        System.out.println("X: " + X + " Y: "+ Y + " D: " + D);
        if(Math.abs(D) < EPS && Math.abs(X) < EPS && Math.abs(Y) < EPS) {
            throw new Exception("Line parallel");
        }
        double x =  X / D;
        double y =  Y / -D;
        return new Point(x, y);
    }

    public double isLeft(Point P0, Line line) {
        return line.a*P0.x + line.b * P0.y + line.c;
    }

    /** 表示多边形，顶点按照顺时针方向存放 */
     Point[] points;
    /** 每一次用line对图形进行裁剪，将最后裁剪后的多边形更新到points中 */
    public void cut(Line line) {
        LinkedList<Point> polygon = new LinkedList<>();
//        System.out.println("Line: " + line);
        for(int i = 0, j; i < points.length; i++) {
            if(isLeft(points[i], line) <= 0) {
                polygon.add(points[i]);
            } else {
                j = (points.length + i - 1) % points.length;
                if( isLeft(points[j], line) < 0) {
                    try {
                        polygon.add(lineIntersect(points[j], points[i], line));
                    } catch (Exception e) {
                        // line parallel, no  intersect to be added
                    }
                }

                j = (i + 1) % points.length;
                if(isLeft(points[j], line) < 0) {
                    try {
                        polygon.add(lineIntersect(points[i], points[j], line));
                    } catch (Exception e) {
                        // line parallel, no  intersect to be added
                    }
                }
            }
        }
//        System.out.println("old points: " + Arrays.toString(points));
        points = null;
        points = new Point[polygon.size()];
        points = polygon.toArray(points);
//        System.out.println("new points: " + Arrays.toString(points));
//        System.out.println("----------------------------------------------");

    }

    /** 如果最终的多边形顶点数目为0，那么就说明半平面交无解  */
    public boolean solved(double radius) {
//        System.out.println("solved BEFORE:" + Arrays.toString(points));
        /**
         * 确定是否存在圆心，可以把原来的凸多边形往内部移动r（圆的半径）的距离之后，再对新的多边形求半平面交，
         * 如果半平面交存在（是个点即可），那么当前大小的圆能够放入。
         *
         * 如何把一条直线往逆时针方向或者顺时针方向移动R的距离?
         * 利用单位圆思路，相当于以原来直线上的一点为圆心，以r为半径做圆，而且与原来的直线成90度夹角；
         * 那么后来点的坐标是（(x0 + cos(PI / 2 +θ ))，（y0 + sin(PI / 2 + θ))）
         *
         */
        Point temp = new Point(0,0);
        Point P0 = new Point(0,0);
        Point P1 = new Point(0,0);
        for(int i = 0, j; i < this.points.length; i++) {
            j = (i + 1) % this.points.length;
            //TODO ???
            temp.x = points[j].y - points[i].y;
            temp.y = points[i].x - points[j].x;
            double k = radius / Math.sqrt(temp.x * temp.x + temp.y * temp.y);

            P0.x = points[i].x + temp.x * k;
            P0.y = points[i].y + temp.y * k;
            P1.x = points[j].x + temp.x * k;
            P1.y = points[j].y + temp.y * k;
//            System.out.println("P0:" + P0 + ", P1: " + P1);
            cut(getLine(P0, P1));
        }
//        System.out.println("solved AFTER:" + Arrays.toString(points));

        return points.length > 0;
    }


    public void doMostDistanceFromSea(Point[] points) {
        this.points = points;
        //规整化方向为ClockWise, 默认多边形顶点方向为顺时针方向
        //makeClockWise(points)
        //采用二分法确定多边形内接圆的半径，范围在[1, 10000]之间
        double minRadius = 1, mid;
        double maxRadius = 1000000;
        while(minRadius + EPS <= maxRadius) {
            mid = (minRadius + maxRadius) / 2;
            if(solved(mid))minRadius = mid;
            else maxRadius = mid;
        }
        //maxRadius即为最大距离
        System.out.println("最大距离为：" + minRadius);
    }

    /*******************************************************************************/

    public static void main(String[] args) {
        Point[] points = new Point[] {
                new Point(1,1),new Point(4, 4),
        };
    }

}
