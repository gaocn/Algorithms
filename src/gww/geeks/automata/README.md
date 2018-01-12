## 有限状态机（FSM，Finite State Machine）

有限状态机是一种用来进行对象行为建模的工具，其作用是描述对象在它的生命周期内所经历的状态序列，以及如何响应来自外界的各种事件。计算机科学中，有限状态机被广泛应用于建模应用行为、软件工程、编译器、网络协议和计算与语言的研究。下图是有名的TCP协议状态机。

![1133568-7a4faa75c260d9b3](D:\EclipseWPFroWin10\GeeksForGeeks\src\gww\geeks\automata\1133568-7a4faa75c260d9b3.jpeg)

通常我们在编程中用到的if/else、switch/case语句就是在和有限状态机打交道。在处理一些业务逻辑比较复杂的需求时，可以看看是否符合用一个有限状态机来描述，如果把一些业务模型抽象成一个有限状态机那么代码逻辑就会非常清、晰结构规整。  

### 1、状态机的要素

状态机可以归纳为4个要素：现态、条件、动作、次态，其中“现态”和“条件”是因，“动作”和“次态”为果。

- 现态：当前所处的状态。
- 条件：又称为事件，当一个条件被满足，将会触发一个动作，或者执行一次状态的迁移。
- 动作：条件被满足后执行的动作，动作执行完成后，可以迁移到新的状态，也可以仍然保持原状态；动作不是必须的，当条件被满足后也可以不执行动作直接迁移到新状态。
- 次态：条件被满足后要迁往的状态，次态是相对于现态的，次态一旦被激活就变为现态；


###2、范例 基于FA（Finite Automata）的字符搜索

问题：对于搜索模式$pat =  "ACACAGA"$，s如何构建状态机？

搜索模式长度为M=7，则FA的状态数为M+1 = 8，构建状态机的关键是对于当前状态以及任何输入字符如何得到下一个状态？设给定任意字符为x，当前状态为k，那么下一个状态对应的字符串为$P  = "pat[0..k-1]x"?$（该字符串为搜索 模式的前k个字符与输入字符x构成），==方法是：找到$P?$的最长前缀 后缀的长度d，这个长度d就是下一个要跳转的状态== ，例如 ：$k = 5, x = 'C'?$，则$P = pat[0..4]x = "ACACAC"?$，则其最长前缀后缀字符串为"ACAC"，长度d=4，那么对于$k = 5, x = 'C'?$ ，下一个状态是4。下图是对于模式 pat="ACACAGA"的状态机和转换表:

```mermaid

graph LR
  0((0))
  1((1))
  2((2))
  3((3))
  4((4))
  5((5))
  6((6))
  7((7))
  style 7 stroke-width:6px
  1 -->|A| 1
  0 ==>|A| 1
  1 ==>|C| 2
  2 ==>|A| 3
  3 -->|A| 1
  3 ==>|C| 4
  5 -->|A| 1
  5 -->|C| 4
  4 ==>|A| 5
  5 ==>|G| 6
  6 ==>|A| 7
  7 -->|A| 1
  7 -->|C| 2
```

| 现态/输入 |      |      |      |      |
| :---: | :--: | :--: | :--: | :--: |
| state |  A   |  C   |  G   |  T   |
|   0   |  1   |  0   |  0   |  0   |
|   1   |  1   |  2   |  0   |  0   |
|   2   |  3   |  0   |  0   |  0   |
|   3   |  1   |  4   |  0   |  0   |
|   4   |  5   |  0   |  0   |  0   |
|   5   |  1   |  4   |  6   |  0   |
|   6   |  7   |  0   |  0   |  0   |
|   7   |  1   |  2   |  0   |  0   |

实现上述状态表最简单的方法是对于任意 给定 状态k和输入字符x，得到字符串P = “pat[0..k-1]x”，遍历P中所有可能成为P后缀的前缀，从中找出最长的前缀长度即为下一个状态，算法的时间复杂度为$O(m^3*NO\_OF\_CHARS)$其中 m为搜索模式的长度，NO_OF_CHARS为搜索模式和 文本中所有可能的字符个数。使用KMP中 请求最长前缀后缀长度的的算法可以将算法复杂度降低到$O(m * NO\_OF\_CHARS)$

