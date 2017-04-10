package pm.pc.vol1;

/**
 * ID: 110107	Check The Check
 * @author ������
 *
 */
public class Alg107 {

	private char[][] gameBoard;

	/**
	 * �ж����������Ƿ�Ϊ��
	 * @return
	 */
	public boolean isGameBoardEmpty() {
		for(int i = 0; i < gameBoard.length; i++) {
			for(int j = 0; j < gameBoard[0].length; j++) {
				if(gameBoard[i][j] != '.')
					return false;
			}
		}
		return true;
	}

	/**
	 * �жϸ�λ����gameBoard���Ƿ�Ϸ�
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOutOfBound(int i, int j) {
		if(i < 0 || j < 0)return false;
		if(i >= gameBoard.length || j >= gameBoard[0].length)return false;
		return true;
	}
	
	/**
	 * ���ݵ�ǰ�Ǻ��廹�ǰ���ȷ��������һ������λ�ò��ж��Ƿ�black king��white king�Ƿ� in check
	 * @param isBlack ��ʾ��ǰ�Ǻ��� 
	 * @return ����true��ʾ king in check
	 */
	public boolean pawn(boolean isBlack, int i, int j) {
		if(!isOutOfBound(i + 1, j - 1) && isInCheck(isBlack, i + 1, j - 1)) {
				return true;
		}
		if(!isOutOfBound(i + 1, j + 1) && isInCheck(isBlack, i + 1, j + 1)) {
				return true;
		}
		if(!isOutOfBound(i - 1, j - 1) && isInCheck(isBlack, i - 1, j - 1)) {
				return true;
		}
		if(!isOutOfBound(i - 1, j + 1) && isInCheck(isBlack, i - 1, j + 1)) {
				return true;
		}
		return false;
	}

	public boolean rook(boolean isBlack, int i, int j) {
		return checkVertivalAndHorional(isBlack, i, j);
	}

	private boolean checkVertivalAndHorional(boolean isBlack, int i, int j) {
		for(int from = j + 1; from < gameBoard[0].length; from++) {
			if(gameBoard[i][from] != '.') break;
			if(isInCheck(isBlack, i, from))
				return true;
		}
		for(int from = j - 1; from >= 0; from--) {
			if(gameBoard[i][from] != '.') break;
			if(isInCheck(isBlack, i, from))
				return true;
		}
		for(int from = i + 1; i < gameBoard.length; from++) {
			if(gameBoard[from][j] != '.') break;
			if(isInCheck(isBlack, from, j))
				return true;
		}
		for(int  from = i - 1; from >= 0; from--) {
			if(gameBoard[from][j] != '.') break;
			if(isInCheck(isBlack, from, j))
				return true;
		}
		return false;
	}

	public boolean bishop(boolean isBlack, int i, int j) {
		return checkDiagonal(isBlack, i, j);
	}
	
	private boolean checkDiagonal(boolean isBlack, int i, int j) {
		
		return false;
	}

	/**
	 * �ж���i��jλ���Ƿ���King
	 * @param isBlack
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean isInCheck(boolean isBlack, int i, int j) {
		if(isOutOfBound(i, j)) return false;
		if(isBlack && gameBoard[i][j] == 'K') {
			return true;
		} else if(!isBlack && gameBoard[i][j] == 'k') {
			return true;
		}
		return false;
	}
	
	
	public Alg107(char[][] gameBoard1) {
		this.gameBoard = gameBoard1;
	}



	public static void main(String[] args) {
		char[][] gameBoard = {{'.', '.', 'k', '.', '.', '.', '.', '.'}, 
							  {'p', 'p', 'p', '.', 'p', 'p', 'p', 'p'}, 
							  {'.', '.', '.', '.', '.', '.', '.', '.'}, 
							  {'.', 'R', '.', '.', '.', 'B', '.', '.'}, 
							  {'.', '.', '.', '.', '.', '.', '.', '.'}, 
							  {'.', '.', '.', '.', '.', '.', '.', '.'}, 
							  {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P',}, 
							  {'K', '.', '.', '.', '.', '.', '.', '.'}};
		Alg107 alg = new Alg107(gameBoard);
		
		
		
		
		
	}

}
