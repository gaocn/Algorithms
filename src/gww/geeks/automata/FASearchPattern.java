package gww.geeks.automata;

import java.util.Arrays;

public class FASearchPattern {

    private final static int NO_OF_CHARS = 256;
    /** transition function of Finite Automata for a  given pattern */
    private int[][] TF;
    private int NUM_STATE;
    private String pattern;

    public FASearchPattern(String pat) {
        NUM_STATE = pat.length();
        TF = new int[NUM_STATE + 1][NO_OF_CHARS];
        this.pattern = pat;
        buildTF();
    }

    private  void  buildTF() {
        for (int state = 0; state  <= NUM_STATE; state++) {
            for (int x = 0; x < NO_OF_CHARS; x++) {
                int  ns = getNextState(state, x);
                if (ns != 0)System.out.println("TF["+state+"]["+  x + "]=" + ns);
                TF[state][x] = ns;
            }
        }
    }

    private int getNextState(int state, int x) {
        //1. ����ǰ�ַ���pattern�е���һ���ַ�һ������״̬����1
        if (state < NUM_STATE && x == pattern.charAt(state))
            return state+1;

//        if(state  == 1 && x  ==  65) {
//            System.out.println("sdf");
//        }
        //2. Ѱ��pattern[0..state-1]x���ǰ׺��׺����
        //��������Ӵ���ʼ���ѣ�ֱ���ҵ�һ�������������Ӵ�
        for (int ns = state; ns > 0; ns--) {
            //�ҵ���x��β���Ӵ�
            if (pattern.charAt(ns - 1) == x) {
                int i;
                for (i = 0; i < ns-1; i++) {
                    if (pattern.charAt(i) != pattern.charAt(state - ns + 1 + i))
                        break;
                }
                if (i == ns - 1)  return ns;
            }
        }
        return 0;
    }

    public void search(String txt)  {
        for (int i = 0, state = 0; i < txt.length(); i++) {
            state = TF[state][txt.charAt(i)];
            if (state == NUM_STATE) {
                System.out.println("Pattern Found at  index:" + (i - NUM_STATE +1));
            }
        }
    }

    public static void main(String[] args) {
//        FASearchPattern alg = new FASearchPattern("ACACAGA");
        FASearchPattern alg = new FASearchPattern("AABA");
        alg.search("AABAACAADAABAAABAA");
    }

}
