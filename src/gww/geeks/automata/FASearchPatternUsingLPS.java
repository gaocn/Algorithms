package gww.geeks.automata;

import java.util.Arrays;

public class FASearchPatternUsingLPS {
    private final static int NO_OF_CHARS = 256;
    private int NUM_STATES;
    private String pattern;
    private int[][] TF;

    public FASearchPatternUsingLPS(String pattern) {
        NUM_STATES = pattern.length();
        this.pattern = pattern;
        TF = new int[NUM_STATES + 1][NO_OF_CHARS];
        buildTF();
    }

    private void buildTF() {
        int lps = 0;

        //1. ��ʼ��
        for (int i = 0; i < NO_OF_CHARS; i++)TF[0][i] = 0;
        TF[0][pattern.charAt(0)] = 1;

        for (int s = 1; s <= NUM_STATES; s++) {

            //�����ǰ׺��׺����
            System.arraycopy(TF[lps], 0,  TF[s], 0, NO_OF_CHARS);
//            for (int i = 0; i <  NO_OF_CHARS; i++) {
//                TF[s][i] = TF[lps][i];
//            }

            //�ҵ���һ�θ���ʱ��lps���ǰ׺��׺λ��
            if (s < NUM_STATES)  {
                TF[s][pattern.charAt(s)] = s + 1;
                System.out.println("lps: " + lps);
                lps = TF[lps][pattern.charAt(s)];
            }
            System.out.println("lps: " + lps);
        }

        for (int[] a : TF) {
            System.out.println(Arrays.toString(a));
        }
    }

    public void search(String txt) {
        int state = 0;
        for (int i =  0; i < txt.length(); i++)  {
            state = TF[state][txt.charAt(i)];
            if (state == NUM_STATES) {
                System.out.println("Pattern Found at Index: " + (i - NUM_STATES + 1));
            }
        }
    }


    public static void main(String[] args) {
        FASearchPatternUsingLPS alg = new FASearchPatternUsingLPS("GEEKS");
//        alg.search("AABAACAADAABAAABAA");
        alg.search("GEEKS FOR GEEKS");
    }
}
