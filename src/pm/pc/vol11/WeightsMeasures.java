package pm.pc.vol11;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 高文文 on 2017/7/14.
 */
public class WeightsMeasures {

    /*
I know, up on top you are seeing great sights,
But down at the bottom, we, too, should have rights.
We turtles can’t stand it. Our shells will all crack!
Besides, we need food. We are starving!” groaned Mack.

Mack, in an effort to avoid being cracked, has enlisted your advice as to the order in which turtles
should be dispatched to form Yertle’s throne. Each of the five thousand, six hundred and seven turtles
ordered by Yertle has a different weight and strength. Your task is to build the largest stack of turtles
possible.

Input
Standard input consists of several lines, each containing a pair of integers separated by one or more
space characters, specifying the weight and strength of a turtle. The weight of the turtle is in grams.
The strength, also in grams, is the turtle’s overall carrying capacity, including its own weight. That is,
a turtle weighing 300g with a strength of 1000g could carry 700g of turtles on its back. There are at
most 5,607 turtles.

Output
Your output is a single integer indicating the maximum number of turtles that can be stacked without
exceeding the strength of any one.

Sample Input
300 1000
1000 1200
200 600
100 101
Sample Output
3
     */


    static class Turtle implements Comparable<Turtle>{
        int weight, strength;
        int capacity;

        public Turtle(int weight, int strength) {
            this.weight = weight;
            this.strength = strength;
            this.capacity = strength - weight;

        }

        @Override
        public int compareTo(Turtle o) {
            if(this.strength < o.strength) return -1;
            else if(this.strength > o.strength) return 1;
            else {
                if(this.weight < o.weight) return -1;
                else if(this.weight < o.weight) return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Turtle{" +
                    "weight=" + weight +
                    ", strength=" + strength +
                    ", capacity=" + capacity +
                    '}';
        }
    }

    public static void main(String[] args) {
        Turtle[] turtles = new Turtle[] {
                new Turtle(300, 1000),
                new Turtle(1000, 1200),
                new Turtle(200, 600),
                new Turtle(100, 101),
        };

        WeightsMeasures alg = new WeightsMeasures();
        alg.doWeightsMeasures(turtles);
    }

    public void doWeightsMeasures(Turtle[] turtles) {
        Arrays.sort(turtles);
        System.out.println(Arrays.toString(turtles));
        int maxHeight = solve(turtles, 0, -1);
        System.out.println("maxLen: " + maxHeight);
    }

    private int solve(Turtle[] turtles, int totalWeight, int idx) {
        if(idx == turtles.length) return 0;
        int maxHeight = Integer.MIN_VALUE, tmpLen;
        for(int i = idx + 1; i < turtles.length; i++) {
//            if(totalWeight <= turtles[i].capacity)
//                tmpLen = solve(turtles, totalWeight + turtles[i].weight, i) + 1;
//            else
//                tmpLen = solve(turtles, totalWeight, i);
//            maxHeight = Math.max(maxHeight, tmpLen);
        }
        return maxHeight;
    }
/*
// 先分析一下按可承重重量增序排列，若可承重重量相同，体重轻的排上面的方法为什么不可行。可
// 承重重量除了给出这只乌龟还能承重的重量外，不能给出更多信息了，如两只乌龟，重量和力量如下：
//
// （1）10 50
// （2）100 120
//
// 如果按照上述的排序方法乌龟（1）应该排在下方，（2）排在上方，能形成的乌龟塔最高为 1，但是实际上
// 若（2）在下，（1）在上，是能形成高度为 2 的乌龟塔的。所以按这种排序方法是不能达到乌龟顺序的最
// 优子结构的，所以也就不能保证一定获得最优解。
//
// 若按重量增序，重量相同按力量增序的顺序排列乌龟，也会得到错误的答案，因为没有考虑到乌龟的可承重
// 重量，反例如下：
//
// （1）10 1000
// （2）20 1000
// （3）30 40
//
// 排好序后乌龟塔的最高高度为 2，实际上应该是 3，只需将（3）放在最上面即可。
//
// 若按力量增序排列，力量相同按体重增序排列，从第一只乌龟开始处理，设当前新增加的乌龟为 i，从 1 到
// i - 1 搜索总重量小于乌龟 i 的可承重重量，且高度能增加的乌龟 j，更新乌龟 i 的总承重重量和能堆
// 叠的最大高度，使用这种方法，对于一般的测试数据，都能得到正确的答案，但是对于如下测试数据，却不能
// 得到正确的答案（之前我就是使用这种方法）：
//
// （1）101 101
// （2）100 201
// （3）99 300
// （4）98 301
// （5）5 302
//
// 前述算法能得到的最大高度是 3，实际上将（2），（3），（4），（5）堆叠起来可以得到高度为 4 的乌
// 龟塔，为什么算法失效了？因为在第二步处理乌龟（2）的时候，因为乌龟（1）能放在乌龟（2）上，故乌龟
// （2）拥有了最大高度 2，从而在处理后续乌龟时，（2）不能作为单独乌龟放置在其他乌龟上，只能是和（1）
// 做为一个整体来放置，所以需要记录能达到高度 K 的乌龟塔时，乌龟的最小总重量，这样在构建乌龟塔时，
// 总是选择当前能堆叠最高且总重量最小的乌龟，才有可能构建更高的乌龟塔。
//
// 那么是否可以按力量来进行排序？答案是肯定的，假设有两只乌龟，重量和力量分别为 W1，S1，W2，S2，
// 且有 S1 <= S2，那么排序如下：
//
// W1 S1
// W2 S2
//
// 若力量为 S1 的乌龟能支撑起包括 W2 在内的乌龟重量，则有 S1 >= （W1 + W2），因为有 S2 >= S1
// 则力量为 S2 的乌龟总是同样能支撑起来，但是反过来就不一定了，即 S2 >= （W1 + W2），不一定有
// S1 >= （W1 + W2），故按力量来排序总是不会改变最优结构的。
 */

}
