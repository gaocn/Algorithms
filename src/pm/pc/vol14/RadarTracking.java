package pm.pc.vol14;

/**
 * Created by 高文文 on 2017/6/27.
 */
public class RadarTracking {

   static class Track {
        double dist;
        double degree;

        public Track(double dist, double degree) {
            this.dist = dist;
            this.degree = degree;
        }
    }


    public static void main(String[] args) {
        Track track1 = new Track(90, 100);
        Track track2 = new Track(90, 110);


    }

    /**
     * 1. 若两次轨迹的角度相同，运动轨迹是过极点的直线
     *      a) 若物体是远离极点(track1.dist <= track2.dist),则物体的下一个位置为(track1.degree, track2.dist+(track2.dist-track1.dist))
     *      b) 若物体是靠近极点，则物体可能会越过极点
     *          b-1)物体没有越过极点 track2.dist - (track1.dist-track2.dist) > 0, 下一个位置为
     *                (track1.degree, track2.dist - (track1.dist-track2.dist))
     *          b-2)物体越过极点，物体下一个位置为
     *              (track1.degree > 180 ? track1.degree - 180 : track1.degree + 180, (track1.dist-track2.dist) - track2.dist)
     * 2. 若两次轨迹的极角相差180度， 运动轨迹是过极点的直线
     *
     *
     * 3. 极角不同且相差不是180度，物体运动轨迹是一条不过极点的直线
     *
     *
     *
     *
     */
    public void doRadarTracking(Track track1, Track track2) {


    }

/*
Input

The input consists of a number of lines, each with four real numbers: a1, d1, a2, d2. The first pair a1, d1 are the angle (in degrees) and distance (in arbitrary distance units) for the first observation while the second pair a2, d2 are the angle and distance for the second observation.
Note that the antenna rotates clockwise; that is, if it points north at time t = 0.0, it points east at t = 0.5, south at t = 1.0, west at t = 1.5, north at t = 2, and so on. If the object is directly on top of the radar antenna, it cannot be observed. Angles are specified as on a compass, where north is 0 or 360, east is 90, south is 180, and west is 270.

Output

The output consists of one line per input case containing all possible solutions. Each solution consists of two real numbers (with two digits after the decimal place) indicating the angle a3 and distance d3 for the next observation.
Sample Input

90.0 100.0 90.0 110.0
90.0 100.0 270.0 10.0
90.0 100.0 180.0 50.0
Sample Output

90.00 120.00
270.00 230.00
199.93 64.96 223.39 130.49
*/

}
