package pm.pc.vol3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 高文文 on 2016/12/28.
 *
 * Problem: 110302	Where's Waldorf?
 */
public class Alg302 {

    char[][] grid;

    public Alg302() {

    }

    /**
     *
     * @param grid M * N grids
     * @param toBeMatchedWords  words to find in {@link #waldorf(char[][], List)}
     */
    public void waldorf(char[][] grid, List<String> toBeMatchedWords) {

        /**
         * for {@link toBeMatchedWords.get(0)} if (i, j) is answer,
         *      then store (i, j) in format (i*grid.length + j) to result[0]
         */
        int[] result = new int[toBeMatchedWords.size()];
        Arrays.fill(result, 0);
        this.grid = grid;

        for(int k = 0; k < toBeMatchedWords.size(); k++) {
            for(int i = 0; i < grid.length && result[k] == 0; i++) {
                for(int j = 0; j < grid[0].length && result[k] == 0; j++) {
                    if(doWaldorf(i, j, toBeMatchedWords.get(k), 0)){
                        result[k] = i * grid.length + j;
                    }
                }
            }
        }

        // parser result as format(i,j) and output it
        for(int i = 0; i < result.length; i++) {
            if(result[i] != 0) {
                int rowIndex    = result[i] / grid.length;
                int columnIndex = result[i] % grid.length;
                System.out.println((rowIndex+1) + " " + (columnIndex + 1));
            } else {
                System.out.println("NOT FOUND");
            }
        }
    }

    private final int[] g = {-1, 0, 1};
    private final int[] f = {-1, 0, 1};

    private boolean doWaldorf(int rowIndex, int columnIndex, String matchedWord, int matchedIndex) {
        if(matchedIndex == matchedWord.length()) {
            return true;
        }
        // horizontal, vertical, diagonal
        if(isRangeValid(rowIndex, columnIndex) && grid[rowIndex][columnIndex] != '?') {
            char ch1 = Character.toLowerCase(matchedWord.charAt(matchedIndex));
            char ch2 = Character.toLowerCase(grid[rowIndex][columnIndex]);
            System.out.println("[ " + matchedIndex +" ]: " +  ch1 + " ? " + ch2);
            if(ch1 == ch2) {
                char backup = grid[rowIndex][columnIndex];
                grid[rowIndex][columnIndex] = '?';
                boolean isWaldorf = false;
                for(int i = 0; i < g.length && !isWaldorf; i++) {
                    for(int j = 0; j < f.length && !isWaldorf; j++) {
                        isWaldorf = doWaldorf(rowIndex + g[i], columnIndex + f[j], matchedWord, matchedIndex + 1);
                    }
                }
                grid[rowIndex][columnIndex] = backup;
                if(isWaldorf) {
                    return true;
                }
            }

        }
        return false;
    }


    public boolean isRangeValid(int row, int column) {
        return row >= 0 && row < grid.length
                && column >= 0 && column < grid[0].length;
}


    public static void main(String[] args) {
       Alg302 alg = new Alg302();

       List<String> words = new ArrayList<>();
       words.add("Waldorf");
       words.add("Bambi");
       words.add("Betty");
       words.add("Dagbert");

      char[][] grid = {
              {'a', 'b', 'c', 'D', 'E', 'F', 'G', 'h', 'i', 'g', 'g'},
              {'h', 'E', 'b', 'k', 'W', 'a', 'l', 'D', 'o', 'r', 'k'},
              {'F', 't', 'y', 'A', 'w', 'a', 'l', 'd', 'O', 'R', 'm'},
              {'F', 't', 's', 'i', 'm', 'r', 'L', 'q', 's', 'r', 'c'},
              {'b', 'y', 'o', 'A', 'r', 'B', 'e', 'D', 'e', 'y', 'v'},
              {'K', 'l', 'c', 'b', 'q', 'w', 'i', 'k', 'o', 'm', 'k'},
              {'s', 't', 'r', 'E', 'B', 'G', 'a', 'd', 'h', 'r', 'b'},
              {'y', 'U', 'i', 'q', 'l', 'x', 'c', 'n', 'B', 'j', 'f'}

      };


        alg.waldorf(grid, words);
    }
}
