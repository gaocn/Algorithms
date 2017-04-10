package pm.pc.vol7;

import java.util.Arrays;

/**
 * Created by 高文文 on 2017/2/16.
 *
 * Problem ID: 110702 Carmichael Numbers
 *
 */
public class Alg702 {
    public static void main(String[] args) {
        Alg702 alg = new Alg702();

        System.out.println(alg.isCarmichaelNumber(1729));
        System.out.println(alg.isCarmichaelNumber(17));
        System.out.println(alg.isCarmichaelNumber(561));
        System.out.println(alg.isCarmichaelNumber(1109));
        System.out.println(alg.isCarmichaelNumber(431));
    }

    public boolean isCarmichaelNumber(int n) {
        sieveOfEratosthenes();
        if(isPrime[n]) return false;

        for(int i = 2; i < n; i++)
            if(powMod(i, n, n) != i)
                return false;
        return true;
    }

    public static final int MAX = 65010;
    boolean[] isPrime = new boolean[MAX];
    private void sieveOfEratosthenes() {
        Arrays.fill(isPrime, true);
        for(int i = 2; i * i < MAX; i++) {
            if(isPrime[i]) {
                for(int j = i * 2; j < MAX; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }

    public long powMod(int x, int y, int m) {
        if(y == 1)return x % m;
        long res = powMod(x, y >> 1, m);
        res = (res * res) % m;
        return (y & 0x1) != 0 ? (res * x) % m : res;
    }

}


/*

 此题关键是应用求模公式 x^y mod n = (x mod n)^y mod n。这里用到了预先计算给定范围内所有
Carmichael 数的方法，因为在这区间里的数并不多！如果使用求模的方式，注意使用
(x mod n) ^ y mod n
    = ((x mod n) ^ (y / 2) mod n) * ((x mod n) ^ (y / 2) mod n) * ((x mod n) ^ (y % 2) mod n) mod n
 */


