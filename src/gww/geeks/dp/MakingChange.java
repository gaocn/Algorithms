package gww.geeks.dp;

/**
 * Created by 高文文 on 2017/7/10.
 */
public class MakingChange {

    /*
        You are given n type of coin denominations of values v(1)<v(2)<...<v(n)[all  integers].
        Assume v(1)=1, so you can always make change for any mount of money C. Given an algorithm which makes change for
        an amount of money C with as few coins as possible.

        设MC(C, V)为用V种类的零钱将C金额换成零钱后的硬币数最小，则对于任意金额i，将其换成零钱后最少的硬币数有两种情况：
         对于第V种类的零钱，
            1. 使用V金额的硬币换零钱；
            2. 不适用V金额的硬币换零钱
            MC(i, V) = MIN{MC{i, V-1}, MC{i - V, V} + 1 if i >= V}
            MC(0,0) = 0
            MC(0,V) = 0
            MC(i, 0) = MAX
     */


    public static void main(String[] args) {
        MakingChange alg = new MakingChange();
        int[] V = {1, 2, 4, 8, 15};
        int   C = 29;
//        int   C = 5;
        System.out.println("minCoins: " + alg.makingChange(C, V, V.length - 1));


    }


    public int makingChange(int C, int[] V, int vIdx) {
        if(C == 0) return 0;
        if(V.length == 0 || vIdx < 0) return Integer.MAX_VALUE / 2;

        int minCoins = Integer.MAX_VALUE;
        int tmp;
        tmp = makingChange(C, V, vIdx - 1);
        if(C >= V[vIdx]) {
            tmp = Math.min(tmp, makingChange(C - V[vIdx], V, vIdx) + 1);
        }

        minCoins = Math.min(minCoins, tmp);
        return minCoins;
    }

}
