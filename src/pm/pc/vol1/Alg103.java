package pm.pc.vol1;

/**
 * ID: 110103	The Trip
 * @author ������
 *
 */
public class Alg103 {

	
	private void theTrip(double[] costs) {
		double averageCosts = 0.0;
		double sumCosts = 0.0;
		for(double i : costs) {
			sumCosts += i;
		}
		averageCosts = (long)((sumCosts / costs.length) * 100.0) / 100.0;
		System.out.println("averageCosts��" + averageCosts);
		double positiveDiff = 0.0;
		double negativeDiff = 0.0;
		for(double cost : costs) {
			if(cost > averageCosts) {
				positiveDiff += (long)((cost - averageCosts) * 100.0) / 100.0;
			} else {
				negativeDiff += (long)((averageCosts - cost) * 100.0) / 100.0;
			}
		}
		double minDiff = Math.min(positiveDiff, negativeDiff);
		System.out.println("$" + minDiff);
		
	}
	
	public static void main(String[] args) {
		Alg103 alg103 = new Alg103();
		double[] testCase1 = {10.00, 20.00, 30.00};
		double[] testCase2 = {15.00, 15.01, 3.00, 3.01};
		alg103.theTrip(testCase1);
		alg103.theTrip(testCase2);
		
		System.out.println((int)((2.01) * 100) / 100.0);
	}

}
