package pm.pc.vol1;

/**
 * ID: 110102	Minesweeper
 * @author ??????
 *
 */
public class Alg102 {
	
	public void mineSweeper(char[][] mines) {
		int[][] memo = new int[mines.length][mines[0].length];
		for(int i = 0; i < mines.length; i++) {
			for(int j = 0; j < mines[0].length; j++) {
				if(mines[i][j] != '*')
					memo[i][j] = countMines(mines, j, i);
			}
		}
		
		for(int i = 0; i < memo.length; i++) {
			for(int j = 0; j < memo[0].length; j++) {
				if(mines[i][j] == '*') {
					System.out.print("*");
				} else {
					System.out.print(memo[i][j]);
				}
					
			}
			System.out.println();
		}
	}
	
	/**
	 * ???????????
	 * @param mines
	 * @param x
	 * @param y
	 * @return
	 */
	private int countMines(char[][] mines, int x, int y) {
		final int[] coordinates = {-1, 0, 1};
		int count = 0;
//		System.out.println("row:" + mines.length + " column:" + mines[0].length);
//		System.out.println("x:" + x + " y:" + y);
		for(int i = 0; i < coordinates.length; i++) {
			for(int j = 0; j < coordinates.length; j++) {
				if(coordinates[i] == 0 && coordinates[j] == 0) continue;
				if(x + coordinates[i] < 0)continue;
				if(x + coordinates[i] >= mines[0].length)continue;
				if(y + coordinates[j] < 0)continue;
				if(y + coordinates[j] >= mines.length)continue;

//				System.out.println("x': " + (x + coordinates[i])  + " y': " + (y + coordinates[j]));
				count += (mines[y + coordinates[j]][x + coordinates[i]] == '*') ? 1 : 0;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		Alg102 alg = new Alg102();
		char[][] mines1 = {
				{'*', '.', '.', '.'},
				{'.', '.', '.', '.'},
				{'.', '*', '.', '.'},
				{'.', '.', '.', '.'}
		};
		System.out.println("Field #1:");
		alg.mineSweeper(mines1);
		char[][] mines2 = {
				{'*', '*', '.', '.', '.'},
				{'.', '.', '.', '.', '.'},
				{'.', '*', '.', '.', '.'}
		};
		System.out.println("Field #2:");
		alg.mineSweeper(mines2);
	}
}
