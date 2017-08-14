package pm.pc.vol13;

import pm.pc.ExampleCode;

import java.io.IOException;

/**
 * Created by 高文文 on 2017/6/7.
 */
public class DogGopher{

    class Point {
        double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {

    }

    private double normSquare(Point P0, Point P1) {
        return (P1.x - P0.x) * (P1.x - P0.x) + (P1.y - P0.y) * (P1.y - P0.y);
    }

    /**
     *
     * @return null this is no collision
     *         Point the collision hole coordinate
     */
    public Point doDogGopher(Point d0, Point g0, Point[] holesCoords) {
        if(holesCoords == null || holesCoords.length < 1) return null;

        Point collisionPoint = new Point(0,0);
        for (Point p : holesCoords) {
            if(normSquare(d0, p) < 4.0 * normSquare(g0, p)) {
                collisionPoint.x = p.x;
                collisionPoint.y = p.y;
                return collisionPoint;
            }
        }
        return null;
    }

}
