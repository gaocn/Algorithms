package gww.geeks.string;

import java.util.Arrays;

public class ZAlgorithm {

    public void ZSearch(String txt, String pat) {
        String cs = pat + "$" + txt;
        int[] Z = new int[cs.length()];

        getZArray(cs, Z);

        int M = pat.length();
        for (int i = M; i < cs.length(); i++) {
            if (Z[i] == M) {
                System.out.println("Pattern Found at Index: " + (i - M - 1));
            }
        }
    }

    public void getZArray(String txt, int[] Z) {
        int N = txt.length();
        //初始化
        int R = 0, L = 0;
        int K;

        for (int i = 1; i < N; i++) {

            if (i > R) {
                R = L = i;
                while (R < N && txt.charAt(R) == txt.charAt(R - L)) R++;
                Z[i] = R - L;
                R--;
            } else {
                K = i - L;
                if (Z[K] < R - i + 1) {
                    Z[i] = Z[K];
                } else {
                    L = i;
                    while (R < N && txt.charAt(R) == txt.charAt(R - L)) R++;
                    Z[i] = R - L;
                    R--;
                }
            }
        }
        System.out.println("Z 数组:" + Arrays.toString(Z));
    }


    public static void main(String[] args) {
        ZAlgorithm zAlgorithm = new ZAlgorithm();
        String S = "baabaa";
        String P = "aab";
        zAlgorithm.ZSearch(S, P);
    }
}
