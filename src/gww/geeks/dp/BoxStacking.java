package gww.geeks.dp;

public class BoxStacking {
    /*
      Box Stacking
        Your are given a set of n types of rectangular 3-D boxes, where the ith box has height h(i), width w(i), depth d(i)
        You want to create a stack of boxes which is as tall as possible, but you can only stack a box on top of another box
        if the dimensions of the 2-D base of the lower box are each strictly larger than those of the 2-D base of the higher
        box. Of course, you can rotate a box so that any side functions as its base, it is also allowable to use multiple instances
        of the same type of box.

        Input: n types of boxes , each whit h, w, d
        第一步:
            改变输入一遍能够更好的关注如何堆叠更高的栈。
            每一个箱子分别将h,w,d为高度进行旋转可以有三种情况:
                1. 以h为高度，底为 w,d;
                2. 以w为高度，底为 h,d;
                3. 以d为高度，底为 h,w;
            为了方便上面的低确定宽w 高h，假设w <= h
        第二步：
            在改变输入后，我们只需要关注对于输入的n*3种箱子，如何能够堆叠最高高度的栈。对于任意第i个箱子，若要第j个箱子堆叠在第i个箱子
            上，需要满足条件：wi > wj && hi > hj
            设置H(j)为第j个箱子在栈顶时的栈的最高高度，则有递推公式：
                H(j) = max{H(i)} + hj if i < j  O(N^2)
             则最终解为：max{H(j)} 因为不确定那个类型的箱子在最上面时得到的栈的高度最高。


     */
}
