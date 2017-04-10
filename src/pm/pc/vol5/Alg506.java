package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110506	Polynomial coefficients
 *
 *
 */
public class Alg506 {


    public static void main(String[] args) {

        Alg506 alg = new Alg506();
        int[] cofficients = {1, 2};
        alg.doPolyCodfficeient(3, cofficients);
    }



    public long doPolyCodfficeient(int n, int[] cofficients) {
        long result = 1L;
        for(int i = 0; i < cofficients.length; i++) {
            result *= fact(cofficients[i]);
        }
        result = fact(n) / result;
        System.out.println("doPolyCodfficeient: " + result);
        return result;
    }
    public long fact(int n) {
        return (n > 1) ? n * fact(n - 1) : 1L;
    }

}


/*
(a + b)^n的系数为C(i, n)
根據二項式定理可以求出解。

公式是C(n,n1)*C(n-n1,n2)*C(n-n1-n2,n3)*...*C(n-n1-n2-n3-...-n(k-1), nk)，化简可得n!/(n1!*n2!*n3!*...*nk!)。

Ex. (x1+x2+x3+x4)^5 = (x1+x2+x3+x4)*(x1+x2+x3+x4)*(x1+x2+x3+x4)*(x1+x2+x3+x4)*(x1+x2+x3+x4)

相乘時，最後出現的每一項就好像是從這五個一樣的式子中的四個變數，各取一個出來相乘。

要知道x1*x2*x3^3這項的可能性，可能是第一個(x1+x2+x3+x4)中取x1，第二個(x1+x2+x3+x4)中取x2，第三、四、五個(x1+x2+x3+x4)中取x3，把取出來的相乘起來。也有可能是第一個(x1+x2+x3+x4)中取x2，第三個(x1+x2+x3+x4)中取x1，第二、四、五個(x1+x2+x3+x4)中取x3，把取出來的相乘起來。

而所有的可能性就是：C(5,1)*C(4,1)*C(3,3) (意思如同：[取x1，從五個(x1+x2+x3+x4)裡面取一個]*[取x2，從剩下四個(x1+x2+x3+x4)裡面取一個]*[取x3，從剩下三個(x1+x2+x3+x4)裡面取三個]) 而所有乘出來的同樣的項，最後會加起來，所以所有的可能性就是係數，即可得解


 */

