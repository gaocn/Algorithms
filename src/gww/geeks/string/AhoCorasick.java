package gww.geeks.string;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AhoCorasick {
    private final static int ALPHABET_SIZE = 26;
    //自动机中的状态数，应该是数组pattern所有的单词长度之和
    private int MAX_NUM_STATS;
    int[][] g;
    int[] f;
    /**
     * OUTPUT function用数组output实现。
     * Bit i in this mask is one if the word with index i appears when the machine enters this state.
     */
    int[] output;

    String[] patterns;

    public AhoCorasick(String[] patterns) {
        this.patterns = patterns;
        MAX_NUM_STATS = 0;
        for (String word : patterns) MAX_NUM_STATS += word.length();
    }

    private int buildStateMachine() {

        g = new int[MAX_NUM_STATS][ALPHABET_SIZE];
        f = new int[MAX_NUM_STATS];
        output = new int[MAX_NUM_STATS];

        for (int i = 0; i < MAX_NUM_STATS; i++) Arrays.fill(g[i], -1);
        Arrays.fill(f, -1);
        Arrays.fill(output, 0);

        //初始状态数为1
        int states = 1;

        /**   构建Go To  */

        //通过创建Trie树，构建Go To function
        for (int i = 0; i < patterns.length; i++) {
            final String word = patterns[i];
            int currentState = 0;

            for (int j = 0; j < word.length(); j++) {
                int ch = word.charAt(j) - 'a';

                if (g[currentState][ch] == -1) g[currentState][ch] = states++;
                currentState = g[currentState][ch];
            }
            //将单词的位图加入当前状态
            output[currentState] |= (1 << i);
        }

        //对于Trie树中root节点中没有后续边的节点，将其Go To指向root节点
        for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
            if (g[0][ch] == -1) g[0][ch] = 0;
        }

        /**   构建Failure  采用广度优先遍历BFS  */
        Arrays.fill(f, -1);
        Queue<Integer> queue = new LinkedList<>();

        for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
            if (g[0][ch] != 0) {
                f[g[0][ch]] = 0;
                queue.offer(g[0][ch]);
            }
        }

        while (queue.size() > 0) {
            int state = queue.poll();

            for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
                if (g[state][ch] != -1) {
                    int failure  = f[state];

                    //Find the deepest node labeled by proper suffix of string from root to current state.
                    while (g[failure][ch] == -1) failure = f[failure];

                    failure = g[failure][ch];
                    f[g[state][ch]] = failure;

                    output[g[state][ch]] |= output[failure];

                    queue.offer(g[state][ch]);
                }
            }
        }
        return states;
    }

    private int findNextState(int currentState, char nextInput) {
        int answer = currentState;
        int ch = nextInput - 'a';

        while (g[answer][ch] == -1) answer = f[answer];

        return g[answer][ch];
    }

    public void search(String text) {
        buildStateMachine();

        int currentState = 0;

        for (int i = 0; i < text.length(); i++) {
            currentState = findNextState(currentState, text.charAt(i));

            // match not found
            if (output[currentState] == 0) continue;

            for (int j = 0; j < patterns.length; ++j) {
                if ((output[currentState] & (1 << j) )!= 0) {
                    System.out.println("Word " + patterns[j] + " appears from " + (i - patterns[j].length() + 1) + " to " + i);
                }
            }
        }
    }

    public static void main(String[] args) {
        String patterns[] = {"he", "she", "hers", "his"};
        String text = "ahishers";
        AhoCorasick ahoCorasick = new AhoCorasick(patterns);

        ahoCorasick.search(text);
    }
}
