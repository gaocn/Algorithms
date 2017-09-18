package pm.pc.vol10;



/*
10278 Fire Station
A city is served by a number of fire stations. Some residents have complained that the distance from
their houses to the nearest station is too far, so a new station is to be built. You are to choose the
location of the fire station so as to reduce the distance to the nearest station from the houses of the
disgruntled residents.
The city has up to 500 intersections, connected by road segments of various lengths. No more than
20 road segments intersect at a given intersection. The location of houses and fire stations alike are
considered to be at intersections (the travel distance from the intersection to the actual building can be
discounted). Furthermore, we assume that there is at least one house associated with every intersection.
There may be more than one fire station per intersection.

Input

The input begins with a single positive integer on a line by itself indicating the number of the cases
following, each of them as described below. This line is followed by a blank line, and there is also a
blank line between two consecutive inputs.
The first line of input contains two positive integers: f, the number of existing fre stations (f ≤ 100)
and i, the number of intersections (i ≤ 500). The intersections are numbered from 1 to i consecutively.
f lines follow; each contains the intersection number at which an existing fre station is found. A
number of lines follow, each containing three positive integers: the number of an intersection, the
number of a different intersection, and the length of the road segment connecting the intersections. All
road segments are two-way (at least as far as fre engines are concerned), and there will exist a route
between any pair of intersections.

Output
For each test case, the output must follow the description below. The outputs of two consecutive cases
will be separated by a blank line.
You are to output a single integer: the lowest intersection number at which a new fire station should
be built so as to minimize the maximum distance from any intersection to the nearest fire station.

Sample Input
1
1 6
2
1 2 10
2 3 10
3 4 10
4 5 10
5 6 10
6 1 10
Sample Output
5


 建模：交点作为vertex，segment作为边，距离作为边的权值，因为是双向路，可以看做是无向图；
 问题：求无向有权图中一个顶点距离其他顶点距离总和最近且顶点编号最小？
【Floyd算法】

// 如何求出路口到最近消防站的距离？回忆学习过的图算法，可以通过求每对结点之间的最短路长度来得到，即
// Floyd 算法。通过 Floyd 算法，可以方便的从图的边权矩阵直接算出距离矩阵，然后通过比较路口到各个
// 消防站的距离得到至最近消防站的距离，然后再比较各个路口至最近消防站的距离得到路口离消防站的最大距
// 离，由于新消防站只能位于所有路口中的一个，那么可以从编号大的路口开始枚举新消防站所处的路口，重新计
// 算路口离消防站的最大距离，在此过程中，取能得到最小最大距离且编号最小的路口编号即为新消防站的位置。


 */



public class FireStation {
}
