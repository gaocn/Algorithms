package gww.geeks.dp;

public class EditDistance {
    /*
        Edit Distance
        Given two text strings A of length n and B of length m, you want to transform A into B with minimum number of
         operations of following types: deletes a character from A, insert a character into A. or change some character
         in A into a new character. The minimum number of such operation required to transform A into B is called the edit
         distance between A and B.

        Input: string A of length n, string B of length m
        Goal:  minimum edit distance transform A into B

        f(i, j)表示长度为i的字符串变换为长度为j的字符串所需要的最小编辑距离，则f(N, M)即为所求
           设删除，插入，替换的代价分别是Cd, Ci, Cr
                      { f(i, j-1) + Ci
        f(i, j) = min { f(i-1, j) + Cd
                      { f(i-1, j-1)      if A[i] == B[j]
                      { f(i-1, j-1) + Cr if A[i] != B[j]
       f(0, 0) = 0
       f(0, j) = j * Ci
       f(i, 0) = i * Cd

     */
}
