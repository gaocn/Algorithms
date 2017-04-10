package pm.pc.vol6;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110603	Counting
 *
 */
public class Alg603 {

    public static void main(String[] args) {
        Alg603 alg = new Alg603();

//        System.out.println(alg.doGustavoCount(2));
//        System.out.println(alg.doGustavoCount(3));

        System.out.println(alg.doGustavoCountDP(99));
        System.out.println(alg.doGustavoCountDP(120));
    }


    public int doGustavoCount(final int number) {
        return gustavoCountHelper(number, 0);
    }
    private int gustavoCountHelper(final int number, int currentSum) {
        if(currentSum == number) return 1;
        if(currentSum > number) return 0;

        int count = 0;
        for(int i = 1; i <= 4; i++) {
            count += gustavoCountHelper(number, currentSum + (i == 4 ? 1 : i));
        }
        return count;
    }

    /**
     * DP Solution
     *
     * dp [n] = dp [n – 1] + dp [n – 2] + dp [n – 3] + dp [n – 1]
     */
    public String doGustavoCountDP(final int number) {
       BigInteger[] gustavoCount = new BigInteger[1000 + 1];
        Arrays.fill(gustavoCount, BigInteger.ZERO);
       gustavoCount[0] = BigInteger.valueOf(1);
       gustavoCount[1] = BigInteger.valueOf(2);
       gustavoCount[2] = BigInteger.valueOf(5);
       gustavoCount[3] = BigInteger.valueOf(13);

       for(int i = 4; i <= 1000; i++) {
           gustavoCount[i] = gustavoCount[i].add(gustavoCount[i - 1]);
           gustavoCount[i] = gustavoCount[i].add(gustavoCount[i - 2]);
           gustavoCount[i] = gustavoCount[i].add(gustavoCount[i - 3]);
           gustavoCount[i] = gustavoCount[i].add(gustavoCount[i - 1]);
       }
       return gustavoCount[number].toString();
    }

}
