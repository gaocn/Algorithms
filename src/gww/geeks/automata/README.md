## ����״̬����FSM��Finite State Machine��

����״̬����һ���������ж�����Ϊ��ģ�Ĺ��ߣ�����������������������������������������״̬���У��Լ������Ӧ�������ĸ����¼����������ѧ�У�����״̬�����㷺Ӧ���ڽ�ģӦ����Ϊ��������̡�������������Э��ͼ��������Ե��о�����ͼ��������TCPЭ��״̬����

![1133568-7a4faa75c260d9b3](D:\EclipseWPFroWin10\GeeksForGeeks\src\gww\geeks\automata\1133568-7a4faa75c260d9b3.jpeg)

ͨ�������ڱ�����õ���if/else��switch/case�������ں�����״̬���򽻵����ڴ���һЩҵ���߼��Ƚϸ��ӵ�����ʱ�����Կ����Ƿ������һ������״̬���������������һЩҵ��ģ�ͳ����һ������״̬����ô�����߼��ͻ�ǳ��塢���ṹ������  

### 1��״̬����Ҫ��

״̬�����Թ���Ϊ4��Ҫ�أ���̬����������������̬�����С���̬���͡����������򣬡��������͡���̬��Ϊ����

- ��̬����ǰ������״̬��
- �������ֳ�Ϊ�¼�����һ�����������㣬���ᴥ��һ������������ִ��һ��״̬��Ǩ�ơ�
- �����������������ִ�еĶ���������ִ����ɺ󣬿���Ǩ�Ƶ��µ�״̬��Ҳ������Ȼ����ԭ״̬���������Ǳ���ģ��������������Ҳ���Բ�ִ�ж���ֱ��Ǩ�Ƶ���״̬��
- ��̬�������������ҪǨ����״̬����̬���������̬�ģ���̬һ��������ͱ�Ϊ��̬��


###2������ ����FA��Finite Automata�����ַ�����

���⣺��������ģʽ$pat =  "ACACAGA"$��s��ι���״̬����

����ģʽ����ΪM=7����FA��״̬��ΪM+1 = 8������״̬���Ĺؼ��Ƕ��ڵ�ǰ״̬�Լ��κ������ַ���εõ���һ��״̬������������ַ�Ϊx����ǰ״̬Ϊk����ô��һ��״̬��Ӧ���ַ���Ϊ$P  = "pat[0..k-1]x"?$�����ַ���Ϊ���� ģʽ��ǰk���ַ��������ַ�x���ɣ���==�����ǣ��ҵ�$P?$���ǰ׺ ��׺�ĳ���d���������d������һ��Ҫ��ת��״̬== ������ ��$k = 5, x = 'C'?$����$P = pat[0..4]x = "ACACAC"?$�������ǰ׺��׺�ַ���Ϊ"ACAC"������d=4����ô����$k = 5, x = 'C'?$ ����һ��״̬��4����ͼ�Ƕ���ģʽ pat="ACACAGA"��״̬����ת����:

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

| ��̬/���� |      |      |      |      |
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

ʵ������״̬����򵥵ķ����Ƕ������� ���� ״̬k�������ַ�x���õ��ַ���P = ��pat[0..k-1]x��������P�����п��ܳ�ΪP��׺��ǰ׺�������ҳ����ǰ׺���ȼ�Ϊ��һ��״̬���㷨��ʱ�临�Ӷ�Ϊ$O(m^3*NO\_OF\_CHARS)$���� mΪ����ģʽ�ĳ��ȣ�NO_OF_CHARSΪ����ģʽ�� �ı������п��ܵ��ַ�������ʹ��KMP�� �����ǰ׺��׺���ȵĵ��㷨���Խ��㷨���ӶȽ��͵�$O(m * NO\_OF\_CHARS)$

