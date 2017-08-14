package nutshell.geometry;

import java.util.*;

/**
 * Created by 高文文 on 2017/5/2.
 */
public class GrometryAlg {

    /*
input:
15
30 30
50 60
60 20
70 45
86 39
112 60
200 113
250 50
300 200
130 240
76 150
47 76
36 40
33 35
30 30
output:
8
60 20
250 50
300 200
130 240
76 150
47 76
30 30
60 20
     */
    public static void main(String[] args){
        Point[] points = new Point[15];

 /*       points = new Point[]{
                new Point(30, 30), new Point(50, 60), new Point(60, 20), new Point(70, 45),
                new Point(86, 39), new Point(112, 60), new Point(200, 113), new Point(250, 50),
                new Point(300, 200), new Point(130, 240), new Point(76, 150), new Point(47, 76),
                new Point(36, 40), new Point(33, 35), new Point(30, 30)
        };*/
        points = new Point[]{
                new Point(1.0f, 1.0f), new Point(-1.0f, 1.0f), new Point(-1.0f, -1.0f), new Point(1.0f, -1.0f)
        };
        GrometryAlg alg = new GrometryAlg();
        System.out.println(Arrays.toString(alg.AndrewConvexHullScan(points)));
        System.out.println(Arrays.toString(alg.Melkman(points)));


    }


    static class Point implements Comparable<Point>{
        float x, y;
        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public int compareTo(Point o) {
            if(this.x < o.x) { return -1; }
            else if(this.x > o.x) { return 1;}
            else {
                if(this.y < o.y) { return -1; }
                else if(this.y > o.y) { return 1; }
            }
            return 0;
        }
    }

    /**
     * 凸包扫描
     * 1. Andrew算法是葛立恒(Graham、Jarvis)扫描法的变种，但是更快，O(NlogN)
     * 2. Melkman算法采用双端队列，O(N)，适用于对时间要求更高的情况
     *
     * 问题：如何处理重复点？ andrew、melman均可以自动处理重复点(忽略)
     */


    public Point[] AndrewConvexHullScan(Point[] points) {
        //排序按照x坐标，x坐标相同按照y坐标排序
        int n = points.length;
        Arrays.sort(points);
        if(n < 3) { return points;}
        //从最左边两个点开始计算上凸包
        int uIdx = 0;
        Point[] upper = new Point[n];
        upper[uIdx++] = points[0];
        upper[uIdx++] = points[1];
        for(int i = 2; i < n; i++) {
            upper[uIdx++] = points[i];
            while(uIdx >= 3 && isLeft(upper[uIdx - 3], upper[uIdx - 2], upper[uIdx - 1]) >= 0) {
                //中间点在包内，将其删除
                uIdx--;
                upper[uIdx - 1] = upper[uIdx];
            }
        }
        //从最右边两个点开始计算下凸包
        int lIdx = 0;
        Point[] lower = new Point[n];
        lower[lIdx++] = points[n - 1];
        lower[lIdx++] = points[n - 2];

        for(int i = n - 3; i >= 0; i--) {
            lower[lIdx++] = points[i];
            while(lIdx >= 3 && isLeft(lower[lIdx - 3], lower[lIdx - 2], lower[lIdx - 1]) >= 0) {
                //中间点在包内，将其删除
                lIdx--;
                lower[lIdx - 1] = lower[lIdx];
            }
        }

        //合并删除重复点, 两个集合中首尾两点是重复的
        Point[] hull = new Point[uIdx + lIdx - 2];
        System.arraycopy(upper, 0, hull, 0, uIdx);
        System.arraycopy(lower, 1, hull, uIdx, lIdx - 2);
        return hull;
    }

    /** >0 p3在p1p2线的左边，<0 p3在p1p2线的右边，=0 三点共线 */
    private float isLeft(Point p1, Point p2, Point p3) {
        return (p3.y - p1.y)*(p2.x - p1.x) - (p3.x - p1.x)*(p2.y - p1.y);
    }

    /**
     *CCW逆时针,Counter Clockwise
     * 在线算法，指它可以以序列化的方式一个个的处理输入，也就是说在开始时并不需要已经知道所有的输入。
     * 离线算法，在开始时就需要知道问题的所有输入数据，而且在解决一个问题后就要立即输出结果。
     *
     * 问题描述：
     *  给定一组二维坐标集合，求从这个集合中找出这些二维坐标的外围边界
     * melkman算法：在线算法，O(N)，使用双端队列实现
     * 算法描述：
     *  1. 初始化，first 3 vertices to deque, 3rd vertex p[2] at the bottom deque
     *      deque: p2 p0 p1 p2或者p2 p1 p0 p2， on deque they form a CCW Triangle
     *  Note that:
     *     a) Deque[] is now the convex hull of already processed vertices
     *     b) Deque[bot] = Deque[top] = the last vertex added to Deque[]
     *  2. for next vertex p[i]
     *      2-1:  If P[i] is left of both Deque[bot]Deque[bot+1] and Deque[top-1]Deque[top]
     *      2-2:  While P[i] is right of D[bot]D[bot+1]
     *               Delete D[bot] from the bottom of D[]
     *            Insert P[i] at the bottom of D[]
     *      2-3:  While P[i] is right of D[top-1]D[top]
     *              Pop D[top] from the top of D[]
     *            Push P[i] onto the top of D[]
     *
     */
    public Point[] Melkman(Point[] points) {
        int n = points.length;
         //initialize a deque D[] from bottom to top so that the 1st three vertices of P[] are a ccw triangle
        Point[] deque = new Point[2 * n + 1];
        int bottom = n - 2, top    = n + 1;
        deque[bottom] = points[2];
        deque[top]    = points[2];
        //保证整体方向是逆时针方向
        if(isLeft(points[0], points[1], points[2]) > 0 ) {
            //ccw vertices are: 2,0,1,2
            deque[bottom + 1] = points[0];
            deque[bottom + 2] = points[1];
        } else {
            // ccw vertices are: 2,1,0,2
            deque[bottom + 1] = points[1];
            deque[bottom + 2] = points[0];
        }
        for(int i = 3; i < n; i++) {
            //if next vertex is inside the deque hull
            if(top - bottom > 1 && isLeft(deque[bottom], deque[bottom + 1], points[i]) > 0 &&
                    isLeft(deque[top - 1], deque[top], points[i]) > 0) continue;
            /** incrementally add an exterior vertex to the deque hull
             *   get the rightmost tangent at the deque bot
             */
            while(top - bottom > 1 && isLeft(deque[bottom], deque[bottom + 1], points[i]) <= 0)
                bottom++;                  //remove bot of deque
            deque[--bottom] = points[i];  //insert P[i] at bot of deque
            /**  get the leftmost tangent at the deque top */
            while (top - bottom > 1 && isLeft(deque[top - 1], deque[top], points[i]) <= 0)
                --top;                   //pop top of deque
            deque[++top] = points[i];    //push P[i] onto top of deque
        }
        //transcribe deque[] to the output hull array hull[]
        Point[] hull = new Point[top - bottom];
        System.arraycopy(deque, bottom, hull, 0, top - bottom);
        deque = null;
        return hull;
    }


    /**
     * 线段扫描
     */






}
