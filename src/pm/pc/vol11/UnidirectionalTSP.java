package pm.pc.vol11;

import java.util.Arrays;

/**
 * Created by 高文文 on 2017/7/18.
 */
public class UnidirectionalTSP {


/*
    Input
    The input consists of a sequence of matrix specifications. Each matrix specification consists of the row
    and column dimensions in that order on a line followed by m · n integers where m is the row dimension
    and n is the column dimension. The integers appear in the input in row major order, i.e., the first n
    integers constitute the first row of the matrix, the second n integers constitute the second row and so
    on. The integers on a line will be separated from other integers by one or more spaces. Note: integers
    are not restricted to being positive.
    There will be one or more matrix specifications in an input fle. Input is terminated by end-of-fle.
    For each specification the number of rows will be between 1 and 10 inclusive; the number of columns
    will be between 1 and 100 inclusive. No path’s weight will exceed integer values representable using 30
    bits.

    Output
Two lines should be output for each matrix specification in the input fle, the first line represents a
minimal-weight path, and the second line is the cost of a minimal path. The path consists of a sequence
of n integers (separated by one or more spaces) representing the rows that constitute the minimal path.
If there is more than one path of minimal weight the path that is lexicographically smallest should be
output.
Note: Lexicographically means the natural order on sequences induced by the order on their elements.
Sample Input
5 6
3 4 1 2 8 6
6 1 8 2 7 4
5 9 3 9 9 5
8 4 1 3 2 6
3 7 2 8 6 4
5 6
3 4 1 2 8 6
6 1 8 2 7 4
5 9 3 9 9 5
8 4 1 3 2 6
3 7 2 1 2 3
2 2
9 10
9 10
Sample Output
1 2 3 4 4 5
16
1 2 1 5 4 5
11
1 1
19
 */

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
            new int[]{3, 4, 1, 2, 8, 6},
            new int[]{6, 1, 8, 2, 7, 4},
            new int[]{5, 9, 3, 9, 9, 5},
            new int[]{8, 4, 1, 3, 2, 6},
            new int[]{3, 7, 2, 8, 6, 4}
        };
        int[][] matrix1 = new int[][]{
                new int[]{3,4,1,2,8,6},
                new int[]{6,1,8,2,7,4},
                new int[]{5,9,3,9,9,5},
                new int[]{8,4,1,3,2,6},
                new int[]{3,7,2,1,2,3}
        };

        UnidirectionalTSP alg = new UnidirectionalTSP();
        alg.doUnidirectTSP(matrix);
        alg.doUnidirectTSP(matrix1);

    }

    public void doUnidirectTSP(int[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        int[] parent = new int[column];
        Arrays.fill(parent, -1);

        int minCost = Integer.MAX_VALUE;
        int step = 0;
        for(int i = 0; i < row; i++) {
            parent[step] = i;
            minCost = Math.min(minCost, unidirectTSP(matrix, i, 0, parent, step + 1));
            System.out.println(Arrays.toString(parent));
        }
        System.out.println("minCost: " + minCost);

    }

    private final static int[] g = {-1, 0, 1};

    private int unidirectTSP(int[][] matrix, int rIdx, int cIdx, int[] parent, int step) {
        int row = matrix.length;
        int column = matrix[0].length;
        if(cIdx == column - 1) {
            return matrix[rIdx][cIdx];
        }
        int minCost = Integer.MAX_VALUE;
        int nextRIdx,nextCIdx, tmpCost;
        for(int i = 0; i < g.length; i++) {
            nextRIdx = (rIdx + g[i] + row) % row;
            nextCIdx = (cIdx + 1);
            tmpCost = unidirectTSP(matrix, nextRIdx, nextCIdx, parent, step + 1) + matrix[rIdx][cIdx];
            if(minCost > tmpCost) {
                minCost = tmpCost;
                parent[step] = nextRIdx;
            }
        }
        return minCost;
    }


}
