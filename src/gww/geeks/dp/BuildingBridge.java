package gww.geeks.dp;

public class BuildingBridge {
    /*
      Building Bridges
        Consider a 2-D map with a horizontal river across passing through its center. There are n cities on the southern
        bank with x-coordinates a(1), a(2), ... a(n) and n cities on the northern bank with x-coordinates b(1),b(2)...b(n)
        You want to connect as many north-south pairs of cities as possible with bridges such that no two bridges cross.
        When connecting cities, "you can only connect city a(i) on the northern bank to city b(i) on the southern bank."

        类似LIS，通过Reduction约简
        令x(i)为 the index on northern bank of corresponding city to the ith city on the southern bank
        例如：
         N    8 1 4 3 5 2 6 7
             -----------------
               <---River--->
             -----------------
         S    1 2 3 4 5 6 7 8

         有上图可知：x(1) = 3
        计算出x(i)的需要一次排序，时间O(NlogN)

        问题转换为：Find longest increasing subsequence of x(1) x(2) x(3)...x(n)
        我们有两种算法：O(N^2) O(NlogN)

        1. Sort the north-south pairs on the basis of increasing order of south x-coordinates.
        2. If two south x-coordinates are same, then sort on the basis of increasing order of north x-coordinates.
        3. Now find the Longest Increasing Subsequence of the north x-coordinates.
        4. One thing to note that in the increasing subsequence a value can be greater as well as can be equal to its previous value.

        //TEST: {6, 2}, {4, 3}, {2, 6}, {1, 5}
        6 4 2 1
        2 3 6 5
     */



}
