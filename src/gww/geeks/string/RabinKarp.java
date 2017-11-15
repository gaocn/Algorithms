package gww.geeks.string;

public class RabinKarp {

    //输入字母表中的字符个数
    public static final int D = 256;

    public void rabinKarpSearch(String S, String P, int q) {
        int N = S.length();
        int M = P.length();

        int hashP = 0;
        int hashS = 0;

        // h = d^m-1
        int h = 1;
        for (int i = 1; i < M; i++) {
            h = (D * h) % q;
        }

        //计算初S和P的初始哈希值
        for (int i = 0; i < M; i++) {
            hashP = (hashP * D + P.charAt(i)) % q;
            hashS = (hashS * D + S.charAt(i)) % q;
        }

        for (int i = 0; i <= N - M; i++) {

            if(hashP == hashS) {
                //there may have a match
                int j = 0;
                for (; j < M; j++) {
                    if(P.charAt(j) != S.charAt(j + i))
                        break;
                }
                if(j == M) {
                    System.out.println("Pattern Found at index: " + i);
                }
            }

            //rehash
            if(i < N - M) {
                hashS = (D * (hashS - S.charAt(i) * h) + S.charAt(i + M)) % q;
                // we may get negative value
                if(hashS < 0) hashS += q;
            }
        }

    }

    public static void main(String[] args) {
        String S = "GEEKS FOR GEEKS";
        String P = "GEEKS";
        int q = 101;

        RabinKarp rk = new RabinKarp();
        rk.rabinKarpSearch(S, P, q);
    }
}
