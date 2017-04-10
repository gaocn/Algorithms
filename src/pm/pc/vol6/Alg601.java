package pm.pc.vol6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110601	How many Fibs?
 *
 * BigInteger : 采用char[110]表示整数
 */
public class Alg601 {

    public static void main(String[] args) {
        Alg601 alg = new Alg601();
        alg.countFibs("10", "100");
        alg.countFibs("1234567890", "9876543210");
    }

    public int countFibs(String from, String to) {
        BigInteger f1 = new BigInteger("1");
        BigInteger f2 = new BigInteger("2");
        BigInteger upBound = new BigInteger("10");
        upBound = upBound.pow(100);
        doFibs(f1, f2, upBound);

        BigInteger start = new BigInteger(from);
        BigInteger end   = new BigInteger(to);


        int startIndex = 0;
        for(; fibs.get(startIndex).compareTo(start) == -1; startIndex++);
        int count = 0;
        for (;fibs.get(startIndex).compareTo(end) != 1; startIndex++)count++;
        System.out.println(count);
        return count;
    }


    private static List<BigInteger> fibs = new ArrayList<>();
    public static void doFibs(BigInteger a, BigInteger b, BigInteger upBound) {
        fibs.add(a);
        if(a.compareTo(upBound) != 1) {
            doFibs(b, a.add(b), upBound);
        }
    }
}
