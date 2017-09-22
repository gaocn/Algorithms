package gww.geeks.graph.tarjan;


/*

割点（Articulation Points、Cut Vertices）
   在无向连通图中，将该顶点和经过该点的边删除后，图变为了非连通的，那么就称该点为割点或关节点。
   在非无向连通图中，若删除该顶点和经过该点的边后导致该图的连通分量个数增加，就称该点为割点。
   割点用于体现一个连通网络的脆弱程度，对于设计可靠性网络很有用。例如：单点故障会导致网络变为2个或多个不相连的分量。
【方法1】
   顺序删除每一个顶点，然后通过BFS或DFS检查删除该顶点后图是否是连通的来判断该点是不是割点。
   时间复杂度为：O(V*(V+E))
【方法2】
   利用DFS，在DFS搜索树中，若u是v的父亲，则顶点v是通过u发现的。在DFS搜索树中，顶点u是割点满足以下两个条件：
      1). u是DFS搜索树的根节点，且u至少有两个孩子节点；
      2). u不是DFS搜索树的根节点，对于u的孩子节点v来说，以v为根的子树中不存在一条<back edge>指向u的任意一个祖先
   时间复杂度为：O(V+E)
   注意：DFS搜索树的叶节点绝对不可能是图的关节点。

  对于上面两个情况，应该如何实现
   case 1:
      维护一个parent[]数组保存每一个顶点的父亲顶点，对于每一个顶点计算其孩子顶点数，若parent[u]=NIL且u有至少两个孩子顶点，则u为割点；
   case 2:
      维护一个disc[]数组，disc[i]保存顶点i在DFS的访问次序
      维护一个low[]数组，low[i]保存以i顶点为根的子树所能访问到的DFS搜索次序最小的顶点
      对于任意一个顶点u，我需要找到以u为根的子树所能够访问的DFS搜索次序最小的顶点，
       -- 若存在一个子节点v, 满足low[v] >= disc[u], 则删除u就会导致v称为独立的连通分量，因此u是割点

 */


import java.util.Arrays;

public class ArticulationPoints {

    final static int NIL = -1;
    final static int INF = Integer.MAX_VALUE;
    static int timer = 0;

    public void articulationPoints(Graph g) {
        int[] parent = new int[g.V];
        int[] disc = new int[g.V];
        int[] low  = new int[g.V];
        boolean[] visited = new boolean[g.V]; // 是否有必要，可否用dist[u] = -1代替
        boolean[] isAPs = new boolean[g.V]; // isAP[u] 用于表明u是不是割点

        Arrays.fill(parent, NIL);
        Arrays.fill(disc, NIL);
        Arrays.fill(low, NIL);
        Arrays.fill(visited, false);

        timer = 0;
        for(int u = 0; u < g.V; u++) {
            if(disc[u] == -1)
                dfs(g, u, parent, disc, low, isAPs);
        }

        //print all articulation points
        for (int u = 0; u < g.V; u++) {
            if(isAPs[u])
                System.out.println(u + "is articulation point");
        }
    }

    public void dfs(Graph g, int u, int[] parent, int[] disc, int[] low, boolean[] isAPs) {

        low[u] = disc[u] = ++timer;

        int children = 0; // store children number of u
        for (int v = 0; v < g.V && g.edges[u][v] != INF ; v++) {
            if(disc[v] != -1) { // not visited
                ++children;
                parent[v] = u;
                dfs(g, v, parent, disc, low, isAPs);

                // tree edge update low
                low[u] = Math.min(low[u], low[v]);

                /** u is articulation point in following cases */

                //case 1: u is root of DFS tree and has two or more children
                if(parent[u] == NIL && children > 1)
                    isAPs[u] = true;

                //case 2: u is not root and low value of one of its child is more then discovery value of u
                if(parent[u] != NIL && low[v] >= disc[u])
                    isAPs[u] = true;

            } else if(v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

}
