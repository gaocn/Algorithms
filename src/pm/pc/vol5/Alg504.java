package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110504	Ones
 *
 * 如果我们把问题倒过来，从1、11、111开始，挨个看它们是不是n的倍数，问题就简单多了。
 * 令a(0) = 1 ,  a(i) = a(i-1)*10 +1
 * 那么a(1)=11, a(2)=111, a(3)=1111 ,….
 * 另 b(i) =  a(i) % n = (a(i-1)*10 +1) % n = (a(i-1)%n *10 + 1) % n
 * 则b(i) = (b(i-1) * 10 +1) % n
 * 所以有0<= b(i) < n 。这样就没有算术溢出的问题了。
 *
 */
public class Alg504 {


    public static void main(String[] args) {

        Alg504 alg = new Alg504();
        System.out.println(alg.doOnes(3));
        System.out.println(alg.doOnes(7));
        System.out.println(alg.doOnes(9901));
        System.out.println(alg.doOnes(1031));
    }

    public int doOnes(int n) {
        int count = 1;
        int from = 1;
        while(from % n != 0) {
            from = (from * 10 + 1) % n;
            count++;
        }
        return count;
    }

}
