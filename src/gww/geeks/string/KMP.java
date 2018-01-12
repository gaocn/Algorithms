package gww.geeks.string;



public class KMP {


    public void kmpSearch(String S, String P) {
        int N = S.length();
        int M = P.length();

        int[] lps = new int[M];
        computeLPS(P, lps);

        int i = 0, j = 0;
        while (i < N) {
            if(S.charAt(i) == P.charAt(j)) {
                i++; j++;
            }
            // Pattern Found
            if(j == M) {
                System.out.println("Pattern Found at index: " + (i - j));
                j = lps[j - 1];
            } else if (i < N && S.charAt(i) != P.charAt(j)) {
                if(j != 0) {  //可以利用lps，跳过已知的匹配的字符串
                    j = lps[j - 1];
                } else {      //没有能够匹配的字符串，需要从下一个窗口开始匹配
                    i++;
                }

            }
        }
    }

    public void computeLPS(String P, int[] lps) {
        // 初始条件
        lps[0] = 0;
        int len = 0;  // 表示前一子串中的long prefix suffix的长度
        int i = 1;    // 当前正在计算的子串
        int M = P.length();

        while (i < M) {
            if (P.charAt(len) == P.charAt(i)) {
                len++;
                lps[i] = len;            // 存在新的longest prefix suffix，更新后，继续先判断下一个子串
                i++;
            } else {
                if (len != 0) {          // len != 0，查找上一个longest prefix suffix与当前子串的关系
                    len = lps[len - 1];
                } else {                 // len = 0, 不存在longest prefix suffix，判断下一个子串
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        String S = "AAAABAAAA";
        String P = "AAAA";

        KMP alg =new KMP();
        alg.kmpSearch(S, P);
    }

}
