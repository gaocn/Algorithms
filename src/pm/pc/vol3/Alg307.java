package pm.pc.vol3;

/**
 * Created by 高文文 on 2016/12/28.
 * Problem：110307	Doublets
 *
 *  Naive:  adjacency matrix
 *     1. construct a tree start from directory, the children of node is its doublets(differ in exactly one letter)
 *     2. BFS, search tree from startWord to endWord
 *  note: use hashmap to store vertex-word mapping to fasten runtime
 *  无权图的最短路问题，可以用 BFS 来实现。由于最长的单词不超过 16 个字母，故可将单词按长度分为 16 种类别，
 *  两个不同长度的单词不会构成 Doublets，这样每种类别平均单词个数大概为 25143/16，同时只在需要时才构建相应长度单词的图表示。
 *
 *
 *
 */
public class Alg307 {



    public Alg307() {

    }


    public static void main(String[] args) {

        Alg307 alg = new Alg307();


    }
}

/* more sophisticated solution
int main()
{
    string wd;
    while ( getline( cin, wd ), wd != "" )
    {
        pos[ wd ] = dict.size();
        dict.push_back( wd );
    }

    int f = 0;
    string s, t;
    while ( cin >> s >> t )
    {
        if ( f++ ) puts("");
        if ( pos.find( s ) == pos.end() )
        {
            puts("No solution.");
            continue;
        }
        if ( pos.find( t ) == pos.end() )
        {
            puts("No solution.");
            continue;
        }
        memset( next, -1, sizeof next );
        int ids = pos[ s ];
        int idt = pos[ t ];
        next[ idt ] = -2;
        queue< int > q;
        q.push( idt );
        while ( !q.empty() )   //BFS
        {
            int u = q.front(); q.pop();
            string s = dict[ u ];
            for (int i=0; i<s.length(); i++)
                for (int c='a'; c<='z'; c++)
                {
                    string t = s;
                    t[ i ] = c;
                    if ( pos.find( t ) != pos.end() )
                    {
                        int v = pos[ t ];
                        if ( next[ v ] == -1 )
                        {
                            next[ v ] = u;
                            q.push( v );
                        }
                    }
                }
        }
        if ( next[ids] == -1 ) puts("No solution.");
        else
        {
            for (int i=ids; i!=-2; i=next[i]) cout << dict[ i ] << endl;
        }
    }

    return 0;
}
 */