``` java
/**
1. ���ʹ��Longest Prefix Suffix���״̬ת�Ʒ��̣�
TF[i][x]�������Ӵ�'pattern[0..i-1]x'�е��ǰ׺��׺���ȣ���lps��ŵ�ǰ�ǰ׺��׺���ȵ���������ǰλ��Ϊi
  ��ʼ����lps = 0��i = 0, ��ʱ'pattern[0..i-1]x'ֻ��һ���ַ����������ǰ׺��׺ TF[i][pat[i]] = 1
  �� i = 1ʱ���Ӵ�Ϊ'pattern[0]x'����'pattern[0]'���ǰ׺��׺���ȵ���lps����TF[1][x] = TF[lps][x]
  �� i = 2ʱ���Ӵ�Ϊ'pattern[0]pattern[1]x'����'pattern[0]pattern[1]'���ǰ׺��׺���ȵ���lps������TF[2][x] = TF[lps][x]
  ...
�����Ͽ�֪����Ҫ��ǰ״̬Ϊi��TF[i]��ֵ����Ҫ���'pattern[0..i-1]'�Ӵ����ǰ׺��׺������״̬��λ��lps�����lps��Ϳ���ֱ�ӽ���״̬�ĸ��ơ�

2. �����lps�� 
��֪lps�ĳ�ʼ״̬Ϊ0��ÿһ�θ���״̬����Ҫ������һ��lps��ֵ���赱ǰ״̬Ϊs������lps = TF[lps][pattern[s]] s < M
*/
private void buildTF() {
  int lps = 0;

  //1. ��ʼ��
  for (int i = 0; i < NO_OF_CHARS; i++)TF[0][i] = 0;
  TF[0][pattern.charAt(0)] = 1;

  for (int s = 1; s <= NUM_STATES; s++) {

    //�����ǰ׺��׺����
    System.arraycopy(TF[lps], 0,  TF[s], 0, NO_OF_CHARS);
    //for (int i = 0; i <  NO_OF_CHARS; i++) {
    //	TF[s][i] = TF[lps][i];
    //}

    //�ҵ���һ�θ���ʱ��lps���ǰ׺��׺λ��
    if (s < NUM_STATES)  {
      TF[s][pattern.charAt(s)] = s + 1;
      lps = TF[lps][pattern.charAt(s)];
    }
  }

}
```


## WiKi
1. �����ĳ������������������һ�֡�״̬��������������֡��������͡�״̬��������̬���ǲ��ȶ��ģ���ʹû��������������������һ��ִ����Ͼͽ����ˣ�����״̬������ȶ������û���ⲿ�����Ĵ�����һ��״̬�ͻ�һֱ������ȥ��

2. ״̬����ʱ©��һЩ״̬��������ת�߼�������������ά��һ��״̬�� �ͷǳ��б�Ҫ���ӱ��п���ֱ�ۿ�����Щ״̬���ڣ���ת·����ʲô�����״̬�����ڣ�����ж�Ӧ�ĵ�Ԫ���Ϊ�ա�д�ô������Ը������ű����review��QAҲ���� ������������ԡ�

3. ״̬��ת�߼�������д����״̬���ж��¼����¼����ж�״̬��һ��ҵ�񳡾���˵��״̬��������ȷ�����ҽ��٣���ͬ״̬����Ҫ������¼�Ҳ��һ�������������¼�������Ƚ϶࣬���õڶ��ֵ������ǿ���ʹ��switch/case������Ϊ�����Ĵ����߼���


## FA(Finite Automata)

FA�Ļ����������£����޻������ݱ��ʽΪ��{Q, $��$,  q, F, $ ��$}��FA��Ϊ���ࣺDFA(Deterministic Finite Automata)��NFA(Nondeterministic Finite Automata)

```
Q: Finite set of states
��: set of input symbols
q_0: Initial state
F: set of final states
��: transition function
```

### 1������ȷ��״̬��DFA

��DFA�У���ĳһ�ض������ַ���״̬�� ֻ�ܽ���һ��״̬�ĸı䡣״̬ת�ƺ���F��������ÿһ��״̬ �� ������ĳһ���� ��״̬���ת�ơ�ע����ǣ���DFA�У����ƶ��ǲ�������ڵģ�����Ϊ���ǲ��ܸı�״̬�ġ����磺��ͼ ��$��={0, 1}$ ������״̬������״̬���ĺ����ǽ������ж����� �ַ�������0��β���ַ������������������״̬Ϊ1˵�����ԡ�0����β��==ע��== ��ͬһ��pattern���ܻ��Ӧ���DFA��ͨ�������ȡ״̬��Ŀ ��С������״̬����

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

###2�����޲�ȷ��״̬��NFA

��DFA��ͬ����NFA�����������ԣ�

