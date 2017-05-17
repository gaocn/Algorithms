package gww.geeks.geometry;

/**
 * Created by 高文文 on 2017/5/17.
 */
public class InclusionOfPointInPolygon {

    /**
     *
     */

    /**
     * @return > 0 if P2 is left of line P0P1
     *         = 0 if P2 is on line P0P1
     *         < 0 if P2 is right of line P0P1
     */
    private double isLeft(Point P0, Point P1, Point P2) {
        return (P2.y - P0.y)*(P1.x - P0.x) - (P2.x - P0.x)*(P1.y - P0.y) ;
    }

    /**
     *
     * @param points  n + 1 vertex points of a polygon V[n] = V[0]
     * @return false: outside   true: inside
     */
    public boolean cnPnPoly(Point[] points, Point P) {
        int crossingNumber = 0;

        int n = points.length - 1;
        //edge from Vi ~Vi+1 [0, n-1]
        for(int i= 0; i < n; i++) {
            if(((points[i].y <= P.y) && (P.y < points[i+1].y))  //a upward edge
                    ||((points[i].y > P.y) && (P.y <= points[i+1].y))) {  // a downward edge
                // compute the actual edge-ray intersect x-coordinate
                double vt = (P.y - points[i].y) / (points[i+1].y - points[i].y);
                if(P.x < points[i].x + vt * (points[i+1].x - points[i].x)) { //P.x < intersect
                    //a valid crossing of y = P.y right of P.x
                    crossingNumber++;
                }
            }
        }
        // crossingNumber is even return false, return true if odd
        return (crossingNumber & 1) != 0;
    }

    /**
     *
     * @param points n + 1 vertex points of a polygon V[n] = V[0]
     * @return 0 iif P is outside
     */
    public int wnPnPoyl(Point[] points, Point P) {
        int windingNumber = 0;
        int n = points.length - 1;
        // loop all edges from ViVi+1 i=[0, n - 1]
        for(int i= 0; i < n; i++) {
            if(points[i].y <= P.y) {
                if(points[i+1].y > P.y) {  // a upward edge
                    if(isLeft(points[i], points[i+1], P) > 0) {
                        //P left of edge, is a valid up intersect
                        windingNumber++;
                    }
                }
            } else {
                if(points[i+1].y <= P.y) { //a downward edge
                    if(isLeft(points[i], points[i+1], P) < 0) {
                        // P right of edge, is a valid down intersect
                        windingNumber--;
                    }
                }
            }
        }


        return windingNumber;
    }


}
