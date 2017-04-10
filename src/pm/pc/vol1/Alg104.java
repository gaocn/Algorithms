package pm.pc.vol1;

/**
 * ID: 110104	LC-Display
 * @author ������
 *
 */
public class Alg104 {

	
	public static void main(String[] args) {
		Alg104 alg = new Alg104();
		alg.LCDisplay(2, "12345");
		alg.LCDisplay(3, "67890");
		alg.LCDisplay(5, "1254367890");
	}

	private void LCDisplay(int size, String displapStr ) {
		if(size <= 0)return;
		int len = displapStr.length();
		char[][] LD = new char[2 * size + 3][(size + 2) * len + len - 1];
		//construct LD
		generateOrginalLD(len, size, LD);
		proccessDigits(LD, displapStr, size);
		printLD(LD);
		
	}

	private void proccessDigits(char[][] LD, String displapStr, int size) {
		for(int i = 0; i < displapStr.length(); i++) {
			char ch = displapStr.charAt(i);
			switch (ch) {
			case '0':
				resetLine(size, LD, size + 1, i);
				break;
			case '1':
				resetLine(size, LD, 0, i);	
				resetLine(size, LD, size + 1, i);
				resetLine(size, LD, 2 * size + 2, i);
				resetLeftBorder(size, LD, i, 0, LD.length - 1);
				break;
			case '2':
				resetLeftBorder(size, LD, i, 1, size);
				resetRightBorder(size, LD, i, 1 + size + 1, size + size + 1);
				break;
			case '3':
				resetLeftBorder(size, LD, i, 0, LD.length - 1);
				break;
			case '4':
				resetLine(size, LD, 0, i);	
				resetLine(size, LD, 2 * size + 2, i);
				resetLeftBorder(size, LD, i, 1 + size + 1, size + size + 1);
				break;
			case '5':
				resetLeftBorder(size, LD, i, 1 + size + 1, size + size + 1);
				resetRightBorder(size, LD, i, 1, size);
				break;
			case '6':
				resetRightBorder(size, LD, i, 1, size);
				break;
			case '7':
				resetLine(size, LD, size + 1, i);
				resetLine(size, LD, 2 * size + 2, i);
				resetLeftBorder(size, LD, i, 0, LD.length - 1);
				break;
			case '8':
				break;
			case '9':
				resetLeftBorder(size, LD, i, 1 + size + 1, size + size + 1);
				break;
			}
		}
	}
	/**
	 * ��LD�еĵ�lineNum�еĵ�num���ַ���"----"������Ϊ��
	 */
	private void resetLine( int size, char[][] LD, int lineNum, int num) {
		int from = 1 + num * (size + 2 + 1);
		int  to = size  + num * (size + 2 + 1);
		for(int j = from; j <= to; j++) {
			LD[lineNum][j] = ' ';
		}
	}
	/**
	 * ��LD�еĵ�from�е�to�еĵĵ�num���ַ�����߽�����Ϊ��
	 */
	private void resetLeftBorder(int size, char[][] LD, int num, int from , int to) {
		int leftBoarder = 0 + num * (size + 2 + 1);
		for(int i = from; i <= to; i++) {
			LD[i][leftBoarder] = ' ';
		}
	}
	/**
	 * ��LD�еĵ�from�е�to�еĵĵ�num���ַ����ұ߽�����Ϊ��
	 */
	private void resetRightBorder(int size, char[][] LD, int num, int from , int to) {
		int rightBoarder = size + 1  + num * (size + 2 + 1);
		for(int i = from; i <= to; i++) {
			LD[i][rightBoarder] = ' ';
		}
	}
	
	/**
	 * 	����len��8����LD��
	 * @param len
	 * @param size
	 * @param LD
	 */
	private void generateOrginalLD(int len, int size, char[][] LD) {
		// ��LD������ "--" �������
		for(int count = 0; count < len; count++) {
			int from = 1 + count * (size + 2 + 1);
			int to   = size  + count * (size + 2 + 1);
//			System.out.println("from = " + from + ", to = " + to);
			for(int j = from; j <= to; j++) {
				LD[0][j] = '-';
				LD[size + 1][j] = '-';
				LD[2 * size + 2][j] = '-';
			}
		}
		// ��LD���� �������
		for(int i = 0; i < LD.length; i++) {
			if(i == 0 || i == size + 1 || i == 2 * size + 2)continue;
			for(int count = 0; count < len; count++) {
				int leftBoarder = 0 + count * (size + 2 + 1);
				int rightBoarder = size + 1  + count * (size + 2 + 1);
				LD[i][leftBoarder] = '|';
				LD[i][rightBoarder] = '|';
			}
		}

		
		
	}
	
	private void printLD(char[][] LD) {
		for(int i = 0; i < LD.length; i++) {
			for(int j = 0; j < LD[0].length; j++) {
				System.out.print(LD[i][j]);
			}
			System.out.println();
		}
	}
}
