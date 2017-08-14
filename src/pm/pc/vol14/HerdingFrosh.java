package pm.pc.vol14;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by 高文文 on 2017/6/13.
 */
public class HerdingFrosh {

    static class  Point implements Comparable<Point> {
        float x, y;

        @Override
        public String toString() {
            return "[" + x +"," +y +
                    "]";
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
        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        HerdingFrosh herdingFrosh = new HerdingFrosh();
        Point[] points = new Point[]{new Point(1.0f, 1.0f), new Point(-1.0f, 1.0f),
                new Point(-1.0f, -1.0f), new Point(1.0f, -1.0f)};
        System.out.println(herdingFrosh.doHerdingFrosh(points));
    }

    private float isLeft(Point P0, Point P1, Point P2) {
        return (P2.y - P0.y)*(P1.x - P0.x) - (P2.x - P0.x)*(P1.y - P0.y);
    }

    /**
     *
     * 1. Andrew凸包扫描
     *    1). sort  ascending coordinates according to point(x, y)
     *    2).
     * 2. Melkmen算法
     *
     **/
    public float doHerdingFrosh(Point[] points) {
        if(points.length < 4) return calculatePerimeter(points);
        Arrays.sort(points);

        int n = points.length;
        int uIdx = 0;
        Point[] upperHull = new Point[n];
        upperHull[uIdx++] = points[0];
        upperHull[uIdx++] = points[1];
        for (int i = 2; i < n; i++) {
            upperHull[uIdx++] = points[i];
            while(uIdx >= 3 && isLeft(upperHull[uIdx - 3],upperHull[uIdx - 2],upperHull[uIdx - 1]) >= 0) {
                //中间点在包内，将其删除
                uIdx--;
                upperHull[uIdx - 1] = upperHull[uIdx];
            }
        }

        int lIdx = 0;
        Point[] lowerHull = new Point[n];
        lowerHull[lIdx++] = points[n - 1];
        lowerHull[lIdx++] = points[n - 2];
        for (int i = n - 3; i >= 0; i--) {
            lowerHull[lIdx++] = points[i];
            while(lIdx >= 3 && isLeft(lowerHull[lIdx - 3], lowerHull[lIdx - 2], lowerHull[lIdx - 1]) >= 0) {
                //中间点在包内，将其删除
                lIdx--;
                lowerHull[lIdx - 1] = lowerHull[lIdx];
            }
        }

        //合并删除重复点, 两个集合中首尾两点是重复的
        Point[] hulls = new Point[uIdx + lIdx - 2];
        System.arraycopy(upperHull, 0, hulls, 0, uIdx);
        System.arraycopy(lowerHull, 1, hulls, uIdx, lIdx - 2);

        return calculatePerimeter(hulls);
    }

    private float calculatePerimeter(Point[] polygon) {
        float perimeter = 0.0f;
        for (int i = 1; i < polygon.length; i++) {
            perimeter += Math.sqrt((polygon[i].x - polygon[i-1].x)*(polygon[i].x - polygon[i-1].x) +
                    (polygon[i].y - polygon[i-1].y)*(polygon[i].y - polygon[i-1].y));
        }
        perimeter += Math.sqrt((polygon[polygon.length - 1].x - polygon[0].x)*(polygon[polygon.length - 1].x - polygon[0].x) +
                (polygon[polygon.length - 1].y - polygon[0].y)*(polygon[polygon.length - 1].y - polygon[0].y));

        //extra line
        perimeter += 2 * Math.sqrt(polygon[0].x * polygon[0].x + polygon[0].y * polygon[0].y);
        return perimeter;
    }
}
