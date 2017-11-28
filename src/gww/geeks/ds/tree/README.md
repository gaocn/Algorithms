# 前缀树(Trie Tree)

使用Trie数进行搜索，搜索的时间复杂度能够达到最优（即搜索词的长度）。通常若是采用平衡二叉树（BST）算法，搜索时间复杂度为O(M*logN)，M为最大单词长度，N是二叉树的节点数。而采用Trie前缀树，时间复杂度为O(M)，但需要额外的存储空间。

前缀树：
1. 每个节点都有多个分支，每一个分支代表单词中的一个字符，每一个分支节点又指向下一层级的孩子Trie节点；
2. 若节点是一个单词的结尾（isEndOfWord），需要进行标注；

``` 
节点定义
    class TrieNode {
        TrieNode children[] = new TrieNode[ALPHABET_SIZE];
        Boolean  isEndOfWord;
    }
```
## 前缀树的构建
插入和查询操作的时间复杂度为O(key_length)，而空间复杂度为O(ALPHABET_SIZE * key_length * N)其中N是单词的个数。为了降低空间复杂度可以采用压缩的前缀树，[ternary search tree](http://en.wikipedia.org/wiki/Ternary_search_tree)等。

- 插入操作
每一个单词中的字符会作为TrieTree的一个节点，每一次插入操作要么是新建一个TrieNode要么是扩展已有的Trie树，单词的长度决定Trie树的长度。需要注意的是，对于单词的结尾需要在Trie中进行标注。

- 查询操作
查询一个单词步骤和插入类似，不同的是我们只需要比较字符然后继续向下移动即可。搜索结束的条件是找到单词的结尾或者搜索不到相应的字符。若查找到isEndOfWord则查找成功，否则是查找失败。

- 删除操作
删除一个单词用bottom-up递归方式，删除条件如下：

  - 删除单词不在Trie树中，删除操作不进行任何操作；
  - 删除单词在Trie树中唯一，既不是其他单词的前缀也不包含其他单词，则直接删除所有节点；
  - 删除单词是其他单词的前缀，直接将删除单词的标识，将isEndOfWord置为false；
  - 删除单词中包含其他单词，即至少有一个单词是要删除单词的前缀，删除从包含的最长前缀单词尾部到最后的节点；

