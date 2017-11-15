package uva.volumn1;

import java.util.LinkedList;
import java.util.List;

/*
 Sample Input 

10
move 9 onto 1
move 8 over 1
move 7 over 1
move 6 over 1
pile 8 over 6
pile 8 over 5
move 2 over 1
move 4 over 9
quit

Sample Output 

 0: 0
 1: 1 9 2 4
 2:
 3: 3
 4:
 5: 5 8 7 6
 6:
 7:
 8:
 9:
 */
/**
 * Problem ID: 101
 * @author ������
 *
 */
public class Alg101 {
	
	/**
	 * ��ʾ�е�block����
	 * 	          ��block[i]Ϊi   ��ʾblock�е�����stack[i]�У�
	 * 		��block[i]��Ϊi ��ʾ��i��block��stack[block[i]]��
	 */
	private int[] blocks;
	private LinkedList<Integer>[] chain;
	
	public Alg101(int n) {
		blocks = new int[n+1];
		for(int i = 0; i <= n; i++)
			blocks[i] = i;
		chain = new LinkedList[n+1];
		for(int i =0; i <= n; i++) {
			chain[i] = new LinkedList<>();
			chain[i].addLast(i);
		}
	}
	
	private void resetBlock(int b) {
		if(b < 0 || b > blocks.length)return;
		while(chain[blocks[b]].getLast() != b) {
			int last = chain[blocks[b]].removeLast();
			chain[last].addLast(last);
			blocks[last] = last;
			System.out.println("�� " + last + "�� " + b + "����");
		}
	}
	
	/**
	 * ��from block�Ƶ�����to��block�ϣ����ҽ�from��to�����block�Żص�ԭ����λ��
	 * ��4������� 1 from��to�ڶ�Ӧ��block�ϣ�
	 * 			2��from�ڶ�Ӧ��block�ϣ�to���ڶ�Ӧ��block�ϣ�
	 * 			3��from���ڶ�Ӧ��block�ϣ�to�ڶ�Ӧ��block�ϣ�
	 * 			4��fron��to�����ڶ�Ӧ��block��
	 * @param from
	 * @param to
	 */
	private void moveOnto(int from, int to) {
		//��from֮���������Ϊԭ����λ��
		resetBlock(blocks[from]);
		//��to֮���������Ϊԭ����λ��
		resetBlock(blocks[to]);
		move(from, to); 
	}

	private void move(int from, int to) {
		System.out.println("�� " + from + "��" + blocks[from] + "�Ƶ�" + blocks[to]);
		chain[blocks[to]].addLast(chain[blocks[from]].removeLast());
		blocks[from] = blocks[to];
	}
	
	/**
	 * ��from block�Ƶ�����to��block�ϣ����ҽ�from�����block�Żص�ԭ����λ�ã���to��block����
	 * @param from
	 * @param to
	 */
	private void moveOver(int from, int to) {
		resetBlock(blocks[from]);
		move(from, to); 
	}
	
	/**
	 * ��from block�Ƶ�����to��block�ϣ����ҽ�to�����block�Żص�ԭ����λ�ã�from�ϵ�block����
	 * @param from
	 * @param to
	 */
	private void pileOnto(int from, int to) {
		resetBlock(blocks[to]);
		pileOver(from, to);
	}

	/**
	 * ��from block�Ƶ�����to��block�ϣ����ҽ�to�����block��from�ϵ�block����
	 * @param from
	 * @param to
	 */
	private void pileOver(int from, int to) {
		int index = chain[blocks[from]].indexOf(from);
		System.out.println("Pile  index=" + index);
		if(index >= 0) {
			List toBeMoved = (List) chain[blocks[from]].subList(index, chain[blocks[from]].size());
			System.out.println("pileOver  toBeMoved=" + toBeMoved);
			LinkedList<Integer> toBeAdded = new LinkedList<>();
			for(int i=0; i < toBeMoved.size(); i++) {
				int key = (int) toBeMoved.get(i);
				toBeAdded.add(key);
				blocks[key] = to;
			}
			System.out.println("pileOver  toBeAdded=" + toBeAdded);
			chain[blocks[to]].addAll(toBeAdded);
			toBeMoved.clear();
		}
	}
	
	/**
	 * ��ӡÿ��block map
	 */
	private void printBlockMap() {
		for(int i = 0; i < blocks.length; i++) {
			System.out.print(i + "("+ blocks[i] + "): ");
			if(blocks[i] == i) {
				for(int j = 0; j < chain[i].size(); j++) {
					System.out.print( chain[i].get(j) + " ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 *  move 9 onto 1
		move 8 over 1
		move 7 over 1
		move 6 over 1
		pile 8 over 6
		pile 8 over 5
		move 2 over 1
		move 4 over 9  
	 * @param args
	 */
	public static void main(String[] args) {
		Alg101 alg = new Alg101(10);
		alg.moveOnto(9, 1);
		alg.moveOver(8, 1);
		alg.moveOver(7, 1);
		alg.moveOver(6, 1);
//		alg.moveOver(3, 5);
//		alg.pileOnto(8, 5);
//		alg.pileOver(1, 5);
		alg.pileOver(8, 6);
		alg.pileOver(8, 5);
		alg.moveOver(2, 1);
		alg.moveOver(7, 9);
//		alg.moveOver(8, 5);
//		alg.pileOnto(9, 8);
		alg.printBlockMap();

	}

}
