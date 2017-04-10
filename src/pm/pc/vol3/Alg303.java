package pm.pc.vol3;

/**
 * Created by 高文文 on 2016/12/28.
 * Problem: 110303	Common Permutation
 */
public class Alg303 {
    /**
     * method1: O(2*(M+N))  sapce: O(2 * 128 * 4B) -> can be less
     *  1. find common characters in both string A and B,
     *  2. output the first permutation in alphabetical order!
     *
     * method2: O(MlogM + NlogN + M + N)
     *  1. sort string A and B
     *  2. output common characters in order
     */

    int[] charsInA = new int[128];
    int[] charsInB = new int[128];
    public Alg303() {
    }

    public String doCommonPermutation(String A, String B) {

        for(char cha : A.toCharArray()) {
            charsInA[cha]++;
        }

        for(char chb : B.toCharArray()) {
            charsInB[chb]++;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < charsInA.length; i++) {
            if(charsInA[i] != 0 && charsInB[i] != 0) {
                for(int rep = 0; rep < Math.min(charsInA[i], charsInB[i]); rep++) {
                    stringBuilder.append((char)i);
                }
            }
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();

    }




    public static void main(String[] args) {
        Alg303 alg = new Alg303();

        String A = "thewalking";
        String B = "streetdown";
        alg.doCommonPermutation(A, B);
    }
}
