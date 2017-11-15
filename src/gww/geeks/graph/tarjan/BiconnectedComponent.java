package gww.geeks.graph.tarjan;


/*

二分图（Biconnected graph)
   设G=(V,E)是一个无向图，如果顶点V可分割为两个互不相交的子集(A,B)[two vertex-disjoint]，并且图中的每条边（i，j）所关联的两个顶点i和j分别属于这两个不同
 的顶点集(i in A,j in B)，则称图G为一个二分图。


【方法1】 DFS深度优先搜索


【方法2】二分图满足图是连通的且不存在割点
    1. 首先判断图中是否含有割点，若没有则图是二分的
    2. 最后检查图中所有的点是否均被访问过，若没有则图不是连通的。

 */


import java.util.Arrays;

public class BiconnectedComponent {

    final static int NIL = -1;
    final static int INF = Integer.MAX_VALUE;

    static int timer = 0;

    public boolean isBiconnectedGraph(Graph g) {
        boolean[] visited = new boolean[g.V];
        int[] disc = new int[g.V];
        int[] low  = new int[g.V];
        int[] parent = new int[g.V];

        Arrays.fill(visited, false);
        Arrays.fill(disc, NIL);
        Arrays.fill(low, NIL);
        Arrays.fill(parent, NIL);

        timer = 0;
        if(hasAP(g, 0, low, disc, parent, visited))
            return false;

        for (boolean checked : visited) {
            if(!checked) return false;
        }
        return true;
    }

    public boolean hasAP(Graph g, int u, int[] low, int[] disc, int[] parent, boolean[] visited) {
        low[u] = disc[u] = ++timer;
        visited[u] = true;

        int children = 0;
        for(int v = 0; v < g.V && g.edges[u][v] != INF; v++) {
            if(visited[v] == false) {
                parent[v] = u;
                children++;

                if(hasAP(g, v, low, disc, parent, visited))
                    return true;
                // check and update low[u]
                low[u] = Math.min(low[u], low[v]);

                //case 1 u is root of DFS tree and has more than 1 children
                if(parent[u] == NIL && children > 1)
                    return true;
                //case 2 u is not root but has at least one children then low[children] >= disc[u]
                if(parent[u] != NIL && low[v] >= disc[u])
                    return true;

            } else if(v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
        return false;
    }

}
