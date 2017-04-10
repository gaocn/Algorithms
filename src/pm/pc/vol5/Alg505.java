package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110505	A multiplication game
 *
 *
 */
public class Alg505 {


    public static void main(String[] args) {

        Alg505 alg = new Alg505();
        alg.doMultiplicationGame(162);
        alg.doMultiplicationGame(17);
        alg.doMultiplicationGame(34012226);
    }

    public void doMultiplicationGame(int N) {
    }

}


/*
方法1： DP
map<int,bool> CanWin;
bool whoWins(long n, long p, bool Current)
{
    for (int i=2; i<=9; i++)
    {
        if (p*i >= n) return Current;

        if (CanWin.find(p*i) != CanWin.end())
        {
            if (CanWin[p*i]) return Current;
            continue;
        }
        CanWin[p*i] =  whoWins(n, p*i, (Current+1)%2) == Current;
        if (CanWin[p*i]) return Current;
    }

    return (Current+1)%2;
}
cout << ( (whoWins(n,1,0)) ? ("Ollie wins.") : ("Stan wins.")) << '\n';


方法2：找规律

Observe that the maximum number the first player can get on his first roll is 9. The max on his second roll is 9*2*9,
  because the second player can roll a 2. And similarly, on his third roll it is 9*2*9*2*9. The second player can get a
  max of 2*9 on his first, 2*9*2*9 on his second roll and on and on.Hence, it is clear that number range define which
  player wins. The range go like this
0-9 stan wins
9+1-9*2 ollie wins
9*2+1 – 9*2*9 stan wins
9*2*9+1 – 9*2*9*2 ollie wins
So we devise a way to check where the numbers fall and we’re done. I chose logs. We can divide the ranges into pairs,
  where each pair is bordered on both sides by a a power of 18. So we find which power of 18 it is using logs and then
  we find if it lies within that power of 18 multiplied by 9.
int main()
{
    long long int n;

    while(scanf("%lld",&n) == 1)
    {
         long long int lo;
         long double lg = logl(n)/logl(18);
        lo = floorl(lg);
        if (lg - lo < 0.00000000001) {cout << "Ollie wins.\n";continue;}
        if ( n/powl(18.0,lo) > 9.0 ) cout << "Ollie wins.\n";
        else cout << "Stan wins.\n";
    }
    return 0;
}

 */