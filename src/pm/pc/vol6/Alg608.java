package pm.pc.vol6;

/**
 * Created by 高文文 on 2017/2/6.
 * Problem ID: 110608	Steps
 *
 * BigInteger大数+递推
 *
 */
public class Alg608 {

    public static void main(String[] args) {
        Alg608 alg = new Alg608();
        System.out.println(alg.doSteps(57, 59));
        System.out.println(alg.doSteps(57, 60));
        System.out.println(alg.doSteps(57, 64));
    }

    public int doSteps(int x, int y) {
        int distance = y - x;

        int n = (int) Math.sqrt(distance);
        if(distance <= 3)
            return distance;

        if(distance == n * n) {
            return 2 * n - 1;
        }
        else if(distance - n * n <= n)
            return 2 * n;
        else
            return 2 * n + 1;
    }

}

/*
 所走步数对应的序列如果左右对称，那么所走的步数应该是最少的。因为最先一步和最后一步长度均必须是
 1，又由于每一步的长度必须是非负整数，且要么比上一步步长恰好大 1，要么相等，要么小 1，则左右对
 称的序列能在最少步数得到最大距离。如果采取左右对称的走法，设两点距离为 distance，n = sqrt
 （distance），则由于最大步数为 n 步时能达到的距离是 1 + 2 + 3 + ... + （n - 1） + n +
 （n - 1） + ... + 3 + 2 + 1 = n^2。比较两点距离 distance 与 n^2，若相等，表明只需走
 2 * （n - 1） + 1 = 2 * n - 1 步，否则若剩余距离 distance - n^2 在 1 - （n + 1），
 只需插入一步即可，若 distance - n^2 大于 （n + 1），则需多插入两步即可。

  distance - n^2
  (n+1)^2 - n^2 = n^2 + 2n + 1 - n^2 = 2n + 1

  distance - n^2 < 2n + 1 <= 2n

 */
