package gww.geeks.geometry;

/**
 * Created by 高文文 on 2017/5/10.
 *
 * A triangle is given by three points
 * A Polygon is given by an array of n + 1 vertex points, with points[n] = points[0]
 *
 *
 */
public class AreaOfPolygons {

    /**
     *   test if a point is Left | On | Right of an infinite 2D line
     * @input:  three points p1, p2, p3
     * @return:
     *          > 0 for p3 left of line through p1 to p2
     *          = 0 for p3 on the line
     *          < 0 for p3 right of the line
     */
    public float isLeft(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y);
    }

    /**
     * test the orientation of a 2D triangle
     * @input:  three points p1, p2, p3
     * @return:
     *          > 0 for counterclockwise
     *          = 0 for none(degenerate)
     *          < 0 for clockwise
     */
    public float orientation2DTriangle(Point p1, Point p2, Point p3) {
        return isLeft(p1, p2, p3);
    }

    /**
     *  compute the area of a 2D triangle
     * @return the float area of triangle
     */
    public float area2DTriangle(Point p1, Point p2, Point p3) {
        return isLeft(p1, p2, p3) / 2.0f;
    }


    /**
     *  test orientation of a 2D polygon
     * @param points an array of n+1 vertices with point[n] = point[0]
     * @return
     *          > 0 for counterclockwise
     *          = 0 for none(degenerate)
     *          < 0 for clockwise
     * Note: this algorithm is faster than computing the signed area like {orientation2DTriangle}
     */
    public float orientation2DPolygon(Point[] points) {

        //1. first find rightmost lowest point of then polygon
        int   rlPointIdx = 0;
        float xmin = points[rlPointIdx].x;
        float ymin = points[rlPointIdx].y;

        for(int i = 1; i < points.length; i++) {
            if(points[i].y > ymin) continue;
            if(Math.abs(points[i].y - ymin) < 1e-10) {
                if(points[i].x < xmin) continue;
            }
            rlPointIdx = i;
            xmin = points[i].x;
            ymin = points[i].y;
        }
        //2. test orientation at rlPoint, ccw <=> the edge leaving points[rlPointIdx] is left the entering edge
        if(rlPointIdx == 0)
            return isLeft(points[points.length - 1], points[0], points[1]);
        else
            return isLeft(points[rlPointIdx - 1], points[rlPointIdx], points[rlPointIdx + 1]);

    }

    /**
     * compute the area of a 2D Polygon
     *  1. areaT = sum{area{PViVi+1}}  i = [0, n-1]  when P=(0,0)
     *  2. area{PViVi+1} = 1/2 * [xi * yi+1 - yi * xi+1]
     *
     * @param points an array of n+1 vertices with point[n] = point[0]
     * @return
     */
    public float area2DPolygon(Point[] points) {
        if(points.length < 3) return 0; // a degenerate polygon
        float area = 0;
        Point p = new Point(0, 0);
        for (int i = 0; i< points.length - 1; i++) {
            area += isLeft(p, points[i], points[i + 1]);
        }
        return Math.abs(area / 2.0f);
    }


}