- null (or $��$ ) move������ģ��ٲ�������ŵ������Ҳ���Ըı�״̬��
- ����һ���ض������Զ����ܹ��ı�����������״̬��

Ȼ����ʹ���������� ���ԣ�NFA��DFA��ȣ�û��ʲô���ƣ�ʵ�������߹����ǵȼ۵ġ�NFA����ѧ���ʽ��DFA�Ĳ�ͬ����״̬ת��������$ ��$,����ͼ״̬ת����������֪������������ ���루���� null or &epsilon��NFA�ܹ�����������Ŀ��״̬ת����

```
Q: Finite set of states
��: set of input symbols
q: Initial state
F: set of final states
��: transition function
�ģ�Q X (�� U ?) --> 2 ^ Q
```

���磺״̬���ĺ����ǽ������ж����� �ַ�������0��β���ַ��������������������״̬Ϊ1˵�����ԡ�0����β��$��={0, 1}?$��NFA����ͼ ��ʾ

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

### �ܽ�

1. ÿһ��DFA����һ��NFA����֮��������
2. DFA��NFA�Ĺ����� һ���ģ�ÿһ��NFA������ת��ΪDFA��
3. �� DFA��NFA�����յ�״̬�������ж����
4. NFA������������ �ĸ��
5. DFA���ڱ༭���еĴʷ�������

#�Զ�������

����״̬����Finite State Machine  or Finite Automata��

| ����Ԫ��                                     | ����                                       | ˵��                |
| ---------------------------------------- | ---------------------------------------- | ----------------- |
| Finite  set  of states                   | $Q=\{q_0, q_1, q_2, ..., q_k\}$          | ������ʼ״̬���м�״̬ ����ֹ״̬ |
| A  start state                           | $q_0$                                    | ״̬���ĳ�ʼ״̬          |
| A set of accepting(final)  states        | $F=\{q_{i1},  q_{i1},  ..., q_{ir}\}$    | ���ܣ���ֹ��״̬��������һ������ |
| A finite alphabet                        | $\sum$                                   | ���޸���������           |
| State transition instructions(functions) | $\sigma : Q \times \sum  \longrightarrow Q \\ \sigma(q_i, a) = q_j$ | ״̬ת������            |

**�Զ����Ĺ�����ʽ**������״̬��Mÿһ�δ���������Q�ж�ȡ���������ң�һ���ַ���M��״̬$q_0$ ��ʼ�жϣ� ���赱ǰ�Զ���M��״̬Ϊ$q_i$��ȡ�����е��ַ�Ϊa����״̬$\sigma(q_i, a)$û�ж���(undefined)�����Զ���CRASH�������Զ���M�ƶ���״̬$\sigma(q_i, a)$ ��  

�Զ�����ѧ���ʽΪ $M = (Q, \sum, q_0,  F, \sigma)$  ������������x��ͬ���Զ����������ֽ����

- �������ַ���x��M������״̬��F�У���M ==accepts== �ַ���x��
- �������ַ���x��M������״̬����F�У���M ==rejects== �ַ���x��
- �������ַ���x��M `crash������δ����״̬��`����M ==crashes== x��

���б��Զ���M ==accepts==�ļ���Ϊ $L_M =   \{ M \in \sum^{\star} | M \qquad accepts \qquad  x\}$ ������$\sum^k  \equiv $ All length k   strings over  the alphabet $\sum \qquad \sum^{\star} = \sum^{0}  \cup  \sum^{1} \cup ...$ 

���ӣ�

