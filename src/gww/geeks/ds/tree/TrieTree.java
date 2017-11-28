package gww.geeks.ds.tree;

public class TrieTree {

    public static class TrieNode {
        public final static int ALPHABET_SIZE = 26;
        TrieNode children[];
        boolean isEndOfWord;

        public TrieNode() {
            this.children = new TrieNode[ALPHABET_SIZE];
            this.isEndOfWord = false;
        }
    }

    TrieNode root;

    public TrieTree() {
        this.root = new TrieNode();

    }

    public void insert(String key) {
        int index;
        TrieNode crawl = root;

        for (int level = 0; level < key.length(); level++) {
            index = key.charAt(level) - 'a';
            if (crawl.children[index] == null) {
                crawl.children[index] = new TrieNode();
            }
            crawl = crawl.children[index];
        }

        crawl.isEndOfWord = true;
    }

    public boolean search(String key) {
        int index;
        TrieNode crawl = root;

        for (int level = 0; level < key.length(); level++) {
            index = key.charAt(level) - 'a';
            if (crawl.children[index] == null) return false;
            crawl = crawl.children[index];
        }
        return crawl != null && crawl.isEndOfWord;
    }

    public void delete(String key) {
        if(key != null && !key.isEmpty()) {
            deleteHelper(root, key, 0);
        }
    }

    private boolean deleteHelper(TrieNode crawl, String key, int level) {
        if (crawl != null) {
            if (level == key.length()) {
                // isLeaf
                crawl.isEndOfWord = false;

                if (isFreeNode(crawl)) {
                    return true;
                }
                return false;
            } else {
                int index = key.charAt(level) - 'a';
                if (deleteHelper(crawl.children[index], key, level + 1)) {
                    crawl.children[index] = null;
                    return !crawl.isEndOfWord && isFreeNode(crawl);
                }
            }
        }
        return false;
    }

    private boolean isFreeNode(TrieNode node) {
        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            if (node.children[i] != null) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String keys[] = {"the", "a", "there", "answer", "any",
                "by", "bye", "their"};
        String output[] = {"Not present in tree", "Present in tree"};

        TrieTree trie = new TrieTree();
        for (String key : keys)
            trie.insert(key);

        trie.delete(keys[0]);
        System.out.println(trie.search("the") ? "Present in tree" : "Not present in tree");
        // Search for different keys
    }

}
