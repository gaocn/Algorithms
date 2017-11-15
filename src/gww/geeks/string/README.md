## String Match
问题描述: 对于给你定字符串S[0..n]和P[0..m]，满足条件 m<n，求出所有S中与P相等的子串。

### Naive Method
最简单的字符串匹配算法就是采用遍历，即将字符串P从S的开始出逐一对比，直到找到能够匹配的子串或查找失败，经过优化后的算法时间复杂度为O(m*(n-m+1))。

算法的缺点是：当P匹配了前面大多数字符串时，在最后出现了一个不匹配的情况，这时算法的效率最低，例如：

``` 
    S = "ABABABABABABAABABAC"
    P = "ABABAC" (a bad case for Naive Method)
```

### KMP Pattern Search
> Degenerating Property: pattern having same sub-patterns appearing more than once in the pattern.

KMP(Knuth Morris Pratt)算法利用Pattern的退化特性，即一个模式中同一子模式出现不止一次，使得在最坏情况下的时间复杂度提高为O(N)。KMP的**基本思想**是：当匹配过程中出现不匹配时，那么我们就知道在下一个窗口中进行匹配时能够匹配的字符串，在进行下一次匹配时我们就直接跳过这些已匹配的字符串。

```
[Match Overview]

        S = "AAAAABAAABA"
        P = "AAAA"
        
[step 1] 
        S = "AAAA ABAAABA"
        P = "AAAA"         初始位置，匹配
[step 2]
        S = "AAAA A BAAABA"
        P =  "AAA A"
在当前窗口进行匹配时，仅仅比较模式的第四个字符来判断字符串是否匹配模式，这就是KMP优化的地方，因为我们已经提前知道前三个字符串一定匹配，那么在比较是直接跳过这三个字符，从第四个字符开始比较。

[预处理]
从上面step 2可以看出，在匹配时如何知道前三个字符是已匹配的呢？因此在匹配之前需要对模式进行预处理，通过数组lps[]记录每一步匹配时，需要跳过的字符数。
```

#### KMP的预处理
KMP算法需要对模式Pattern进行预处理，预处理结果是一个数组，大小为模式字符串的长度，用于存储在每一次匹配时直接跳过比较字符串的个数。

lps表示模式的proper prefix(不包括字符串本身)也是该模式suffix(包括字符串本身)，或者lps表示为模式的prefix也该模式是proper suffix，例如：

``` 
    P = "ABC"
    proper prefix: "A"  "AB"
    suffix : "C" "BC" "ABC"
```

对于任意模式P[0..i]，0 <= i <= m，lps[i]用于存储所有P[0..i]的proper prefix也是P[0..i]的suffix中最长proper prefix的长度。

``` 
    P = "AAAA"
    lps = [0, 1, 2, 3]
    
    P = "AABAACAABAA"
    lps = [0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 5]

[预处理算法]
    对于P[0..m]， P[0..i]表示当前处理的子串，用于len表示P[0..i-1]中logest prefix suffix的子串长度。
    lps[i]表示P[0..i]中longest prefix suffix的子串长度
    
             { len + 1    if P[len] = P[i] <------------------------------|
    lps[i] = {                                                            |
             {            else if len != 0 update len=lps[len-1], then recursive 
             {
             { len        else len = 0
             
    初始条件：lps[0] = 0 因为长度为1的字符串不存在proper prefix
             i = 1 len = 0

```

#### 使用lps进行搜索
在使用KMP进行字符串比较时，我们就不需要每一次比较所有的字符是否相等，而是通过lps决定这一次要进行比较的字符的位置(跳过那些已经确定会匹配的字符串)。那么应该如何通过lps决定要进行比较的字符的位置或者说要跳过多少字符串再进行比较？

- 从开始(i=0, j=0)进行匹配，若S[i]=P[j]匹配，则(i++, j++)自增1，进行下一次比较；
- 当出现S[i]!=P[j]时，我们知道子串P[0..j-1]与子串S[i-j+1..i-1]是匹配的，同时我们知道lps[j-1]为子串P[0..j-1]中的proper prefix和suffix的最长长度。从上述两点，可以知道：在S[i-j+1..i-1]的子串中，有lps[j-1]个字符是不需要比较的，因为它们是匹配的。

``` 
          S = "AAAAABAAAA"
          P = "AAAA"
          lps = {0, 1, 2, 3}
[匹配流程]
          i = 0, j = 0   S[i]=P[j] i, j 自增1
          i = 1, j = 1   S[i]=P[j] i, j 自增1
          i = 2, j = 2   S[i]=P[j] i, j 自增1
          i = 3, j = 3   S[i]=P[j] i, j 自增1
          i = 4, j = 4   j = M, 模式找到， 重置j = lps[j-1] = lps[3] = 3
          i = 4, j = 3   S[i]=P[j] i, j 自增1
          i = 5, j = 4   j = M, 模式找到， 重置j = lps[j-1] = lps[3] = 3
          i = 5, j = 3   S[i]!=P[j], 重置j = lps[j-1]=lps[2] = 2
          i = 5, j = 2   S[i]!=P[j], 重置j = lps[j-1]=lps[1] = 1
          i = 5, j = 1   S[i]!=P[j], 重置j = lps[j-1]=lps[0] = 0
          i = 5, j = 0   S[i]!=P[j] && j ==0, 重置i = i + 1
          i = 6, j = 0   ....

```

### Rabin-Karp Pattern Search

基本的模式匹配算法Naive Method是通过每一次滑动一个字符后，然后在当前窗口，一个个比较模式中的字符，若完全匹配则成功。Rabin-Karp搜索算法同Naive Method算法一样，也是需要每一次滑动一个字符，不同的是在当前窗口不是一个一个比较字符，而是比较当前窗口的字符串长度为M(模式字符串的长度)的子串与模式Pattern的哈希值是否相同，若相同则匹配成功。Rabin-Karp算法需要计算下面字符串的哈希值：

 - 模式自身的哈希值；
 - 要匹配字符串中所有长度为M的子串的哈希值；
 
#### 如何计算哈希值

为了能够高效的计算字符串的哈希值，我们希望能够根据当前窗口中的子串的哈希值和下一窗口中新增的字符高效的计算出下一窗口中子串的哈希值，公式推导如下：

```
    假设当前窗口的子串为：S[s..s+m-1]，其哈希值为hash(S[s..s+m-1])，下一窗口的子串为：S[s+1..s+m]，则有：
        hash(S[s+1..s+m]) = rehash(hash(S[s..s+m-1]), S[s+m])
    要求rehash操作的时间复杂度为：O(1)
    
    为了保证计算的hash值能够用整型存放，在计算哈希值时，需要使用取模操作计算哈希值
    对于P[0..m-1]，其hash值的计算公式为：
       初始条件：p = 0
       p = (d * p + s.charAt(i)) % q  0 <= i <= m-1
    rehash函数的表达式为：
       hash(S[s+1..s+m]) = (d * ( hash(S[s..s+m-1]) - S[s]*h ) + S[s+m] ) % q
       d: 字母表中字符的数目
       q: 一个素数
       h: d^(m-1)   
``` 

#### 算法时间复杂度分析

Rabin-Karp算法的平均和最好时间复杂度为O(N+M)，但是最坏情况下的时间复杂度为O(N*M)，最坏情况是所有模式中的字符都一样且与要匹配的字符串中的所有字符一样，这样所有子串的哈希值与模式中的哈希值相同，因此算法就退化为逐一进行比较。例如: S="AAAAAAA"  P="AAAA"
