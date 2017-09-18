package pm.pc.vol10;



/*

My little sister had a beautiful necklace made of colorful beads. Two successive beads in the necklace
shared a common color at their meeting point. The fgure below shows a segment of the necklace:
But, alas! One day, the necklace was torn and the beads were all scattered over the ﬂoor. My sister
did her best to recollect all the beads from the ﬂoor, but she is not sure whether she was able to collect
all of them. Now, she has come to me for help. She wants to know whether it is possible to make a
necklace using all the beads she has in the same way her original necklace was made and if so in which
order the bids must be put.
Please help me write a program to solve the problem.

Input
The input contains T test cases. The frst line of the input contains the integer T.
The frst line of each test case contains an integer N (5 ≤ N ≤ 1000) giving the number of beads
my sister was able to collect. Each of the next N lines contains two integers describing the colors of a
bead. Colors are represented by integers ranging from 1 to 50.

Output
For each test case in the input frst output the test case number as shown in the sample output. Then
if you apprehend that some beads may be lost just print the sentence “some beads may be lost”
on a line by itself. Otherwise, print N lines with a single bead description on each line. Each bead
description consists of two integers giving the colors of its two ends. For 1 ≤ i ≤ N 1, the second
integer on line i must be the same as the frst integer on line i + 1. Additionally, the second integer on
line N must be equal to the frst integer on line 1. Since there are many solutions, any one of them is
acceptable.
Print a blank line between two successive test cases.

Sample Input
25
1 2
2 3
3 4
4 5
5 6
5
2 1
2 2
3 4
3 1
2 4

Sample Output
Case #1
some beads may be lost
Case #2
2 1
1 3
3 4
4 2
2 2

 若把珠子两端的颜色看作是顶点，珠子本身看成是边，则该题可以建模成欧拉回路问题。需要判断顶点的度
 是否为都为偶数，若都为偶数才可能包含欧拉回路，然后判断图是否连通，若这两个条件都满足，则肯定存在
 欧拉回路，找出即可。使用宽度或深度优先遍历确定图的连通性，亦可考虑使用不相交集合并/查数据结构
 （并查集）来实现判断。由于本题有较多的输入输出，在输出换行时，使用 “\n" 而不是 endl 可以减少
 一些运行时间（使用 endl 时运行时间为 1.668s，因为 endl 在输出换行符后都要立即刷新输出缓存，
 故耗费较多时间）


 【欧拉回路】如果存在一条回路经过图G每条边有且仅有一次，称这条回路为欧拉回路
 【判断欧拉回路的方法】
    有向图：图连通，且所有的顶点出度=入度
    无向图：图联通，且所有顶点都是偶度数

 【哈密顿回路】经过图中所有顶点一次且仅一次的回路

 */


public class TheNecklace {



}
