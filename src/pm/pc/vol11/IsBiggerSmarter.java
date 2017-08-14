package pm.pc.vol11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by 高文文 on 2017/7/5.
 */
public class IsBiggerSmarter {

    static class Elephant {
        int weight, IQ;
        int index;

        public Elephant(int index, int weight, int IQ) {
            this.weight = weight;
            this.IQ = IQ;
            this.index = index;
        }

        @Override
        public String toString() {
            return "Elephant{" +
                    "weight=" + weight +
                    ", IQ=" + IQ +
                    ", index=" + index +
                    '}';
        }
    }


    /*
    Sample Input

6008 1300
6000 2100
500 2000
1000 4000
1100 3000
6000 2000
8000 1400
6000 1200
2000 1900
Sample Output

4
4
5
9
7
     */

    public static void main(String[] args) {
        Elephant[] elephants = new Elephant[] {
                new Elephant(1, 6008, 1300),
                new Elephant(2, 6000, 2100),
                new Elephant(3, 500, 2000),
                new Elephant(4, 1000, 4000),
                new Elephant(5, 1100, 3000),
                new Elephant(6, 6000, 2000),
                new Elephant(7, 8000, 1400),
                new Elephant(8, 6000, 1200),
                new Elephant(9, 2000, 1900),
        };
        IsBiggerSmarter alg =new IsBiggerSmarter();
        alg.doBiggerSmarterRecursively(elephants);
    }


    public void doBiggerSmarterRecursively(Elephant[] elephants) {
        Arrays.sort(elephants, (o1, o2) -> {
            if(o1.weight < o2.weight) return -1;
            else if(o1.weight > o2.weight) return 1;
            else {
                if(o1.IQ < o2.IQ) return 1;
                else if(o1.IQ > o2.IQ) return -1;
            }
            return 0;
        });
        System.out.println(Arrays.toString(elephants));

        int length = solved(elephants, elephants.length - 1);
        System.out.println("len: " + length);
    }


    private int solved(Elephant[] elephants, int idx) {
        if(idx == 0 ) return 1;
        int res , max_ending = 1;
        for(int i = 0; i < idx; i++) {
            res = solved(elephants, i);
            if(elephants[i].weight < elephants[idx].weight
                    && elephants[i].IQ > elephants[idx].IQ
                    && res + 1 > max_ending) {
                 max_ending = res + 1;
            }
        }
        return max_ending;
    }






    /** 首先排序，DP求解按照IQ最长递减子序列LDS
     *  用parent数组存放最长递减子序列；
     *  用length[i]数组存放长度为i的数据中最大递减子序列的长度；
     * */
    public int doBiggerSmarter(Elephant[] elephants) {


        return 0; //TODO
    }

}
