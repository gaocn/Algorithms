package pm.pc.vol6;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110602	How Many Pieces of Land?
 *
 * 有一个椭圆区域，在边界上选n(0<=2^31)个点并两两相连得到n(n-1)/2条线段。问他们最多能把椭圆区域分成多少个部分。
 * Euler’s formula: Let V be the number of vertices, E the number of edges and F the number of
 *                  faces of a planar graph. Then V − E + F = 2
 *
 * C(n, 4)+C(n, 2)+1 = n*(n-1)/2 + n*(n-1)*(n-2)*(n-3)/24 + 1
 * http://www.arbelos.co.uk/Papers/Chords-regions.pdf
 *
 * BigDecimal
 */
public class Alg602 {

    public static void main(String[] args) {
        Alg602 alg = new Alg602();
        alg.calFacesOfPoints(1);
        alg.calFacesOfPoints(2);
        alg.calFacesOfPoints(3);
        alg.calFacesOfPoints(4);
        alg.calFacesOfPoints(5);
        alg.calFacesOfPoints(6);
    }

    public int calFacesOfPoints(int numOfPoints) {
        BigDecimal n = BigDecimal.valueOf(numOfPoints);
        BigDecimal result , tmp;

        tmp = n.multiply(n.subtract(BigDecimal.valueOf(1)));
        result = tmp.divide(BigDecimal.valueOf(2));
//        System.out.println("result: " + result);

        tmp = n.multiply(n.subtract(BigDecimal.valueOf(1)));
        tmp = tmp.multiply(n.subtract(BigDecimal.valueOf(2)));
        tmp = tmp.multiply(n.subtract(BigDecimal.valueOf(3)));
        result = result.add(tmp.divide(BigDecimal.valueOf(24)));
//        System.out.println("result: " + result);

        result = result.add(BigDecimal.valueOf(1));

        System.out.println(result);
        return result.intValue();
    }
}