``` java
/**
1. 如何使用Longest Prefix Suffix求解状态转移方程？
TF[i][x]含义是子串'pattern[0..i-1]x'中的最长前缀后缀长度，用lps存放当前最长前缀后缀长度的索引，当前位置为i
  初始化：lps = 0，i = 0, 此时'pattern[0..i-1]x'只有一个字符，不存在最长前缀后缀 TF[i][pat[i]] = 1
  当 i = 1时，子串为'pattern[0]x'，设'pattern[0]'的最长前缀后缀长度等于lps，则TF[1][x] = TF[lps][x]
  当 i = 2时，子串为'pattern[0]pattern[1]x'，设'pattern[0]pattern[1]'的最长前缀后缀长度等于lps，，即TF[2][x] = TF[lps][x]
  ...
由以上可知：若要求当前状态为i的TF[i]的值，需要求出'pattern[0..i-1]'子串的最长前缀后缀所处的状态或位置lps，求出lps后就可以直接进行状态的复制。

2. 如何求lps？ 
已知lps的初始状态为0，每一次更新状态后需要计算下一个lps的值，设当前状态为s，则有lps = TF[lps][pattern[s]] s < M
*/
private void buildTF() {
  int lps = 0;

  //1. 初始化
  for (int i = 0; i < NO_OF_CHARS; i++)TF[0][i] = 0;
  TF[0][pattern.charAt(0)] = 1;

  for (int s = 1; s <= NUM_STATES; s++) {

    //复制最长前缀后缀长度
    System.arraycopy(TF[lps], 0,  TF[s], 0, NO_OF_CHARS);
    //for (int i = 0; i <  NO_OF_CHARS; i++) {
    //	TF[s][i] = TF[lps][i];
    //}

    //找到下一次更新时，lps的最长前缀后缀位置
    if (s < NUM_STATES)  {
      TF[s][pattern.charAt(s)] = s + 1;
      lps = TF[lps][pattern.charAt(s)];
    }
  }

}
```


## WiKi
1. 避免把某个“程序动作”当作是一种“状态”来处理，如何区分“动作”和“状态”？“动态”是不稳定的，即使没有条件触发，“动作”一旦执行完毕就结束了；而“状态”相对稳定，如果没有外部条件的触发，一个状态就会一直持续下去。

2. 状态划分时漏掉一些状态，导致跳转逻辑不完整。所以维护一张状态表 就非常有必要，从表中可以直观看出哪些状态存在，跳转路径是什么。如果状态不存在，表格中对应的单元格就为空。写好代码后可以根据这张表进行review，QA也可以 根据这个做测试。

3. 状态跳转逻辑的两种写法：状态中判断事件、事件中判断状态。一般业务场景来说，状态的数量是确定的且较少，不同状态下需要处理的事件也不一样；而触发的事件数量则比较多，采用第二种的优势是可以使用switch/case语句剥离为单独的处理逻辑。


## FA(Finite Automata)

FA的基本构成如下，有限机的数据表达式为：{Q, $∑$,  q, F, $ δ$}。FA分为两类：DFA(Deterministic Finite Automata)、NFA(Nondeterministic Finite Automata)

```
Q: Finite set of states
∑: set of input symbols
q_0: Initial state
F: set of final states
δ: transition function
```

### 1、有限确定状态机DFA

在DFA中，对某一特定输入字符，状态机 只能进行一次状态的改变。状态转移函数F定义了在每一个状态 下 ，输入某一符号 后状态如何转移。注意的是：在DFA中，不移动是不允许存在的，输入为空是不能改变状态的。例如：下图 是$∑={0, 1}$ 的有限状态机，该状态机的含义是接受所有二进制 字符串中以0结尾的字符串，最后若输入序列状态为1说明是以‘0’结尾。==注意== ：同一个pattern可能会对应多个DFA，通常情况下取状态数目 最小的有限状态机。

```mermaid
graph LR;
  s1((0))
  s2((1))
  s1-->|1|s1
  s2-->|0|s2
  s1-->|0|s2
  s2-->|1|s1
  input-->s1
  style s2 fill:#FF3A5E, stroke-width:4px;
  style input fill:#f9f,stroke:#333,stroke-width:2px;
```

###2、有限不确定状态机NFA

与DFA不同的是NFA满足以下特性：

- null (or $ε$ ) move是允许的，再不输入符号的情况下也可以改变状态；
- 对于一个特定输入自动机能够改变任意数量个状态；

然而即使有上述两个 特性，NFA与DFA相比，没有什么优势，实际上两者功能是等价的。NFA的数学表达式与DFA的不同是在状态转换函数上$ δ$,有下图状态转换函数可以知道，对于任意 输入（包括 null or &epsilon）NFA能够进行任意数目的状态转换。

```
Q: Finite set of states
∑: set of input symbols
q: Initial state
F: set of final states
δ: transition function
δ：Q X (∑ U ?) --> 2 ^ Q
```

