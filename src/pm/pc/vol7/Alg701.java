package pm.pc.vol7;

/**
 * Created by 高文文 on 2017/2/16.
 *
 * Problem ID: 110701	Light, more light
 *
 */
public class Alg701 {
    public static void main(String[] args) {
        Alg701 alg = new Alg701();

        System.out.println(alg.doLightOnOrOff(3));
        System.out.println(alg.doLightOnOrOff(6241));
        System.out.println(alg.doLightOnOrOff(8191));
    }

    public boolean doLightOnOrOff(int n) {
        int sqrtN = (int) Math.sqrt(n);
        return sqrtN * sqrtN == n;
    }
}


/*
  灯泡的初始状态是：关闭no

  y   n   n   y   n   n   n   y
  1   2   3   4   5   6   7   8   9
  1   1   1   1   1   1   1   1
      2   3   2   5   2   7   2
              4       3       4
                      6       8


 对于大于 1 的非平方数来说，它的因子总是成对出现的，这个是解题关键。对于 n >= 1，如果 n 为平
 方数，它的因子总数是奇数，否则是偶数，对应题目则是奇数次开关灯后灯是亮的，偶数次是灭的，判断数
 n 是否是平方数即可。

 */


