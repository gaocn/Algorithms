package pm.pc.vol14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by 高文文 on 2017/6/19.
 */
public class ChainsawMassacre {

    public static class Point{
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        ChainsawMassacre lag = new ChainsawMassacre();
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(1,1));
        points.add(new Point(1,9));
        points.add(new Point(9,1));
        points.add(new Point(9,9));
        System.out.println(lag.doChainsawMassacre(points, 10, 10));
    }

    /**
     * 找最一个内部为空的长方形
     * 依次以一棵数作为左边界，往右扫描确定右边界，当左右边界确定后，就可以确定其面积，然后上下再进行一次同样的扫描，即可确定最终的最大矩形面积。
     *
     */


    public int doChainsawMassacre(ArrayList<Point> points, int l, int w) {
        //将左下角和右上角的考虑进去，作为矩形的左右边界
        points.add(new Point(0,0));
        points.add(new Point(l, w));
        int maxArea = Integer.MIN_VALUE;

        //X轴正方向扫描
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.x < o2.x) return -1;
                else if(o1.x > o2.x) return 1;
                return 0;
            }
        });
        int topY ,lowY;
        int n = points.size();
        for(int i = 0; i < n; i++) {
            topY = w; lowY = 0;
            for(int j = i +1; j < n; j++) {
                Point P = points.get(j);
                Point Q = points.get(i);
                if(P.x == Q.x) continue;
                maxArea = Math.max(maxArea, (P.x - Q.x)*(topY - lowY));
                if(P.y < Q.y) lowY = Math.max(lowY, P.y);
                else topY = Math.min(topY, P.y);
            }
        }

        //Y轴正方向扫描
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.y < o2.y) return -1;
                else if(o1.y > o2.y) return 1;
                return 0;
            }
        });
        int leftX, rightX;
        for(int i = 0; i < n; i++) {
            leftX = 0; rightX = l;
            for(int j = i + 1; j < n; j++) {
                Point P = points.get(j);
                Point Q = points.get(i);
                if(P.y == Q.y) continue;
                maxArea = Math.max(maxArea, (rightX - leftX)*(P.y - Q.y));
                if(P.x > Q.x) rightX = Math.min(rightX, P.x);
                else leftX = Math.max(leftX, P.x);
            }
        }

        return maxArea;
    }
}
