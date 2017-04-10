package uva.volumn1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * Problem ID: 102
 * Ecological Bin Packing
 * Type：greedy, ad hoc
 * 
 * @author 高文文
 *
 */
public class Alg102 {
	
	
	
	public static void main(String[] args) {
		Alg102 alg = new Alg102();
		
		int testCase1[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8,9}};
		int testCase2[][] = {{5, 10, 5}, {20, 10, 5}, {10, 20, 10}};
		alg.binPacking(testCase1);
		alg.binPacking(testCase2);
	}
	
	/**
	 * 总共有6中情况
	 */
	private void binPacking(int[][] bin) {
		final String[] possibleSolution = {"BCG", "BGC", "CBG", "CGB", "GBC", "GCB"};
		int sumMoves[] = new int[6];
		sumMoves[0] = bin[1][0] + bin[2][0] + bin[0][2] + bin[2][2] + bin[0][1] + bin[1][1];
		sumMoves[1] = bin[1][0] + bin[2][0] + bin[0][1] + bin[2][1] + bin[0][2] + bin[1][2];
		sumMoves[2] = bin[1][2] + bin[2][2] + bin[0][0] + bin[2][0] + bin[0][1] + bin[1][1];
		sumMoves[3] = bin[1][2] + bin[2][2] + bin[0][1] + bin[2][1] + bin[0][0] + bin[1][0];
		sumMoves[4] = bin[1][1] + bin[2][1] + bin[0][0] + bin[2][0] + bin[0][2] + bin[1][2];
		sumMoves[5] = bin[1][1] + bin[2][1] + bin[0][2] + bin[2][2] + bin[0][0] + bin[1][0];
		int index = 0;
		int sum = Integer.MAX_VALUE;
		for(int i = 0; i < sumMoves.length; i++) {
			if(sumMoves[i] < sum) {
				index = i;
				sum = sumMoves[i];
			}
		}
		System.out.println(Arrays.toString(sumMoves));
		System.out.println(possibleSolution[index] + " " + sum);
		
	}

}
