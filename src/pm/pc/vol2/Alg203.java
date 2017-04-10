package pm.pc.vol2;

/**
 * 110203	Hartals
 * @author ������
 *
 */
public class Alg203 {

	
	public static void main(String[] args) {
		Alg203 alg = new Alg203();
		
		int hartals1[] = {3, 3, 4, 8};
		int days1 = 14;
		
		System.out.println("Strike Days: " + alg.calculateHartalDays(hartals1, days1));
		
		int hartals2[] = {12, 15,25, 40};
		int days2 = 100;
		System.out.println("Strike Days: " + alg.calculateHartalDays(hartals2, days2));
	}

	public int calculateHartalDays(int[] hartals, int days) {
		
		boolean hasStrike[] = new boolean[days];
		
		for(int i = 0; i < hartals.length; i++) {
			int hartal = hartals[i];
			int count = 2;
			while(hartal <= days) {
				hasStrike[hartal - 1] = true;
				hartal = hartals[i] * count++;
			}
		}
		// count strike days except Fri Sat
		int strikeDays = 0;
		for(int i = 0; i < hasStrike.length; i++) {
			if(i % 7 == 5 || i % 7 == 6)continue;
			strikeDays += hasStrike[i] ? 1 : 0;
		}
		
		return strikeDays;
	}
	
	//TODO
	public int caculateHartals(int days, int[] hartals) {
		/**
		 * ģ��һ�ܣ�����0��ʾ���գ�һ�����ƣ�������ʹ��hartalDays����ģ�⣻����������14�죬��0��ʼ
		 *  hartalDay����   [0]  [1]  [2]  [3]   [4]   [5]   [6]         
		 *  ���磺 
		 *  	  ��һ������    0    1    2    3     4     5     6
		 *  	  �ڶ��� ���� 7%7  8%7  9%7  10%7  11%7  12%7  13%7
		 */
		int[] hartalDays = new int[7];
		
		return 0;
	}

}
