package pm.pc.vol13;

/**
 * Created by 高文文 on 2017/6/8.
 */
public class RoundTableKnights {

    public static void main(String[] args) {
        RoundTableKnights alg = new RoundTableKnights();

        System.out.println(alg.doRoundTableKnights(12.0, 12.0, 8.0));

    }

    /**
     *
     * C = (a + b + c)
     * p  = C / 2
     * St = sqrt(p*(p - a)*(p - b)*(p - c))
     * 三角形的内切圆的半径为 r = 2*St / C
     *
     */
    public double doRoundTableKnights(double a, double b, double c) {
        double p = (a + b + c)/2;
        double St = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return St / p;
    }


}
