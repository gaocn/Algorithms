package pm.pc.vol14;

import jdk.internal.dynalink.support.BottomGuardingDynamicLinker;

import java.util.Arrays;

/**
 * Created by 高文文 on 2017/6/26.
 */
public class TilePacker {


    public static void main(String[] args) {
        TilePacker alg = new TilePacker();

        HotterColder.Point[] points = new HotterColder.Point[] {
            new HotterColder.Point(0, 0),
            new HotterColder.Point(2, 0),
            new HotterColder.Point(2, 2),
            new HotterColder.Point(1, 1),
            new HotterColder.Point(0, 2),
        };

        System.out.println(Arrays.toString(alg.melkmen(points)));
        System.out.println("Tile #1\n" +
                "Wasted Space = " + alg.doTilePacker(points));
    }

    public double doTilePacker(HotterColder.Point[] points) {
        HotterColder.Point[] hull = melkmen(points);
        double hullArea = calculateArea(hull);
        double tileArea = calculateArea(points);
        return (hullArea - tileArea)/hullArea;
    }


    public HotterColder.Point[] melkmen(HotterColder.Point[] points) {
        if(points.length < 4) return points;
        int n = points.length;
        //initialize a Deque from bottom to top so that 1st three vertices in Deque is CCW
        HotterColder.Point[] D = new HotterColder.Point[2 * n + 1];
        int bottom = n - 2, top = n + 1;
        D[bottom] = points[2];
        D[top] = points[2];
        if(isLeft(points[0], points[1], points[2]) > 0) {
            // CCW vertices are: p2, p0, p1, p2
            D[bottom + 1] = points[0];
            D[bottom + 2] = points[1];
        } else {
            // CC vertices are: p2, p1, p0, p2
            D[bottom + 1] = points[1];
            D[bottom + 2] = points[0];
        }

        for(int i = 3; i < n; i++) {
            // point is inside the deque hull
            if(top - bottom > 1 && isLeft(D[bottom], D[bottom + 1], points[i]) > 0
                    && isLeft(D[top - 1], D[top], points[i]) > 0) continue;

            /**case 1: get rightmost tangent from point[i] to deque hull*/
            while(top - bottom > 1 && isLeft(D[bottom], D[bottom + 1], points[i]) <= 0)
                bottom++;
            D[--bottom] = points[i];

            /**case 2: get leftmost tangent from point[i] to deque hull*/
            while (top - bottom > 1 && isLeft(D[top - 1], D[top], points[i]) <= 0)
                top--;
            D[++top] = points[i];
        }
        //transcribe D[] to output hull array hull[]
        HotterColder.Point[] hull = new HotterColder.Point[top - bottom];
        System.arraycopy(D, bottom, hull, 0, top - bottom);
        D = null;
        return hull;
    }


    public double isLeft(HotterColder.Point P0, HotterColder.Point P1, HotterColder.Point P2) {
        return (P2.y - P0.y)*(P1.x - P0.x) - (P2.x - P0.x)*(P1.y - P0.y);
    }

    /**
     * <b>NOTE:</b>the are may be negative if points given is not CCW
     * @param points  represents a polygon with n points
     * @return the are of the polygon
     */
    public double calculateArea(HotterColder.Point[] points) {
        double area = 0;
        HotterColder.Point P = new HotterColder.Point(0, 0);
        for(int i = 0, j; i < points.length; i++) {
            j = (i + 1) % points.length;
            area += isLeft(P, points[i], points[j]);
        }
        return Math.abs(area) / 2.0;
    }

/*
Sample Input

5
0 0
2 0
2 2
1 1
0 2
5
0 0
0 2
1 3
2 2
2 0
0
Sample Output

Tile #1
Wasted Space = 25.00 %

Tile #2
Wasted Space = 0.00 %

 */


}
