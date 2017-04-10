package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110508	Pairsumonious Numbers
 *
 *
 */
public class Alg508 {


    public static void main(String[] args) {

        Alg508 alg = new Alg508();
    }


}

/*
 http://blog.csdn.net/metaphysis/article/details/6453207

 假设一个数组 A，有 N 项，按从小到大的顺序排列，数组 P，有 n * （n - 1） / 2项，是数组 A 中
 元素两两之和，也按从小到大的顺序排列，已知数组 P，要求数组 A，这就是本问题的另外一种表示。显而
 易见，P[0] = A[0] + A[1]，P[1] = A[0] + A[2]，但是对于 P[2] 来说却不一定存在 P[2] =
 A[0] + A[3]，因为有可能 P[2] = A[1] + A[2]。假设知道了 A[0]，则可以知道 A[1] 和 A[2]，
 从数组 P 中删除 A[0] + A[1]，A[0] + A[2]，A[1] + A[2]，则可以保证剩下的和中最小的是
 A[0] + A[3]，则可以知道 A[3]，再次从数组 P 中删除 A[3] 与 A[0]，A[1]，A[2] 的和，则数组
 P 中最小的和肯定是 A[0] + A[4]，以此计算可得到所有 A 的元素.

确定A[0]的方法有两种：
    1. 由于P中最小的元素是 P[0] = A[0] + A[1]，若 P[0] 为自然数，则有 0 <= A[0] <= (P[0] / 2)，
       只需在此范围内枚举 A[0] 的值，最终确定数组 A 其他元素的值，若所有值都能匹配数组 P 中的和，则
       可将数组 A 内容输出作为一组答案。若和 P[0] 不在自然数的范围，小于 0，则可将所有和加上一个常数
       来将所有和调整到自然数范围，即：P[X] + （-2） * P[0] 可将数组P中的任意元素 P[X] 调整到自然
       数范围，只需在结果数组 A 中将每个元素A减去调整值的一半即可，即（-1） * P[0]。

    2. 由于知道了 P[0] = A[0] + A[1]，P[1] = A[0] + A[2]，只要知道 A[1] + A[2] 的值，则可由
        A[0] = （P[0] + P[1] - A[1] + A[2]） / 2来确定A[0]的值，由于和 A[1] + A[2] 肯定在数
        组P 中，只要逐个尝试即可，只需要搜索和数组 P[2] 至 P[N + 1]。
*/