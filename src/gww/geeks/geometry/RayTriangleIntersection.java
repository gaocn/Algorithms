package gww.geeks.geometry;


/**
 * Created by 高文文 on 2017/5/27.
 */
public class RayTriangleIntersection {
    private static final float SMALL_NUM = 0.000001f;
     class Point {
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
     class Vector {
        float x, y, z;
        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

         @Override
         public boolean equals(Object obj) {
            if(obj instanceof Vector) {
                Vector v2 = (Vector)obj;
                if(Math.abs(this.x - v2.x) < SMALL_NUM &&
                        Math.abs(this.y - v2.y) < SMALL_NUM &&
                        Math.abs(this.z - v2.z) < SMALL_NUM) {
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
     class Segment {
        Point P0, P1; // start from P0, end at P1
        public Segment(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
     class Ray {
        //start from P0,  extended beyond P1
        Point P0, P1;
        public Ray(Point p0, Point p1) {
            P0 = p0;
            P1 = p1;
        }
    }
     class Plane {
        Point V0;
        Vector n;
        public Plane(Point v0, Vector n) {
            V0 = v0;
            this.n = n;
        }
    }
     class Triangle {
        Point V0, V1, V2;
        public Triangle(Point v0, Point v1, Point v2) {
            V0 = v0;
            V1 = v1;
            V2 = v2;
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
     * @input
     *      Ray R from P0 to P1
     *      Triangle T with vertices V0, V1 V2, T lie in plane P through V0 with normal vector n=crossProd(V1-V0,V2-V0)
     * @Output:
     *      get intersection of R with T
     * @Solution:
     *  first determine the intersection of R and P, if it does not intersect, the it also does not intersect T;
     *  Otherwise, they intersect in the point Pi=P(ri), we need to determine if this point is inside T for there to be
     *  a valid intersection.
     *@Algorithm test for inclusion of point inside a 3D planar triangle
     *  1. project the point and triangle onto a 2D coordinate plane where inclusion is tested.
     *  To implement these algorithms, one must choose a projection coordinate plane which avoids a degenerate
     *  projection. This is done by excluding the coordinate which has the largest component in the plane normal vector n
     *  However, there is a small overhead involved in selecting and applying the projection function.
     *  2. finds a solution using direct 3D computations [faster then first]
     *
     *
     */


    /**
     *  find the 3D intersection of a ray with a triangle
     * @output I intersection point (when exists)
     * @return -1 triangle is degenerate(to a segment or point)
     *          0 disjoint
     *          1 intersect in unique point I
     */
    public int intersect3DRayTriangle(Ray R, Triangle T, Point I) {
        Vector u = new Vector(T.V1.x - T.V0.x,T.V1.y - T.V0.y, T.V1.z - T.V0.z);
        Vector v = new Vector(T.V2.x - T.V0.x,T.V2.y - T.V0.y, T.V2.z - T.V0.z);
        Vector n = crossProduct(u, v);

        if(n.equals(new Vector(0,0,0)))
            return -1; // degenerate, ignore this case

        Vector rayDirection = new Vector(R.P1.x - R.P0.x,R.P1.y - R.P0.y,R.P1.z - R.P0.z );
        Vector w0 = new Vector(R.P0.x - T.V0.x,R.P0.y - T.V0.y,R.P0.z - T.V0.z );
        float a = dot(rayDirection, n);
        float b = -dot(n, w0);
        if(a < SMALL_NUM) { // ray is parallel to triangle
            if(b < SMALL_NUM)
                return 2; //ray lies in triangle plane
            return 0;    // ray disjoint from triangle plane
        }

        // get intersect point of ray with triangle plane
        float r = b / a;
        I.x = R.P0.x + r * rayDirection.x;
        I.x = R.P0.x + r * rayDirection.y;
        I.x = R.P0.x + r * rayDirection.x;

        // is I inside Plane
        Vector w = new Vector(I.x - T.V0.x, I.y - T.V0.y,I.z - T.V0.z);
        Vector nCrossV = crossProduct(n, v);
        Vector nCrossU = crossProduct(n, u);

        float s = dot(w, nCrossV) / dot(u, nCrossV);
        if(s < 0.0f || s> 1.0f)
            return 0;  // I is outside T
        float t = dot(w, nCrossU) / dot(v, nCrossU);
        if(t < 0.0f || t >1.0f)
            return 0; // I is outside T

        return  1;    // I is inside T
    }




}
