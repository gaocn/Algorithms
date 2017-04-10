package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110507	The Stern-Brocot Number System
 *
 *  二叉树搜索
 */
public class Alg507 {


    public static void main(String[] args) {

        Alg507 alg = new Alg507();
        alg.doSternBrocotTree(5, 7);
        alg.doSternBrocotTree(878, 323);
    }

    public boolean doSternBrocotTree(int m, int n) {
        StringBuilder sb = new StringBuilder();
        int LM = 0, LN = 1;
        int RM = 1, RN = 0;
        int MM = 1, MN = 1;

        double value = (double) m / n;
        for(; MM != m || MN != n; MM = LM + RM, MN = LN + RN) {
            if(value > (double) MM / MN) {
                LM = MM;
                LN = MN;
                sb.append("R");
            } else {
                RM = MM;
                RN = MN;
                sb.append("L");
            }
        }

        System.out.println(sb.toString());
        return true;
    }

}

