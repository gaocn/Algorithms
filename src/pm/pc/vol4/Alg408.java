package pm.pc.vol4;

import java.util.Collections;
import java.util.TreeMap;

/**
 * Created by 高文文 on 2017/1/6.
 *
 * Problem ID: 110408	Football (aka Soccer)
 *
 * TreeMap + Comparator
 */
public class Alg408 {

    class SoccerTeamStatistics implements Comparable<SoccerTeamStatistics> {
        String teamName;
        int points;
        int wins;
        int maxGoalDifference;
        int maxScores;
        int numberOfPlayedGames;

        @Override
        public int compareTo(SoccerTeamStatistics o) {
            if(this.points > o.points) {
                return -1;
            } else if(this.points < o.points) {
                return 1;
            } else {
                if(this.wins > o.points) {
                    return -1;
                } else if(this.wins < o.wins) {
                    return 1;
                } else {
                    if(this.maxGoalDifference > o.maxGoalDifference) {
                        return -1;
                    } else if(this.maxGoalDifference < o.maxGoalDifference) {
                        return 1;
                    } else {
                        if(this.maxScores > o.maxScores) {
                            return -1;
                        } else if(this.maxScores < o.maxScores) {
                            return 1;
                        } else {
                            if(this.numberOfPlayedGames > o.numberOfPlayedGames) {
                                return -1;
                            } else if(this.numberOfPlayedGames < o.numberOfPlayedGames) {
                                return 1;
                            } else {
                                return this.teamName.compareTo(o.teamName);
                            }
                        }
                    }
                }
            }
        }

    }


    public static void main(String[] args) {
        Alg408 alg = new Alg408();
        TreeMap<SoccerTeamStatistics,Integer> map = new TreeMap<>();

    }
}