例如：状态机的含义是接受所有二进制 字符串中以0结尾的字符串，，最后若输入序列状态为1说明是以‘0’结尾，$∑={0, 1}?$的NFA如下图 所示

```mermaid
graph LR;
  s1((0))
  s2((1*))
  s1-->|0,1|s1
  s1-->|0|s2
  input-->s1
  style input fill:#f9f,stroke:#333,stroke-width:2px;
```

https://www.geeksforgeeks.org/c-program-to-simulate-nondeterministic-finite-automata-nfa/

### 总结

1. 每一个DFA都是一个NFA，反之不成立；
2. DFA和NFA的功能是 一样的，每一个NFA都可以转换为DFA；
3. 在 DFA和NFA中最终的状态数可能有多个；
4. NFA更多是理论上 的概念；
5. DFA用于编辑器中的词法分析；

#自动机理论

有限状态机（Finite State Machine  or Finite Automata）

| 构成元素                                     | 符号                                       | 说明                |
| ---------------------------------------- | ---------------------------------------- | ----------------- |
| Finite  set  of states                   | $Q=\{q_0, q_1, q_2, ..., q_k\}$          | 包括起始状态、中间状态 、终止状态 |
| A  start state                           | $q_0$                                    | 状态机的初始状态          |
| A set of accepting(final)  states        | $F=\{q_{i1},  q_{i1},  ..., q_{ir}\}$    | 接受（终止）状态，可以是一个或多个 |
| A finite alphabet                        | $\sum$                                   | 有限个输入序列           |
| State transition instructions(functions) | $\sigma : Q \times \sum  \longrightarrow Q \\ \sigma(q_i, a) = q_j$ | 状态转换函数            |

**自动机的工作方式**：有限状态机M每一次从输入序列Q中读取（从左往右）一个字符，M从状态$q_0$ 开始判断， 假设当前自动机M的状态为$q_i$读取序列中的字符为a，若状态$\sigma(q_i, a)$没有定义(undefined)，则自动机CRASH，否则自动机M移动到状态$\sigma(q_i, a)$ 。  

自动的数学表达式为 $M = (Q, \sum, q_0,  F, \sigma)$  根据输入序列x不同，自动机返回三种结果：

- 若读入字符串x后M的最终状态在F中，则M ==accepts== 字符串x；
- 若读入字符串x后M的最终状态不在F中，则M ==rejects== 字符串x；
- 若读入字符串x后M `crash（出现未定义状态）`，则M ==crashes== x；

所有被自动机M ==accepts==的集合为 $L_M =   \{ M \in \sum^{\star} | M \qquad accepts \qquad  x\}$ ，其中$\sum^k  \equiv $ All length k   strings over  the alphabet $\sum \qquad \sum^{\star} = \sum^{0}  \cup  \sum^{1} \cup ...$ 

例子：

```mermaid
graph LR
  note1[红色边线表示初始状态, 带笑脸的表示accept状态]
  CASE1(图1状态机含: L = $a,b$* = all finite strings of a and b)
  style note1 fill:#23AAFF
  A((*_*))
  style A fill:#FFFF00, stroke:#FF0000,stroke-width:2px; 
  A -->|a, b|A
  
  note2[白色边线表示中间状态]
  CASE2(图2状态机含义: L = all even length strings of a and b)
  style note2 fill:#23AAFF
  B((*_*))
  style B fill:#FFFF00, stroke:#FF0000,stroke-width:2px;
  C((MS))
  style C fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  B -->|a, b|C
  C -->|a, b|B
  
  CASE3(图3状态机含义: L = all strings in #a,b#* that contain at least one a)
  E((IS))
  style E fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  F((*_*))
  style F fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  E -->|b|E
  E -->|a|F
  F -->|a, b|F
  
  CASE4(图4状态机含义: L = strings with an odd numern of b and any number of a)
  G((IS))
  style G fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  H((*_*))
  style H fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  G -->|a|G
  G -->|b|H
  H -->|a|H
  H -->|b|G
  
  CASE5(图5状态机含义: L = any string ending with ab)
  I((IS))
  style I fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  J((*_*))
  style J fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  I -->|a|I
  I -->|b|J
  J -->|a|I
  J -->|b|J
  
  CASE6(图6状态机含义: L = any string with at least two a)
  K((IS))
  style K fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  L((MS))
  style L fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  M((*_*))
  style M fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  K -->|b|K
  K -->|a|L
  L -->|b|L
  L -->|a|M
  M -->|a, b|M
  
  CASE7(图7状态机含义: L = any string with a and b)
  N((IS))
  style N fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  O1((MS))
  style O1 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  O2((MS))
  style O2 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  P((*_*))
  style P fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  N -->|a|O1
  N -->|b|O2
  O1 -->|a|O1
  O2 -->|b|O2
  O1 -->|b|P
  O2 -->|a|P
  P -->|a, b|P
  
  CASE8(图8状态机含义: L = strings with an  even number of ab pairs)
  Q((*_*))
  style Q fill:#FFFF00, stroke:#FF0000,stroke-width:2px;
  
  S((*_*))
  style S fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  R1((MS))
  style R1 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  R2((MS))
  style R2 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;

  Q -->|b|Q
  Q -->|a|S
  S -->|b|R2
  R2 -->|a|R1
  R1 -->|b|Q
  S -->|a|S
  R1 -->|b|R1
  R2 -->|b|R2

```

