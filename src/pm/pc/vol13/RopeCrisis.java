package pm.pc.vol13;

/**
 * Created by 高文文 on 2017/6/7.
 */
public class RopeCrisis {

    class Point {
        double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    private double norm(DogGopher.Point P0, DogGopher.Point P1) {
        return Math.sqrt((P1.x - P0.x) * (P1.x - P0.x) + (P1.y - P0.y) * (P1.y - P0.y));
    }
    public static void main(String[] args) {


    }

    /**
     * 根据P0P1的参数方程P(t)=P0 + tV与圆的方程x^2 + y^2 =r^2转化为关于t的一元二次方程
     * 1. 若P0P1与圆有两个交点(delta > 0)，则绳子的最短长度为两个切线的长度加上在圆周上的长度；
     * 2. 若P0P1与圆的交点小于2(delta <= 0 )，则两点之间的距离为绳子的最短长度；
     */
    public double doRopeCrisis(int x0, int y0, int x1, int y1, int r) {



        return -.0;
    }


}
