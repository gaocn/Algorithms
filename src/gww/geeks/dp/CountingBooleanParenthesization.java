package gww.geeks.dp;

public class CountingBooleanParenthesization {

    /*
        Counting Boolean Parenthesizations
        You are given a boolean expression consisting of a string of symbols 'true' 'false' 'or' 'and' 'xor'. Count the
         number of ways to parenthesize the expression such that it will evaluate to true. For example, there are 2 ways
         to parenthesize 'true and false xor true' such that it evaluates to true.

         Input: Boolean Expressions
         Goal: Count # ways to parenthesize expression such that it evaluates to true,.
             T F T F
            |_______|

         T(i, j): # ways to parenthesize symbols i..j such that this subexpression evaluates to true.
         T(2, 4) = 0
         F(i, j): similar to above only # ways to make subexpression i..j false
         Base Cases: T(i, i) F(i, i)

         How to calculate T(i, j), F(i, j)?
                i       k         j
                |_|_|_|_|_|_|_|_|_|

                      { T(i, k) * T(k+1, j)                                'AND'
         T(i, j) = sum{ Total(i, k) * Total(k+1, j) - F(i, k) * F(k+1, j)  'OR'
                      { T(i, k)F(k+1, j) + F(i, k) * T(k+1, j)             'XOR'

         k属于区间[i, j-1], Total(i, k) = T(i, k) + F(i, k)

                      { Total(i, k) * Total(k+1, j) - T(i, k) * T(k+1, j)   'AND'
         F(i, j) = sum{ F(i, k) * F(k+1, j)                                 'OR'
                      { T(i, k)T(k+1, j) + F(i, k) * F(k+1, j)              'XOR'

     */

}
