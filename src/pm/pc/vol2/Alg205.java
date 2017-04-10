package pm.pc.vol2;

import java.util.Arrays;

/**
 * 110205	Stack 'em Up
 * @author ������
 * 
 */
public class Alg205 {
	private final static String suits[] = {"Clubs", "Diamonds", "Hearts", "Spades"};
	private final static String[]  values= {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
	int[] cards;
	
	public Alg205 () {
		cards = new int[52 + 1];
		for(int i = 0; i < cards.length; i++) {
			cards[i] = i;
		}
	}
	
	public void shuffle(int[] shuffles) {
		for(int i = 1; i < shuffles.length; i++) {
			if(shuffles[i] != i && shuffles[i] != -1) {
				swap(cards, shuffles[i], shuffles[shuffles[i]]);
				shuffles[shuffles[i]] = shuffles[i];
				shuffles[i] = i;
			}
		}
		System.out.println(Arrays.toString(cards));
	}
	
	private void swap(int[] cards, int i, int j) {
		cards[i] -= cards[j];
		cards[j] += cards[i];
		cards[i] = cards[j] - cards[i];
	}
	
	public void showShuffledCard() {
		for(int i = 1; i < cards.length; i++) {
			int suitIndex = (cards[i] - 1) / 13;
			int valueIndex =(cards[i] - 1) % 13;
			System.out.println(values[valueIndex] + " of " + suits[suitIndex]);
		}
		
	}
	
	public static void main(String[] args) {
		Alg205 alg = new Alg205();
		int[] shuffles1 = {0, 2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 52, 51};
		alg.shuffle(shuffles1);
		int[] shuffles2 = {0, 52, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 1};
		alg.shuffle(shuffles2);
		alg.showShuffledCard();
	}


}
