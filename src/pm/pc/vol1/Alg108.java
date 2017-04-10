package pm.pc.vol1;

import java.io.IOException;

/**
 * ID: 110108	Australian Voting
 * @author ������
 *
 */
public class Alg108 {
	String[] candidates = new String[20];
	int[][] votes = new int[20][1000];
	
	/**
	 * -1 ��ʾ�ж����ѡ�˻����ͬƱ����
	 * >= 0 ��ʾcandidates�еĺ�ѡ�˻�ʤ
	 * @return
	 */
	public int calculateCandidate() {
		
		
		
		return calculateCandidate();
	}
	
	
	/*
		3
		John Doe
		Jane Smith
		Jane Austen
		1 2 3
		2 1 3
		2 3 1
		1 2 3
		3 1 2
	 */
	
	public static void main(String[] args) {
		Alg108 alg = new Alg108();
		
		String[] candidates = {"John Doe", "Jane Smith", "Jane Austen"};
		int row = candidates.length;
		int column = 5;
		
		String line = ReadLine(255);
		while(!line.isEmpty() && !line.equals("\r")) {
			System.out.println("line " + line + " " + line.length() + " " + (line.charAt(0) == '\r'));
			line = ReadLine(255);
		}
	}
	
	/**
	 * Window���滻����"\r\n"�� Linux�»�����"\n"�����
	 * Windows�·��ص���"\r"��Linux���ص���null
	 * �ж��������£�!line.isEmpty() && !line.equals("\r")
	 * @param maxLength
	 * @return
	 */
   public static String ReadLine(int maxLength) { 
		byte line[] = new byte[maxLength];
		int length = 0;
		int input = -1;
		try {
			while (length < maxLength) {
				input = System.in.read();
				if ((input < 0) || (input == '\n'))
					break; 
				line[length++] += input;
			}

			if ((input < 0) && (length == 0))
				return null; // eof
			return new String(line, 0, length);
		} catch (IOException e) {
			return null;
		}
	}

}
