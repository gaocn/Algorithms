package gww.geeks.geometry;

/**
 * Created by 高文文 on 2017/6/2.
 */
public class LineToLineDistance {
    private static final double SMALL_NUM = 0.000001;
    class Point {
        double x, y, z;
        public Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Point) {
                Point p = (Point) obj;
                if(this.x == p.x && this.y == p.y && this.z == p.z) {
                    return true;
                }
            }
            return false;
        }
    }
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
    class Vector {
        double x, y, z;
        public Vector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof RayTriangleIntersection.Vector) {
                RayTriangleIntersection.Vector v2 = (RayTriangleIntersection.Vector)obj;
                if(Math.abs(this.x - v2.x) < SMALL_NUM &&
                        Math.abs(this.y - v2.y) < SMALL_NUM &&
                        Math.abs(this.z - v2.z) < SMALL_NUM) {
                    return true;
                }
            }
            return false;
        }
    }
    private double dotProduct(Vector P0, Vector P1) {
        return P0.x * P1.x + P0.y * P1.y + P0.z * P1.z;
    }
    /** norm = length of  vector */
    private double norm(Vector v) {
        return Math.sqrt(dotProduct(v , v));
    }
    /**  distance = norm of difference */
    private double d(Vector u, Vector v) {
        return norm(new Vector(u.x - v.x, u.y - v.y, u.z - v.z));
    }
    private double d(Point u, Point v) {
        return norm(new Vector(u.x - v.x, u.y - v.y, u.z - v.z));
    }
    /**
     * Euclidean distance between any two geometric objects is defined as the minimum distance between any two of their points.
     *
     * 1. Distance between Lines
     *    line1: P(s) = P0 + su
     *    line2: Q(t) = Q0 + tv
     *    w(s, t) = P(s) - Q(t) = (P0-Q0) + su - tv  vector between points on the two line
     *    Assume: in any n-dimensional space, L1 and L2 are closest at unique points P(sc) Q(tc)
     *    IF L1 and L2 are NOT parallel and do NOT intersect each other, then segment P(sc)Q(tc) is uniquely
     *      perpendicular to both lines IIF P(sc)Q(sc) is the closest distance of the two lines
     *
     *      w0 = P0 - Q0
     *      wc = (sc, tc)  = W0 + sc * u - tc * v
     *      dot(wc, u) = 0
     *      dot(wc, v) = 0
     *
     *      (u·u)sc - (u·v)tc = -u·w0
     *      (v·u)sc - (v·v)tc = -v·w0
     *      a = u·u   b = u·v  c = v·v   d = u·w0    e = v·w0
     *
     *      sc = (be-cd)/(ac-b^2)
     *      tc = (ae-bd)/(ac-b^2)
     *Note: when  ac–b^2 = 0, two lines are parallel, we can fix sc = 0, so we get tc = d/b = e/c
     *
     *      d(L1, L2) = |P(sc) - Q(sc)| = |w0 + [(be-cd)u - (ae - bd)v]/(ac-b^2)|
     *
     */

    /**
     * get the 3D minimum distance between 2 lines
     *
     * @return the shortest distance between L1 and L2
     */
    public double lineToLineD(Line L1, Line L2) {
        Vector u = new Vector(L1.P1.x - L1.P0.x, L1.P1.y - L1.P0.y, L1.P1.z - L1.P0.z);
        Vector v = new Vector(L2.P1.x - L2.P0.x, L2.P1.y - L2.P0.y, L2.P1.z - L2.P0.z);
        Vector w0 = new Vector(L1.P0.x - L2.P0.x, L1.P0.y - L2.P0.y, L1.P0.z - L2.P0.z);

        double a = dotProduct(u, u);
        double b = dotProduct(u, v);
        double c = dotProduct(v, v);
        double d = dotProduct(u, w0);
        double e = dotProduct(v, w0);
        double sc, tc;
        double D = a * c - b * b;
        if(D < SMALL_NUM) {  // line is almost parallel
            sc = 0;
            tc = (b > c ? d/b : e/c);  // use the largest denominator
        } else {
            sc = (b * e - c * d) / D;
            tc = (a * e - b * d) / D;
        }

        //get the difference of two closest points
        Vector dP = new Vector(
                w0.x + sc * u.x - tc * v.x,
                w0.y + sc * u.y - tc * v.y,
                w0.z + sc * u.z - tc * v.z
        );

        return norm(dP);
    }

     /**
     * 2. Distance between Segments and Rays
     *
     *
     */



     /**
      * 3. Closest Point of Approach (CPA)
      *  how to compute the closest point of approach (CPA) between two points that are dynamically moving in straight lines.
      *  for example to determine how close two planes or two ships, represented as point "tracks", will get as they pass each other.
      *  1) Consider two dynamically changing points whose positions at time t are P(t) and Q(t);
      *  2) Let their positions at time t = 0 be P0 and Q0;
      *  3) let their velocity vectors per unit of time be u and v;
      *  4) Then, the equations of motion for these two points are P(t)=P0+tu and Q(t)=Q0+tv
      *  At time t, the distance between them is d(t) = |P(t) – Q(t)| = |w(t)| where w(t)=w0+t(u-v) with w0=P0-Q0
      *  since d(t) is a minimum when D(t) = d(t)^2 is a minimum, we can compute:
      *     D(t) = w(t) · w(t) = (u-v)·(u-v)t^2 + 2w0·(u-v)t + w0·w0
      *  which has a minimum when
      *     0 = D'(t) = 2(u-v)·(u-v)t + 2w0·(u-v)
      *  => tcpa = [-w0·(u-v)]/|u-v|^2
      *
      *  if |u-v| = 0, then  two point tracks are traveling in the same direction at the same speed,
      *    and will always remain the same distance apart, so one can use tCPA = 0.
      *
      *  Dcpa[P(t), Q(t)] = |P(tcpa) - Q(tcpa)|
      *
      *  when tcpa < 0 then the CPA has already occurred in the past, and the two tracks are getting further apart as they move on in time.
      */
     class Track {
         Point P0;
         Vector v;
         public Track(Point p0, Vector v) {
             P0 = p0;
             this.v = v;
         }
     }

    /**
     *  compute the time of CPA for two tracks
     * @return the time at which the two tracks are cloest
     */
     public double cpaTime(Track Tr1, Track Tr2) {
        Vector dv = new Vector(Tr1.v.x - Tr2.v.x, Tr1.v.y - Tr2.v.y, Tr1.v.z - Tr2.v.z);

        double mDV = dotProduct(dv, dv);
        if(mDV < SMALL_NUM) {
            return 0.0; // the tracks are parallel, any time is ok , use time 0
        }

        Vector w0 = new Vector(Tr1.P0.x - Tr2.P0.x, Tr1.P0.y - Tr2.P0.y, Tr1.P0.z - Tr2.P0.z);
        double cpatime = - dotProduct(w0, dv) / mDV;
        return cpatime;
     }
    /**
     *  compute the distance at CPA for two tracks
     * @return the distance for which the two tracks are closest
     */
     public double cpaDistance(Track Tr1, Track Tr2) {
        double cpatime = cpaTime(Tr1, Tr2);
        Point P1 = new Point(Tr1.P0.x + cpatime * Tr1.v.x,Tr1.P0.y + cpatime * Tr1.v.y,Tr1.P0.z + cpatime * Tr1.v.z);
        Point Q1 = new Point(Tr2.P0.x + cpatime * Tr2.v.x,Tr2.P0.y + cpatime * Tr2.v.y,Tr2.P0.z + cpatime * Tr2.v.z);
        return d(P1, Q1);
     }
























}
