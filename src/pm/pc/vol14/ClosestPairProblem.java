package pm.pc.vol14;

import sun.nio.cs.ext.MacHebrew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by 高文文 on 2017/6/15.
 */
public class ClosestPairProblem {

    public static void main(String[] args) {
        ClosestPairProblem alg = new ClosestPairProblem();
        Point[] points = new Point[]{
                new Point(0f, 2f), new Point(6f, 67f),
                new Point(43f, 71f), new Point(39f, 107f),
                new Point(189f, 140f)
        };
        alg.doClosestPairProblem(points);
        alg.doClosestPairWithDC(points);
    }

    static  class Point {
        float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    private void sortPointsByX(Point[] points) {
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.x < o2.x) return -1;
                else if(o1.x > o2.x) return 1;
                return 0;
            }
        });
    }

    private static final int MAX_DISTANCE = 1000;
    private float d(Point P0, Point P1) {
        return (float) Math.sqrt((P1.x - P0.x)*(P1.x - P0.x) + (P1.y - P0.y)*(P1.y - P0.y));
    }
    public void doClosestPairProblem(Point[] points) {
        float minDist = Float.MAX_VALUE;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                minDist = Math.min(d(points[i], points[j]), minDist);
            }
        }
        if(minDist > MAX_DISTANCE) {
            System.out.println("INFINITY");
        } else {
            System.out.println(minDist);
        }

    }

    /** O(NlogN) Recursive Divide and Conquer
     *  将所给平面上n个点的集合S分成两个子集S1和S2，每个子集中约有n/2个点。然后在每个子集中递归地求最接近的点对。其中的一个关键问题
     *  如何实现分治法中的合并步骤，即由S1和S2的最接近点对，如何求得原集合S中的最接近点对？
     *【一维情况】
     *  此时S中的n个点退化为x轴上的n的实数X1,X2,...Xn, 此时最近点对为n个实数中相差最小的两个实数。采用分治算法解决这个问题：
     *   1. 假设m点将S1, S2两个集合，对于S1中任意点P,S2中任意点Q，有 P < Q
     *   2. 递归地在S1，S2上找出最近点对(P1,P2),(Q1,Q2), 设 d = min{|P1-P1|, |Q2-Q1|}
     *   则S中最近点对是|P2-P1|、|Q2-Q1|、|Q3-P3|, 如下图所示
     *      /-------S1--------\  /-------S2--------\
     *    ---*---*----*--*---*--|--*-----*----*------*--*--------> X轴
     *                P1 P2  P3 m  Q3                Q1  Q2
     *    若最近点对为(P3,Q3),则|Q3-P3| < d,且在区间(m-d, d)和(d, m+d)有且仅有一个点，可以在线性时间内合并
     *【二维情况】
     *  同理分析在二维平面中的情况，难点在于如何实现线性合并？，在形成的宽度为2*d的带状区间中，最多可能有n个点，合并时间为N^2。
     *  而实际上，左右两个带状区域有如下性质：
     *    设置左边d X 2d的带状区域为L,右边d X 2d的带状区域为R，则对于L中的任意一点P1，则P2点一定落在R中；
     *    由于若满足P2点在L中，且落再R中的点的距离至少为d，有鸽巢定理可以知道最多有6个点满足要求，这样对于L中任意一点P1，最多
     *    只需要检查R中的6个点，可以在线性时间内完成(可以按照Y轴排序后线性扫描)。
     *
     *【优化方法】
     * 1. 数组中保存的只是各个点的序号而已，并不是点的坐标，这样可以减少一些数据复制的时间
     *
     **/
    public void doClosestPairWithDC(Point[] points) {
        sortPointsByX(points);
        float dist = divideAndConquer(points, 0, (points.length - 1));
        if(dist > MAX_DISTANCE) {
            System.out.println("INFINITY");
        } else {
            System.out.println(dist);
        }

    }

    private float divideAndConquer(Point[] points, int left, int right) {
        if (right == left) return MAX_DISTANCE;
        if (right == left + 1) return d(points[left], points[right]);
        int mid = (left + right) >>> 1;
        float d1 = divideAndConquer(points, left, mid);
        float d2 = divideAndConquer(points, mid+1, right);
        float d = Math.min(d1, d2);
        ArrayList<Point> temp = new ArrayList<>();
        //分理出宽度为d的区间
        for (int i = left; i <= right; i++) {
            if(Math.abs(points[mid].x - points[i].x) <= d) {
                temp.add(points[i]);
            }
        }
        Collections.sort(temp, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.y < o2.y) return -1;
                else if(o1.y > o2.y) return 1;
                return 0;
            }
        });
        //线性扫描
        int size = temp.size();
        for(int i = 0; i < size; i++) {
            for(int j = i+1; j < size && (points[j].y - points[i].y < d); j++) {
                d = Math.min(d, d(points[j], points[i]));
            }
        }
        return d;
    }

}
