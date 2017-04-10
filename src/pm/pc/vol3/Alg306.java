package pm.pc.vol3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高文文 on 2016/12/28.
 * Problem：110306	File Fragmentation
 *
 * Since we know that we have half fragments and that they should come together to make N full fragments,
 *   then we always have an even number of fragments. Hence, Total number of bits in all fragments / N = number of bits per fragment.
 *   Once we know this, we look for any two fragments that fit together to produce the expected number of bits and if we
 *   find them, we try to join the rest to create the same pattern.
 *
 *   O(2 *N^2)       naive
 *   O(NlogN + N^2)  hashmap
 *   O(NlogN) O(N)   sort
 *
 *  同一文件的多个副本的碎片 ？？//TODO  all of the files on the tray were identical
 */
public class Alg306 {



    public Alg306() {

    }


    public static void main(String[] args) {

        Alg306 alg = new Alg306();


    }
}
