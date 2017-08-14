package pm.pc.vol11;

/**
 * Created by 高文文 on 2017/7/6.
 */
public class DistinctSubSequence {

    public static void main(String[] args) {
/*        String X = "babgbag";
        int i = X.length() - 1;
        if(i == 0) X = X.substring(i+1);
        else if(i == X.length() - 1) X = X.substring(0, X.length() - 1);
        else X = X.substring(0, i) + X.substring(i+1, X.length());
        System.out.println(X);*/

        DistinctSubSequence alg = new DistinctSubSequence();
        String X1= "babgbag";  // --> 5
        String Z1 = "bag";
        System.out.println("Distinct Number: " + alg.doDistictSubSeq(X1, Z1, 0));
        String X2 = "rabbbit";
        String Z2 = "rabbit";
        System.out.println("Distinct Number: " + alg.doDistictSubSeq(X2, Z2, 0));


        String X3 = "ABCDE";
        String Z3 = "ACE";
        System.out.println("Distinct Number: " + alg.doDistictSubSeq(X3, Z3, 0));

    }
/*
Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters
without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

Here is an example:
S = "rabbbit", T = "rabbit"

Return 3.
 */
    public int doDistictSubSeq(String X, String Z, int from) {
        if(X.length() == Z.length()) {
            if(X.equals(Z)) return 1;
            else return 0;
        }
        if(X.length() < Z.length()) return 0;
        int res = 0;
        for(int i = from; i < X.length(); i++) {
            String tmp;
            if(i == 0) tmp = X.substring(i+1);
            else if(i == X.length() - 1) tmp = X.substring(0, X.length() - 1);
            else tmp = X.substring(0, i) + X.substring(i+1, X.length());
//            System.out.println(tmp + "   " + Z + "  " + from);
            res += doDistictSubSeq(tmp, Z, i);
        }
        return res;
    }
}
