package gww.geeks.geometry;

import java.util.Comparator;

/**
 * Created by 高文文 on 2017/5/10.
 */
public class Point implements Comparator<Point>{
    double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public int compare(Point o1, Point o2) {
        if(o1.x < o2.x) {return -1; }
        else if (o1.x > o2.x) {return 1; }
        else {
            if(o1.y < o2.y) {return -1;}
            else if(o1.y > o2.y) {return  1;}
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
