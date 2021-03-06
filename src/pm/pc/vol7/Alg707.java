package pm.pc.vol7;

/**
 * Created by 高文文 on 2017/2/16.
 *
 * Problem ID: 110707	Marbles
 *
 */
public class Alg707 {
    public static void main(String[] args) {
        Alg707 alg = new Alg707();

    }
}

/*
    求不定方程 n1 * x + n2 * y = n 的正整数解，且要求 x * c1 + y * c2 最小？(求解一元线性同余式)


    首先根据情况判断是否有解。若有解，先求出同余式 n1 * x ≡ n (mod n2) 的解，若 GCD(n1, n2) = 1， ==>根据扩展的 Euclid 算法获得满足 a * x + b * y = GCD(a, b) 的 x，y
    该同余式在模 n2 的意义下有唯一解，即扩展Euclid 算法输出的 x'。上述同余式的解为 :
       x = n / GCD(n1, n2) * x' + k * n2 /GCD(n1, n2)
       y = n / GCD(n1, n2) * y' - n1 / GCD(n1, n2) * k
    即不定方程 n1 * x + n2 * y = n，有解x,y
        1. 若有 c1 * n2 < c2 * n1, 则 x 和 y 在保证为正整数的情况下 x 越大，花费越少;
        2. 若 c1 * n2 > c2 * n1，则 x 应为最小的正整数则花费较少;
        3. 若相等，则任意满足条件的 x，y 花费相同。
【解释】
    为何会这样？因为假设有 M 颗弹珠，全部用第一种规格的盒子装，花费是 M / n1 * c1，若用第二种规格的
    盒子装，则花费为 M /n2 * c2，若有 M / n1 * c1 < M / n2 * c2，则用第一种规格的盒子越多，花费越
    省，化简可以得到：c1 * n2 < c2 * n1，反之 c1 * n2 > c2 * n1，则用第二种规格的盒子越多，花费越
    省，若相等，则任意满足方程的非负整数 x，y 所产生的花费都是相等的。

 注意本题中数据类型的使用，因为 1 ≤ n ≤ 2,000,000,000，所以最好使用 long long 型整数以免中间计算结果出错导致 WA。


 【一元线性同余方程求解】
    ax ≡ b(mod m)表示：(ax - b) mod m = 0，即同余
    对于方程ax≡b(mod n)，存在ax + by = gcd(a,b)，x，y是整数。而ax ≡ b(mod n)的解可以由x，y来堆砌。具体做法，见下面的MLES算法。
    定理一(欧几里得算法)：gcd(a,b) = gcd(b,a mod b)
    定理二(扩展欧几里得算法)：gcd(b,a mod b) = b * x' + (a mod b) * y'
                          = b * x' + (a - a / b * b) * y'
                          = a * y' + b * (x' - a / b *      y')
                          = a * x + b * y
                  则：x = y'   y = x' - a / b * y'

    对于线性同余方程 ax ≡ b (mod n)   (1)
    若 d = gcd(a, n) 整除 b，那么b/d为整数，由裴蜀定理，存在整数对 (r,s)，可用扩展欧几里得算法，使得 ar+sn=d，因此x=(rb)/d是方程
    的一个解。
    例如：
        方程12x ≡ 20 (mod 28)中 d = gcd(12,28) = 4 。
        注意到4 = 12 * （-2） + 28 * 1，因此x≡5*(-2)≡-10≡4 (mod 7)是一个解，对于模28来说，所有的解就是{4， 11， 18， 25}



  【中国剩余定理和一次同余方程组】
        一堆鸡蛋，3个3个数余2，5个5个数余1，7个7个数余6，问这堆鸡蛋最少有多少个？
         ==> N = 2(mod 3) = 1(mod 5) = 6(mod 7); 求解最小N

   中国剩余定理（孙子定理）<==>  一次同余方程组
        x = a1(mod m1)
        x = a2(mod m2)
             ...
        x = ak(mod mk)
    若m1,m2,......,mk这些数两两互质，且定义miMi = m1m2......mk以及CiMi = 1 (mod mi)。则此一次同余方程组的解为：
        x = a1M1C1 + a2M2C2 + ......+akMkCk (mod m)。

    例子：线性同余方程组的求解可以分解为求若干个线性同余方程。比如，对于线性同余方程组：
        2x ≡ 2 (mod 6)
        3x ≡ 2 (mod 7)
        2x ≡ 4 (mod 8)
    首先求解第一个方程，得到x ≡ 1 (mod 3)，于是令x = 3k + 1，第二个方程就变为：
        9k ≡ −1 (mod 7)
    解得k ≡ 3 (mod 7)。于是，再令k = 7/ + 3，第三个方程就可以化为：
        42/≡ −16 (mod 8)
    解出：/≡ 0 (mod 4)，即 /= 4m。代入原来的表达式就有 x = 21(4m) + 10 = 84m + 10，即解为：
        x ≡ 10 (mod 84)
    对于一般情况下是否有解，以及解得情况，则需用到数论中的中国剩余定理


 【裴蜀定理（Bézout's lemma）】
    关于最大公约数（或最大公约式）的定理：
        对任意两个整数a、b，设d是它们的最大公约数ax+by=m有整数解时当且仅当m是d的倍数。裴蜀等式有解时必然有无穷多个整数解，每组解x、y都称为裴蜀数
    例如，12和42的最大公约数是6，则方程12x+42y=6有解。事实上有(-3)×12 + 1×42 = 6及4×12 + (-1)×42 = 6。
    特别来说，方程 x+by=1 有整数解当且仅当整数a和b互素。
 */