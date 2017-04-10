package pm.pc.vol4;

/**
 * Created by 高文文 on 2017/1/6.
 *
 * Problem ID: 110405	Shoemaker's Problem
 * 贪心解法：首先按照fine/time降序排列，对于值相同的再按序号升序排列；---（一天减少的最大损失，稳定排序）
 * 证明：
 *      假设x和y为排好序的订单，由于x、y之后订单的顺序是固定的，所以无论排成xy还是yx，对后面的罚金没有影响；
 *      罚金的差别在于排成xy还是yx：
 *          1. 若是xy，则罚金为 Tx * Sy
 *          2. 若是yx，则罚金为 Ty * Sx
 *      因为假设x在y之前，则有：Tx * Sy < Ty * Sx  => Sy/Ty < Sx/Tx
 * （编程中：用乘法比较，避免相除以后浮点数产生误差 ）
 */
public class Alg405 {

    static class Order {
        int id;
        int time;
        int fine;
    }

    public static void main(String[] args) {
        Alg405 alg = new Alg405();


    }
}
