package pm.pc.vol2;

import java.util.Arrays;

/**
 * 110201	Jolly Jumpers
 * @author ������
 *
 */
public class Alg201 {

	/*
	 * Sample Input
		4 1 4 2 3
		5 1 4 2 -1 6
	   
	   Sample Output
		Jolly
		Not jolly

	 */
	
	
	
	public static void main(String[] args) {
		Alg201 alg = new Alg201();
		
		int[] testCase1 = {1, 4, 2, 3};
		int[] testCase2 = {1, 4, 2, -1, 6};
		if(alg.isJollyJunpers(testCase1)) {
			System.out.println("Jolly");
		} else {
			System.out.println("Not jolly");
		}
		
		if(alg.isJollyJunpers(testCase2)) {
			System.out.println("Jolly");
		} else {
			System.out.println("Not jolly");
		}
		
	}

	public  boolean isJollyJunpers(int[] jumpers) {
		int diffs[] = new int[jumpers.length - 1];
		for(int i = 0; i < jumpers.length - 1; i++) {
			diffs[i] = Math.abs(jumpers[i] - jumpers[i + 1]);
		}
		Arrays.sort(diffs);
		System.out.println("diffs: " + Arrays.toString(diffs));
		int index = 0;
		for(; index < diffs.length - 1; index++) {
			if(diffs[index] >= diffs[index + 1]) {
				return false;
			}
		}
		return index == diffs.length - 1; 
	}

}
