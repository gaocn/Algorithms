package pm.pc.vol8;


/**
 * Created by 高文文 on 2017/2/27.
 * Problem ID: 110803	Queue
 * 1. 枚举所有permutation，超时
 * 2. 递推：
 */
public class Alg803 {


    public static void main(String[] args) {
        Alg803 alg = new Alg803();
    }




    //1 回溯构建所有排列，采用Algorithm Manual中的结构




    /**
     * 假设现在队列由i-1个人变成了i个，由于谁后进到队列是无所谓的，不妨假设最矮的人是最后一个进入队列的，
     * 那么其所占的位置会有三种情况，第一种情况是站在队首，增加1个在前面能看到的人数，第二种情况是站在队尾，
     * 增加1个在后面能看到的人数，第三种情况是站在队伍中间，一共有i-2个位置可以站，但不会增加可见的人数。
     *  perm[n][p][r] = perm[n - 1][p][r] * (n - 2) + perm[n - 1][p - 1][r] + perm[n - 1][p][r - 1]
     *  perm[1][1][1] = 1;
     *
     * （1）perm[n - 1][p][r] * (n - 2)：新增最矮的人可以放在排列好的 n - 1 个人形成的左边刚好p 人，
     *      右边刚好 r 人视野不被挡住的队列的中间，因为有 n - 2 个空，所以共有 perm[n - 1][p][r] * (n - 2) 种安排方法。
     * （2）perm[n - 1][p - 1][r]：由于该人比所有人矮，故可以直接放在队首。
     * （3）perm[n - 1][p][r - 1]：由于该人比所有人矮，故可以直接放在队尾。
     *
     *
     */




}
