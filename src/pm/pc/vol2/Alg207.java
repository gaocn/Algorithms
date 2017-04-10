package pm.pc.vol2;

import java.util.List;
import java.util.Map;

/**
 * 110207	Contest Scoreboard
 * @author 高文文
 * 
 */
public class Alg207 {
	Contestant[] contestants;
	// problemId  -> contestantId
	Map<Integer, Integer> incorrectedMap;
	
	public Alg207 () {
		contestants = new Contestant[100];
	}
	
	public void calcuScore(List<SubmittedProblemInfo> statistics) {
		
	}
	
	public static void main(String[] args) {
		Alg207 alg = new Alg207();
	}

}


class SubmittedProblemInfo {
	int contestantId;
	int problemId;
	int score;
	Result result;
}

class Contestant implements Comparable<Contestant> {
	int contestantId;
	int solvedPrblems;
	int score;
	
	@Override
	public int compareTo(Contestant other) {
		if(this.solvedPrblems != other.solvedPrblems) {
			if(this.solvedPrblems < other.solvedPrblems) {
				return -1;
			} else {
				return 1;
			}
		} else if(this.score != other.score) {
			if(this.score < other.score) {
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}
}

enum Result {
	C,
	I,
	R,
	U,
	E
}
