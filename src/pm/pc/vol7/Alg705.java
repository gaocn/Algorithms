package pm.pc.vol7;

import java.util.HashMap;

/**
 * Created by 高文文 on 2017/2/16.
 *
 * Problem ID: 110705	Summation of Four Primes
 *
 * Waring's prime number conjecture：任何大于3的奇数，要么是素数要么是三个素数之和；
 * 哥德巴赫猜想（Goldbach's conjecture）: 每个大于等于4的偶数，都可以表示为两个素数之和；
 *
 * 当 N <= 7， 则不可能拆分成四个素数之和；
 * 当 N > 8， 则可以将数表示为：
 *              1. 若N为奇数，3 + 2 + “summation of two primes” ==> N - 3 - 2 为偶数
 *              2. 若N为偶数，2 + 2 + “summation of two primes” ==> N - 2 - 2 为偶数
 */
public class Alg705 {
    public static void main(String[] args) {
        Alg705 alg = new Alg705();

        alg.sumOfFourPrimes(5);
        alg.sumOfFourPrimes(24);
    }

    private long a;
    private long b;
    public void sumOfFourPrimes(long n) {
        if(n <= 7) {
            System.out.println("Impossible");
            return;
        }
        if( (n & 0x1) ==0) {
            if(splitEven(n - 2 - 2)) {
                System.out.println("2 2 "+ a + " " + b);
            }
        } else {
            if(splitEven(n - 2 - 3)) {
                System.out.println("2 3 "+ a + " " + b);
            }
        }
    }


    public boolean splitEven(long n) {
        if(n == 4L) {
            a = 2L;
            b = 2L;
            return true;
        }

        for(long i = 3L; i <= n / 2L; i += 2L) {
            if(isPrime(i) && isPrime(n - i)) {
                a = i;
                b = n - i;
                return true;
            }
        }
        return false;
    }

    public boolean isPrime(long n) {
        if(n == 2) return true;
        if(n % 2 == 0) return false;

        for(long i = 3; i <= Math.sqrt(n); i += 2) {
            if( n % i == 0) return false;
        }
        return true;
    }
}
