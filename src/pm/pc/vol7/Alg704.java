package pm.pc.vol7;

import java.util.HashMap;

/**
 * Created by 高文文 on 2017/2/16.
 *
 * Problem ID: 110704	Factovisors
 *
 */
public class Alg704 {
    public static void main(String[] args) {
        Alg704 alg = new Alg704();
        primeFactors(4*9*25);
    }

    public boolean isFactovisors(int n, int m) {

        return false;
    }


    // A function to print all prime factors
    // of a given number n
    public static HashMap<Integer, Integer> primeFactors(int n) {
        HashMap<Integer, Integer> factors = new HashMap<>();

        // Print the number of 2s that divide n
        while (n%2==0) {
            factors.put(2, (factors.get(2) == null ? 0 : factors.get(2)) + 1);
            n /= 2;
        }

        // n must be odd at this point.  So we can
        // skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i+= 2) {
            // While i divides n, print i and divide n
            while (n%i == 0) {
                factors.put(i, (factors.get(i) == null ? 0 : factors.get(i)) + 1);
                n /= i;
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2) {
            factors.put(n, (factors.get(n) == null ? 0 : factors.get(n)) + 1);
        }

        System.out.print(factors.toString());
        return factors;
    }

    public int getPowers(int n, int p) {
        int res = 0;
        for(int pow = p; pow <= n; pow *= p) {
            res += n / pow;
        }
        return res;
    }

}

/*



1. Any number, x, can be represented as product of powers of primes: x=p1^a1 * p2^a2 * p3^a3 * ... pm^am ... This is called the "prime factorization" of x.
2. b|a  <=> a=kb
To solve this problem, find the prime factorization of the number to divide the factorial and see if the powers of its prime factorization are less than or equal to the powers of those same prime factors in the factorial. The power of a prime factor, p, in n! can be found using:

int get_powers(n, p)

For example, assume we need to know whether or not 12 divides 6!.

    1. Start by representing 12 as its prime factorization: 12=2^{2} * 3^{1}.
    2. Call get_powers(6, p), where p in {2, 3}
        1). get_powers(6, 2) returns 4, so 2 appears in the factorization of n! a greater number of times than it does in the factorization of 12.
        2). Likewise, get_powers(6, 3) returns 2, which is greater than the number of times 3 appears in the prime factorization of 12.
After checking all the prime factors of 12 without any of them appearing more frequently than the same factors in the factorization of n!, we find that 12 divides 6!

//count how many times a prime p appears in n!
int get_powers(int n, int p)
{
    int res = 0;
    for (int power = p ; power <= n ; power *= p)
        res += n/power;
    return res;
}

get_powers证明：
    Take the case of finding all the prime factors in 10!

    10! = 10*9*8*7*6*5*4*3*2*1
    <----------------------------------------------------------------
    10! = (2*5) * (3*3) * (2*2*2) * 7 * (2*3) * 5 * (2*2) * 3 * 2 * 1

  Obviously, there's a 2 in every second element of 10!, and there's a 3 in every third, etc. Now, in every fourth
    element of 10! there's a second 2. And in every eighth element, there's a third 2.

 */

