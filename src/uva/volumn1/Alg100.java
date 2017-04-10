package uva.volumn1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem ID: 100
 * 3N + 1 Problem
 * Type：DP，Simulation
 * 
 * @author 高文文
 *
 */
public class Alg100 {
	
	
	private int cycleLength(int n) {
		if(n == 1)return 1;
		if((n & 0x1) == 0)
			return cycleLength(n>>1) + 1;
		else 
			//解决溢出问题
			return cycleLength(n + ((n + 1) >> 1)) + 2;
//			return cycleLength((n << 1) + n + 1) + 1;
	}
	
	//可以采用 map代替 减少预分配的内存空间
	private int[] cached = new int[2000000];
	
	private int iterativeCycleLength(int n) {
		if(cached[n] != 0)return cached[n];
		cached[n]  = ((n & 0x1) == 0) ? iterativeCycleLength(n>>1) + 1 : iterativeCycleLength(n + ((n + 1) >> 1)) + 2 ;
		return cached[n];
	}
	
	private int maxCycleLength(int from, int to) {
		int max = Integer.MIN_VALUE;
		cached[1] = 1;
		for(int i = from; i <= to; i++) {
			int cycleLen = iterativeCycleLength(i);
			max = max < cycleLen ? cycleLen : max; 
		}
		return max;
	}
	
	public static void main(String[] args) {
		Alg100 alg001 = new Alg100();
		alg001.cached[1] = 1;
		
		System.out.println( "iterativeCycleLength start  " );
		long start = System.currentTimeMillis();
		for(int i = 10; i <= 30; i++) {
			System.out.println("cycleLength(" + i  +")" + alg001.iterativeCycleLength(i));
		}
		System.out.println( "Total Time: "+ (System.currentTimeMillis() - start) + 
				" \niterativeCycleLength end  " );

	}

}
