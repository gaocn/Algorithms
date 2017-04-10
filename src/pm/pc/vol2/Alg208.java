package pm.pc.vol2;

import java.util.Arrays;

/**
 * 110208	Yahtzee
 * 
 * 1. optimized backtrack  cache[i][j] 表示第i行在位置j处的值 ， 
 * 2. DP 
 * 3. Selection of optimal disposition (Munkers' Algorithm)
 * @author 高文文
 * 
 */
public class Alg208 {
	boolean hasBonus;
	
	int size = 9;
	
	int[] chosen;
	int[][] rounds;
	
	static int times = 0;
	
	public int doYahtzee() {
		return doYahtzee(size - 1);
	}

	/**
	 *  当测试为(category  == 10)时，需要时间Time: 21s， 由于是按照n!阶层增加时间，因此当n > 10的时候需要的时间会很长。
	 *  【存在重复运算】 =》  DP
	 *  例如： 
	 *     1 2 3 4 5 
	 *     5 2 3 4 1
	 *     2 1 3 4 5
	 */
	
	//TODO ???
	public int doYahtzee(int category) {
		if(category  < 0) {
			System.out.println(Arrays.toString(chosen));
			times++;
			return 0;
		}
		 
		int maxScore = Integer.MIN_VALUE;
		for(int i = size - 1; i >= 0; i--) {
			if(chosen[i] == -1) {
				chosen[i] = category;
				maxScore = Math.max(maxScore, doYahtzee(category - 1) + calculateScore(category, i));
				chosen[i] = -1;
			}
		}
		
		//plus a bonus of 35 points if the sum of the first six categories is 63 or greater.
		//TODO
/*		if(firstSixSum() > 63) {
			maxScore += 35;
			hasBonus = true;
		}*/
		
		return maxScore;
	}
	
	public void showInitialScore() {
		for(int i = 0; i < rounds.length; i++) {
			System.out.print(this.calculateScore(i, i) + " ");
		}
		System.out.println();
	}
	
/*	private int firstSixSum() {
		
	}*/
	
	private int calculateScore(int category, int position) {
		int score = 0;
		switch (position) {
			case 0:
				for(int i = 0; i < rounds[category].length; i++) {
					if(rounds[category][i] == 1) {
						score += 1;
					}
				}
				break;
			case 1:
				for(int i = 0; i < rounds[category].length; i++) {
					if(rounds[category][i] == 2) {
						score += 2;
					}
				}
				break;
			case 2:
				for(int i = 0; i < rounds[category].length; i++) {
					if(rounds[category][i] == 3) {
						score += 3;
					}
				}
				break;
			case 3:
				for(int i = 0; i < rounds[category].length; i++) {
					if(rounds[category][i] == 4) {
						score += 4;
					}
				}
				break;
			case 4:
				for(int i = 0; i < rounds[category].length; i++) {
					if(rounds[category][i] == 5) {
						score += 5;
					}
				}
				break;
			case 5:
				for(int i = 0; i < rounds[category].length; i++) {
					if(rounds[category][i] == 6) {
						score += 6;
					}
				}
				break;
			case 6:
				for(int i = 0; i < rounds[category].length; i++) {
						score += rounds[category][i];
				}
				break;
			case 7:
				if(countOfAKind(category) >= 3) {
					for(int i = 0; i < rounds[category].length; i++) {
						score += rounds[category][i];
					}
				}
				break;
			case 8:
				if(countOfAKind(category) >= 4) {
					for(int i = 0; i < rounds[category].length; i++) {
						score += rounds[category][i];
					}
				}
				break;
			case 9:
				if(countOfAKind(category) >= 5) {
					score = 50;
				}
				break;
			case 10:
					if(isShortStraight(category)) {
						score = 25;
					}
				break;
			case 11:
				if(isLongStraight(category)) {
					score = 35;
				}
				break;
			case 12:
				if(isFullHouse(category)) {
					score = 40;
				}
				break;
		}
		return score;
	}
	
	private int countOfAKind(int category) {
		int pivot = rounds[category][0];
		int count = 1;
		for(int i = 1; i < rounds[category].length; i++) {
			if(rounds[category][i] == pivot) {
				count++;
			} else {
				count--;
			}
			if(count == 0) {
				count = 1;
				pivot = rounds[category][i];
			}
		}
		
		// calcaulate appeared times of pivot in rounds[category]
		count = 0;
		for(int i = 0; i < rounds[category].length; i++) {
			if(pivot == rounds[category][i]) {
				count++;
			}
		}
//		System.out.println("countOfAKind in rounds[" + category + "]：" + count);
		return count;
	}
	
	/**
	 * 1,2,3,4 or 2,3,4,5 or 3,4,5,6
	 */
	private boolean isShortStraight(int category) {
		int[] bucket = {0, 0, 0, 0, 0, 0, 0};
		for(int i = 0; i < rounds[category].length; i++) {
			bucket[rounds[category][i]]++;
		}
		
		int increasingSeqLen = 0;
		for(int i = 1; i < bucket.length; i++) {
			if(bucket[i] != 0) {
				increasingSeqLen++;
			} else if(increasingSeqLen >= 4){
				return true;
			} else {
				increasingSeqLen = 0;
			}
		}
//		System.out.println("increasingSeqLen=" + increasingSeqLen);
		return increasingSeqLen >= 4;
	}

	private boolean isLongStraight(int category) {
		return Arrays.equals(rounds[category], new int[]{1,2,3,4,5}) ||
				Arrays.equals(rounds[category], new int[]{2,3,4,5,6});
	}

	private boolean isFullHouse(int category) {
//		System.out.println(Arrays.toString(rounds[category]));
		int pivot1 = rounds[category][0];
		int pivot2 = -1;
		int count1 = 1;
		int count2 = 0;
		
		for(int i = 1; i < rounds[category].length; i++) {
			if(rounds[category][i] == pivot1) {
				count1++;
			} else if(-1 == pivot2){
				pivot2 = rounds[category][i];
				count2 = 1;
			} else if(rounds[category][i] == pivot2) {
				count2++;
			}
		}
		
//		System.out.println("pivot1=" + pivot1 + " count1="+ count1 + ", pivot2=" + pivot2 + " count2=" + count2);
		return (count1 == 2 && count2  == 3)||(count1 == 3 && count2  == 2);
	}


	public Alg208 (int[][] rounds) {
		this.rounds = rounds;
		hasBonus = false;
		chosen = new int[13];
		Arrays.fill(chosen, -1);
	}
	
	
	public static void main(String[] args) {
		
		
		int rounds1[][] = {
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
		};
		
		int rounds2[][] = {
				{1, 1, 1, 1, 1},
				{6, 6, 6, 6, 6},
				{6, 6, 6, 1, 1},
				{1, 1, 1, 2, 2},
				{1, 1, 1, 2, 3},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 6},
				{6, 1, 2, 6, 6},
				{1, 4, 5, 5, 5},
				{5, 5, 5, 5, 6},
				{4, 4, 4, 5, 6},
				{3, 1, 3, 6, 3},
				{2, 2, 2, 4, 6}
		};
		
		Alg208 alg1 = new Alg208(rounds1);
//		System.out.println("maxScore: " + alg1.doYahtzee() + " hasBonus:" + alg1.hasBonus);
		
		Alg208 alg2 = new Alg208(rounds2);
		long start = System.currentTimeMillis();
		System.out.println("maxScore: " + alg2.doYahtzee() + " hasBonus:" + alg2.hasBonus);
		System.out.println("times: " + alg2.times);
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) / 1000 + "s");
	}

}
