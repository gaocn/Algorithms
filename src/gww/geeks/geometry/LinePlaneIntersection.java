package gww.geeks.geometry;

/**
 * Created by 高文文 on 2017/5/22.
 */
public class LinePlaneIntersection {

    private final static float SMALL_NUM = 0.00000001f;
    public class Point {
        float x, y, z;
        public Point(float x, float y, float z) {
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
    public class Vector {
        float x, y, z;
        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    public class Line {
        Point P0, P1;
        public Line(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
    public class Segment {
        Point P0, P1; // start from P0, end at P1
        public Segment(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
    public class Ray {
        //start from P0,  extended beyond P1
        Point P0, P1;
        public Ray(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
    public class Plane {
        Point V0;
        Vector n;
        public Plane(Point v0, Vector n) {
            V0 = v0;
            this.n = n;
        }
    }

    private float dot(Vector P0, Point P1) {
        return P0.x * P1.x + P0.y * P1.y + P0.z * P1.z;
    }
    private float dot(Vector P0, Vector P1) {
        return P0.x * P1.x + P0.y * P1.y + P0.z * P1.z;
    }
    private float perpDot(Vector u, Vector v) { // 2D
        return u.x*v.y - u.y*v.x;
    }
    private Vector crossProduct(Vector v1, Vector v2) {
        float x = v1.y * v2.z - v1.z * v2.y;
        float y = -(v1.x * v2.z - v1.z * v2.x);
        float z = v1.x * v2.y - v1.y * v2.x;
        return new Vector(x, y ,z);
    }
    /**
     * Line and Segment Intersection
     *  计算直线、线段之间的焦点时最好采用参数方程表示直线，任何维度过P0, P1的直线可以表示为：
     *              P(s) = P0 + s(P1 - P0)
     *  共线(collinear)
     *    任意两点的斜率相等， u1/v1 = u2/v2 <=> u1v2 - u2v1 = 0
     *    PERP算子(perp operator):  假设u(u1, u2) 则u的垂直算子为u^_|_ = (-u2, u1), 则有u^_|_·u = 0
     *    垂直点乘(perp dot product): 假设u(u1, u2) v(v1, v2), u^_|_·v = (-u2, u1) · (v1, v2) = u1v2 - u2v1
     *
     *  在二维平面上，两条直线不平行一定相交，而对于高维空间的直线可能会不相交，但若高维空间的直线相交那么其在二维空间中的
     *  投影一定是相交的。因此可以将求直线的交点的计算限制在二维空间中。例如：若I是直线P(si)、Q(si)在二维平面上的交点，然
     *  后测试在所有维度中I是否满足P(si)=Q(si)
     *
     */
    /**
     * determine if a poing  is inside a segment
     * Input: a point P, and a collinear segment S
     * @return true = P is inside S
     *         false = P is not inside S
     */
    private boolean inSegment(Point P, Segment S) {
        //若线段不是垂直，测试x轴
        if(S.P0.x != S.P1.x) {
             if(S.P0.x <= P.x && S.P1.x >= P.x)
                 return true;
             if(S.P0.x >= P.x && S.P1.x >= P.x)
                 return true;
        } else {
        //直线是垂直的，测试y轴
            if(S.P0.y <= P.y && P.y <= S.P1.y)
                return true;
            if(S.P0.y >= P.y && P.y >= S.P1.y)
                return true;
        }
        return false;
    }

    /**
     *  find the 2D intersection of 2 finite segments
     * @param I0 intersect point when is exists
     * @param I1 end point of intersect segment[I0, I1] when it exists
     * @return 0 disjoint (no intersect)
     *         1 intersect in unique point I0
     *         2 overlap in segment from I0 to I1
     */
    public int intersect2DSegment(Segment S1, Segment S2, Point I0, Point I1) {
        Vector u = new Vector(S1.P1.x - S1.P0.x, S1.P1.y - S1.P0.y, S1.P1.z - S1.P0.z);
        Vector v = new Vector(S2.P1.x - S2.P0.x, S2.P1.y - S2.P0.y, S2.P1.z - S2.P0.z);
        Vector w = new Vector(S1.P0.x - S2.P0.x, S1.P0.y - S2.P0.y, S1.P0.z - S2.P0.z);
        float D = perpDot(u, v);

        //1. segments are parallel(include either being a point)
        if(Math.abs(D) < SMALL_NUM) {
            if(perpDot(u, w) != 0 || perpDot(v, w) != 0) {
                //not same segment, so parallel segments have no intersect
                return 0;
            }
            // they are collinear or degenerate points
            float du = dot(u, u);
            float dv = dot(v, v);
            // both segment degenerate to point
            if(du == 0 && dv == 0) {
                if(!S1.P0.equals(S2.P0)) {
                    // degenerate to different points
                    return 0;
                }
                // degenerate to same point
                I0.x = S1.P0.x;
                I0.y = S1.P0.y;
                I0.z = S1.P0.z;
                return 1;
            }
            // S1 degenerate to a point
            if(du == 0) {
                // S1 not in S2
                if(!inSegment(S1.P0, S2)) return 0;
                // S1 in S2
                I0.x = S1.P0.x;
                I0.y = S1.P0.y;
                I0.z = S1.P0.z;
                return 1;
            }
            // S2 degenerate to a point
            if(dv == 0) {
                if(!inSegment(S2.P0, S1)) return 0;
                I0.x = S2.P0.x;
                I0.y = S2.P0.y;
                I0.z = S2.P0.z;
                return 1;
            }
            // they are overlap segments, 将线段Q0Q1作为参考，设P0P1与Q0Q1重合部分
            float t0, t1;
            Vector w2 = new Vector(S1.P1.x - S2.P0.x, S1.P1.y - S2.P0.y,S1.P1.z - S2.P0.z);
            if(v.x != 0) {
                t0 = w.x  / v.x;
                t1 = w2.x / v.x;
            } else {
                t0 = w.y  / v.y;
                t1 = w2.y / v.y;
            }

            //must have t0 < t1
            if(t0 > t1) {
                float t = t0; t0 = t1; t1 = t;
            }
            if(t0 > 1 || t1 < 0) {
                /**
                 * case: t0 > 1    Q0----Q1  P0----P1
                 * case: t1 < 0    P0----P1  Q0----Q1
                 */
                return 0; // no overlap
            }

            /** case: t1 > t0 t0:[0, 1]*/
            t0 = t0 < 0 ? 0 : t0;
            t1 = t1 > 1 ? 1 : t1;
            if(t0 == t1) {
                I0.x = S2.P0.x + t0 * v.x;
                I0.y = S2.P0.y + t0 * v.y;
                I0.z = S2.P0.z + t0 * v.z;
                return 1;
            }

            // overlap in a valid subsegment
            I0.x = S2.P0.x + t0 * v.x;
            I0.y = S2.P0.y + t0 * v.y;
            I0.z = S2.P0.z + t0 * v.z;
            I1.x = S2.P0.x + t1 * v.x;
            I1.y = S2.P0.y + t1 * v.y;
            I1.z = S2.P0.z + t1 * v.z;
            return 2;
        }

        //2. segments are skew and may intersect in a point
        float si = perpDot(v, w) / D;
        if(si < 0 || si > 1)  // no intersect
            return 0;

        float ti = perpDot(u, w) / D;
        if(ti < 0 || ti > 1) // no intersect
            return 0;
        // one intersect
        I0.x = S1.P0.x + si * u.x;
        I0.y = S1.P0.y + si * u.y;
        I0.z = S1.P0.z + si * u.z;
        return 1;
    }


    /**
     * Line-Plane Intersection
     *
     * line parametric equation: P(s) = P0 + s(P1-P0)  u=P0P1
     * plane representation:     V0 and normal vector n=(a,b,c)
     *
     * 1. if line is parallel to plane
     *       u · n = 0  <=> line is parallel with plane or in plane
     *    线平面是不相交还是包含在平面中(Disjointness or coincidence)
     *      (P0V0) · n == 0  <=> coincidence  else  Disjointness
     *
     * 2. if not parallel, then there must be an intersect
     *    assume P(s) is the intersect, then P(s) - V0 must in plane
     *    [ P(s) - V0 ]· n = 0
     *    (P0 - V0 + su)·n = 0
     *    s = n·(V0 - P0) / n·u
     *
     *    case 1: line P0P1 is segment, s=[0 ,1] if there is a intersect
     *    case 2: line P0P1 is ray,     s=[0, OO] if there is a intersect
     */

    /**
     * find 3D intersect of a segment and a plane
     * @param I0 output the intersect point
     * @return 0 disjoint
     *         1 intersection in the unique point I0
     *         2 the segment lies in the plane
     */
    public int intersect3DSegmentPlane(Segment S, Plane Pn, Point  I0) {
        Vector u = new Vector(S.P1.x - S.P0.x,S.P1.y - S.P0.y,S.P1.z - S.P0.z);
        Vector w = new Vector(S.P0.x - Pn.V0.x, S.P0.y - Pn.V0.y, S.P0.z - Pn.V0.z);

        float D = dot(Pn.n, u);
        float N = -dot(Pn.n, w);

        if(D < SMALL_NUM) {
            if (N == 0) {
                //segment lies in plane
                return 2;
            } else {
                return 0;
            }
        }

        // intersect
        float si = N / D;
        if(si < 0 || si > 1) {
            return 0;
        }
        I0.x = S.P0.x + si * u.x;
        I0.x = S.P0.y + si * u.y;
        I0.x = S.P0.z + si * u.z;
        return 1;
    }


    /***
     * find the 3D intersection of two planes
     * @param L the intersection line (when it exists)
     * @return 0 disjoint
     *         1 the two planes coincide
     *         2 intersection in the unique line L
     */
    public int intersect3D2Planes(Plane Pn1, Plane Pn2, Line L) {
        Vector u = crossProduct(Pn1.n, Pn2.n);
        float absX = u.x > 0 ? u.x : -u.x;
        float abcY = u.y > 0 ? u.y : - u.y;
        float abcZ = u.z > 0 ? u.z : - u.z;

        //1. if two planes are parallel
        if((absX + abcY + abcZ) < SMALL_NUM) {
            // if disjoint or coincide
            Vector v = new Vector(Pn2.V0.x - Pn1.V0.x, Pn2.V0.y - Pn1.V0.y,Pn2.V0.z - Pn1.V0.z);
            if(dot(Pn1.n, v) < SMALL_NUM) { //Pn2.n lies in Pn1
                return 1;  // coincide
            } else {
                return 0;  // disjoint
            }
        }

        // Pn1 Pn2 intersect in a line

        // first, find max abs coordinate of cross product
        int maxc;
        if(absX > abcY) {
            if(absX > abcZ)
                maxc = 1;
            else
                maxc = 3;
        } else {
            if(abcY > abcZ)
                maxc = 2;
            else
                maxc = 3;
        }
        // second, get a point on the intersect line by zero the max coord, and solve for the other two
        Point PI = new Point(0, 0, 0);
        float d1 = - dot(Pn1.n, Pn1.V0);
        float d2 = - dot(Pn2.n, Pn2.V0);

        switch (maxc) {
            case 1:
                PI.x = 0;
                PI.y = (d2 * Pn1.n.z - d1 * Pn2.n.z) / u.x;
                PI.z = (d1 * Pn2.n.y - d2 * Pn1.n.y) / u.x;
                break;
            case 2:
                PI.x = (d1 * Pn2.n.z - d2 * Pn1.n.z) / u.y;
                PI.y = 0;
                PI.z = (d2 * Pn1.n.x - d1 * Pn2.n.x) / u.y;
                break;
            case 3:
                PI.x = (d2 * Pn1.n.y - d1 * Pn2.n.y) / u.z;
                PI.y = (d1 * Pn2.n.x - d2 * Pn1.n.x) / u.z;
                PI.z = 0;
                break;
        }
        L.P0 = PI;
        L.P1 = new Point(PI.x + u.x, PI.y + u.y, PI.z + u.z);
        return 2;
    }


}