```mermaid
graph LR
  note1[��ɫ���߱�ʾ��ʼ״̬, ��Ц���ı�ʾaccept״̬]
  CASE1(ͼ1״̬����: L = $a,b$* = all finite strings of a and b)
  style note1 fill:#23AAFF
  A((*_*))
  style A fill:#FFFF00, stroke:#FF0000,stroke-width:2px; 
  A -->|a, b|A
  
  note2[��ɫ���߱�ʾ�м�״̬]
  CASE2(ͼ2״̬������: L = all even length strings of a and b)
  style note2 fill:#23AAFF
  B((*_*))
  style B fill:#FFFF00, stroke:#FF0000,stroke-width:2px;
  C((MS))
  style C fill:#FFAB34, stroke:#FFFFFF,stroke-width:4px;
  B -->|a, b|C
  C -->|a, b|B
  
  CASE3(ͼ3״̬������: L = all strings in #a,b#* that contain at least one a)
  E((IS))
  style E fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  F((*_*))
  style F fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  E -->|b|E
  E -->|a|F
  F -->|a, b|F
  
  CASE4(ͼ4״̬������: L = strings with an odd numern of b and any number of a)
  G((IS))
  style G fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  H((*_*))
  style H fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  G -->|a|G
  G -->|b|H
  H -->|a|H
  H -->|b|G
  
  CASE5(ͼ5״̬������: L = any string ending with ab)
  I((IS))
  style I fill:#FFAB34, stroke:#FF0000,stroke-width:2px;
  J((*_*))
  style J fill:#FFFF00, stroke:#FFA455,stroke-width:4px;
  I -->|a|I
  I -->|b|J
  J -->|a|I
  J -->|b|J
  
  CASE6(ͼ6״̬������: L = any string with at least two a)
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
  
  CASE7(ͼ7״̬������: L = any string with a and b)
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
  
  CASE8(ͼ8״̬������: L = strings with an  even number of ab pairs)
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

##grep���� 

����Ϊ�ı�T����Ϊt������ģʽΪP����Ϊp�� �������ж����ı�T���Ƿ����ģʽP��Naive��� ������ʱ�临�Ӷ�ΪO(t*p)�������Զ������Խ��������� ������һ��״̬��M�ܹ�==accepts== �κ���PΪ�����Ӵ�(consecutive substring)���ַ����У�Ȼ���ı�Tι���Զ���M���ɵõ������ʱ�临�Ӷ�Ϊ��O(t   comparisons + time to build M)�������Զ�״̬���ķ����У� 

- Knuth
- Morris
- Pratt

���ӣ� ��������ģʽP='ababb'���Զ�״̬��

> Invariant:   sΪ��ǰ״̬��ֻ�������ڵ�ǰ״̬�е����׺�Ӵ�Ϊ'ababb'��ǰ׺ʱ��״̬s����==acceptable== 

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
> **Language**  : ������������L������$L  \subseteq \sum^*$ ���LΪLanguage��LΪ�ַ����ļ��ϣ���ΪLanguage����Ϊ��ʷԭ��
>
> **Regular  Language**  : �� $L  \subseteq \sum^*$   �������� һ�������Զ����ܹ�==accepts== L�е������ַ������� ��LΪ�������ԡ����磺$\sum^*$ ������`ababb`�������ַ���

**Theorem�� Any finite  language  is regular**

֤��������L�������ַ�������һ�������Զ�״̬�� �����磺��������L={a, bcd, ac, bb}�е�ÿһ�����ʹ���һ��״̬������ô���״̬��������״̬����Ҳ��֪�����������Regular��

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

$$Theorem�� \qquad a^nb^n \qquad is \qquad  not \qquad  regular \\ Proof�� \qquad  ����\exists M ��k��״̬��accepts����Language={a^nb^n}  \\ \qquad  \qquad ��������i�� ����0 \le i \le k����S_iΪ�Զ���M�ڶ�ȡa^i���״̬�� ��\exists i, j \le k, s.t. S_i=S_j, i\ne j , \\ \qquad  \qquad ʹ���Զ�����a^ib^i �� a^jb^j��Ч����һ���ģ����ǺϷ����Զ���MӦ�ý���a^ib^i���ܾ�a^jb^j�ġ����費�������������������Զ���ʹ��a^nb^n��Regular�ġ� $$

**���������е�Language����Regular��**

����1��$L = a^nb^n = \{\varepsilon, ab, aabb, aaabbb, ...\}$ ��������a�������b����ĿҪ��a����Ŀ��ͬ��û������״̬���ܹ�==accepts== ���Language��`��Ϊû�������Զ������㹻���״̬����a���ֵĴ���` ��

����2��L = {����ab���ִ�����`ba`���ִ�����ͬ���ַ���}��LҲ����Regular����Ϊ�����Զ���û���㹻���״̬��¼ab���ֵĴ�����



>MORAL: 
>
>1. Finite automata can��t count.
>2. There is a unique smallest automaton for any regular language.



[^1]: FLAC�γ̣�Formal Languages, Automata, and Computation 