##grep问题 

输入为文本T长度为t，查找模式为P长度为p， 问题是判断在文本T中是否存在模式P？Naive解决 方法的时间复杂度为O(t*p)。采用自动机可以解决这个问题 ，构建一个状态机M能够==accepts== 任何以P为连续子串(consecutive substring)的字符序列；然后将文本T喂给自动机M即可得到结果。时间复杂度为：O(t   comparisons + time to build M)。构建自动状态机的方法有： 

- Knuth
- Morris
- Pratt

例子： 构建包含模式P='ababb'的自动状态机

> Invariant:   s为当前状态，只有输入在当前状态中的最长后缀子串为'ababb'的前缀时，状态s才是==acceptable== 

```mermaid
graph LR
  A((IS))
  style A fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  B1((MS))
  style B1 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  B2((MS))
  style B2 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  B3((MS))
  style B3 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  B4((MS))
  style B4 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  C((*_*))
  style C fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  A -->|a|B1
  B1 -->|b|B2
  B2  -->|a|B3
  B3  -->|b|B4
  B4  -->|b|C
  A -->|b|A
  B1 -->|a|B1
  C -->|a, b|C
  B2 -->|b|A
  B3  -->|a|B1
  B4 -->|a|B3
  
  
```

##Regular Language[^1]

> Definition
>
> **Language**  : 对于任意序列L，满足$L  \subseteq \sum^*$ 则称L为Language，L为字符串的集合，称为Language是因为历史原因。
>
> **Regular  Language**  : 若 $L  \subseteq \sum^*$   ，若存在 一个有限自动机能够==accepts== L中的所有字符串，则 称L为正则语言。例如：$\sum^*$ 、包含`ababb`的所有字符串

**Theorem： Any finite  language  is regular**

证明：对于L中所有字符串构建一个有限自动状态机 ，例如：对于语言L={a, bcd, ac, bb}中的每一个单词构建一个状态机，那么这个状态机是有限状态机，也就知道这个语言是Regular。

```mermaid
graph LR
   A((IS))
  style A fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  B1((MS))
  style B1 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  B2((MS))
  style B2 fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  C1((*_*))
  style C1 fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  C2((*_*))
  style C2 fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  C3((*_*))
  style C3 fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  C4((*_*))
  style C4 fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  A -->|a|C1
  A -->|b|B1
  C1 -->|c|C2
  B1 -->|c|B2
  B1 -->|b|C3
  B2 -->|d|C4
```

$$Theorem： \qquad a^nb^n \qquad is \qquad  not \qquad  regular \\ Proof： \qquad  假设\exists M 有k个状态，accepts给定Language={a^nb^n}  \\ \qquad  \qquad 对于任意i， 满足0 \le i \le k，令S_i为自动机M在读取a^i后的状态， 则\exists i, j \le k, s.t. S_i=S_j, i\ne j , \\ \qquad  \qquad 使得自动机对a^ib^i 与 a^jb^j的效果是一样的，但是合法的自动机M应该接受a^ib^i而拒绝a^jb^j的。假设不成立，即不存在有限自动机使得a^nb^n是Regular的。 $$

**并不是所有的Language都是Regular的**

例如1：$L = a^nb^n = \{\varepsilon, ab, aabb, aaabbb, ...\}$ ，含义是a后面跟的b的数目要与a的数目相同。没有有限状态机能够==accepts== 这个Language。`因为没有有限自动机有足够多的状态计算a出现的次数` 。

例如2：L = {所有ab出现次数与`ba`出现次数相同的字符串}，L也不是Regular，因为有限自动机没有足够多的状态记录ab出现的次数。



>MORAL: 
>
>1. Finite automata can’t count.
>2. There is a unique smallest automaton for any regular language.



[^1]: FLAC课程，Formal Languages, Automata, and Computation 











