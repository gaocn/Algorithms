package gww.geeks.geometry;

/**
 * Created by 高文文 on 2017/5/18.
 */
public class PointToPlaneDistance {

    /**
     * 三维或多维平面的表示方法
     *  http://geomalgorithms.com/a04-_planes.html
     *
     */
    class Point {
        double x, y, z;
        public Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    class Plane {
        Point V0;
        Point n;
        public Plane(Point v0, Point n) {
            V0 = v0;
            this.n = n;
        }
    }
    private double dotProduct(Point u, Point v) {
        return u.x * v.x + u.y * v.y + u.z * v.z;
    }
    private double norm(Point v) {
        return Math.sqrt(dotProduct(v, v));
    }
    private double d(Point P, Point Q) {
        return norm(new Point(P.x - Q.x, P.y - Q.y, P.z - Q.z));
    }

    /**
     * 采用这种方式的原因是因为，需要求出P点到平面PL的垂直节点Pb
     * @param P
     * @param PL plane with point V0 and normal n
     * @param Pb output base point on PL of perpendicular from P
     * @return  distance from point P to Plane PL
     */
    public double pointToPlaneDist(Point P, Plane PL, Point Pb) {
        double sn = - dotProduct(P, PL.n);
        double sd = dotProduct(PL.n, PL.n);
        double sb = sn / sd;

        Pb.x = P.x + sb * PL.n.x;
        Pb.y = P.y + sb * PL.n.y;
        Pb.z = P.z + sb * PL.n.z;
        return d(P, Pb);
    }





}
